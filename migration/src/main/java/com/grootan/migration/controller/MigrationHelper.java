package com.grootan.migration.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.grootan.migration.bean.MigrationBean;


public class MigrationHelper {
	
  public static String TYPE = "application/vnd.ms-excel";
  static String[] HEADERs = { "userName", "firstName", "lastName", "password","email","address" };

  public static boolean hasCSVFormat(MultipartFile file) {

    if (!TYPE.equals(file.getContentType())) {
      return false;
    }

    return true;
  }

  public static List<MigrationBean> csvToBean(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

      List<MigrationBean> migrationBeanList = new ArrayList<MigrationBean>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {
    	  MigrationBean migrationBean = new MigrationBean(csvRecord.get(0),csvRecord.get("firstName"),csvRecord.get("lastName"),csvRecord.get("email"),csvRecord.get("password"),csvRecord.get("address"));
//System.out.println(csvRecord.get(0));
//System.out.println(csvRecord.get("firstName"));
//System.out.println(csvRecord.get("lastName"));
//System.out.println(csvRecord.get("email"));
//System.out.println(csvRecord.get("password"));
//System.out.println(csvRecord.get("address"));
    	  migrationBeanList.add(migrationBean);
      }

      return migrationBeanList;
    } catch (IOException e) {
    	e.printStackTrace();
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }

}