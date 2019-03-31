package com.hutech.musicplayer.managers;

public class ValidateFullName implements IValidate {
    String fullName;
    public ValidateFullName(String fullName){
        this.fullName = fullName;
    }
    @Override
    public boolean validate() throws Exception {
        if(fullName.isEmpty()){
            throw new ValidateFullNameException(ErrorFullName.empty);
        }
        return true;
    }
}
