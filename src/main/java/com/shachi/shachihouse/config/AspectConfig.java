package com.shachi.shachihouse.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Configuration
@EnableAspectJAutoProxy
@Component
public class AspectConfig {
    private Logger logger = LoggerFactory.getLogger(AspectConfig.class);

    @Pointcut("execution(* com.shachi.shachihouse.controller.*.*(..))")
    public void controllerMethods() {}

    @Before("controllerMethods()")
    public void before(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info(" before called methodName :  " + methodName );
    }

    @After("controllerMethods()")
    public void after(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info(" after called methodName : " + methodName);
    }

    @AfterReturning("controllerMethods()")
    public void AfterReturning(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info(" AfterReturning called methodName : " + methodName);
    }

    @AfterThrowing("controllerMethods()")
    public void AfterThrowing(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info(" AfterThrowing called methodName : " + methodName);
    }

    @Around("controllerMethods()")
    public Object measureControllerMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long start = System.nanoTime();
        // thực thi phương thức
        Object returnValue = proceedingJoinPoint.proceed();
        long end = System.nanoTime();
        String methodName = proceedingJoinPoint.getSignature().getName();
        logger.info("Execution of "+ methodName + " took "+ TimeUnit.NANOSECONDS.toMillis(end - start)+ "ms");
        return returnValue;
    }
}
