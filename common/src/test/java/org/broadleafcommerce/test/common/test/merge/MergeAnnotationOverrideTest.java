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
package org.broadleafcommerce.test.common.test.merge;

import org.broadleafcommerce.common.extensibility.FrameworkXmlBeanDefinitionReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Iterator;
import java.util.Set;
import jakarta.annotation.Resource;

/**
 * @author Nick Crum ncrum
 */
@ExtendWith(SpringExtension.class)
public class MergeAnnotationOverrideTest {

    @Configuration
    @Import({MainConfiguration.BroadleafFrameworkConfiguration.class}) // import our Broadleaf configurations
    @ImportResource("classpath:context/merge/local.xml") // import our local xml configuration
    public static class MainConfiguration {

        @ImportResource(value = {                               // define our Broadleaf xml configurations
                "classpath:context/merge/bl-framework.xml",
                "classpath:context/merge/bl-module.xml"
        }, reader = FrameworkXmlBeanDefinitionReader.class)
        public static class BroadleafFrameworkConfiguration {}
    }

    @Resource(name="mergedList")
    protected Set<String> mergedList;

    @Test
    public void testMergedOrder() {
        Iterator<String> iterator = mergedList.iterator();
        Assertions.assertEquals("common-xml", iterator.next());
        Assertions.assertEquals("framework-xml", iterator.next());
        Assertions.assertEquals("module-xml", iterator.next());
        Assertions.assertEquals("framework-config1", iterator.next());
        Assertions.assertEquals("framework-config2", iterator.next());
        Assertions.assertEquals("local-xml", iterator.next());
        Assertions.assertEquals("local-config1", iterator.next());
        Assertions.assertEquals("local-config2", iterator.next());
    }


}
