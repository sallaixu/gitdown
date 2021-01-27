package down.sallai.ml.controller;

import com.alibaba.fastjson.JSON;
import down.sallai.ml.bean.MyFile;
import down.sallai.ml.bean.SystemInfo;
import down.sallai.ml.bean.User;
import down.sallai.ml.services.FileMangerImpl;
import down.sallai.ml.services.MonitorService;
import down.sallai.ml.util.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Controller
public class Index {

    @Value("${mydown.fileLocal}")
    String fileLocal;
    @GetMapping({"/index","/"})
    public String  IndexPage(){
        return "index";
    }

    @PostMapping({"/login"})
    @ResponseBody
    public String  Alogin(String Aname,String Apwd,HttpServletRequest request,HttpServletResponse response) throws IOException {
        System.out.println("登陆检查->"+Aname+Apwd);
        FileReader fileReader;
        String pwd=null;
        String name=null;
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("application.properties");
            Properties properties = new Properties();
            properties.load(in);
            pwd = properties.getProperty("Apwd");
            name = properties.getProperty("Aname");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("name->"+name+pwd);
        if(null!=pwd&&null!=name&&name.equals(Aname)&&pwd.equals(Apwd)){

            request.getSession().setAttribute("user",new User(name));

            response.sendRedirect("/admin");

            return new R().setCode(200).setMsg("登陆成功").toString();
        }
        return new R().setCode(300).setMsg("用户名或密码错误").toString();
    }

    @GetMapping("/admin")
    public String admin(Model model,HttpServletRequest request){
//        FileMangerImpl fileManger = new FileMangerImpl();
//        ArrayList<MyFile> allFile = fileManger.getAllFile(fileLocal);
        model.addAttribute("user",request.getSession().getAttribute("user"));
        return "admin";
    }

    @GetMapping("/admin/file")
    public String file(Model model){
        FileMangerImpl fileManger = new FileMangerImpl();
        ArrayList<MyFile> allFile = fileManger.getAllFile(fileLocal);
        model.addAttribute("fileList",allFile);

        return "file";
    }
    @GetMapping("/admin/systemInfo")
    public String getSystemInfo(Model model){
        MonitorService monitorService = new MonitorService();
        SystemInfo allSystemInfo = monitorService.getAllSystemInfo(fileLocal);
        model.addAttribute("info", allSystemInfo);
        System.out.println(JSON.toJSONString(allSystemInfo));
        return "systemInfo";
    }


}
