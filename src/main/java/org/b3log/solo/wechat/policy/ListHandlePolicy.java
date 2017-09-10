package org.b3log.solo.wechat.policy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.solo.repository.ArticleRepository;
import org.b3log.solo.wechat.WechatMessageType;
import org.b3log.solo.wechat.menu.MenuConstant;
import org.b3log.solo.wechat.menu.MenuItem;
import org.b3log.solo.wechat.message.BaseMessage;
import org.b3log.solo.wechat.message.TextMessage;
import org.b3log.solo.wechat.message.model.Article;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * 返回超链接列表文本
 * @author Rocky.Wang
 *
 */
public class ListHandlePolicy extends AbstractHandlePolicy{
	
	private ArticleRepository articleRepository;
	
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ListHandlePolicy.class);
	
	public ListHandlePolicy(TextMessage sourceMessage,ArticleRepository articleRepository) {
		super(sourceMessage);
		this.articleRepository = articleRepository;
	}

	@Override
	protected BaseMessage hook(String toUserName, String fromUserName) {
		// TODO Auto-generated method stub
		TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(WechatMessageType.TEXT.msgType);
        //读H2获取博客title数据，组装为超链接的列表形式
        StringBuilder content = new StringBuilder("博客列表：");
        content.append(MenuConstant.DOUBLE_NEW_LINE);
    	try {
    		List<JSONObject> jsonResult = articleRepository.getRecentArticles(8);
    	
	    	for(JSONObject json : jsonResult) {
	    		String title = json.getString(org.b3log.solo.model.Article.ARTICLE_TITLE);
	    		String url = json.getString(org.b3log.solo.model.Article.ARTICLE_PERMALINK);
	    		content.append("<a href=\""+debugHost+url+"\">《"+title+"》</a>").append(MenuConstant.NEW_LINE);
	    	}
    	} catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets recent articles failed", e);
        } catch (JSONException e) {
        	 LOGGER.log(Level.ERROR, "Gets recent articles failed", e);
		}
        textMessage.setContent(content.toString());
//		 textMessage.setContent("作者：Rocky Wang，4年互联网研发经验，干过后端，做过架构，曾就职于58，乐视等知名互联网公司。");
     	return textMessage;
	}

}
