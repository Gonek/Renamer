package hu.deadcode.renamer.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ConfigController {

	private static final String CONFIG_FILE = "config.properties";

	private static Properties config = loadConfigFile();

	private static Properties loadConfigFile() {
		Properties config = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(CONFIG_FILE);
			config.load(input);
		} catch (IOException ex) {
			System.out.println("Config file missing! The program create a default!");
			config = createDefaultConfigFile();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					System.out.println("ERROR! " + e.getMessage());
				}
			}
		}
		return config;
	}

	private static Properties createDefaultConfigFile() {
		Properties config = new Properties();
		OutputStream output = null;
		try {
			output = new FileOutputStream("config.properties");
			config.setProperty("input_path", "D:\\képek\\Input\\");
			config.setProperty("output_path", "D:\\képek\\");
			config.setProperty("file_types", "JPG, JPEG, MOV, MP4");
			config.store(output, null);
		} catch (IOException io) {
			System.out.println("ERROR! Default configuration file not created");
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					System.out.println("ERROR! " + e.getMessage());
				}
			}
		}
		return config;
	}

	public static void checkConfigParams() throws IOException {
		File inputPath = new File(getInputPath());
		if (!(inputPath.exists() && inputPath.isDirectory())) {
			throw new IOException("Incorrect input path");
		}

		File outputPath = new File(getOutputPath());
		if (!(outputPath.exists() && outputPath.isDirectory())) {
			throw new IOException("Incorrect output path");
		}

	}

	public static String getInputPath() {
		return config.getProperty("input_path");
	}

	public static String getOutputPath() {
		return config.getProperty("output_path");
	}

	public static List<String> getFileTypes() {
		String fileTypes = config.getProperty("file_types");
		return Arrays.asList(fileTypes.split(","));
	}

}
