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

import org.broadleafcommerce.common.time.SystemTime;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class DateUtil {

    public static final long ONE_HOUR_MILLIS = Duration.ofHours(1).toMillis();
    public static final long ONE_DAY_MILLIS = Duration.ofDays(1).toMillis();
    public static final long ONE_WEEK_MILLIS = Duration.ofDays(7).toMillis();

    public static final long ONE_HOUR_SECONDS = Duration.ofHours(1).getSeconds();
    public static final long ONE_DAY_SECONDS = Duration.ofDays(1).getSeconds();
    public static final long ONE_WEEK_SECONDS = Duration.ofDays(7).getSeconds();
    public static final long SIX_MONTHS_SECONDS = Duration.ofDays(180).getSeconds();

    /**
     * Determine if a date range is active.
     * <p>
     * A date range is active if the following statement is true: {@code startDate < now <= endDate}
     * <p>
     * If {@code startDate} is {@code null} then the date range is inactive. If {@code endDate} is null, then only the
     * {@code startDate} is checked.
     *
     * @param includeTime If true, then the full timestamp to the millisecond is used. If false, only the date will be
     * used and the time is zeroed out.
     * @return True if the date range is currently active, false otherwise.
     */
    public static boolean isActive(Date startDate, Date endDate, boolean includeTime) {
        Long date = SystemTime.asMillis(includeTime);
        return !(startDate == null || startDate.getTime() >= date || (endDate != null && endDate.getTime() < date));
    }

    /**
     * Return the {@link Date} to be used in a query based on a cached {@link Date} and a resolution.
     * <p>
     * This is meant to be used as a mechanism to allow caching to occur in queries that compare a date to {@code now}.
     * Since caching will only occur if the query is exactly the same, you cannot use the exact current timestamp or the
     * query will never be cached. This method will return the same timestamp for a {@link Date} until it has exceeded
     * the resolution, at which point the current timestamp will be used.
     * <p>
     * NOTE: This method will update the passed in {@code cachedDate} if the resolution has passed.
     *
     * @param cachedDate a {@link Date} that is cached, typically by a DAO, to be used in queries that involve date
     * ranges. This object will be updated if the resolution has passed.
     * @param currentDateResolution resolution in milliseconds.
     * @return the {@link Date} object to use in a query involving date ranges.
     */
    public static Date getCurrentDateAfterFactoringInDateResolution(Date cachedDate, Long currentDateResolution) {
        Date returnDate = SystemTime.getCurrentDateWithinTimeResolution(cachedDate, currentDateResolution);
        if (returnDate != cachedDate) {
            if (SystemTime.shouldCacheDate()) {
                cachedDate.setTime(returnDate.getTime());
            }
        }
        return returnDate;
    }

}
