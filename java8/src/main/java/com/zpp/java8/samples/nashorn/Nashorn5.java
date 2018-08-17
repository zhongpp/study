package com.zpp.java8.samples.nashorn;



import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;

/**
 * Bind java objects to custom javascript objects.
 *
 * @author Benjamin Winterberg
 */
public class Nashorn5 {

    public static void main(String[] args) throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

        File file = FileUtil.findFile(Nashorn5.class, "nashorn5.js");
        engine.eval("load('"+file.getPath().replaceAll("\\\\","//")+"')");

        Invocable invocable = (Invocable) engine;

        Product product = new Product();
        product.setName("Rubber");
        product.setPrice(1.99);
        product.setStock(1037);

        Object result = invocable.invokeFunction("getValueOfGoods", product);
        System.out.println(result);
    }

}