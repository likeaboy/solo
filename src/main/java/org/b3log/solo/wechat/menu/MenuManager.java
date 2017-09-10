package org.b3log.solo.wechat.menu;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.b3log.solo.wechat.UserInputMessageCode;
/**
 * 菜单管理器
 * @author Rocky.Wang
 *
 */
public class MenuManager {
	
	private final static MenuManager instance = new MenuManager();
	private Map<Integer,Menu> dict = new ConcurrentHashMap<>();
	private Map<String,Integer> visitContext = new ConcurrentHashMap<>();
	
	private MenuManager() {
		Menu rootMenu = IMenuOperation.Factory.create();
		String aboutAuthor = "作者：Rocky Wang，4年互联网研发经验，干过后端，做过架构，曾就职于58，乐视等知名互联网公司。";
		rootMenu.addMenuItme(new MenuItem("获取最新博客 /:<W>",UserInputMessageCode.GET_LEAST_BLOGS_LIST,null,null));
		rootMenu.addMenuItme(new MenuItem("关于作者 \ue513",UserInputMessageCode.GET_AUTHOR,null,null,aboutAuthor));
		rootMenu.addMenuItme(new MenuItem("获取博客列表 /:gift",UserInputMessageCode.GET_ALL_BLOGS_LIST,null,null));
		//测试二级菜单
//		MenuItem testItme = new MenuItem("特种服务号码",UserInputMessageCode.TEST,null,null);
//		rootMenu.addMenuItme(testItme);
		dict.put(rootMenu.getCode(), rootMenu);
		
//		Menu testMenu = IMenuOperation.Factory.create(true,testItme,UserInputMessageCode.TEST.code,"常用特种服务号码列表：");
		
//		testItme.setChild(testMenu);
//		testMenu.addMenuItme(new MenuItem("火警119",UserInputMessageCode.FIRE,null,null));
//		testMenu.addMenuItme(new MenuItem("急救120",UserInputMessageCode.THIEF,null,null));
//		testMenu.addMenuItme(new MenuItem("匪警110",UserInputMessageCode.POLICE,null,null));
		
//		dict.put(testMenu.getCode(), testMenu);
	}
	
	public static MenuManager getInstance() {
		return instance;
	}

	public Map<String, Integer> getVisitContext() {
		return visitContext;
	}

	public void setVisitContext(Map<String, Integer> visitContext) {
		this.visitContext = visitContext;
	}

	public Map<Integer, Menu> getDict() {
		return dict;
	}
}
