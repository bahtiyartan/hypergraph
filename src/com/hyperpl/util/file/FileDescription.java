package com.hyperpl.util.file;

import java.io.File;
import java.io.Serializable;

@SuppressWarnings("serial")
public class FileDescription implements Serializable {

	private static final String SEPERATOR = "\u009A";

	private String fileName;
	private boolean isDirectory;
	private long size;
	private long changedAt;

	public FileDescription(String name, boolean isdir, long size, long changedAt) {
		this.fileName = name;
		this.isDirectory = isdir;
		this.size = size;
		this.changedAt = changedAt;
	}

	public FileDescription(File file) {
		this(file.getName(), file.isDirectory(), file.length(), file.lastModified());
	}

	public static FileDescription deserialize(String info) {

		String[] str = info.split(FileDescription.SEPERATOR);
		boolean isDirectory = "true".equals(str[1]);

		return new FileDescription(str[0], isDirectory, Long.parseLong(str[2]), Long.parseLong(str[3]));
	}

	public String serialize() {
		StringBuilder sb = new StringBuilder();

		sb.append(fileName).append(FileDescription.SEPERATOR);
		sb.append(Boolean.toString(isDirectory)).append(SEPERATOR);
		sb.append(size).append(FileDescription.SEPERATOR);
		sb.append(changedAt);

		return sb.toString();
	}

	// for only debugging
	public String toString() {
		return super.toString() + serialize();
	}

	public boolean isDirectory() {
		return isDirectory;
	}

	public String getName() {
		return fileName;
	}

	public long getLength() {
		return size;
	}

	public long changedAt() {
		return changedAt;
	}
}
