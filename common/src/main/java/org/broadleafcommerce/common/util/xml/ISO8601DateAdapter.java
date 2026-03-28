/*
 * #%L
 * BroadleafCommerce Common Libraries
 * %%
 * Copyright (C) 2009 - 2016 Broadleaf Commerce
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
package org.broadleafcommerce.common.util.xml;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class ISO8601DateAdapter extends XmlAdapter<String, Date> {

    protected DateTimeFormatter isoFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .withZone(ZoneId.systemDefault());

    @Override
    public String marshal(Date arg0) throws Exception {
        if (arg0 == null) {
            return null;
        }
        return isoFormat.format(arg0.toInstant());
    }

    @Override
    public Date unmarshal(String arg0) throws Exception {
        if (arg0 == null) {
            return null;
        }
        Instant instant = Instant.from(isoFormat.parse(arg0));
        return Date.from(instant);
    }

}
