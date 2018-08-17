package com.zpp.java8.samples.nashorn;


import com.zpp.java8.samples.lambda.Person;
import jdk.nashorn.api.scripting.NashornScriptEngine;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;

/**
 * @author Benjamin Winterberg
 */
public class Nashorn8 {
    public static void main(String[] args) throws Exception {
        NashornScriptEngine engine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");

        File file = FileUtil.findFile(Nashorn8.class, "nashorn8.js");
        engine.eval("load('"+file.getPath().replaceAll("\\\\","//")+"')");

        engine.invokeFunction("evaluate1");                             // [object global]
        engine.invokeFunction("evaluate2");                             // [object Object]
        engine.invokeFunction("evaluate3", "Foobar");                   // Foobar
        engine.invokeFunction("evaluate3", new Person("John", "Doe"));  // [object global] <- ???????
    }

}
