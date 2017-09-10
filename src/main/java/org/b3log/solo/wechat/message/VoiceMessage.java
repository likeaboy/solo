package org.b3log.solo.wechat.message;

import org.b3log.solo.wechat.message.model.Voice;

/**
 * 
 * @author Rocky.Wang
 *
 */
public class VoiceMessage extends BaseMessage {
    // 语音
    private Voice Voice;

    public Voice getVoice() {
        return Voice;
    }

    public void setVoice(Voice voice) {
        Voice = voice;
    }
}