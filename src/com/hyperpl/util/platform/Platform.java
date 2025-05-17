package com.hyperpl.util.platform;

import java.util.Locale;

public abstract class Platform {

	public static String getFileSeperator() {
		return System.getProperty("file.separator");
	}

	public static String getOSName() {
		return System.getProperty("os.name");
	}

	public static String getLineSeperator() {
		return System.getProperty("line.separator");
	}

	public static String getTmpDir() {
		return System.getProperty("java.io.tmpdir");
	}

	public static String getUserDirectory() {
		return System.getProperty("user.dir");
	}

	public static String getUserName() {
		return System.getProperty("user.name");
	}

	public static String getFileEncoding() {
		return System.getProperty("file.encoding");
	}

	public static String getUserHome() {
		return System.getProperty("user.home");
	}

	public static String getOSVersion() {
		return System.getProperty("os.version");
	}

	public static String getJavaVersion() {
		return System.getProperty("java.version");
	}

	public static String getJavaSpecificationVersion() {
		return System.getProperty("java.specification.version");
	}

	public static String getJavaRuntimeVersion() {
		return System.getProperty("java.runtime.version");
	}

	public static double getJavaVersionAsNumber() {
		return Double.parseDouble(getJavaSpecificationVersion());
	}

	public static String getJavaArchitecture() {
		return " (" + System.getProperty("sun.arch.data.model") + "-bit)";
	}

	public static String getUserLanguage() {
		return System.getProperty("user.language");
	}

	public static String getJavaHome() {
		return System.getProperty("java.home");
	}

	public static boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
		return os != null && os.startsWith("windows");
	}

	public static boolean isLinux() {
		String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);;
		return os.toLowerCase().indexOf("nux") >= 0;
	}

	public static boolean isMacOsX() {
		String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);;
		return os.toLowerCase().indexOf("mac") >= 0;
	}

	public static String getOSArchitecture() {
		return System.getProperty("os.arch");
	}

	public static String getOSDir() {
		String dir = "windir";
		if (isWindows()) {
			dir = "windir";
		} else if (isLinux()) {
			dir = "LD_LIBRARY_PATH";
		} else if (isMacOsX()) {
			dir = "PATH";
		}

		return System.getenv(dir);
	}
}
