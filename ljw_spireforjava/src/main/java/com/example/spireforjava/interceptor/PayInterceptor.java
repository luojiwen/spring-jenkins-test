package com.example.spireforjava.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PayInterceptor implements HandlerInterceptor {

	@Value("${hdpay.pass.ip:1}")
	private String passIps;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String clientIp = request.getRemoteAddr();
		System.out.println(clientIp);
		if(passIps == null || passIps.trim().equals("") || passIps.trim().equals("*")
				|| passIps.indexOf(clientIp) > -1) {
			return true;
		}
		
		response.setStatus(403);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

		
	}
	
}
