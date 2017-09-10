package org.b3log.solo.wechat.message;

import org.b3log.solo.wechat.message.model.Music;

/**
 * 
 * @author Rocky.Wang
 *
 */
public class MusicMessage extends BaseMessage {
    // 音乐
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}