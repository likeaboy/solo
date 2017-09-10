package org.b3log.solo.wechat.menu;

import java.util.ArrayList;
import java.util.List;
/**
 * 文本菜单
 * @author Rocky.Wang
 *
 */
public class Menu implements IMenuOperation{

		private boolean hasParent;
		private MenuItem parent;
		//输入码
		private int code;
		private String title;
		private List<MenuItem> items = new ArrayList<MenuItem>();
		
		public Menu() {
			this(false,null,0,MenuConstant.WELCOME_TITLE);
		}
		
		public Menu(boolean hasParent,MenuItem parent,int code,String title) {
			this.hasParent = hasParent;
			this.parent = parent;
			this.code = code;
			this.title = title;
		}
		
		@Override
		public String getMenu4Text() {
			// TODO Auto-generated method stub
			StringBuilder menuText = new StringBuilder();
			menuText.append(title).append(MenuConstant.DOUBLE_NEW_LINE);
			for(MenuItem e : items) {
				menuText.append(e.getUserInputCode().code).append(MenuConstant.BLANK).append(MenuConstant.BLANK);
				menuText.append(e.getName()).append(MenuConstant.NEW_LINE);
			}
			menuText.append(MenuConstant.NEW_LINE);
			menuText.append(MenuConstant.HELP);
			return menuText.toString();
		}

		@Override
		public void addMenuItme(MenuItem e) {
			// TODO Auto-generated method stub
			items.add(e);
		}

		@Override
		public void remoteMenuItem() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}

		@Override
		public List<MenuItem> getMenuItemList() {
			return items;
		}

		public boolean isHasParent() {
			return hasParent;
		}

		public void setHasParent(boolean hasParent) {
			this.hasParent = hasParent;
		}

		public MenuItem getParent() {
			return parent;
		}

		public void setParent(MenuItem parent) {
			this.parent = parent;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}