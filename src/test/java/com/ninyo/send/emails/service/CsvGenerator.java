package com.ninyo.send.emails.service;

import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvGenerator {

    private static CsvGenerator instance = null;
    private static final Logger logger = Logger.getLogger(CsvGenerator.class);
    private static final String CSV_FILE_NAME = "src/test/resources/Users.csv";
    private int linesNumber;

    public CsvGenerator(int linesNumber) {
        this.linesNumber = linesNumber;
    }

    public void generate() throws IOException {
        StringBuilder outputBuilder = new StringBuilder();
        for (int i = 1; i <= linesNumber; i++) {
            String line = "email" + i + "@xxx.com;" + "firstName" + i + ";lastName" + i + "\n";
            outputBuilder.append(line);
        }
        writeToFile(outputBuilder.toString(), CSV_FILE_NAME);
    }

    private void writeToFile(String output, String fileName) throws IOException {
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter(fileName);
            bw = new BufferedWriter(fw);
            bw.write(output);
        } catch (IOException e) {
            logger.error(e);
            throw e;
        } finally {
            close(bw);
            close(fw);
        }
    }

    private void close(BufferedWriter writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

    private void close(FileWriter writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

}