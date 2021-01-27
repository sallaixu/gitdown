package down.sallai.ml;

import com.alibaba.fastjson.JSON;
import down.sallai.ml.bean.SystemInfo;
import down.sallai.ml.services.MonitorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.*;

@SpringBootTest
class GitdownApplicationTests {
    @Value("${mydown.fileLocal}")
    private String fileLocal;
    @Test
    void contextLoads() {
        String flag = "https://github.com";
        String url = "https://github.com/sallai1/java/archive/master.zip";
        System.out.println(url.indexOf(flag));

    }
    @Test
    public void  test01() throws InterruptedException {

       try{
           ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(12,
                   30,600,
                   TimeUnit.SECONDS,
                   new ArrayBlockingQueue<>(20),Executors.defaultThreadFactory()
           );

           for (int i = 0; i < 50; i++) {

               threadPoolExecutor.execute(()->{
                   System.out.println("线程池"+Thread.currentThread().getName());
               });

           }


       }catch (Exception e){
           System.err.println(e);
       }



    }

}
