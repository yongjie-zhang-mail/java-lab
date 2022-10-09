package com.yj.lab.log.generator;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author zhangyj21
 */
@Slf4j
public class LogGenerator {

    static String readFileName;
    static String writeFileName;

    public static void main(String[] args) {
        readFileName = args[0];
        writeFileName = args[1];
        readFileByLines(readFileName);
    }

    public static void readFileByLines(String fileName) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        String tempString = null;
        try {
            log.info("以行为单位，读取文件内容： ");
            fis = new FileInputStream(fileName);
            isr = new InputStreamReader(fis, "GBK");
            br = new BufferedReader(isr);
            int count = 0;
            while ((tempString = br.readLine()) != null) {
                count++;
                Thread.sleep(300);
                String str = new String(tempString.getBytes(StandardCharsets.UTF_8), "GBK");
                log.info("row: " + count + ">>>>>>>>" + tempString);
                writeFile(writeFileName, tempString);
            }
            isr.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    public static void writeFile(String file, String content) {

        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            out.write("\n");
            out.write(content);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}


































