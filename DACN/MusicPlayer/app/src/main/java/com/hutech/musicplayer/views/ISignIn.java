package com.hutech.musicplayer.views;

import com.hutech.musicplayer.managers.ErrorCode;
import com.hutech.musicplayer.models.User;

public interface ISignIn {
    void loginFailure(ErrorCode errorCode);
    void loginSuccess();
}
