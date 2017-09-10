package org.b3log.solo.wechat.policy;

import java.util.Date;

import org.b3log.solo.wechat.WechatMessageType;
import org.b3log.solo.wechat.menu.Menu;
import org.b3log.solo.wechat.menu.MenuItem;
import org.b3log.solo.wechat.message.BaseMessage;
import org.b3log.solo.wechat.message.TextMessage;
/**
 * 返回菜单文本
 * @author Rocky.Wang
 *
 */
public class MenuHandlePolicy extends AbstractHandlePolicy{
	
	private Menu menu;

	public MenuHandlePolicy(TextMessage sourceMessage,Menu menu) {
		super(sourceMessage);
		this.menu = menu;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected BaseMessage hook(String toUserName, String fromUserName) {
		// TODO Auto-generated method stub
		TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(WechatMessageType.TEXT.msgType);
        textMessage.setContent(menu.getMenu4Text());
     	return textMessage;
	}

}
