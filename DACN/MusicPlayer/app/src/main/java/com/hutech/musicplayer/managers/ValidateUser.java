package com.hutech.musicplayer.managers;

public class ValidateUser implements IValidate {
    String user;

    //String username; //username từ firebase
    public ValidateUser(String user) {
        this.user = user;
    }

    @Override
    public boolean validate() throws Exception {

        if (user.isEmpty()) {
            throw new ValidateUserException(ErrorUser.empty);
        }
        return true;
    }
}
