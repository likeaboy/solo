package org.b3log.solo.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.b3log.latke.logging.Logger;
import org.b3log.solo.wechat.WechatEventType;
import org.b3log.solo.wechat.WechatMessageType;
import org.b3log.solo.wechat.menu.MenuManager;
import org.b3log.solo.wechat.message.BaseMessage;
import org.b3log.solo.wechat.message.TextMessage;
/**
 * 微信服务抽象类
 * @author Rocky.Wang
 *
 */
public abstract class AbstractWechatService {
	
	/**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(AbstractWechatService.class);
    
    protected abstract BaseMessage handleTextMessage(TextMessage sourceMessage,HttpServletRequest request);
    
    protected BaseMessage handleMessage(Map<String, String> requestMap,HttpServletRequest request) {
    	// 发送方帐号
        String fromUserName = requestMap.get("FromUserName");
        // 开发者微信号
        String toUserName = requestMap.get("ToUserName");
        // 消息内容
        String content = requestMap.get("Content");
        // 消息类型
        String msgType = requestMap.get("MsgType");
        
        //构建用户发送的消息
        TextMessage sourceMessage = new TextMessage();
        sourceMessage.setFromUserName(fromUserName);
        sourceMessage.setToUserName(toUserName);
        sourceMessage.setContent(content==null?content:content.trim());
        sourceMessage.setMsgType(msgType);
        
        WechatMessageType messageType = WechatMessageType.get(msgType);
        
    	// 回复文本消息
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(WechatMessageType.TEXT.msgType);
    	
    	
    	 switch(messageType) {
         // 文本消息
         case TEXT :
        	 return handleTextMessage(sourceMessage,request);
         // 图片消息	
         case IMAGE :
        	 textMessage.setContent(WechatMessageType.IMAGE.content);
         	return textMessage;
         // 语音消息
         case VOICE :
        	 textMessage.setContent(WechatMessageType.VOICE.content);
        	 return textMessage;
         // 视频消息
         case VIDEO :
        	 textMessage.setContent(WechatMessageType.VIDEO.content);
        	 return textMessage;
         // 小视频消息
         case SHORTVIDEO :
        	 textMessage.setContent(WechatMessageType.SHORTVIDEO.content);
        	 return textMessage;
         // 地理位置消息
         case LOCATION :
        	 textMessage.setContent(WechatMessageType.LOCATION.content);
        	 return textMessage;
         // 链接消息
         case LINK:
        	 textMessage.setContent(WechatMessageType.LINK.content);
        	 return textMessage;
         // 事件消息
         case EVENT :
        	 String eventType = requestMap.get("Event");
        	 textMessage.setContent( handleEvent(WechatEventType.get(eventType)));
        	 return textMessage;
		default:
			 textMessage.setContent(WechatMessageType.UNKOWN.content);
			 return textMessage;
         }
    }
    
    protected String handleEvent(WechatEventType evtType) {
    	switch(evtType) {
    	// 关注
    	case SUBSCRIBE:
    		return MenuManager.getInstance().getDict().get(0).getMenu4Text();
		// 取消关注
    	case UNSUBSCRIBE:
		// 扫描带参数二维码
    	case SCAN:
		// 上报地理位置
    	case LOCATION:
		 // 自定义菜单
    	case CLICK:
    		return "暂不支持！";
		default:
			return "未知的事件类型";
    	}
    }
}
