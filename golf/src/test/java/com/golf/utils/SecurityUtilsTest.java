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
package com.golf.utils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Test;

/**
 * @author Thunder.Hsu 2012-12-18
 */
public class SecurityUtilsTest {


    /**
     * Test method for {@link com.golf.utils.SecurityUtils#generateSecretKey()}.
     * @throws IOException 
     * @throws NoSuchAlgorithmException 
     */
    @Test
    public void testGenerateSecretKey() throws NoSuchAlgorithmException, IOException {
        SecurityUtils.generateSecretKey();
    }

    /**
     * Test method for {@link com.golf.utils.SecurityUtils#encrypt(byte[], java.lang.String)}.
     * @throws ClassNotFoundException 
     * @throws IOException 
     * @throws BadPaddingException 
     * @throws IllegalBlockSizeException 
     * @throws NoSuchPaddingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     */
    @Test
    public void testEncryptByteArrayString() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException, ClassNotFoundException {
        System.out.println(SecurityUtils.encrypt("asdfasfdsa".getBytes())); 
    }

    /**
     * Test method for {@link com.golf.utils.SecurityUtils#encrypt(byte[])}.
     */
    @Test
    public void testEncryptByteArray() {
        // fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.golf.utils.SecurityUtils#decrypt(byte[], java.lang.String)}.
     * @throws ClassNotFoundException 
     * @throws IOException 
     * @throws BadPaddingException 
     * @throws IllegalBlockSizeException 
     * @throws NoSuchPaddingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     */
    @Test
    public void testDecryptByteArrayString() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException, ClassNotFoundException {
       
        byte[] a = SecurityUtils.encrypt("1234567890abcde".getBytes()); 
        System.out.println(a.length);
        System.out.println(new String(a));
        byte[] b = SecurityUtils.decrypt(a);
        System.out.println(new String(b));

        
    }

    /**
     * Test method for {@link com.golf.utils.SecurityUtils#decrypt(byte[])}.
     */
    @Test
    public void testDecryptByteArray() {
        // fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.golf.utils.SecurityUtils#getSecretKeyFromLocal()}.
     */
    @Test
    public void testGetSecretKeyFromLocal() {
        // fail("Not yet implemented");
    }

}
