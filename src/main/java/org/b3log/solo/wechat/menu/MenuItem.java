package org.b3log.solo.wechat.menu;

import org.b3log.solo.wechat.UserInputMessageCode;
/**
 * 菜单项
 * @author Rocky.Wang
 *
 */
public class MenuItem {
	private String name;
	private String content;
	private UserInputMessageCode userInputCode;
	private Menu parent;
	private Menu child;
	
	public MenuItem() {}

	public MenuItem(String name, UserInputMessageCode userInputCode, Menu parent, Menu child,String content) {
		this.name = name;
		this.userInputCode = userInputCode;
		this.parent = parent;
		this.child = child;
		this.content = content;
	}
	
	public MenuItem(String name, UserInputMessageCode userInputCode, Menu parent, Menu child) {
		this(name,userInputCode,parent,child,name);
	}
	
	public boolean hasChildren() {
		if(child == null)
			return false;
		return true;
	}
	
	public UserInputMessageCode getUserInputCode() {
		return userInputCode;
	}

	public void setUserInputCode(UserInputMessageCode userInputCode) {
		this.userInputCode = userInputCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public Menu getChild() {
		return child;
	}

	public void setChild(Menu child) {
		this.child = child;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
