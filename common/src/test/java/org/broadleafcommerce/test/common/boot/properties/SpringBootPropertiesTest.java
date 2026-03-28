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
package org.broadleafcommerce.test.common.boot.properties;

import org.broadleafcommerce.common.config.BroadleafEnvironmentConfiguringApplicationListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Test that ensures our {@link BroadleafEnvironmentConfiguringApplicationListener} is applied automatically in a Spring Boot environment, and that
 * any application.properties files overrides any of the Broadleaf sources
 * 
 * @author Phillip Verheyden (phillipuniverse)
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SpringBootPropertiesTest {

    @Configuration
    public static class PropertiesConfig{}
    
    public static final String TEST_PROPERTY = "test.property.source";
    
    @Autowired
    protected ConfigurableEnvironment env;
    
    @Test
    public void testProfileSourcesRegisteredOverridesCommon() {
        Assertions.assertTrue(env.getPropertySources().contains(BroadleafEnvironmentConfiguringApplicationListener.FRAMEWORK_SOURCES_NAME));
        Assertions.assertTrue(env.getPropertySources().contains(BroadleafEnvironmentConfiguringApplicationListener.PROFILE_AWARE_SOURCES_NAME));
        
        String developmentSourceName = new ClassPathResource("common-test-properties/profile-aware-properties/development.properties").getDescription();
        CompositePropertySource profileAwareSource = (CompositePropertySource) env.getPropertySources().get(BroadleafEnvironmentConfiguringApplicationListener.PROFILE_AWARE_SOURCES_NAME);
        
        Assertions.assertTrue(profileAwareSource.getPropertySources().contains(PropertySource.named(developmentSourceName)));
    }
    
    @Test
    public void testBootPropertiesOverrideBroadleaf() {
        Assertions.assertEquals("boot.property.value", env.getProperty(TEST_PROPERTY));
    }
    
}
