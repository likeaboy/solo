package org.b3log.solo.wechat;
/**
 * 用户输入码
 * @author Rocky.Wang
 *
 */
public enum UserInputMessageCode {
	GET_MENU(0,"?",HandleType.RETURN_MENU),
	GET_LEAST_BLOGS_LIST(1,"leastlist",HandleType.RETURN_NEWS),
	GET_AUTHOR(2,"about",HandleType.RETURN_PLAIN),
	GET_ALL_BLOGS_LIST(3,"bloglist",HandleType.RETURN_LIST),
	TEST(4,"test",HandleType.RETURN_MENU),
	FIRE(5,"fire",HandleType.RETURN_PLAIN),
	THIEF(6,"thief",HandleType.RETURN_PLAIN),
	POLICE(7,"police",HandleType.RETURN_PLAIN);
	
	public final int code;
	public final String alias;
	public final HandleType ht;
	
	private UserInputMessageCode(int _code,String alias,HandleType ht){
		this.code = _code;
		this.alias = alias;
		this.ht = ht;
	}
	
	public static UserInputMessageCode get(int code) {
		return UserInputMessageCode.values()[code];
	}
}
