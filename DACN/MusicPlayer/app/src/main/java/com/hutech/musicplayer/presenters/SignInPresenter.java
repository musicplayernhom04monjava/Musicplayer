package com.hutech.musicplayer.presenters;

import android.util.Log;

import com.hutech.musicplayer.managers.ErrorCode;
import com.hutech.musicplayer.managers.FirebaseManager;
import com.hutech.musicplayer.managers.SessionManager;
import com.hutech.musicplayer.models.Song;
import com.hutech.musicplayer.models.User;
import com.hutech.musicplayer.views.ISignIn;

import java.util.List;

public class SignInPresenter implements IFirebase{

    ISignIn mSignin;
    public SignInPresenter(ISignIn v){
        this.mSignin = v;
    }
    public void login(String username, String password){
        FirebaseManager.getInstance(this).login(username,password);
    }

    @Override
    public void fireBaseCallBack(User user) {
        SessionManager.sharedInstance.createLoginSession(user);
        mSignin.loginSuccess();
    }

    @Override
    public void fireBaseErrorCallBack(ErrorCode code) {
        Log.d("SignIn Presenter","" + code.message() );
        mSignin.loginFailure(code);
    }

    @Override
    public void fireBaseCallBack(List<Song> songs) {

    }

    @Override
    public void fireBaseCallBackSearchSongs(List<Song> songs) {

    }

}
