// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.command;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import org.junit.Test;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * The Argus4jTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class Argus4jTest {

    @Test
    public void testArgs() {
        String[] args = new String[] {"-t", "rensanning", "-f", "c:/aa.txt", "-b", "-s", "10", "-D", "key1=value1" , "-D", "key2=value2"};

        try {
            Args4jOptions options = new Args4jOptions();
            CmdLineParser parser = new CmdLineParser(options);

            // print usage
            parser.printUsage(System.out);
            System.out.println();

            parser.parseArgument(args);

            // check the options have been set correctly
            System.out.println(options);
            System.out.println(options.getText());
            System.out.println(options.getFile().getName());
            if(options.isBol()) {
                System.out.println(new Date());
            }
            System.out.println(options.getSize());
            System.out.println(options.getProperties().get("key1"));
            System.out.println(options.getProperties().get("key2"));

        } catch (Exception ex) {
            System.out.println("Unexpected exception:" + ex.getMessage());
        }
    }

    @Data
    public static class Args4jOptions {
        @Option(name = "-t", aliases = "-text", usage = "use given information(String)")
        private String text;
        @Option(name = "-b", usage = "display current time(boolean)")
        private boolean bol = false;
        @Option(name = "-s", aliases = "-size", usage = "use given size(Integer)")
        private int size = 0;
        @Option(name = "-f", aliases = { "-file" }, metaVar = "<file>", usage = "use given file(File)")
        private File file;

        private Map<String, String> properties = new HashMap<String, String>();
        @Option(name = "-D", metaVar = "<property>=<value>", usage = "use value for given property(property=value)")
        public void setProperty(final String property) {
            String[] arr = property.split("=");
            properties.put(arr[0], arr[1]);
        }
    }
}
