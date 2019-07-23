// Copyright 2016 Baidu Inc. All rights reserved.

package com.jtools.io;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Object save to file.
 *
 * @author Li Wenwei(liwenwei@baidu.com)
 */
public class ObjectUtils {
    private static Log logger = LogFactory.getLog(ObjectUtils.class);

    public static void objectToFile(Object obj, File file) {
        ObjectOutputStream save = null;
        try {
            FileOutputStream saveFile = new FileOutputStream(file);

            save = new ObjectOutputStream(saveFile);

            // Now we do the save.
            save.writeObject(obj);
        } catch (Exception e) {
            logger.error("Object to file failed.", e);
        } finally {
            IOUtils.closeQuietly(save);
        }
    }

    public static Object fileToObject(File file) {
        ObjectInputStream save = null;
        try {
            FileInputStream saveFile = new FileInputStream(file);

            save = new ObjectInputStream(saveFile);

            return save.readObject();
        } catch (Exception e) {
            logger.error("Object to file failed.", e);
        } finally {
            IOUtils.closeQuietly(save);
        }
        return null;
    }
}
