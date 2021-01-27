package down.sallai.ml.controller;


import down.sallai.ml.util.HttpsDownUtils;
import down.sallai.ml.util.R;
import down.sallai.ml.util.VerityCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@RestController
public class Download {
    @Value("${mydown.fileLocal}")
    String fileLocal;



    @PostMapping("/download")
    public String downLoad(String url,String verityCode, HttpServletRequest request){
//       示例链接 https://github.com/sallai1/java/archive/master.zip

// 验证验证吗
        String code = (String)request.getSession().getAttribute("verityCode");
        System.out.println("传入->"+verityCode);
        System.out.println("待验证->"+code);
        if(null==code||!verityCode.equalsIgnoreCase(code)){
           return new R().setCode(303).setMsg("验证码错误").toString();
        }
        request.getSession().setAttribute("verityCode",null);   //验证完销毁

        System.out.println("存储路径->"+fileLocal);
        String flag = "https://github.com";
        url = url.trim();
        String localhost =  request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
        String fileName = UUID.randomUUID()+"-master.zip";
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("fileName",localhost+"/download/file?fileName="+fileName);
        if(url.indexOf(flag)!=0)
            return new R().setCode(300).setMsg("链接格式不争取，只能下载github项目").toString();

        System.out.println(url);
        try {
            HttpsDownUtils.downloadFile(url,fileLocal+fileName);
        } catch (Exception e) {
            System.err.println(e);
             return new R().setCode(300).setMsg("下载异常").toString();
        }

        return new R().setCode(200).setMsg("下载已完成！").setData(stringStringHashMap).toString();
    }

    @GetMapping("/download/file")
    public void downLoad(String fileName, HttpServletResponse response){
        FileInputStream in=null;
        response.setContentType("application/multipart/form-data;charset=utf8");
        response.setHeader("Content-Disposition","attachment;filename=master.zip");
        try {
            in = new FileInputStream(fileLocal + fileName);
            ServletOutputStream outputStream = response.getOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while((len=in.read(buffer))>0){
                outputStream.write(buffer,0,len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();


        } catch (IOException e) {
            e.printStackTrace();

        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @GetMapping({"/img"})
    public void  verityCode(HttpServletResponse response, HttpServletRequest request){
        VerityCode verityCode = new VerityCode();
        try {
            verityCode.verifyCode(request,response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
