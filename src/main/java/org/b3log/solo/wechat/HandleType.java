package org.b3log.solo.wechat;
/**
 * 处理方式
 * @author Rocky.Wang
 *
 */
public enum HandleType {
	//返回menu菜单
	RETURN_MENU,
	//返回图文消息列表
	RETURN_NEWS,
	//返回超链接列表
	RETURN_LIST,
	//返回MenuItem中的name信息
	RETURN_PLAIN;
	
}
