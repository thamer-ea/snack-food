/*
 * CONFIDENTIAL – This document contains confidential information.
 * Access is restricted and ownership or possession by Logicalis
 * or any of its subsidiaries or partner companies, and it is
 * protected against publication by applicable legislation.
 * Undue possession, visualization, publication, distribution or
 * unauthorized use of this document is strictly prohibited.
 * <p>
 * Copyright 2018, Logicalis.
 * All rights reserved.
 */

package com.example.snack.Config;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class LogConfiguration {

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public Logger logger(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }
}
