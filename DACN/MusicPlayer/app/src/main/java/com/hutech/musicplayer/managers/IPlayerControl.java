package com.hutech.musicplayer.managers;

public interface IPlayerControl {
    void loadMedia(String resourceId);
    void play();
    void pause();
    void next();
    void previous();
    void seekTo(int position);
    void reset();
    void stop();
}
