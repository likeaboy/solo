package org.b3log.solo.wechat.message;

/**
 * 文本消息
 * @author Rocky.Wang
 *
 */
public class TextMessage extends BaseMessage {
    // 回复的消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}