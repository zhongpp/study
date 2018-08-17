package com.zpp.designpatterns.bridge;

/**
 * @author pingpingZhong
 * Date: 2018/3/23
 * Time: 10:48
 */
public class Bus extends Car {

    public Bus(Engine engine) {
        super(engine);
    }

    @Override
    public void installEngine() {
        System.out.print("Bus:");
        this.getEngine().installEngine();
    }

}
