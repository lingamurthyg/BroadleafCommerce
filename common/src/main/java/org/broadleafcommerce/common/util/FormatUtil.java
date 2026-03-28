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
package org.broadleafcommerce.common.util;

import org.broadleafcommerce.common.web.BroadleafRequestContext;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import jakarta.xml.datatype.DatatypeConfigurationException;

/**
 * @author Jeff Fischer
 */
public class FormatUtil {

    public static final String DATE_FORMAT = "yyyy.MM.dd HH:mm:ss";
    public static final String DATE_FORMAT_WITH_TIMEZONE = "yyyy.MM.dd HH:mm:ss Z";

    public static DateTimeFormatter getDateFormat() {
        return DateTimeFormatter.ofPattern(DATE_FORMAT)
                .withZone(BroadleafRequestContext.getBroadleafRequestContext().getTimeZone().toZoneId());
    }

    /**
     * Used with dates in rules since they are not stored as a Timestamp type (and thus not converted to a specific database
     * timezone on a save). In order to provide accurate information, the timezone must also be preserved in the MVEL rule
     * expression
     *
     * @return
     */
    public static DateTimeFormatter getTimeZoneFormat() {
        return DateTimeFormatter.ofPattern(DATE_FORMAT_WITH_TIMEZONE)
                .withZone(BroadleafRequestContext.getBroadleafRequestContext().getTimeZone().toZoneId());
    }

    /**
     * Use to produce date Strings in the W3C date format
     *
     * @param date
     * @return
     * @throws DatatypeConfigurationException
     */
    public static String formatDateUsingW3C(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        return zdt.format(formatter);
    }

}
