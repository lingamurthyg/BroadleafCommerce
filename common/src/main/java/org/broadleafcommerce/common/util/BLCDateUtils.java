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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.Locale;

/**
 * Convenience class to facilitate date manipulation.
 *
 * @author Chris Kittrell (ckittrell)
 */
public class BLCDateUtils {

    private static final Log LOG = LogFactory.getLog(BLCDateUtils.class);

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * Converts the given date to the UTC time zone so that dates can be correctly converted on the client side
     *
     * @param date
     * @return the message
     */
    public static String convertDateToUTC(Date date) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)
                .withZone(ZoneId.of("UTC"));
        return dateFormat.format(date.toInstant());
    }

    public static String formatDateAsString(Date date) {
        // format date list grid cells
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("MMM d, yyyy @ hh:mma")
                .toFormatter(Locale.getDefault());

        return formatter.format(date.toInstant().atZone(ZoneId.systemDefault()))
                .replace("AM", "am")
                .replace("PM", "pm");
    }

    public static Date parseStringToDate(String dateString) {
        return parseStringToDate(dateString, DEFAULT_DATE_FORMAT);
    }

    public static Date parseStringToDate(String dateString, String dateFormat) {
        Date parsedDate = null;
        try {
            if (StringUtils.isNotEmpty(dateString)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
                LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
                parsedDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            }
        } catch (Exception e) {
            LOG.warn("The date string could not be parsed into the given format: " + dateFormat, e);
        }
        return parsedDate;
    }

    public static String formatDate(Date date) {
        return formatDate(date, DEFAULT_DATE_FORMAT);
    }

    public static String formatDate(Date date, String format) {
        if (date == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format)
                .withZone(ZoneId.systemDefault());
        return formatter.format(date.toInstant());
    }
}
