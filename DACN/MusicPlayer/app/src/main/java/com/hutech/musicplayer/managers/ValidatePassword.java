package com.hutech.musicplayer.managers;

//--------------------
//Validate password
public class ValidatePassword implements IValidate {
    String password;
    public ValidatePassword(String password)
    {
        this.password = password;
    }
    @Override
    public boolean validate() throws Exception {

        if (password.isEmpty())
        {
            throw new ValidatePasswordException(ErrorPassword.empty);
        }
        return true;
    }
}
