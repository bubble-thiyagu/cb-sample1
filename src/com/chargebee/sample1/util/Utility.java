package com.chargebee.sample1.util;

/*
 * Copyright (c) 2013 ChargeBee Inc
 * All Rights Reserved.
 */

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author chargebee-ajit
 */
public class Utility {

    public static String money(int money) {
        return "$"+(money/100);
    }
    
    public static String prettyDate(Timestamp time) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        return sd.format(time);
    }
}
