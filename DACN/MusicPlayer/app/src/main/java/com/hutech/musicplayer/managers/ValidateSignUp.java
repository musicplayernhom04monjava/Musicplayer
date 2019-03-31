package com.hutech.musicplayer.managers;

public class ValidateSignUp implements IValidate {
    ValidateFullName validateFullName;
    ValidatePassword validatePassword;
    ValidateUser validateUser;
    public ValidateSignUp(String userName, String password, String fullName){
        validateFullName = new ValidateFullName(fullName);
        validatePassword = new ValidatePassword(password);
        validateUser = new ValidateUser(userName);
    }
    @Override
    public boolean validate() throws Exception {
        return validateUser.validate() && validatePassword.validate() && validateFullName.validate();
    }
}
