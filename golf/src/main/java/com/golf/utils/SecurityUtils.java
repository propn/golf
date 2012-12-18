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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * @author Thunder.Hsu 2012-12-18
 */
public class SecurityUtils {

    private final static String ENCRYPT_ALGORITHM = "AES";

    private final static String SECRET_KEY_FILE_NAME = "secret.key";

    /**
     * 生成加解密的密钥
     * 
     * @param algorithm
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static SecretKey generateSecretKey(String algorithm) throws NoSuchAlgorithmException, IOException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        SecretKey secretKey = keyGenerator.generateKey();
        // 保存到文件中
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(new File(SECRET_KEY_FILE_NAME));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(secretKey);
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (oos != null) {
                oos.close();
            }
        }

        return secretKey;
    }

    /**
     * 生成加解密的密钥
     * 
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException, IOException {
        return generateSecretKey(ENCRYPT_ALGORITHM);
    }

    /**
     * 加密内容，并返回
     * 
     * @param data
     * @param algorithm
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] encrypt(byte[] data, String algorithm) throws NoSuchAlgorithmException, IOException,
            ClassNotFoundException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        SecretKey secretKey = getSecretKeyFromLocal();
        if (secretKey == null) {
            secretKey = generateSecretKey(algorithm);
        }
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return cipher.doFinal(data);
    }

    /**
     * 加密内容，并返回
     * 
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] encrypt(byte[] data) throws NoSuchAlgorithmException, IOException, ClassNotFoundException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return encrypt(data, ENCRYPT_ALGORITHM);
    }

    /**
     * 解密内容，并返回
     * 
     * @param data
     * @param algorithm
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] decrypt(byte[] data, String algorithm) throws NoSuchAlgorithmException, IOException,
            ClassNotFoundException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        SecretKey secretKey = getSecretKeyFromLocal();
        if (secretKey == null) {
            secretKey = generateSecretKey(algorithm);
        }
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return cipher.doFinal(data);
    }

    /**
     * 解密内容，并返回
     * 
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] decrypt(byte[] data) throws NoSuchAlgorithmException, IOException, ClassNotFoundException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return decrypt(data, ENCRYPT_ALGORITHM);
    }

    /**
     * 获取密钥对象
     * 
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static SecretKey getSecretKeyFromLocal() throws NoSuchAlgorithmException, IOException,
            ClassNotFoundException {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        SecretKey secretKey = null;
        try {
            // 加载私钥文件
            fis = new FileInputStream(new File(SECRET_KEY_FILE_NAME));
            ois = new ObjectInputStream(fis);
            // 读取对象
            secretKey = (SecretKey) ois.readObject();
        } finally {
            // 关闭流
            if (fis != null) {
                fis.close();
            }
            if (ois != null) {
                ois.close();
            }
        }
        return secretKey;
    }

}
