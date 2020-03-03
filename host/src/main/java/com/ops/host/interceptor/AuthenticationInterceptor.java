//package com.ops.host.interceptor;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.exceptions.TokenExpiredException;
//import com.ops.commons.annotations.PassToken;
//import com.ops.commons.annotations.UserLoginToken;
//import com.ops.host.entity.User;
//import com.ops.commons.enums.ErrorCodeAndMsg;
//import com.ops.commons.exception.BaseException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.lang.reflect.Method;
//
//public class AuthenticationInterceptor implements HandlerInterceptor {
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    UserMapper userMapper;
//
//    @Override
//    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Object object) throws Exception{
//        String token = httpServletRequest.getHeader("Authorization");
//        if(!(object instanceof HandlerMethod)){
//            return true;
//        }
//        HandlerMethod handlerMethod = (HandlerMethod)object;
//        Method method = handlerMethod.getMethod();
//
//        if(method.isAnnotationPresent(PassToken.class)){
//            PassToken passToken = method.getAnnotation(PassToken.class);
//            if(passToken.required()) {
//                return true;
//            }
//        }
//        if(method.isAnnotationPresent(UserLoginToken.class)){
//            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
//            if(userLoginToken.required()){
//                if(token==null){
//                    throw new BaseException(ErrorCodeAndMsg.Token_is_null);
//                }
//                try{
//                    String tokenUserName = JWT.decode(token).getAudience().get(0);
//                    User user = userMapper.selectByUserName(tokenUserName);
//                    if(user == null){
//                        throw new BaseException(ErrorCodeAndMsg.Token_is_error);
//                    }
//                    JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassWord())).build();
//                    try{
//                        jwtVerifier.verify(token);
//                    }catch (Exception e){
//                        if(e instanceof BaseException) {
//                            throw e;
//                        }else if(e instanceof TokenExpiredException) {
//                            throw new BaseException(ErrorCodeAndMsg.Token_is_expired);
//                        }else if(e instanceof JWTVerificationException){
//                            throw new BaseException(ErrorCodeAndMsg.Token_is_error);
//                        }else{
//                            e.printStackTrace();
//                            throw new BaseException(ErrorCodeAndMsg.Network_error);
//                        }
//                    }
//                    return true;
//                }catch (Exception e){
//                    if(e instanceof BaseException){
//                        throw e;
//                    }else{
//                        throw new BaseException(ErrorCodeAndMsg.Token_is_error);
//                    }
//                }
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object, ModelAndView modelAndView) throws Exception{ }
//
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,Object object,Exception e) throws Exception{ }
//}
