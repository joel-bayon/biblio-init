package aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component("loggerAspect")
@Aspect
public class LoggerAspect {
	
	@Before("execution(* service..*.*(..))")
	public void logBefore(JoinPoint joinPoint) {
		Logger logger =Logger.getLogger(joinPoint.getTarget().getClass());
		String name = joinPoint.getSignature().getName();
		if(logger.isInfoEnabled())
			logger.info(name);
	}


}
