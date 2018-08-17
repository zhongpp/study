package com.zpp.designpatterns.bridge;

/**
 * Bridge 模式又叫做桥接模式，是构造型的设计模式之一。
 * Bridge模式基于类的最小设计原则，通过使用封装，
 * 聚合以及继承等行为来让不同的类承担不同的责任。
 * 它的主要特点是把抽象（abstraction）与行为实现（implementation）分离开来，
 * 从而可以保持各部分的独立性以及应对它们的功能扩展。
 *
 * @author pingpingZhong
 * Date: 2018/3/23
 * Time: 10:49
 */
public class Main {
    public static void main(String[] args) {
        Engine engine2000 = new Engine2000();
        Engine engine2200 = new Engine2200();

        Car bus = new Bus(engine2000);
        bus.installEngine();

        Car jeep = new Jeep(engine2200);
        jeep.installEngine();
    }
}
