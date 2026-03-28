/*
 * #%L
 * BroadleafCommerce Common Libraries
 * %%
 * Copyright (C) 2009 - 2017 Broadleaf Commerce
 * %%
 * Licensed under the Broadleaf Fair Use License Agreement, Version 1.0
 * (the "Fair Use License" located  at http://license.broadleafcommerce.org/fair_use_license-1.0.txt)
 * unless the restrictions on use therein are violated and require payment to Broadleaf in which case
 * the Broadleaf End User License Agreement (EULA), Version 1.1
 * (the "Commercial License" located at http://license.broadleafcommerce.org/commercial_license-1.1.txt)
 * shall apply.
 * 
 * Alternatively, the Commercial License may be replaced with a mutually agreed upon license (the "Custom License")
 * between you and Broadleaf Commerce. You may not use this file except in compliance with the applicable license.
 * #L%
 */
/**
 * 
 */
package org.broadleafcommerce.test.common.properties;

import org.broadleafcommerce.common.config.BroadleafEnvironmentConfiguringApplicationListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


/**
 * Test to verify that properties are added to the environment early enough via the ApplicationListener so that @ConditionalOnProperty works
 * 
 * @author Phillip Verheyden (phillipuniverse)
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = BroadleafEnvironmentConfiguringApplicationListener.class)
public class ConditionalOnPropertyTest {

    @Configuration
    public static class ConditionalOnPropertyConfig {
        
        @Bean
        @ConditionalOnProperty("test.property.source")
        public String shouldFind() {
            return "shouldFind";
        }
        
        @Bean
        @ConditionalOnProperty("property.that.does.not.exist")
        public String shouldNotFind() {
            return "shouldNotFind";
        }
    }
    
    @Autowired
    ApplicationContext ctx;
    
    @Test
    public void foundPropertyInstantiatesBean() {
        Assertions.assertTrue(ctx.containsBean("shouldFind"));
    }
    
    @Test
    public void missingPropertyPreventsBean() {
        Assertions.assertFalse(ctx.containsBean("shouldNotFind"));
    }
}
