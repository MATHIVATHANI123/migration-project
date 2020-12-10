/**
 * 
 */
package com.grootan.migration.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.grootan.migration.bean.MigrationBean;


/**
 * @author Mathivathani
 *
 */
@Service("migrationDao")
public class MigrationDaoImpl implements MigrationDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(MigrationDaoImpl.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
		
	@Override
	public boolean saveDetails(MigrationBean userinfo) {
		
		String query = "insert into userdetails(username,firstname,lastname,email,passwd,address)values(:username,:firstname,:lastname,:email,:password,:address)";
		boolean	result = false;
		Map<String,String> inputparam = new HashMap<String,String>();
		inputparam.put("username", userinfo.getUserName());
		inputparam.put("firstname", userinfo.getFirstName());
		inputparam.put("lastname", userinfo.getLastName());
		inputparam.put("email", userinfo.getEmail());
		inputparam.put("password", userinfo.getPassword());
		inputparam.put("address", userinfo.getAddress());
		int count=namedParameterJdbcTemplate.update(query, inputparam);
		if(count>0){
			result=true;
			System.out.println("Count:: "+count);
		}
		
		return result;
	}

}
