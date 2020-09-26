package com.poly.printcode.Retrofit2;

public class APIUtils {
    public static final String Base_url = "http://192.168.43.138:8686/tinhvnpd_02843/asm/";
    public static DataClient getData(){
        return RetrofitClient.getClienr(Base_url).create(DataClient.class);
    }
}
