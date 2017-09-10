package org.b3log.solo.wechat;
/**
 * 微信消息类型
 * @author Rocky.Wang
 *
 */
public enum WechatMessageType {
	
	TEXT("text","消息类型"),
	IMAGE("image","图片类型"),
	VOICE("voice","语音类型"),
	VIDEO("video","视频类型"),
	SHORTVIDEO("shortvideo","小视频类型"),
	LOCATION("location","地理位置类型"),
	LINK("link","链接类型"),
	MUSIC("music","音乐类型"),
	NEWS("news","图文类型"),
	EVENT("event","事件类型"),
	UNKOWN("unkown","未知类型");
	
	public final String msgType;
	public final String content;
	
	private WechatMessageType(String _msgType,String _content){
		this.msgType = _msgType;
		this.content = _content;
	}
	
	public static WechatMessageType get(String msgType) {
		return WechatMessageType.valueOf(msgType.toUpperCase());
	}
}
