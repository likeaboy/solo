package org.b3log.solo.wechat.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * SHA-1加密算法
 * @author Rocky.Wang
 *
 */
public class DecriptUtil {
  
    public static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    /*private String bytes2Hex(byte[] src) {
	 if (src == null || src.length <= 0) { 
	  return null; 
	 } 
	 
	 char[] res = new char[src.length * 2]; // 每个byte对应两个字符
	 final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	 for (int i = 0, j = 0; i < src.length; i++) {
	  res[j++] = hexDigits[src[i] >> 4 & 0x0f]; // 先存byte的高4位
	  res[j++] = hexDigits[src[i] & 0x0f]; // 再存byte的低4位
	 }
	 
	 return new String(res);
	}*/
}