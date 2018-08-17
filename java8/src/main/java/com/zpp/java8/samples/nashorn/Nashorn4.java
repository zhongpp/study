package com.zpp.java8.samples.nashorn;



import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;

/**
 * Working with java types from javascript.
 *
 * @author Benjamin Winterberg
 */
public class Nashorn4 {

    public static void main(String[] args) throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
//        engine.eval("loadWithNewGlobal('res/nashorn4.js')");


        File file = FileUtil.findFile(Nashorn4.class, "nashorn4.js");
        engine.eval("loadWithNewGlobal('" + file.getPath().replaceAll("\\\\","//") + "')");
//        engine.eval("loadWithNewGlobal('D://IdeaProjects//study-demo//build//resources//main//res//nashorn4.js')");

    }

}