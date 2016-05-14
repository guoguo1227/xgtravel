package com.stars.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Description :MD5加密方式
 * Author : guo
 * Date : 2016/1/24 14:10
 */
public class EncryptUtil {
    /**
     * md5
     *
     * @描述: MD5加密方式
     * @作者: samuel
     * @param password
     *            原密码
     * @return String类型加密后的密码
     */

    public static String md5(String password) {
        return encrypt(password, "md5");
    }

    /**
     * sha
     *
     * @描述: SHA加密方式
     * @作者: samuel
     * @param password
     *            原密码
     * @return String类型加密后的密码
     */
	/*
	 * 处理逻辑：以password和sha-1为参数，调用encrypt函数，实现SHA的方式给密码加密
	 */

    public static String sha(String password) {
        return encrypt(password, "sha-1");
    }

    /**
     * encrypt
     *
     * @描述: 用md5或者sha-1加密方式给密码加密
     * @作者: samuel
     * @param password
     *            要加密的密码
     * @param algorithmName
     *            加密算法名称，md5或者sha-1，不区分大小写
     * @return 加密后的密码{String}
     */
	/*
	 * 处理逻辑：根据传入的参数判断调用MessageDigest类的MD5方法或者SHA方法对密码进行加密
	 * 异常处理设计：加密的代码放入try-catch块中，产生异常时则将异常堆栈打印出来
	 */

    private static String encrypt(String password, String algorithmName) {

        if (password == null || "".equals(password.trim())) {
            throw new IllegalArgumentException("传入的参数为空");
        }

        // 默认加密方法为md5
        if (algorithmName == null || "".equals(algorithmName.trim())) {
            algorithmName = "md5";
        }

        // 加密处理
        try {
            MessageDigest m = MessageDigest.getInstance(algorithmName);
            m.update(password.getBytes("UTF8"));
            byte s[] = m.digest();

            return hex(s);
        } catch (NoSuchAlgorithmException NoSuchAlgorithmEx) {
            NoSuchAlgorithmEx.printStackTrace();
        } catch (UnsupportedEncodingException UnsupportedEncodingEx) {
            UnsupportedEncodingEx.printStackTrace();
        }

        return null;
    }

    /**
     * hex
     *
     * @描述: 将加密后的密码转换成十六进制字符串
     * @作者: samuel
     * @param encryptPassword
     *            加密后的byte[]类型密码数组
     * @return finalPassword：加密后的String类型的密码
     */
	/*
	 * 处理逻辑：调用Integer类的方法，将byte[]类型数组转换成字符串
	 */

    private static String hex(byte[] encryptPassword) {

        // 加密后的密码
        StringBuffer finalPassword = new StringBuffer();

        for (int i = 0; i < encryptPassword.length; ++i) {
            finalPassword.append(Integer.toHexString(
                    (encryptPassword[i] & 0xFF) | 0x100).substring(1, 3));
        }

        return finalPassword.toString();
    }
}
