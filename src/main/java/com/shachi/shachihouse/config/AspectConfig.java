package com.travelbee.app.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Configuration
@EnableAspectJAutoProxy
@Component
public class AspectConfig {
    private Logger logger = LoggerFactory.getLogger(AspectConfig.class);

    @Before("execution(* com.travelbee.app.controller.*.*.*(..))")
    public void before(JoinPoint joinPoint){
        logger.info(" before called " + joinPoint.toString());
    }

    @After("execution(* com.travelbee.app.controller.*.*.*(..))")
    public void after(JoinPoint joinPoint){
        logger.info(" after called " + joinPoint.toString());
    }

    @AfterReturning("execution(* com.travelbee.app.controller.*.*.*(..))")
    public void AfterReturning(JoinPoint joinPoint){
        logger.info(" AfterReturning called " + joinPoint.toString());
    }

    @AfterThrowing("execution(* com.travelbee.app.controller.*.*.*(..))")
    public void AfterThrowing(JoinPoint joinPoint){
        logger.info(" AfterThrowing called " + joinPoint.toString());
    }
}
