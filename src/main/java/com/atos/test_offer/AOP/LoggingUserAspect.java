package com.atos.test_offer.AOP;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingUserAspect {
    //long startTime = 0;
    //long endTime = 0;
    long startTime = System.currentTimeMillis();
    long endTime = System.currentTimeMillis();

    private static final Logger logger = LogManager.getLogger(LoggingUserAspect.class);

    @Around("execution(* com.atos.test_offer.APIController.UserAPIController.add*(..) )")
    /*public Object callOnUserPOST( ProceedingJoinPoint proceedingJoinPoint ) throws Throwable {
        return logDataFromUserAPI(proceedingJoinPoint);
    }*/
    public Object callAddUser(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        logger.info("---------- Before addUser execution ----------");

        startTime = System.currentTimeMillis();
        Object value = null;
        try {
            value = proceedingJoinPoint.proceed(); // call edit
        } catch (Throwable e) {

            logger.info("---------- Problem on addUser method ----------");
            logger.info(e.getMessage());
            throw e;

        }
        endTime = System.currentTimeMillis();

        logger.info("---------- After addUser execution (duration : " + (endTime - startTime) + ") ----------");
        return value;
    }

    @Around("execution(* com.atos.test_offer.APIController.UserAPIController.get*(..) )")
    public Object callOnUserGET( ProceedingJoinPoint proceedingJoinPoint ) throws Throwable {
        logger.info("---------- Before findUser execution ----------");

        startTime = System.currentTimeMillis();
        Object value = null;
        try {
            value = proceedingJoinPoint.proceed(); // call edit
        } catch (Throwable e) {

            logger.info("---------- Problem on findUser method ----------");
            logger.info(e.getMessage());
            throw e;

        }
        endTime = System.currentTimeMillis();

        logger.info("---------- After findUser execution (duration : " + (endTime - startTime) + ") ----------");
        return value;
    }
}
