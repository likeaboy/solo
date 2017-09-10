package org.b3log.solo.processor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.servlet.HTTPRequestContext;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.solo.processor.renderer.PlainTextRender;
import org.b3log.solo.service.DefaultWechatService;
import org.b3log.solo.wechat.WechatConstants;
import org.b3log.solo.wechat.message.TextMessage;
import org.b3log.solo.wechat.util.DecriptUtil;
import org.b3log.solo.wechat.util.MessageUtil;

/**
 * 微信模块处理器
 * @author Rocky.Wang
 *
 */
@RequestProcessor
public class WechatProcessor {
	
	 private static final Logger LOGGER = Logger.getLogger(WechatProcessor.class);
	 
	 @Inject
    private DefaultWechatService wechatService;
	
	public static void main(String[] args) {
		WechatProcessor processor = new WechatProcessor();
//		long _time = System.currentTimeMillis();
		long _time = 1504853434150L;
		//dd42d2607a68acc66f06756d0bdcbb1c180799b2
		String signature = processor.genSignature(String.valueOf(_time), String.valueOf(37));
		boolean b =processor.checkSignature(signature,String.valueOf(_time),String.valueOf(37));
		System.out.println(b);
	}
	
	
	
    @RequestProcessing(value = "/wechat.do", method = HTTPRequestMethod.POST)
    public void doPost(final HttpServletRequest request, final HTTPRequestContext context) {
    	// 消息的接收、处理、响应
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        try {
			request.setCharacterEncoding("UTF-8");
			LOGGER.log(Level.INFO, "recive the message from wechat..");
	        context.getResponse().setCharacterEncoding("UTF-8");
	
	        // 调用核心业务类接收消息、处理消息
	        String respXml = wechatService.processRequest(request);
//	        String respXml = processRequest(request);
	
	        // 响应消息
	        PrintWriter out = context.getResponse().getWriter();
	        out.print(respXml);
	        out.close();
        } catch (UnsupportedEncodingException e) {
        	LOGGER.log(Level.ERROR, "response to user message error unsupported encoding", e);
		} catch (IOException e) {
			LOGGER.log(Level.ERROR, "response to user message error io exception", e);
		}
    }
	
	
	 @RequestProcessing(value = "/wechat.do", method = HTTPRequestMethod.GET)
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
	        String tmpStr = null;  
	  
	        try {  
	        	tmpStr= DecriptUtil.SHA1(content.toString());
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        System.out.println(tmpStr);
	        return tmpStr;
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
	        	tmpStr= DecriptUtil.SHA1(content.toString());
	        } catch (Exception e) {  
	        	LOGGER.log(Level.ERROR, "signature check error", e);
	        }  
	  
	        content = null;  
	        LOGGER.log(Level.INFO,"加密排序后的字符串：" + tmpStr);
	        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信  
	        return tmpStr != null ? tmpStr.equals(signature) : false;  
	    } 
}
