package com.zpp.designpatterns.bridge;

/**
 * @author pingpingZhong
 * Date: 2018/3/23
 * Time: 10:47
 */
public abstract class Car {

    private Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public abstract void installEngine();
}
