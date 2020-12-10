/**
 * 
 */
package com.grootan.migration.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.grootan.migration.bean.MigrationBean;
import com.grootan.migration.controller.MigrationHelper;
import com.grootan.migration.dao.MigrationDao;

/**
 * @author Mathivathani
 *
 */
@Service("migrationService")
public class MigrationServiceImpl implements MigrationService {

	@Autowired
	MigrationDao migrationDao;
	
	List<MigrationBean> invalidUserInfoList = new ArrayList<MigrationBean>();
	
	@Override
	public int saveDetails(MultipartFile file) {
		int count = 0;
		try {
		      List<MigrationBean> userInfoList = MigrationHelper.csvToBean(file.getInputStream());
		      
		      for(MigrationBean userinfo: userInfoList) {
		    	  if(!("".equals(userinfo.getUserName()) || userinfo.getUserName() == null || "".equals(userinfo.getFirstName()) || userinfo.getFirstName() == null || 
		    			  "".equals(userinfo.getLastName()) || userinfo.getLastName() == null || "".equals(userinfo.getEmail()) || userinfo.getEmail() == null || 
		    			  "".equals(userinfo.getPassword()) || userinfo.getPassword() == null || "".equals(userinfo.getAddress()) || userinfo.getAddress() == null)) {
		    		  boolean result=migrationDao.saveDetails(userinfo);
				    	if(!result) {
				    		invalidUserInfoList.add(userinfo);
				    	}
		    	  }else {
		    		  invalidUserInfoList.add(userinfo);
		    	  }
		      }
		      count = invalidUserInfoList.size();
		    } catch (IOException e) {
		      throw new RuntimeException("fail to store csv data: " + e.getMessage());
		    }
		return count;
	}

	@Override
	public void downloadCsv(PrintWriter writer) {
		writer.write("User Name, First Name, Last Name, Email, Password, Address \n");
        for (MigrationBean userInfo : invalidUserInfoList) {
			/*
			 * if("".equals(userInfo.getUserName()) || userInfo.getUserName() == null ) {
			 * userInfo.setUserName(""); } if("".equals(userInfo.getFirstName()) ||
			 * userInfo.getFirstName() == null ) { userInfo.setFirstName("--NA--"); }
			 * if("".equals(userInfo.getLastName()) || userInfo.getLastName() == null ) {
			 * userInfo.setLastName("--NA--"); } if("".equals(userInfo.getEmail()) ||
			 * userInfo.getEmail() == null ) { userInfo.setEmail("--NA--"); }
			 * if("".equals(userInfo.getPassword()) || userInfo.getPassword() == null ) {
			 * userInfo.setPassword("--NA--"); } if("".equals(userInfo.getAddress()) ||
			 * userInfo.getAddress() == null ) { userInfo.setAddress("--NA--"); }
			 */
            writer.write(userInfo.getUserName() + "," + userInfo.getFirstName() + "," + userInfo.getLastName() +  "," + 
            		userInfo.getEmail() +  ","  + userInfo.getPassword() +  "," + userInfo.getAddress() + "\n");
        }
	}

}
