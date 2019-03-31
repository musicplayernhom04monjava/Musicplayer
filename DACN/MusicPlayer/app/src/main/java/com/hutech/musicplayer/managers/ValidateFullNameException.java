package com.hutech.musicplayer.managers;
enum ErrorFullName{
    empty;
}
public class ValidateFullNameException extends Exception {
    ErrorFullName error;
    public ValidateFullNameException(ErrorFullName error){
        this.error = error;
    }
    public String message(){
        switch (error){
            case empty: return "Vui lòng nhập họ và tên";
            default: return "";
        }
    }
}
