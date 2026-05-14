package com.nttdata.qa.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class CsvDataReader {

    public static List<String[]> readAll(String classpathResource) {
        try (CSVReader reader = new CSVReader(
                new InputStreamReader(
                    Objects.requireNonNull(
                        CsvDataReader.class.getClassLoader().getResourceAsStream(classpathResource),
                        "Resource not found: " + classpathResource
                    )
                )
        )) {
            List<String[]> rows = reader.readAll();
            if (!rows.isEmpty()) rows.remove(0);
            return rows;
        } catch (IOException | CsvException e) {
            throw new RuntimeException("Failed to read CSV: " + classpathResource, e);
        }
    }

    public static final int COL_USERNAME      = 0;
    public static final int COL_PASSWORD      = 1;
    public static final int COL_FIRST_NAME    = 2;
    public static final int COL_LAST_NAME     = 3;
    public static final int COL_ZIP_CODE      = 4;
    public static final int COL_PRODUCT_COUNT = 5;
}
