package down.sallai.ml.config;

import down.sallai.ml.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**","/login","/","/bootstrap/**","/index/js/**","/img","/download/**");
    }
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
////        registry.addViewController("/index").setViewName("index");
////        registry.addViewController("/indexTest").setViewName("index");
//    }
//
//    //注册拦截器
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**","/login","/");
//    }

}