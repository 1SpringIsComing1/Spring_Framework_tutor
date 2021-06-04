package maksym.kruhovykh.app.service.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut(value = "execution(* maksym.kruhovykh.app.controller..*(..))" +
            " || execution(* maksym.kruhovykh.app.service..*(..))")
    public void logMethodPointcut() {
    }

    @Around("logMethodPointcut()")
    public Object logMethod(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        Object args = pjp.getArgs() == null ? Collections.emptyList() : pjp.getArgs();
        String pathClassMethodArgs = signature.getDeclaringTypeName()
                .replaceAll("maksym.kruhovykh.app", "**")
                + "." + signature.getName()
                + "(" + Stream.of(args).map(o -> o.getClass().getSimpleName()).collect(Collectors.joining()) + ")";

        log.info(">>> Start method: " + pathClassMethodArgs);

        long startTime = System.currentTimeMillis();
        Object result = pjp.proceed();
        long endTime = System.currentTimeMillis();

        log.info("<<< End method: {}, execution time [{}]ms", pathClassMethodArgs, (endTime - startTime));

        return result;
    }
}
