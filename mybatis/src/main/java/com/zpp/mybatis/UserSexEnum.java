package com.zpp.mybatis;

/**
 * @author pingpingZhong
 *         Date: 2017/8/2
 *         Time: 16:16
 */
public enum UserSexEnum {

    WOMAN(0,"女性"),MAN(1,"男性");

    private int index;

    private String description;

    UserSexEnum(int index, String description){
        this.index = index;
        this.description = description;
    }

    public static void main(String[] args) {
        System.out.println(UserSexEnum.MAN.index);
        System.out.println(UserSexEnum.MAN.index);
    }
}
