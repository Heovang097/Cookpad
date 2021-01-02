package com.example.cookpad;

public class AccountInfo {
    private static AccountInfo obj;
    private String _userID;

    private AccountInfo(){
        _userID="";
    }
    public static AccountInfo getAccountInfoHolder(){
        if(obj==null){
            synchronized (AccountInfo.class){
                if(obj==null){
                    obj=new AccountInfo();
                }
            }
        }
        return obj;
    }
    public void setupInfo(String userID){
        _userID = userID;
    }
    public String getUserID(){
        return _userID;
    }
}
