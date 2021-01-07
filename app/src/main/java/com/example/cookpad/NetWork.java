package com.example.cookpad;

public class NetWork {
    private static NetWork obj;
    private String SERVER ;

    private NetWork(){
        SERVER="192.168.122.1:8999";
    }
    public static NetWork getNetworkInfoHolder(){
        if(obj==null){
            synchronized (NetWork.class){
                if(obj==null){
                    obj=new NetWork();
                }
            }
        }
        return obj;
    }
    public String getSERVER(){
        return SERVER;
    }
}
