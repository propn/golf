/*
 * Copyright (C) 2012 The Golf Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 */
package com.golf.mvc;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * @author Thunder.Hsu
 * 2012-12-17
 */
public class GolfFilterTest {

    @Test
    public void test() {
       String p="/console/aa";
       String WHERE_REXP = "/console|/a]";
       Pattern pss = Pattern.compile(WHERE_REXP);
       Matcher m = pss.matcher(p);
       if(m.find()){
           System.out.println(m.group());
       }
       
    }

}
