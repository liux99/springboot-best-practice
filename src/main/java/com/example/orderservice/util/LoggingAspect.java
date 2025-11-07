package com.example.orderservice.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
	@Around("execution(* com.example.orderservice..*(..))")
	public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("Entering: {}", joinPoint.getSignature());
		Object result = joinPoint.proceed();
		log.info("Exiting: {}", joinPoint.getSignature());
		return result;
	}
}
