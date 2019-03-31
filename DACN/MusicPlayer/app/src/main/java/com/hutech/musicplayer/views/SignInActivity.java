package com.hutech.musicplayer.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hutech.musicplayer.R;
import com.hutech.musicplayer.base.BaseActivity;
import com.hutech.musicplayer.managers.ConnectionReceiver;
import com.hutech.musicplayer.managers.ErrorCode;
import com.hutech.musicplayer.managers.ValidatePasswordException;
import com.hutech.musicplayer.managers.ValidateSignIn;
import com.hutech.musicplayer.managers.ValidateUserException;
import com.hutech.musicplayer.presenters.SignInPresenter;

public class SignInActivity extends BaseActivity implements View.OnClickListener, ISignIn, ConnectionReceiver.ListenerNetwork {
    SignInPresenter mpresenter;
    Button btn_Login, btn_Register;
    EditText edt_Username, edt_Password;
    Intent intent;
    ConnectionReceiver connectionReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        this.setUp_View();
        connectionReceiver = new ConnectionReceiver(this, this);
        mpresenter = new SignInPresenter(this);
//        HashMap<String,String> data = SessionManager.sharedInstance.getUserDetail();
//        Log.d("SignIn",data.get(SessionManager.KEY_USERNAME).toString());


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connectionReceiver.unRegister();
    }

    

    @Override
    public void onNetWorkStateChange(boolean isCheck) {
        if(isCheck == false)
            super.dialog("Vui lòng mở wifi hoặc mạng di động!");
    }
}
