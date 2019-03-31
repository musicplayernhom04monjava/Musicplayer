package com.hutech.musicplayer.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hutech.musicplayer.views.SignInActivity;
import com.hutech.musicplayer.views.SignUpActivity;

public class BaseActivity extends AppCompatActivity {
    View viewClick;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void dialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage(message)
                .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

//    public void disableDoubleClick(){
//        viewClick.setVisibility(View.VISIBLE);
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                viewClick.setVisibility(View.GONE);
//            }
//        }, 1000);
//    }
}
