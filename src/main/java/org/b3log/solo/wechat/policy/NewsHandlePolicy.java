package org.b3log.solo.wechat.policy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.b3log.latke.Keys;
import org.b3log.latke.Latkes;
import org.b3log.latke.ioc.inject.Inject;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.solo.repository.ArticleRepository;
import org.b3log.solo.service.ArchiveDateQueryService;
import org.b3log.solo.wechat.WechatMessageType;
import org.b3log.solo.wechat.message.BaseMessage;
import org.b3log.solo.wechat.message.NewsMessage;
import org.b3log.solo.wechat.message.TextMessage;
import org.b3log.solo.wechat.message.model.Article;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * 返回图文消息
 * @author Rocky.Wang
 *
 */
public class NewsHandlePolicy extends AbstractHandlePolicy{
	
    private ArticleRepository articleRepository;
    
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(NewsHandlePolicy.class);

	public NewsHandlePolicy(TextMessage sourceMessage,ArticleRepository articleRepository) {
		super(sourceMessage);
		this.articleRepository = articleRepository;
	}

	@Override
	protected BaseMessage hook(String toUserName, String fromUserName) {
		// TODO Auto-generated method stub
		NewsMessage news = new NewsMessage();
		news.setToUserName(fromUserName);
    	news.setFromUserName(toUserName);
    	news.setCreateTime(new Date().getTime());
    	news.setMsgType(WechatMessageType.NEWS.msgType);
    	
    	//获取最新博客
    	try {
    		List<Article> articles = new ArrayList<Article>();
    		List<JSONObject> jsonResult = articleRepository.getRecentArticles(8);
    	
//	    	final String articleId = articles.get(0).getString(Keys.OBJECT_ID);
	    	for(JSONObject json : jsonResult) {
	    		String title = json.getString(org.b3log.solo.model.Article.ARTICLE_TITLE);
	    		String url = json.getString(org.b3log.solo.model.Article.ARTICLE_PERMALINK);
//	    		String picUrl = json.getString(org.b3log.solo.model.Article.ARTICLE_TITLE);
//	    		String desc = json.getString(org.b3log.solo.model.Article.ARTICLE_TITLE);
	    		articles.add(createArticle(title,url,null,""));
	    	}
	    	news.setArticleCount(articles.size());
	    	news.setArticles(articles);
    	} catch (final RepositoryException e) {
            LOGGER.log(Level.ERROR, "Gets recent articles failed", e);
        } catch (JSONException e) {
        	 LOGGER.log(Level.ERROR, "Gets recent articles failed", e);
		}
    	return news;
	}
	
	private Article createArticle(String title,String url,String picUrl,String desc) {
    	Article e = new Article();
    	e.setDescription(desc);
    	picUrl = "https://mmbiz.qlogo.cn/mmbiz_jpg/11VlyVBbCjvEHRUrXqpIiapibVZNPKPQ1O54hEpiccGKV4SKl6ibWfEkEayegePuDjWDcdEfclJHMLx6zpfHmNicrng/0?wx_fmt=jpeg";
    	e.setPicUrl(picUrl);
    	e.setTitle(title);
//    	String url = "http://blog.jrocky.com/articles/2017/09/07/1504787732284.html";
//    	e.setUrl(Latkes.getServerHost() + url);
    	e.setUrl(debugHost + url);
    	
    	return e;
    }
	
}
