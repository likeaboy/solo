package org.b3log.solo.processor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.b3log.latke.Keys;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.latke.servlet.renderer.JSONRenderer;
import org.b3log.solo.processor.renderer.PlainTextRender;
import org.b3log.solo.wechat.Decript;
import org.b3log.solo.wechat.WechatConstants;
import org.json.JSONObject;

@RequestProcessor
public class WechatCheckProcessor {
	
	 private static final Logger LOGGER = Logger.getLogger(WechatCheckProcessor.class);
	
	public static void main(String[] args) {
		WechatCheckProcessor processor = new WechatCheckProcessor();
//		long _time = System.currentTimeMillis();
		long _time = 1504853434150L;
		//dd42d2607a68acc66f06756d0bdcbb1c180799b2
		String signature = processor.genSignature(String.valueOf(_time), String.valueOf(37));
		boolean b =processor.checkSignature(signature,String.valueOf(_time),String.valueOf(37));
		System.out.println(b);
	}
	
	 @RequestProcessing(value = "/wechat_check.do", method = HTTPRequestMethod.GET)
	    public void check(final HttpServletRequest request, final HTTPRequestContext context) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		LOGGER.log(Level.INFO,"signature from wechat : " + signature);
		
		boolean isSuccess = checkSignature(signature, timestamp, nonce);
		final PlainTextRender render = new PlainTextRender();
		context.setRenderer(render);
		try {
		if(isSuccess) {
			render.setPlainText(echostr);
		}else {
			render.setPlainText("error");
		}
		}catch(Exception e) {
			
		}
	 }
	 
	 public String genSignature(String timestamp,String nonce) {
		 String[] arr = new String[] { WechatConstants.token, timestamp, nonce }; 
		 Arrays.sort(arr);  
	        StringBuilder content = new StringBuilder();  
	        for (int i = 0; i < arr.length; i++) {  
	            content.append(arr[i]);  
	        }  
	        MessageDigest md = null;  
	        String tmpStr = null;  
	  
	        try {  
//	            md = MessageDigest.getInstance("SHA-1");  
//	            // 将三个参数字符串拼接成一个字符串进行sha1加密  
//	            byte[] digest = md.digest(content.toString().getBytes());  
//	            tmpStr = bytes2Hex(digest);
	        	tmpStr= Decript.SHA1(content.toString());
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        System.out.println(tmpStr);
	        return tmpStr;
	 }
	 public String bytes2Hex(byte[] src) {
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
		}
	 /** 
	     * 验证签名 
	     *  
	     * @param signature 
	     * @param timestamp 
	     * @param nonce 
	     * @return 
	     */  
	    public boolean checkSignature(String signature, String timestamp, String nonce) {  
	        String[] arr = new String[] { WechatConstants.token, timestamp, nonce };  
	        // 将token、timestamp、nonce三个参数进行字典序排序  
	        Arrays.sort(arr);  
	        StringBuilder content = new StringBuilder();  
	        for (int i = 0; i < arr.length; i++) {  
	            content.append(arr[i]);  
	        }  
	        MessageDigest md = null;  
	        String tmpStr = null;  
	  
	        try {  
//	            md = MessageDigest.getInstance("SHA-1");  
//	            // 将三个参数字符串拼接成一个字符串进行sha1加密  
//	            byte[] digest = md.digest(content.toString().getBytes());  
//	            tmpStr = bytes2Hex(digest);
	        	tmpStr= Decript.SHA1(content.toString());
	        } catch (Exception e) {  
	        	LOGGER.log(Level.ERROR, "signature check error", e);
	        }  
	  
	        content = null;  
	        LOGGER.log(Level.INFO,"加密排序后的字符串：" + tmpStr);
	        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信  
	        return tmpStr != null ? tmpStr.equals(signature) : false;  
	    } 
}
