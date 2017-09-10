package org.b3log.solo.wechat;
/**
 * 微信推送消息类型
 * @author Rocky.Wang
 *
 */
public enum WechatEventType {
	
	SUBSCRIBE("subscribe","订阅事件"),
	UNSUBSCRIBE("unsubscribe","订阅事件"),
	SCAN("scan","订阅事件"),
	LOCATION("LOCATION","订阅事件"),
	CLICK("CLICK","订阅事件");
	
	public final String eventType;
	public final String content;
	
	private WechatEventType(String _eventType,String _content){
		this.eventType = _eventType;
		this.content = _content;
	}
	
	public static WechatEventType get(String eventType) {
		return WechatEventType.valueOf(eventType.toUpperCase());
	}
}
