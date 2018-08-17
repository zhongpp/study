package com.zpp.designpatterns.decorator;

/**
 * @author pingpingZhong
 * Date: 2018/3/23
 * Time: 13:58
 */
public class Main {

    public static void main(String[] args) {
        //普通美国女孩
        Girl g1 = new AmericanGirl();
        System.out.println(g1.getDescription());

        //喜欢科学的
        Science g2 = new Science(g1);
        System.out.println(g2.getDescription());

        //喜欢艺术的
        Art g3 = new Art(g2);
        System.out.println(g3.getDescription());
    }

}
