package com.danielqueiroz.fooddelivery.core.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter {

	/*
	 *  Deverá loggar:
	 *  - Horário de entrada da requisição
	 *  - Cabeçalhos da requisição 
	 *  - Corpo da requisição, 
	 *  
	 *  - Resposta, 
	 *  - Horário finalização da requisição, 
	 *  
	 *  - Possíveis Exceptions.
	 * */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		log.info("-[PreHandle Executado]");
		log.info("Header: {}", request);
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		log.info("-[PostHandle Executado]");
		log.info(" " + request);
		
		super.postHandle(request, response, handler, modelAndView);
	}
	
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		log.info("--- [AfterCompletion] [{}]",request);		
		super.afterCompletion(request, response, handler, ex);
	}
	
}
