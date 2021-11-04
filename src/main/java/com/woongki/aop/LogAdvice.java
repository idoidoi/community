package com.woongki.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect
@Component
@Log4j
public class LogAdvice {
	
		
	 @Before("execution(* com.woongki.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
	  public void logBeforeWithParam(String str1, String str2) {

	    log.info("str1: " + str1);
	    log.info("str2: " + str2);
	  }  

}
