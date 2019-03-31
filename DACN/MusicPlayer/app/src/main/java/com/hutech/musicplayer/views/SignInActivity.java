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

    private void setUp_View()
    {
        btn_Login = findViewById(R.id.btn_signin);
        btn_Login.setOnClickListener(this);
        btn_Register = findViewById(R.id.btn_register);
        btn_Register.setOnClickListener(this);
        edt_Username = findViewById(R.id.edt_username);
        edt_Password = findViewById(R.id.edt_password);
    }
    private void checkLogin()
    {
        try{
            String username = edt_Username.getText().toString();
            String password = edt_Password.getText().toString();
            ValidateSignIn validate = new ValidateSignIn(username, password);
            validate.validate();
            btn_Login.setEnabled(false);
            mpresenter.login(username.trim(),password.trim());
        } catch (ValidateUserException ex) {
            Log.d("SignInActivity","Exception: "+ex.message());
            super.dialog(ex.message());
            //validateSignIn.getMessage();
        }catch (ValidatePasswordException ex){
            super.dialog(ex.message());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private void register()
    {
        try {
            intent = new Intent(this, SignUpActivity.class);
            this.startActivity(intent);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_signin:

                checkLogin();
//                if(edt_Username.getText().length() > 0 && edt_Password.getText().length() > 0){
//
//                }else {
//                    Toast.makeText(this,"Vui lòng nhập tên đăng nhập và mật khẩu",Toast.LENGTH_LONG).show();
//                    btn_Login.setEnabled(true);
//                }

                break;
            case R.id.btn_register:
                register();
                break;
        }

    }


    @Override
    public void loginFailure(ErrorCode errorCode) {
        super.dialog(errorCode.message());
        btn_Login.setEnabled(true);
//       // Toast.makeText(this,errorCode.message(),Toast.LENGTH_LONG).show();
//        AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
//        builder.setTitle("Thông báo")
//                .setMessage(errorCode.message())
//                .setPositiveButton("Đồng ý",null)
//                .show();
    }

    @Override
    public void loginSuccess() {
        btn_Login.setEnabled(true);
        Log.d("SignInActivity","Login success");
        Toast.makeText(this,"Đăng nhập thành công!",Toast.LENGTH_LONG);
        intent = new Intent(this,DetailMusicActivity.class);
        this.startActivity(intent);
        finish();
    }

    @Override
    public void onNetWorkStateChange(boolean isCheck) {
        if(isCheck == false)
            super.dialog("Vui lòng mở wifi hoặc mạng di động!");
    }
}
