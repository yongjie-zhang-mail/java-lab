package com.yj.lab.spring.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class IoTests {

    public static void main(String[] args) {
        try {
            File file = new File("IoTests.java");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String a;
            while (((a = br.readLine()) != null)) {
                System.out.println(a);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
