package fileManipulator;

import java.util.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.io.*;

public class File_Folder_Manipulator {

	private String rootDirectory = "";

	public File_Folder_Manipulator(String rootDir) {

		if (!Validator.isNullOrEmpty(rootDir))
			this.rootDirectory = rootDir;
	}

	public boolean createDirectory(String dirName) {
		File f = new File(dirName);
		try {
			if (f.mkdir()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.err.println("Error Occurred while creating Directory : " + dirName);
			return false;
		}
	}

	// root Directory
	public void getAllDirectoriesFromRoot(String dirPath, HashMap<String, List<String>> allFiles) {

		try {
			File dir = new File(dirPath);

			if (!allFiles.containsKey(dir.getAbsolutePath())) {
				allFiles.put(dir.getAbsolutePath(), new ArrayList<String>());
			}

			File[] files = dir.listFiles();

			for (File file : files) {

				if (file.isDirectory()) {
					String absolutePath = file.getAbsolutePath();
					// If Directories are found
					if (!allFiles.containsKey(absolutePath)) {
						allFiles.put(absolutePath, new ArrayList<String>());
						// Recursive if Folder
						getAllDirectoriesFromRoot(absolutePath, allFiles);
					}
				} else {
					// If Files are found then do the below functions
					File parentDir = file.getParentFile();
					String parentAbsPath = parentDir.getAbsolutePath();
					if (parentDir.isDirectory()) {
						if (allFiles.containsKey(parentAbsPath)) {
							List<String> values = allFiles.get(parentAbsPath);
							List<String> newValues = new ArrayList<String>();
							newValues.addAll(values);
							newValues.add(file.getName());
							allFiles.remove(parentAbsPath);
							allFiles.put(parentAbsPath, newValues);
						}
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Error While Getting File Information - Message :" + e.getMessage());
		}

	}

	// To get the filenames in key and the folders where the same filename exists
	public HashMap<String, List<String>> getNonDistinctFileNames(HashMap<String, List<String>> allFiles) {

		HashMap<String, List<String>> filenames = new HashMap<String, List<String>>();
		for (Map.Entry value : allFiles.entrySet()) {

			String folderAbsolutePath = value.getKey().toString();
			List<String> filesinFolder = allFiles.get(folderAbsolutePath);

			for (String filename : filesinFolder) {

				if (!filenames.containsKey(filename)) {
					List<String> foldernames = new ArrayList<String>();
					foldernames.add(folderAbsolutePath);
					filenames.put(filename, foldernames);
				} else {
					List<String> values = filenames.get(filename);
					List<String> newValues = new ArrayList<String>();
					newValues.addAll(values);
					newValues.add(folderAbsolutePath);
					filenames.remove(filename);
					filenames.put(filename, newValues);
				}
			}
		}

		return filenames;
	}

	// To check if the new filename exists in the same folder or not
	public boolean fileExistsinSameDirectory(String filename, String dirAbsolutePath,
			HashMap<String, List<String>> filenames) {

		boolean status = false;

		if (filenames.containsKey(filename)) {
			List<String> foldersOfSameFilename = filenames.get(filename);

			for (String dirPath : foldersOfSameFilename) {

				if (dirPath.equals(dirAbsolutePath)) {
					status = true;
					break;
				}
			}
		}

		return status;
	}

	// To Rename the physical file in the machine
	// Note path should be given as Absolute path of the file
	public boolean renameFile(String oldFilePath, String newFilePath) {
		File oldName = new File(oldFilePath);
		File newName = new File(newFilePath);

		if (oldName.renameTo(newName)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateAddedFileToCollection(String filepath, HashMap<String, List<String>> filenames) {
		boolean status = false;

		File file = new File(filepath);
		File parent = file.getParentFile();
		String dirOfFile = parent.getName();

		if (!filenames.containsKey(file.getName())) {

			List<String> folders = new ArrayList<String>();
			folders.add(dirOfFile);
			filenames.put(file.getName(), folders);
			status = true;
		} else {
			List<String> oldFolders = filenames.get(file.getName());

			if (oldFolders != null && !oldFolders.contains(dirOfFile)) {

				List<String> newValues = new ArrayList<String>();
				newValues.addAll(oldFolders);
				newValues.add(dirOfFile);

				filenames.remove(file.getName());
				filenames.put(file.getName(), newValues);
				status = true;
			}
		}
		return status;
	}

	public boolean updateRenamedFileToCollection(String oldFilePath, String newFilePath, String dirOfFile,
			HashMap<String, List<String>> filenames) {
		boolean status = false;

		Path oldpath = Paths.get(oldFilePath);
		String oldfileName = oldpath.getFileName().toString();

		Path newpath = Paths.get(newFilePath);
		String newfileName = newpath.getFileName().toString();

		if (filenames.containsKey(oldfileName)) {
			List<String> foldersOfSameFilename = filenames.get(oldfileName);
			boolean checked = false;
			for (String dirPath : foldersOfSameFilename) {

				if (dirPath.equals(dirOfFile)) {
					checked = true;
					break;
				}
			}

			if (checked) {
				List<String> values = filenames.get(oldfileName);
				if (values != null && values.size() > 1) {
					List<String> newValues = new ArrayList<String>();
					newValues.addAll(values);
					filenames.remove(oldfileName);
					filenames.put(newfileName, newValues);
					status = true;
				} else {
					filenames.remove(oldfileName);
					filenames.put(newfileName, new ArrayList<String>());
					status = true;
				}
			}
		}
		return status;
	}

	public boolean deleteFile(String filepath) {
		File myObj = new File(filepath);
		if (myObj.delete()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateDeletedFileToCollection(String filepath, String dirOfFile,
			HashMap<String, List<String>> filenames) {
		boolean status = false;

		Path path = Paths.get(filepath);
		String fileName = path.getFileName().toString();

		if (filenames.containsKey(fileName)) {

			List<String> foldersOfSameFilename = filenames.get(fileName);
			boolean checked = false;
			for (String dirPath : foldersOfSameFilename) {

				if (dirPath.equals(dirOfFile)) {
					checked = true;
					break;
				}
			}

			if (checked) {
				List<String> values = filenames.get(fileName);
				if (values != null && values.size() >= 1) {
					filenames.remove(fileName);
					status = true;
				} else {
					List<String> newValues = new ArrayList<String>();
					for (String folderpath : values) {

						if (!folderpath.equals(dirOfFile)) {
							newValues.add(folderpath);
						}
						filenames.remove(fileName);
						filenames.put(fileName, newValues);
						status = true;
					}
				}
			}

		}

		return status;
	}

	// Searching files using the Keyword
	public List<String> searchFilesUsingKeywords(String keyword, HashMap<String, List<String>> filenames) {
		List<String> outputFilesMatchingKeyword = new ArrayList<String>();

		for (Map.Entry value : filenames.entrySet()) {

			String fileName = value.getKey().toString();
			if (fileName.contains(keyword)) {
				List<String> foldersOfFile = filenames.get(fileName);

				for (String folderAbsPath : foldersOfFile) {
					Path fullpath = Paths.get(folderAbsPath, fileName);
					outputFilesMatchingKeyword.add(fullpath.toString());
				}
			}
		}
		return outputFilesMatchingKeyword;
	}

	public List<String> ascendingSort(HashMap<String, List<String>> filenames, String dirPath) {
		List<String> sortedFileNames = new ArrayList<String>();
		try {
			Set<String> keys = filenames.keySet();

			List<String> keyslst = new ArrayList<String>();
			for (String key : keys) {
				if (dirPath.equals(key)) {

					File file = new File(dirPath);

					if (file.exists() && file.isDirectory()) {

						FileFilter filter = new FileFilter() {
							@Override
							public boolean accept(File pathname) {
								return pathname.isFile();
							}
						};

						for (File childfile : file.listFiles(filter)) {
							keyslst.add(childfile.getName());
						}

					}
					break;
				}
			}
			Collections.sort(keyslst);
			sortedFileNames = keyslst;
		} catch (Exception e) {
			System.err.println("Error Occurred while Sorting files in Ascending order");
		}
		return sortedFileNames;
	}

	public List<String> descendingSort(HashMap<String, List<String>> filenames, String dirPath) {
		List<String> sortedFileNames = new ArrayList<String>();
		try {
			Set<String> keys = filenames.keySet();

			List<String> keyslst = new ArrayList<String>();
			for (String key : keys) {
				if (dirPath.equals(key)) {

					File file = new File(dirPath);

					if (file.exists() && file.isDirectory()) {

						FileFilter filter = new FileFilter() {
							@Override
							public boolean accept(File pathname) {
								return pathname.isFile();
							}
						};

						for (File childfile : file.listFiles(filter)) {
							keyslst.add(childfile.getName());
						}

					}
					break;
				}
			}
			Collections.sort(keyslst, Collections.reverseOrder());
			sortedFileNames = keyslst;
		} catch (Exception e) {
			System.err.println("Error Occurred while Sorting files in Descending order");
		}
		return sortedFileNames;
	}

	public List<String> getFilenamesMatchingExtension(HashMap<String, List<String>> filenames, String fileExtension) {
		List<String> newFileNames = new ArrayList<String>();
		try {

			Set<String> keys = filenames.keySet();

			for (String filename : keys) {

				if (!newFileNames.contains(filename)) {
					int index = filename.lastIndexOf('.');
					if (index > 0) {
						String extension = filename.substring(index + 1);
						newFileNames.add(extension);
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Error Occurred while file extensions of the Files from the root Directory");
		}
		return newFileNames;
	}

	public void BasicFileAttributes(Path path) throws IOException {
		System.out.println("FILE INFORMATION");

		File file = path.toFile();
		System.out.println("File Name          : " + file.getName());
		System.out.println("File size          : " + file.length() + " Bytes");
		System.out.println("Path               : " + file.getPath());
		System.out.println("Absolute Path      : " + file.getAbsolutePath());
		System.out.println("Parent             : " + file.getParent());

		BasicFileAttributes basicFileAttributes = Files.readAttributes(path, BasicFileAttributes.class);

		// Print basic file attributes
		System.out.println("Creation Time      : " + basicFileAttributes.creationTime());
		System.out.println("Last Access Time   : " + basicFileAttributes.lastAccessTime());
		System.out.println("Last Modified Time : " + basicFileAttributes.lastModifiedTime());
		System.out.println("Size               : " + basicFileAttributes.size());
		System.out.println("Is Regular file    : " + basicFileAttributes.isRegularFile());
		System.out.println("Is Symbolic Link   : " + basicFileAttributes.isSymbolicLink());
		System.out.println("Other              : " + basicFileAttributes.isOther());

		System.out.println("Is File Exists     : " + file.exists());
		System.out.println("Is File Writable   : " + file.canWrite());
		System.out.println("Is File Readable   : " + file.canRead());
		System.out.println("Is File Hidden     : " + file.isHidden());
		System.out.println("Is Directory       : " + file.isDirectory());
		System.out.println("Is File            : " + file.isFile());
		System.out.println("Is File Absolute   : " + file.isAbsolute());

	}
	
	
	
}
