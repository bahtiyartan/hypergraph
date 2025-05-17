package com.hyperpl.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.hyperpl.util.platform.Platform;


public abstract class FileAccess {

	private static String DefaultEncoding = "UTF-8";
	private static long tempFileNameIndex = 0;

	public static boolean ensureFolder(String folderPath) {
		try {
			File path = new File(folderPath);
			if (!path.exists()) {
				path.mkdirs();
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean ensureFolderForFile(String filePath) {
		try {
			filePath = filePath.replace('\\', '/');
			filePath = filePath.replace("/", File.separator);

			int tmpint = filePath.lastIndexOf(File.separator);

			if (tmpint != -1) {
				filePath = filePath.substring(0, tmpint);
				return ensureFolder(filePath);
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean ensureFile(String file) {

		File path = new File(FileAccess.getFolderPart(file));
		if (!path.exists()) {
			path.mkdirs();
		}

		File fOutPut = new File(file);
		if (!fOutPut.exists()) {
			try {
				fOutPut.createNewFile();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return false;
			}
		}

		return true;
	}

	public static byte[] getByteFromFile(String path) {
		File iFile = null;
		try {
			iFile = new File(path);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!iFile.exists()) {
			System.out.println("File not found FileAccess.getByteFromFile() " + path);
		}
		byte[] arryBuffer = new byte[(int) iFile.length()];
		InputStream iOs = null;
		try {
			iOs = new FileInputStream(iFile);
			iOs.read(arryBuffer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (iOs != null) {
				try {
					iOs.close();
				} catch (IOException e) {
				}
			}
		}
		return arryBuffer;
	}

	public static boolean write(String path, String content) {
		return writeWithEncoding(path, content, DefaultEncoding);
	}

	public static boolean writeWithEncoding(String path, String content, String encoding) {

		if (ensureFile(path)) {

			File fOutPut = new File(path);
			try {
				FileOutputStream fileOut = new FileOutputStream(fOutPut);
				fileOut.write(content.getBytes(encoding));
				fileOut.close();
				return true;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				return false;
			}
		}

		return false;
	}

	@SuppressWarnings("resource")
	public static boolean writeWithEncoding2(String path, String content, String encoding) {

		try {
			File iFile = new File(path);
			FileChannel iChannel = new RandomAccessFile(iFile, "rw").getChannel();
			byte[] bytes = content.getBytes(encoding);
			ByteBuffer bb = iChannel.map(FileChannel.MapMode.READ_WRITE, 0, bytes.length);
			bb.put(bytes);
			iChannel.close();
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return false;
	}

	public static boolean writeWithEncoding3(String path, String content, String encoding) {

		File fOutPut = new File(path);
		try {
			FileOutputStream fileOut = new FileOutputStream(fOutPut);
			fileOut.write(content.getBytes(encoding));
			fileOut.close();
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public static boolean appendToFile(String path, String content) {

		if (ensureFile(path)) {
			try {
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true));
				bufferedWriter.write(content);
				bufferedWriter.close();
				return true;
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}

		return false;
	}

	public static boolean appendToFileNewLine(String path, String content) {

		if (ensureFile(path)) {
			try {
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true));
				bufferedWriter.write(content);
				bufferedWriter.newLine();
				bufferedWriter.close();
				return true;
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}

		return false;
	}

	public static byte[] readByte(String path) {
		byte[] arrReturn = new byte[0];
		try {
			byte[] arrBuffer = new byte[1024];
			File jFile = new File(path);

			if (!jFile.exists()) {
				return arrReturn;
			}

			FileInputStream fileInput = new FileInputStream(jFile);
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

			int nLength = 0;
			while (nLength != -1) {
				byteOut.write(arrBuffer, 0, nLength);
				nLength = fileInput.read(arrBuffer);
			}

			byteOut.close();
			fileInput.close();

			arrReturn = byteOut.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}

		return arrReturn;
	}

	public static String readString(String path) {
		byte[] bytData = readByte(path);
		return new String(bytData);
	}

	public static String readString(String path, String encoding) {
		byte[] bytData = readByte(path);
		try {
			return new String(bytData, encoding);
		} catch (UnsupportedEncodingException e) {
			System.err.println(e.getMessage());
		}

		return "";
	}

	public static boolean copyFile(File jSource, File jDestination) {
		try {
			BufferedInputStream jIn = new BufferedInputStream(new FileInputStream(jSource));
			BufferedOutputStream jOut = new BufferedOutputStream(new FileOutputStream(jDestination));

			byte[] jBuffer = new byte[1024];
			int nLength = 0;
			while ((nLength = jIn.read(jBuffer)) > 0) {
				jOut.write(jBuffer, 0, nLength);
			}

			jIn.close();
			jOut.flush();
			jOut.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	public static ArrayList<FileDescription> listDirectory(String path) {
		ArrayList<FileDescription> vFileList = new ArrayList<FileDescription>();
		try {

			File jDirectorList = new File(path);
			if (jDirectorList.exists()) {
				if (jDirectorList.isDirectory()) {
					File[] fArray = jDirectorList.listFiles();
					for (int i = 0; i < fArray.length; i++) {
						vFileList.add(new FileDescription(fArray[i]));
					}
				} else {
					vFileList.add(new FileDescription(jDirectorList));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vFileList;
	}

	public static String getFolderPart(String path) {

		String tmpstr = path;

		int strindex1 = path.lastIndexOf("\\");
		int strindex2 = path.lastIndexOf("/");
		strindex1 = Math.max(strindex1, strindex2);
		if (strindex1 != -1) {
			tmpstr = path.substring(0, strindex1);
		}

		return tmpstr.trim();
	}

	public static boolean exists(String path) {
		try {
			File jFile = new File(path);
			return jFile.exists();
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean delete(String path) {
		try {
			return new File(path).delete();
		} catch (Exception e) {
			return false;
		}
	}

	public static String getAbsolutePath(String path) {
		try {
			return new File(path).getAbsolutePath();
		} catch (Exception e) {
			return "";
		}
	}

	public static long getFileSize(String fileName) {
		try {
			Path path = Paths.get(fileName);
			return Files.size(path);
		} catch (IOException e) {
			return 0l;
		}
	}

	public static synchronized String getTemporaryFileName(String fileName) {
		tempFileNameIndex++;
		if (tempFileNameIndex == Long.MAX_VALUE) { //to avoid overflow
			tempFileNameIndex = 0;
		}

		String tempName = Platform.getTmpDir() + Platform.getFileSeperator() + Long.toString(tempFileNameIndex);
		int nIndex = fileName.lastIndexOf(".");
		if (nIndex > 0) {
			tempName = tempName + fileName.substring(nIndex);
		}

		return tempName;
	}

	// test method
	public static void main(String[] args) {
		ArrayList<FileDescription> list = FileAccess.listDirectory("C:\\TMP\\");

		for (FileDescription fileDescription : list) {
			FileDescription deserialized = FileDescription.deserialize(fileDescription.serialize());
			System.out.print(deserialized.serialize().equals(fileDescription.serialize()));
			System.out.print("\t");
			System.out.println(fileDescription.serialize());
		}
	}
}
