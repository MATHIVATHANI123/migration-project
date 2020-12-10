/**
 * 
 */
package com.grootan.migration.service;

import java.io.PrintWriter;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Mathivathani
 *
 */
public interface MigrationService {

	public int saveDetails(MultipartFile file);

	public void downloadCsv(PrintWriter writer);

}
