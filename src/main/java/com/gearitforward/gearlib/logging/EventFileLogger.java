package com.gearitforward.gearlib.logging;

import java.io.IOException;
import java.util.function.DoubleSupplier;

/**
 * @author Patrick Ubelhor
 * @since 3/17/2022
 */
public class EventFileLogger extends FileLogger {
	
	private static final String EVENT_LOG_PREFIX = "events";
	
	
	/**
	 * Constructs a <code>EventFileLogger</code>.
	 *
	 * @param directories List of potential directories to store the log file. Will search until it
	 *     finds one that exists in this list.
	 * @param timeSupplier Provides the current timestamp to include in entries of the logfile.
	 */
	public EventFileLogger(String[] directories, DoubleSupplier timeSupplier) {
		super(EVENT_LOG_PREFIX, directories, timeSupplier);
	}
	
	/**
	 * Writes a new line to the event log file in the form `timestamp,mode,message`
	 * (csv format).
	 *
	 * @param mode The current game mode (or some other identifying name).
	 * @param message The event message.
	 */
	public void addEvent(String mode, String message) {
		if (fw == null) {
			return;
		}
		
		String line = String.format("%s,[%s],%s", getCurrentTime(), mode, message);
		
		try {
			fw.append(line).append("\n");
		} catch (IOException e) {
			System.err.println("Failed to run event logger");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		flush();
	}
}
