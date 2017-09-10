package org.b3log.solo.wechat.message;

import org.b3log.solo.wechat.message.model.Image;

/**
 * 
 * @author Rocky.Wang
 *
 */
public class ImageMessage extends BaseMessage {
    
    private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }
}