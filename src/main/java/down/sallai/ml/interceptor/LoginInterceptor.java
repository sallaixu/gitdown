package down.sallai.ml.interceptor;

import down.sallai.ml.bean.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("自定义拦截器开始执行");
        Object o = request.getSession().getAttribute("user");

        if(o!=null){

            return true;
        }else{
            request.getRequestDispatcher("/").forward(request,response);
            return false;
        }
//        Boolean loginState = (Boolean)request.getSession().getAttribute("loginState");
//        if(loginState!=null&&loginState){
//
//            return true;
//        }
//        else{
//            request.getRequestDispatcher("/").forward(request, response);
//            return false;
//        }
//
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }
}
