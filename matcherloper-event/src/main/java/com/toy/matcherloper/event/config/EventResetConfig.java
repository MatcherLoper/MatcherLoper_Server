package com.toy.matcherloper.event.config;

import com.toy.matcherloper.event.dispatcher.Events;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(0)
@Component
public class EventResetConfig {
    private ThreadLocal<Integer> nestedCount = ThreadLocal.withInitial(() -> 0);

    @Around("execution(public * com.toy.matcherloper..*Service.*(..))")
    public Object doReset(ProceedingJoinPoint joinPoint) throws Throwable {
        nestedCount.set(nestedCount.get() + 1);
        try {
            return joinPoint.proceed();
        } finally {
            nestedCount.set(nestedCount.get() - 1);
            if (nestedCount.get() == 0) {
                Events.reset();
            }
        }
    }
}
