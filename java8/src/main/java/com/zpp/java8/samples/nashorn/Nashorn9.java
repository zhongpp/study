package com.zpp.java8.samples.nashorn;


import jdk.nashorn.api.scripting.NashornScriptEngine;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author Benjamin Winterberg
 */
public class Nashorn9 {

    public static void main(String[] args) throws Exception {
        NashornScriptEngine engine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
        File file = FileUtil.findFile(Nashorn9.class, "nashorn9.js");
        engine.eval("load('"+file.getPath().replaceAll("\\\\","//")+"')");

        long t0 = System.nanoTime();

        double result = 0;
        for (int i = 0; i < 1000; i++) {
            double num = (double) engine.invokeFunction("testPerf");
            result += num;
        }

        System.out.println(result > 0);

        long took = System.nanoTime() - t0;
        System.out.format("Elapsed time: %d ms", TimeUnit.NANOSECONDS.toMillis(took));
    }
}
