package org.b3log.solo.wechat.menu;

import java.util.List;
/**
 * 菜单操作接口
 * @author Rocky.Wang
 *
 */
public interface IMenuOperation {

	String getMenu4Text();
	
	void addMenuItme(MenuItem e);
	
	void remoteMenuItem();
	
	List<MenuItem> getMenuItemList();
	
	public static class Factory{
		public static Menu create() {
			return new Menu();
		}
		
		public static Menu create(boolean hasParent,MenuItem parent,int level,String title) {
			return new Menu(hasParent,parent,level,title);
		}
	}
}
