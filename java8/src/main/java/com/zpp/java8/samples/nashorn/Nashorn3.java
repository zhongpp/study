package com.zpp.java8.samples.nashorn;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileReader;

/**
 * Working with java types from javascript.
 *
 * @author Benjamin Winterberg
 */
public class Nashorn3 {

    public static void main(String[] args) throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        File file = FileUtil.findFile(Nashorn3.class,"nashorn3.js");
        engine.eval(new FileReader(file));
    }

}