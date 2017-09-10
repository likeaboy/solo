package org.b3log.solo.wechat.policy;

import java.util.Date;

import org.b3log.solo.wechat.WechatMessageType;
import org.b3log.solo.wechat.menu.MenuItem;
import org.b3log.solo.wechat.menu.MenuManager;
import org.b3log.solo.wechat.message.BaseMessage;
import org.b3log.solo.wechat.message.TextMessage;
/**
 * 默认处理策略
 * @author Rocky.Wang
 *
 */
public class DefaultHandlePolicy extends AbstractHandlePolicy{
	
	public DefaultHandlePolicy(TextMessage sourceMessage) {
		super(sourceMessage);
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
        textMessage.setContent(MenuManager.getInstance().getDict().get(0).getMenu4Text());
//		 textMessage.setContent("作者：Rocky Wang，4年互联网研发经验，干过后端，做过架构，曾就职于58，乐视等知名互联网公司。");
     	return textMessage;
	}

}
