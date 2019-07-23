// Copyright 2016 Baidu Inc. All rights reserved.

package com.jtools.io;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Object utils test.
 *
 * @author Li Wenwei(liwenwei@baidu.com)
 */
public class ObjectUtilsTest {

    @Test
    public void testWriteObject() {
        // Create some data objects for us to save.
        boolean powerSwitch = true;
        int x = 9, y = 150, z = 675;
        String name = "Galormadron", setting = "on", plant = "rutabaga";
        ArrayList stuff = new ArrayList();
        stuff.add("One");
        stuff.add("Two");
        stuff.add("Three");
        stuff.add("Four");
        stuff.add("Five");

        try { // Catch errors in I/O if necessary.
              // Open a file to write to, named SavedObj.sav.
            FileOutputStream saveFile = new FileOutputStream("/tmp/SaveObj.sav");

            // Create an ObjectOutputStream to put objects into save file.
            ObjectOutputStream save = new ObjectOutputStream(saveFile);

            // Now we do the save.
            save.writeObject(powerSwitch);
            save.writeObject(x);
            save.writeObject(y);
            save.writeObject(z);
            save.writeObject(name);
            save.writeObject(setting);
            save.writeObject(plant);
            save.writeObject(stuff);

            // Close the file.
            save.close(); // This also closes saveFile.
        } catch (Exception exc) {
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }

    @Test
    public void testReadObject() {
        // Create the data objects for us to restore.
        boolean powerSwitch = false;
        int x = 0, y = 0, z = 0;
        String name = "", setting = "", plant = "";
        ArrayList stuff = new ArrayList();

        // Wrap all in a try/catch block to trap I/O errors.
        try {
            // Open file to read from, named SavedObj.sav.
            FileInputStream saveFile = new FileInputStream("SaveObj.sav");

            // Create an ObjectInputStream to get objects from save file.
            ObjectInputStream save = new ObjectInputStream(saveFile);

            // Now we do the restore.
            // readObject() returns a generic Object, we cast those back
            // into their original class type.
            // For primitive types, use the corresponding reference class.
            powerSwitch = (Boolean) save.readObject();
            x = (Integer) save.readObject();
            y = (Integer) save.readObject();
            z = (Integer) save.readObject();
            name = (String) save.readObject();
            setting = (String) save.readObject();
            plant = (String) save.readObject();
            stuff = (ArrayList) save.readObject();

            // Close the file.
            save.close(); // This also closes saveFile.
        } catch (Exception exc) {
            exc.printStackTrace(); // If there was an error, print the info.
        }

        // Print the values, to see that they've been recovered.
        System.out.println("\nRestored Object Values:\n");
        System.out.println("\tpowerSwitch: " + powerSwitch);
        System.out.println("\tx=" + x + " y=" + y + " z=" + z);
        System.out.println("\tname: " + name);
        System.out.println("\tsetting: " + setting);
        System.out.println("\tplant: " + plant);
        System.out.println("\tContents of stuff: ");
        System.out.println("\t\t" + stuff);
        System.out.println();

        // All done.
    }
}
