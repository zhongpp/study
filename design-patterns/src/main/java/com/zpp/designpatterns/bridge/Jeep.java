package com.zpp.designpatterns.bridge;

/**
 * @author pingpingZhong
 * Date: 2018/3/23
 * Time: 10:49
 */
public class Jeep extends Car {

    public Jeep(Engine engine) {
        super(engine);
    }
    @Override
    public void installEngine() {
        System.out.print("Jeep:");
        this.getEngine().installEngine();
    }

}
