package com.hutech.musicplayer.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hutech.musicplayer.R;
import com.hutech.musicplayer.base.BaseActivity;
import com.hutech.musicplayer.managers.ConnectionReceiver;
import com.hutech.musicplayer.managers.ErrorCode;
import com.hutech.musicplayer.managers.FirebaseManager;
import com.hutech.musicplayer.managers.ValidateFullNameException;
import com.hutech.musicplayer.managers.ValidatePasswordException;
import com.hutech.musicplayer.managers.ValidateSignUp;
import com.hutech.musicplayer.managers.ValidateUserException;

public class SignUpActivity extends BaseActivity implements ISignUpActivity, ConnectionReceiver.ListenerNetwork {

    EditText edtUserName, edtPassword, edtFullName;
    Button btnSignUp;
    FirebaseManager firebaseManager;
    ConnectionReceiver connectionReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        addControls();
        connectionReceiver = new ConnectionReceiver(this, this);
        addEvents();
    }

    private void addControls() {
        edtFullName = findViewById(R.id.edt_fullnameSignUp);
        edtUserName = findViewById(R.id.edt_usernameSignUp);
        edtPassword = findViewById(R.id.edt_passwordSignUp);
        btnSignUp = findViewById(R.id.btn_signup);
    }

    private void checkRegister(){
        try {
            String userName = edtUserName.getText().toString();
            String password = edtPassword.getText().toString();
            String fullName = edtFullName.getText().toString();
            ValidateSignUp validateSignUp = new ValidateSignUp(userName, password, fullName);
            validateSignUp.validate();
            btnSignUp.setEnabled(false);
            firebaseManager = new FirebaseManager(SignUpActivity.this);
            firebaseManager.createWithUser(userName.trim(), password.toString().trim(), fullName.toString().trim());
        }catch (ValidateUserException ex) {
            super.dialog(ex.message());
        }catch (ValidatePasswordException ex) {
            super.dialog(ex.message());
        }catch (ValidateFullNameException ex) {
            super.dialog(ex.message());
        }catch (Exception ex){ex.printStackTrace();}
    }
    private void addEvents()
    {
            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                       checkRegister();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                }
            });
    }

    public void dialog(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
        .setTitle("Thông báo")
        .setMessage(message)
        .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.create().show();
    }

    @Override
    public void onSuccess(String message) {
        dialog(message);
        btnSignUp.setEnabled(true);
    }

    @Override
    public void onFailure(ErrorCode errorCode) {
        super.dialog(errorCode.message());
        btnSignUp.setEnabled(true);
    }

    @Override
    public void onNetWorkStateChange(boolean isCheck) {
        if(isCheck == false)
            super.dialog("Vui lòng mở wifi hoặc mạng di động!");
    }
}
