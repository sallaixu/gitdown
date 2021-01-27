package down.sallai.ml.util;



import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class HttpsDownUtils {

    /**
     * @param fileUrl   https 远程路径
     * @param fileLocal 本地文件存放路径,需要注意的是这里是要传一个已经存在的文件，否则会报拒绝访问的的错误
     * @throws Exception
     */
    final static int THREAD_NUMBER= 3;
    static Thread[] threads;
    static String fileUrl;
    public static void downloadFile(String fileUrl, String fileLocal) throws Exception {

        HttpsDownUtils.fileUrl = fileUrl;
        SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
        sslcontext.init(null, new TrustManager[]{new X509TrustUtiil()}, new java.security.SecureRandom());
        URL url = new URL(fileUrl);
        HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslsession) {
                log.warn("Hostname is not matched for cert.");
                return true;
            }
        };


        HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
        HttpsURLConnection urlCon = (HttpsURLConnection) url.openConnection();
        urlCon.setConnectTimeout(20000);
        urlCon.setReadTimeout(20000);

        ThreadPoolExecutor threadPoolExecutor = MyThreadPool.getThreadPoolExecutor(); //获取线程池

        int code = urlCon.getResponseCode();
        if (code != HttpURLConnection.HTTP_OK) {
            throw new Exception("文件读取失败");
        }
        int contentLength = urlCon.getContentLength();

        int point = contentLength/THREAD_NUMBER+1;
        System.out.println("文件长度"+" 块大小"+point);
        RandomAccessFile file=new RandomAccessFile(fileLocal,"rw");
        //设置本地文件的大小
        file.setLength(contentLength);
        file.close();

        for (int i = 0; i < THREAD_NUMBER; i++) {
            System.out.println("分配线程任务");
            RandomAccessFile f = new RandomAccessFile(fileLocal,"rw");
            f.seek(i*point);
            MyRunable myRunable = new MyRunable(i*point,point,f);
            threadPoolExecutor.execute(myRunable);


        }

//        // 读文件流
//        DataInputStream in = new DataInputStream(urlCon.getInputStream());
//        DataOutputStream out = new DataOutputStream(new FileOutputStream(fileLocal));
//        byte[] buffer = new byte[2048];
//        int count = 0;
//        while ((count = in.read(buffer)) > 0) {
//            out.write(buffer, 0, count);
//        }
//        out.close();
//        in.close();

    }

    /**
     * X509Trust
     */
    static class X509TrustUtiil implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }




    static class MyRunable implements Runnable {
        //当前线程的下载位置
        private int startPos;
        //定义当前线程负责下载的文件大小
        private int currentPartSize;
        //当前线程需要下载的文件块
        private RandomAccessFile currentPart;
        //定义该线程已下载的字节数
        public int length;

        public MyRunable(int start,int currentPartSize,RandomAccessFile currentPart){
            this.startPos = start;
            this.currentPartSize = currentPartSize;
            this.currentPart = currentPart;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"执行任务,起点->"+startPos+"负责大小->"+currentPartSize);
            SSLContext sslcontext = null;
            try {
                sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
                sslcontext.init(null, new TrustManager[]{new HttpsDownUtils.X509TrustUtiil()}, new java.security.SecureRandom());
                URL url = new URL(fileUrl);
                HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslsession) {
                        log.warn("Hostname is not matched for cert.");
                        return true;
                    }
                };


                HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
                HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
                HttpsURLConnection urlCon = (HttpsURLConnection) url.openConnection();
                urlCon.setConnectTimeout(20000);
                urlCon.setReadTimeout(20000);

                ThreadPoolExecutor threadPoolExecutor = MyThreadPool.getThreadPoolExecutor(); //获取线程池

                int code = urlCon.getResponseCode();
                if (code != HttpURLConnection.HTTP_OK) {
                    throw new Exception("文件读取失败");
                }

                // 读文件流

                InputStream  in = urlCon.getInputStream();
                in.skip(startPos);
                byte[] buffer = new byte[1024];

                int count = 0;
                while (count<currentPartSize&&(count = in.read(buffer)) > 0) {
                    currentPart.write(buffer, 0, count);
                    this.length+=count;
                }
                currentPart.close();
                in.close();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName()+"执行完毕！");

        }
    }





}


