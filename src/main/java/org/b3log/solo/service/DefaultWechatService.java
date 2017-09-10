package org.b3log.solo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.service.annotation.Service;
import org.b3log.solo.repository.ArticleRepository;
import org.b3log.solo.wechat.HandleType;
import org.b3log.solo.wechat.UserInputMessageCode;
import org.b3log.solo.wechat.WechatEventType;
import org.b3log.solo.wechat.WechatMessageType;
import org.b3log.solo.wechat.menu.Menu;
import org.b3log.solo.wechat.menu.MenuConstant;
import org.b3log.solo.wechat.menu.MenuItem;
import org.b3log.solo.wechat.menu.MenuManager;
import org.b3log.solo.wechat.message.BaseMessage;
import org.b3log.solo.wechat.message.NewsMessage;
import org.b3log.solo.wechat.message.TextMessage;
import org.b3log.solo.wechat.message.model.Article;
import org.b3log.solo.wechat.policy.DefaultHandlePolicy;
import org.b3log.solo.wechat.policy.IHandlePolicy;
import org.b3log.solo.wechat.policy.ListHandlePolicy;
import org.b3log.solo.wechat.policy.MenuHandlePolicy;
import org.b3log.solo.wechat.policy.NewsHandlePolicy;
import org.b3log.solo.wechat.policy.PlainHandlePolicy;
import org.b3log.solo.wechat.util.MessageUtil;
/**
 * 默认微信服务
 * @author Rocky.Wang
 *
 */
@Service
public class DefaultWechatService extends AbstractWechatService{

	/**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(DefaultWechatService.class);
    
    /**
     * Article repository.
     */
    @Inject
    private ArticleRepository articleRepository;
    
    /**
     * 
     * 根据用户发送的编码返回图文消息（NEWS类型）
     * NEWS类型xml结构：
     * <xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[news]]></MsgType>
		<ArticleCount>2</ArticleCount>
		<Articles>
		<item>
		<Title><![CDATA[title1]]></Title> 
		<Description><![CDATA[description1]]></Description>
		<PicUrl><![CDATA[picurl]]></PicUrl>
		<Url><![CDATA[url]]></Url>
		</item>
		<item>
		<Title><![CDATA[title]]></Title>
		<Description><![CDATA[description]]></Description>
		<PicUrl><![CDATA[picurl]]></PicUrl>
		<Url><![CDATA[url]]></Url>
		</item>
		</Articles>
		</xml>
     * @param requestMap
     * @return
     */
    @Override
	protected BaseMessage handleTextMessage(TextMessage sourceMessage,HttpServletRequest request) {
		// TODO Auto-generated method stub
//    	TextMessage sourceMessage = createSourceMessage(requestMap);
    	TextMessage returnMessage = new TextMessage();
    	returnMessage.setFromUserName(sourceMessage.getToUserName());
    	returnMessage.setToUserName(sourceMessage.getFromUserName());
    	returnMessage.setCreateTime(new Date().getTime());
    	returnMessage.setMsgType(WechatMessageType.TEXT.msgType);
        int code = 0;
        try {
        	code = Integer.parseInt(sourceMessage.getContent());
        	
        	if(code > UserInputMessageCode.values().length-1) {
        		returnMessage.setContent(MenuConstant.HELP);
        		MenuManager.getInstance().getVisitContext().put(sourceMessage.getFromUserName(),0);
                return returnMessage;
        	}
        }catch(Exception e){
        	 UserInputMessageCode inputCode = UserInputMessageCode.get(code);
        	 //中文字符？？？
             if(sourceMessage.getContent().equals(inputCode.alias) || sourceMessage.getContent().equals("？"))
            	 returnMessage.setContent(MenuManager.getInstance().getDict().get(0).getMenu4Text());
             else
            	 returnMessage.setContent(MenuConstant.HELP);
            MenuManager.getInstance().getVisitContext().put(sourceMessage.getFromUserName(),0);
            return returnMessage;
        }
        
        return doProcess(sourceMessage,code);
	}
    
    
    private BaseMessage doProcess(TextMessage sourceMessage,int code) {
    	int defCode = 0;
    	if(MenuManager.getInstance().getVisitContext().containsKey(sourceMessage.getFromUserName())) {
    		defCode = MenuManager.getInstance().getVisitContext().get(sourceMessage.getFromUserName());
        }
    	
    	Menu menu = MenuManager.getInstance().getDict().get(defCode < code ? code : defCode);
    	if(menu == null)
    		menu = MenuManager.getInstance().getDict().get(defCode);
    	
    	IHandlePolicy policy;
    	HandleType ht = UserInputMessageCode.get(code).ht;
    	switch(ht) {
	    	case RETURN_MENU:
	    		MenuManager.getInstance().getDict();
	    		
	    		policy = new MenuHandlePolicy(sourceMessage, menu);
	    		MenuManager.getInstance().getVisitContext().put(sourceMessage.getFromUserName(),menu.getCode());
	    		break;
	    	case RETURN_LIST:
	    		policy = new ListHandlePolicy(sourceMessage,articleRepository);
	    		break;
	    	case RETURN_NEWS:
	    		policy = new NewsHandlePolicy(sourceMessage,articleRepository);
	    		break;
	    	case RETURN_PLAIN:
	    		MenuItem item=null;
	    		List<MenuItem> items = menu.getMenuItemList();
	    		for(MenuItem e : items){
	    			if(e.getUserInputCode().equals(UserInputMessageCode.get(code))) {
	    				item = e;
	    				break;
	    			}
	    		}
	    		policy = new PlainHandlePolicy(sourceMessage,item);
	    		break;
			default :
				policy = new DefaultHandlePolicy(sourceMessage);
		        MenuManager.getInstance().getVisitContext().put(sourceMessage.getFromUserName(),0);
    	}
    	return policy.process();
	}
    
    /**
     * 处理微信发来的请求，目前仅支持文本消息处理
     * @param request
     * @return xml
     */
    public String processRequest(HttpServletRequest request) {
    	// xml格式的消息数据
        String respXml = null;
        try {
            // 调用parseXml方法解析请求消息
            Map<String, String> requestMap = MessageUtil.parseXml(request);
           BaseMessage message =  handleMessage(requestMap,request);
            // 将文本消息对象转换成xml
            respXml = MessageUtil.messageToXml(message);
        } catch (Exception e) {
        	LOGGER.log(Level.ERROR, "response error", e);
        }
        return respXml;
    }
	
}
