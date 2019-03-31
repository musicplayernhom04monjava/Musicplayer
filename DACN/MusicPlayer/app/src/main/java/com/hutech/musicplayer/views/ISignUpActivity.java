package com.hutech.musicplayer.views;

import com.hutech.musicplayer.managers.ErrorCode;

public interface ISignUpActivity {
    void onSuccess(String message);
    void onFailure(ErrorCode errorCode);
}
