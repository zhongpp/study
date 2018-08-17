package com.zpp.lombok;

import lombok.Cleanup;
import lombok.Data;
import lombok.NonNull;
import lombok.val;

import java.io.*;
import java.util.ArrayList;

/**
 * @author pingpingZhong
 *         Date: 2017/6/21
 *         Time: 16:57
 */
public class LombokTest {

    /**
     * val 变量的使用
     * @return
     */
    public static String example() {
        val example = new ArrayList<String>();
        example.add("Hello, World!");
        val foo = example.get(0);
        return foo.toLowerCase();
    }

    /**
     * @NotNull
     * @param person
     */
    public static void testNull(@NonNull Person person) {
        System.out.println(person.getName());
    }

    /**
     * @Cleanup
     * @param args
     * @throws IOException
     */
    public static void testCleanup(String[] args) throws IOException {
        @Cleanup InputStream in = new FileInputStream(args[0]);
        @Cleanup OutputStream out = new FileOutputStream(args[1]);
        byte[] b = new byte[10000];
        while (true) {
            int r = in.read(b);
            if (r == -1) break;
            out.write(b, 0, r);
        }
    }

    /**
     * @RequiredArgsConstructor(staticName = "of")
     * @param args
     */

    /**
     * @ToString
     * @EqualsAndHashCode
     * @Getter(所有字段)
     * @Setter (所有非final字段)
     * @RequiredArgsConstructor
     * @param args
     */


    /**
     * @Value
     * @param args
     */
    public static void main(String[] args) {
        testNull(null);
    }
}

@Data
class Person{
    private String name;
}