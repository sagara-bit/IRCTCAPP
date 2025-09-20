package com.example.irctc.irctc_backend.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Logs before TrainServiceImpl.getAllTrains(..)
    @Before("execution(* com.example.irctc.irctc_backend.service.impl.TrainServiceImpl.getAllTrains(..))")
    public void logBeforeGetAllTrains() {
        logger.info("Before method execution: TrainServiceImpl.getAllTrains()");
    }

    // Logs before ANY method inside service.impl package
    @Before("execution(* com.example.irctc.irctc_backend.service.impl.*.*(..))")
    public void logBeforeAnyServiceMethod() {
        logger.info("Before ANY method in service.impl package");
    }
}
