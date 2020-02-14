package com.kemal.spring.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kemal.spring.domain.User;
@Component
@Order(value = 1)
public class Interceptor  extends HandlerInterceptorAdapter{
		 
			 public boolean preHandle(HttpServletRequest request,
			            HttpServletResponse response, Object handler) throws Exception {
			        System.out.println("Inside preHandle");
			        if((!request.getRequestURI().contains("/css"))&&
			        		(!request.getRequestURI().contains("/js"))&&
			        		(!request.getRequestURI().contains("/img"))&&
			        		(!request.getRequestURI().contains("/images"))&&
			        		(!request.getRequestURI().contains("/listar-contribuyente"))&&
			        		(!request.getRequestURI().contains("/recuperar-clave"))&&
			        		(!request.getRequestURI().contains("/abrir-recuperar-clave"))
			         		
			        		
			        		
			        		){
			        	  User us=null;
					        try {
					        	  us=(User)request.getSession(false).getAttribute("oUsuario");
					        }catch (Exception e) {
								//e.printStackTrace();
							}
					        if((!request.getRequestURI().equals(request.getContextPath()+"/login")&&
					        		!request.getRequestURI().equals(request.getContextPath()+"/")&&
					        		!request.getRequestURI().equals(request.getContextPath()))&&
					        		!request.getRequestURI().equals(request.getContextPath()+"/logout")&&
					        		null == us){   
		                        System.out.println("Send login");
						        response.sendRedirect(request.getContextPath());
						        return false;
						    }
					       if((request.getRequestURI().equals(request.getContextPath()+"/logout"))){   
		                        System.out.println("Send login");
		                      //Set to expire far in the past.  
		                        response.setDateHeader("Expires", 0);  
		                        //Set standard HTTP/1.1 no-cache headers.  
		                        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
		                        //Set IE extended HTTP/1.1 no-cache headers (use addHeader).  
		                        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
		                        //Set standard HTTP/1.0 no-cache header.  
		                        response.setHeader("Pragma", "no-cache");
					
						    }
			        }
			      
			        return true;
		}

}
