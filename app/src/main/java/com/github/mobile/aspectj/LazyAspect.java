package com.github.mobile.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
class LazyAspect{
    @Pointcut("execution(* *..makeLazy*(..))")
    public void LazyPointcutRule(){}

    @Around("LazyPointcutRule()")
    public void aroundLazyPointcut(ProceedingJoinPoint point) throws Throwable
    {
        if(point.getArgs()[0] == null) {
            point.proceed();
        }
    }
}