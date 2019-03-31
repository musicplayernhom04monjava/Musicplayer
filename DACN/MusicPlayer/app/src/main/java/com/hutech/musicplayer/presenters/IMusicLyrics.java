package com.hutech.musicplayer.presenters;

import com.hutech.musicplayer.models.LrcRow;

import java.util.List;

public interface IMusicLyrics {
    void getLyrics(List<LrcRow> lyrics);
    void getLyrics(String lyrics);
}
