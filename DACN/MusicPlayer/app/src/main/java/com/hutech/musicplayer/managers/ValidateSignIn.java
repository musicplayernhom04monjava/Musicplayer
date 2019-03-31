package com.hutech.musicplayer.managers;

public class ValidateSignIn implements IValidate{

    ValidateUser validateUser;
    ValidatePassword validatePassword;
    public ValidateSignIn(String username, String password){
        validateUser = new ValidateUser(username);
        validatePassword = new ValidatePassword(password);
    }
    @Override
    public boolean validate() throws Exception {
        return  validateUser.validate() && validatePassword.validate();
    }
}
