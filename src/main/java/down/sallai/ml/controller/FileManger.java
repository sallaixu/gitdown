package down.sallai.ml.controller;

import com.alibaba.fastjson.JSON;
import down.sallai.ml.services.FileMangerImpl;
import down.sallai.ml.services.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.lang.management.MonitorInfo;

@Controller
public class FileManger {
    @Autowired
    MonitorService monitorService;
    @Value("${mydown.fileLocal}")
    private String fileLocal;

    @GetMapping("/spaceInfo")
    @ResponseBody
    public String getSystemInfo(){

      return JSON.toJSONString(monitorService.getAllSystemInfo(fileLocal));


    }
    @GetMapping("/deleteAllFile")
    @ResponseBody
    public void deleteAllFile(){
        down.sallai.ml.services.FileManger fileManger = new FileMangerImpl();
        System.out.println(fileManger.deleteAllFile(fileLocal));
    }
    @GetMapping("file/delByName")
    @ResponseBody
    public Boolean delByName(String name){
        down.sallai.ml.services.FileManger fileManger = new FileMangerImpl();
        Boolean aBoolean = fileManger.delByName(name, fileLocal);
        return  aBoolean;
    }


}
