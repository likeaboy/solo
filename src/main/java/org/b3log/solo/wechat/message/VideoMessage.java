package org.b3log.solo.wechat.message;

import org.b3log.solo.wechat.message.model.Video;

/**
 * 
 * @author Rocky.Wang
 *
 */
public class VideoMessage extends BaseMessage {
    // 视频
    private Video Video;

    public Video getVideo() {
        return Video;
    }

    public void setVideo(Video video) {
        Video = video;
    }
}