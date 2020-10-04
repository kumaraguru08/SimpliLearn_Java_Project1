package fileManipulator;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class MainClass {

	public static void main(String[] args) {

		boolean canClose = false;
		String rootDir = null;
		List<String> history = new ArrayList<String>();
		Commands myCommand = new Commands();
		Validator check = new Validator();
		WelcomeScreen welcome = new WelcomeScreen();
		welcome.DisplayWelcomeText();

		Scanner scanner = new Scanner(System.in);

		while (!canClose){
			
			System.out.println("\nKindly Enter the root Directory to start the Application :");
			rootDir = scanner.nextLine();

			if (!Validator.isNullOrEmpty(rootDir)) {
				File dir = new File(rootDir);

				if (dir.exists()) {

					System.out.println("\nRoot Directory is set Successfully!");
					System.out.println("\nFile Manipulator Welcomes you :)");

					boolean canCloseWindow = false;
					while (!canCloseWindow) {

						HashMap<String, List<String>> filenames = new HashMap<String, List<String>>();
						// "D:\\ParentFolder"
						HashMap<String, List<String>> allFiles = new HashMap<String, List<String>>();
						File_Folder_Manipulator manipulator = new File_Folder_Manipulator(rootDir);
						manipulator.getAllDirectoriesFromRoot(rootDir, allFiles);

						try {
							System.out.println("\nEnter a Command : ");
							String input = scanner.nextLine();

							if (check.isValidCommand(myCommand, input)) {

								filenames = manipulator.getNonDistinctFileNames(allFiles);
								String command = check.getValidCommand(myCommand).toUpperCase();
								String inputFilePath = "";

								switch (command.toUpperCase()) {
								case "MYFILES":

									try {
										history.add(input);
										RetrieveMyFiles(filenames);
										System.out.println("\n");
									} catch (Exception e) {
										System.err.println(
												"Something went wrong while Retrieving files for the Directory!");
									}

									break;
								case "ADD":
									try {
										history.add(input);
										AddCommand(check, input, filenames, manipulator);
									} catch (Exception e) {
										System.err.println("Something went wrong while Adding File!");
									}
									break;
								case "DELETE":
									try {
										history.add(input);
										DeleteCommand(check, filenames, manipulator, input);
									} catch (Exception e) {
										System.err.println("Something went wrong while Deleting!");
									}
									break;
								case "FIND":

									try {
										history.add(input);
										FindCommand(check, filenames, manipulator, input);
									} catch (Exception e) {
										System.err.println("Something went wrong while Searching Files with KeyWords!");
									}

									break;
								case "FINDBYEXTENSTSION":

									try {
										history.add(input);
										inputFilePath = check.getBetweenStrings(input, "[", "]");
										if (inputFilePath.matches("[a-zA-Z.? ]*")) {
											List<String> matchingFilenames = manipulator
													.getFilenamesMatchingExtension(filenames, inputFilePath);

											for (String value : matchingFilenames) {
												System.out.println("\n" + value);
											}
										} else {
											System.out.println("\n" + "Invalid Extension!");
										}

									} catch (Exception e) {
										System.err.println(
												"Something went wrong while Finding files matching the extension!");
									}

									break;
								case "FILEINFO":

									try {
										history.add(input);
										inputFilePath = check.getBetweenStrings(input, "[", "]");

										if (Validator.isPathValid(inputFilePath)) {

											File file = new File(inputFilePath);

											Path path = Paths.get(file.getAbsolutePath());
											manipulator.BasicFileAttributes(path);

										}
									} catch (Exception e) {
										System.err.println("Something went wrong while Finding file information!");
									}
									System.out.println("\n");
									break;
								case "SORT":
									try {

										history.add(input);
										AscendingSortCommand(check, allFiles, manipulator, input);

									} catch (Exception e) {
										System.err.println("Something went wrong while Sorting in Ascending!");
									}

									break;
								case "SORTA":
									try {

										history.add(input);
										AscendingSortCommand(check, allFiles, manipulator, input);
									} catch (Exception e) {
										System.err.println("Something went wrong while Sorting in Ascending!");
									}

									break;
								case "SORTD":
									try {

										history.add(input);
										DescendingSortCommand(check, allFiles, manipulator, input);
									} catch (Exception e) {
										System.err.println("Something went wrong while Sorting in Descending!");
									}

									break;
								case "RENAME":
									try {
										history.add(input);
										RenameCommand(check, filenames, manipulator, input);

									} catch (Exception e) {
										System.err.println("Something went wrong while Renaming!");
									}
									break;
								case "EDITCOMMANDNAME":

									try {
										history.add(input);
										ChangeCommand(myCommand, check, input);

									} catch (Exception e) {
										System.err.println("Something went wrong while Changing Command Name!");
									}
									break;
								case "MAIN":

									try {
										history.add(input);
										welcome.DisplayWelcomeText();
									} catch (Exception e) {
										System.err.println("Something went wrong while going back to the Main Menu!");
									}
									break;
								case "HISTORY":
									try {

										for (String value : history) {

											System.out.println("\n" + value);
										}

										history.add(input);
									} catch (Exception e) {
										System.err.println("Something went wrong while Showing History!");
									}
									break;
								case "RESET":
									history = new ArrayList<String>();
									System.out.println("Reset Successfully!");
									System.out.println("\n");
									break;
								case "CLOSE":
									canClose = true;
									canCloseWindow = true;
									break;
								default:
									// Invalid Command Entered Popup
									System.err.println("Invalid Command, Kindly Enter a Valid Command to proceed");
									break;
								}

							} else {
								// Invalid Command Entered Popup
								System.out.println("Invalid Command!");
							}

						} catch (Exception e) {
							System.err.println("An error occurred - Message : " + e.getMessage());
							e.printStackTrace();
						}
					}

				} else {
					// Root Directory doesnt exists..Enter Valid root Directory
					System.out.println("Root Directory doesn't Exist, Kindly provide a Valid Root Directory to proceed");
				}
			} else {
				// Empty root dir command Validation popup
				System.out.println("Enter a Valid Root Directory, Empty Input for the root directory is not Admissible");
			}
		}

	}

	/**
	 * @param check
	 * @param allFiles
	 * @param manipulator
	 * @param input
	 */
	private static void AscendingSortCommand(Validator check, HashMap<String, List<String>> allFiles,
			File_Folder_Manipulator manipulator, String input) {
		String inputFilePath;
		inputFilePath = check.getBetweenStrings(input, "[", "]");
		if (Validator.isPathValid(inputFilePath)) {

			if ((new File(inputFilePath).isDirectory())) {
			} else {
				System.out.println("Not a Valid Directory!");
			}
			List<String> sortedFiles = manipulator.ascendingSort(allFiles, inputFilePath);

			for (String file : sortedFiles) {
				System.out.println(file);
			}
		} else {
			System.out.println("Not a Valid File!");
		}
	}

	/**
	 * @param check
	 * @param allFiles
	 * @param manipulator
	 * @param input
	 */
	private static void DescendingSortCommand(Validator check, HashMap<String, List<String>> allFiles,
			File_Folder_Manipulator manipulator, String input) {
		String inputFilePath;
		inputFilePath = check.getBetweenStrings(input, "[", "]");
		if (Validator.isPathValid(inputFilePath)) {

			if ((new File(inputFilePath).isDirectory())) {
			} else {
				System.out.println("Not a Valid Directory!");
			}
			List<String> sortedFiles = manipulator.descendingSort(allFiles, inputFilePath);

			for (String file : sortedFiles) {
				System.out.println(file);
			}
		} else {
			System.out.println("Not a Valid File!");
		}
	}

	/**
	 * @param myCommand
	 * @param check
	 * @param input
	 */
	private static void ChangeCommand(Commands myCommand, Validator check, String input) {
		String inputFilePath;
		inputFilePath = check.getBetweenStrings(input, "[", "]");
		if (inputFilePath.contains(",")) {
			String[] commands = inputFilePath.split(",");
			if (commands.length == 2) {
				String defaultCommand = commands[0];
				String newCommand = commands[1];

				if (myCommand.isOneOfDefaultCommands(defaultCommand)) {

					boolean changedCommand = myCommand.changeCommandName(defaultCommand.toUpperCase(), newCommand.toUpperCase());

					if (changedCommand) {
						System.out.println("Command Name - " + defaultCommand + " is changed to " + newCommand
								+ " Successfully!");
					} else {
						System.out.println("Command is not Changed");
					}
				}
			}
		} else {
			System.out.println("Invalid Command Names Input, Comma Seperator Not Found!");
		}
	}

	/**
	 * @param check
	 * @param filenames
	 * @param manipulator
	 * @param input
	 */
	private static void FindCommand(Validator check, HashMap<String, List<String>> filenames,
			File_Folder_Manipulator manipulator, String input) {
		String keyword = check.getBetweenStrings(input, "[", "]");

		if (!Validator.isNullOrEmpty(keyword)) {

			List<String> outputLstContainingKeywords = manipulator.searchFilesUsingKeywords(keyword, filenames);

			if (outputLstContainingKeywords != null && outputLstContainingKeywords.size() > 0) {

				for (String filename : outputLstContainingKeywords) {
					System.out.println("\n" + filename);
				}

			} else {
				System.out.println("No Files found with the Mentioned KeyWords to Find!");
			}

		} else {
			System.out.println("Invalid FilePath Entered in Find Command!");
		}
	}

	/**
	 * @param check
	 * @param input
	 * @throws IOException
	 */
	private static void AddCommand(Validator check, String input, HashMap<String, List<String>> filenames,
			File_Folder_Manipulator manipulator) throws IOException {
		String inputFilePath;
		inputFilePath = check.getBetweenStrings(input, "[", "]");

		if (inputFilePath.contains(",")) {
			String[] filePaths = inputFilePath.split(",");
			if (filePaths.length == 2) {
				String oldFilePath = filePaths[0];
				String newfilePath = filePaths[1];
				if (Validator.isPathValid(oldFilePath) && Validator.isPathValid(newfilePath)) {
					File oldfile = new File(oldFilePath);

					Path fullpath = Paths.get(newfilePath);

					if (oldfile.exists() && !fullpath.toFile().exists()) {
						Files.copy(new File(oldFilePath).toPath(), fullpath);

						if (fullpath.toFile().exists()) {

							boolean isAdded = manipulator.updateAddedFileToCollection(newfilePath, filenames);
							if (isAdded) {
								System.out.println("File Sucessfully Added");
							} else {
								System.out.println("File is not Added");
							}

						} else {
							System.out.println("File is not Added");
						}
					} else {
						System.out.println(
								"Either File Does not Exists in Old File Path or File Exists in New File Path");
					}
				} else {
					System.out.println("File Path is not Valid");
				}
			}
		} else {
			if (Validator.isPathValid(inputFilePath)) {

				File addfile = new File(inputFilePath);
				if (!addfile.exists()) {

					if (addfile.createNewFile()) {
						boolean isAdded = manipulator.updateAddedFileToCollection(addfile.getAbsolutePath(), filenames);
						if (isAdded) {
							System.out.println("File Sucessfully Added");
						} else {
							System.out.println("File is not Added");
						}
					} else {
						System.out.println("File is not Added");
					}
				} else {
					System.out.println(
							"File Already Exits in the same Directory, Kindly add a different file or add it to a different directory");
				}

			} else {
				System.out.println("Invalid FilePath Entered in Add Command!");
			}
		}
	}

	/**
	 * @param filenames
	 */
	private static void RetrieveMyFiles(HashMap<String, List<String>> filenames) {
		for (Map.Entry<String, List<String>> value : filenames.entrySet()) {

			String key = value.getKey();

			for (String folderPath : value.getValue()) {

				Path path = Paths.get(folderPath, key);
				System.out.println(path.toString());
			}
		}
	}

	private static void DeleteCommand(Validator check, HashMap<String, List<String>> filenames,
			File_Folder_Manipulator manipulator, String input) {
		String inputFilePath;
		inputFilePath = check.getBetweenStrings(input, "[", "]");

		if (Validator.isPathValid(inputFilePath)) {
			File filetoDelete = (new File(inputFilePath));

			if (filetoDelete.exists()) {

				if (manipulator.deleteFile(filetoDelete.getAbsolutePath())) {

					boolean deleted = manipulator.updateDeletedFileToCollection(inputFilePath, filetoDelete.getParent(),
							filenames);
					if (deleted) {
						System.out.println("File Deleted Successfully!");
					} else {

					}
				} else {
					System.err.println("Error Occurred : Something went wrong while deleting the file!");
				}

			} else {
				System.err.println("FilePath Entered to Delete does not Exists!");
			}

		} else {
			System.out.println("Invalid FilePath Entered in Delete Command!");
		}
	}

	private static void RenameCommand(Validator check, HashMap<String, List<String>> filenames,
			File_Folder_Manipulator manipulator, String input) {
		String inputFilePath = check.getBetweenStrings(input, "[", "]");
		if (inputFilePath.contains(",")) {
			String[] filePaths = inputFilePath.split(",");
			if (filePaths.length == 2) {
				String oldFilePath = filePaths[0];
				String newFilePath = filePaths[1];
				if (Validator.isPathValid(oldFilePath) && Validator.isPathValid(newFilePath)) {

					if (manipulator.renameFile(oldFilePath, newFilePath)) {

						File oldfile = (new File(oldFilePath));
						File newfile = (new File(newFilePath));
						String oldFileParent = oldfile.getParent();
						String newFileParent = newfile.getParent();
						if (oldFileParent.equals(newFileParent)) {
							boolean renamed = manipulator.updateRenamedFileToCollection(oldFilePath, newFilePath,
									oldfile.getParent(), filenames);
							if (renamed) {
								System.out.println(
										oldfile.getName() + " renamed to " + newfile.getName() + " Successfully");
							}
						} else {
							System.out
									.println("Entered Old File Path and New File Path is not from the Same Directory");
						}
					} else {
						System.err.println("Error While Renaming the file");
					}

				} else {
					System.out.println("Invalid FilePaths Entered in Rename Command!");
				}
			} else {
				System.err.println("Invalid FilePaths Comma Seperator not Found for Rename Command");
			}
		} else {
			System.err.println("Invalid FilePaths Comma Seperator not Found for Rename Command");
		}
	}
}
