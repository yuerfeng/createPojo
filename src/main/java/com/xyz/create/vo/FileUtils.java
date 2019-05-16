package com.xyz.create.vo;

import java.io.File;

public class FileUtils {
	public static void createNewFolder(String fullPath) {
		try {
			File dir = new File(fullPath);
			if (!dir.exists()) {
				dir.mkdir();
			} else {
				String[] subfiles = dir.list();
				for (String fileName : subfiles) {
					System.console().printf(fullPath + File.separator + fileName + " delete");
					new File(fullPath + File.separator + fileName).delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
