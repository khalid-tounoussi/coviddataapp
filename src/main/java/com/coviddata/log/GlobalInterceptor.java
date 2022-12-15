package com.coviddata.log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class GlobalInterceptor  {

    static final String IN = "- IN -";
    static final String OUT = "- OUT -";
    public static final String ERROR = "Un erreur est declanché :" ;
    private static Logger logger = LogManager.getLogger(GlobalInterceptor.class.getName());

    public static String getInput( JoinPoint joinPoint ){
        String className = ((MethodSignature)joinPoint.getSignature()).getDeclaringTypeName();
        String[] paramsNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        Class[] paramsTypes = ((MethodSignature)joinPoint.getSignature()).getParameterTypes();
        Object[] args = joinPoint.getArgs();
        String[] params = new String[paramsNames.length];
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        for( int i = 0; i < params.length; i++)
        {
            params[i] = paramsTypes[i].getName() + " : "  + paramsNames[i] + " { " + args[i] + " }";
        }
        return className + " - " + methodName +  IN + Arrays.toString(params );
    }


    @Around("execution(*  com.coviddata..*(..) )"
            + "&& !@annotation( org.springframework.web.bind.annotation.ExceptionHandler)")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object temp = null;
        String inputString = getInput(joinPoint);
        logger.debug(inputString);
        boolean flag = false;
        try{
            temp = joinPoint.proceed();
        }
        catch (Exception e) {
            flag = true;
            logger.error( ERROR  + "  " + ((MethodSignature)joinPoint.getSignature()).getDeclaringTypeName() + " " + joinPoint.getSignature().getName() );
            throw e;
        }
        finally {
            logger.debug( OUT  + ((MethodSignature)joinPoint.getSignature()).getReturnType() );
            if(! flag)
                return temp;
        }
        return null;
    }
}

