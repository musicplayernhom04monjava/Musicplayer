package com.hutech.musicplayer.managers;
enum ErrorUser{
    empty,
    fail;
}
public class  ValidateUserException extends Exception {

    ErrorUser error;
    ValidateUserException(ErrorUser error)
    {
        this.error = error;
    }
    public String message(){
        switch (error){
            case empty: return "Vui lòng nhập tên đăng nhập";
            //case fail: return "Tên đăng nhập không đúng";
            default: return "";
        }
    }
}
