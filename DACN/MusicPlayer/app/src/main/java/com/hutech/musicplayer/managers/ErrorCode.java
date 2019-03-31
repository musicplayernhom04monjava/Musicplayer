package com.hutech.musicplayer.managers;

public enum ErrorCode {
    userNotExists,
    userExists,
    userIncorrect,
    registerError,
    searchFail;
    public String message() {
        switch (this){
            case userNotExists: return "Tên đăng nhập không tồn tại";
            case userIncorrect: return "Sai tên đăng nhập hoặc mật khẩu";
            case userExists: return "Tài khoản đã tồn tại";
            case registerError: return "Đăng ký thất bại";
            case searchFail: return "Không tìm thấy bài hát này";
            default: return "";
        }
    }
}

