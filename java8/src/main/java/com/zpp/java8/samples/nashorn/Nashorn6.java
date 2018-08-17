package com.zpp.java8.samples.nashorn;


import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;

/**
 * Using Backbone Models from Nashorn.
 *
 * @author Benjamin Winterberg
 */
public class Nashorn6 {

    public static void main(String[] args) throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

        File file = FileUtil.findFile(Nashorn6.class, "nashorn6.js");
        engine.eval("load('"+file.getPath().replaceAll("\\\\","//")+"')");


        Invocable invocable = (Invocable) engine;

        Product product = new Product();
        product.setName("Rubber");
        product.setPrice(1.99);
        product.setStock(1337);

        ScriptObjectMirror result = (ScriptObjectMirror)
                invocable.invokeFunction("calculate", product);
        System.out.println(result.get("name") + ": " + result.get("valueOfGoods"));
    }

    public static void getProduct(ScriptObjectMirror result) {
        System.out.println(result.get("name") + ": " + result.get("valueOfGoods"));
    }

}