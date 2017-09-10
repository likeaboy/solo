package org.b3log.solo.wechat.policy;

import org.b3log.solo.wechat.message.BaseMessage;

/**
 * 处理策略
 * @author Rocky.Wang
 *
 */
public interface IHandlePolicy {
	
//	public String debugHost = "http://ga7aaa.natappfree.cc";
	public String debugHost = "http://blog.jrocky.com";
	
	
	BaseMessage process();
}
