// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * The DerbyTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class DerbyTest {

    public static void main(String[] args) {

        DerbyTest testClient = new DerbyTest();

        testClient.showZipCodes();

    }

    public void showZipCodes() {
        try {
            String driver = "org.apache.derby.jdbc.EmbeddedDriver";

            Class.forName(driver).newInstance();

            Connection conn = null;

            conn = DriverManager.getConnection

                    ("jdbc:derby://localhost:1527/myDB;user=sa;password=sa");

            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery("SELECT * FROM restaurants");

            while (rs.next()) {

                System.out.println("序号    :" + rs.getInt(1));

                System.out.println("名字    :" + rs.getString(2));

                System.out.println("城市    :" + rs.getString(3));

                System.out.println();

            }

            rs.close();

            s.close();

            conn.close();

        } catch (Exception e) {

            System.out.println("Exception: " + e);

            e.printStackTrace();

        }

    }

}
