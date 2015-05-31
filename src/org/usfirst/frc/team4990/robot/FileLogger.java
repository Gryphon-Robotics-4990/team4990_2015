package org.usfirst.frc.team4990.robot;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLogger {

	private File log;
	PrintWriter pw;
	
	FileLogger(String filePath)
	{
		this.log = new File(filePath);
		if(!this.log.isFile() )
		{
			try {
				this.log.getParentFile().mkdirs();
				this.log.createNewFile();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
		}
		try {
			this.pw = new PrintWriter(
					new BufferedWriter(
							new FileWriter(log) ), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	public void writeToLog(String text)
	{
		DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/YYYY");
		Date date = new Date();
		
		try {
			this.pw.println(df.format(date) + "      "  + text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
