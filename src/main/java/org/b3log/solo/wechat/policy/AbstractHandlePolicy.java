package org.b3log.solo.wechat.policy;

import org.b3log.solo.wechat.message.BaseMessage;
import org.b3log.solo.wechat.message.TextMessage;
/**
 * 抽象处理策略
 * @author Rocky.Wang
 *
 */
public abstract class AbstractHandlePolicy implements IHandlePolicy{
	
	private TextMessage sourceMessage;
	
	public AbstractHandlePolicy(TextMessage sourceMessage) {
		this.sourceMessage = sourceMessage;
	}

	@Override
	public BaseMessage process() {
		// TODO Auto-generated method stub
		return hook(sourceMessage.getToUserName(),sourceMessage.getFromUserName());
	}
	
	protected abstract BaseMessage hook(String toUserName,String fromUserName);

}
