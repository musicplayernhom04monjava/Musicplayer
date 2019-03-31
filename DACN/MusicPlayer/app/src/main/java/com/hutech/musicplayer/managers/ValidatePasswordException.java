package com.hutech.musicplayer.managers;
enum ErrorPassword{
    empty,
    fail;

}
public class ValidatePasswordException extends Exception {
    ErrorPassword error;
    ValidatePasswordException(ErrorPassword error)
    {
        this.error = error;
    }
    public String message(){
        switch (error){
            case empty: return "Vui lòng nhập mật khẩu";
            //case fail: return "Mật khẩu không đúng";
            default: return "";
        }
    }
}
