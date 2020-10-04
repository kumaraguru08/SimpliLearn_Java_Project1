package fileManipulator;

import java.nio.file.Paths;
import java.util.*;

public class Validator {

	private String command = new String("");

	public boolean isValidCommand(Commands myCommand, String input) {
		boolean status = false;

		try {
			List<String> commands = myCommand.getMyCommands();

			String inputCommand = "";

			if (!isNullOrEmpty(input)) {

				if (input.contains("[")) {
					Character lastLetter = input.charAt(input.length() - 1);
					if (lastLetter == ']') {
						String[] splittedWords = input.split("\\[");
						inputCommand = splittedWords[0];
					}
				} else {
					inputCommand = input;
				}

				if (!isNullOrEmpty(inputCommand)) {

					if (commands.contains(inputCommand.toUpperCase())) {
						command = inputCommand;
						status = true;
					}
				}
			}

		} catch (Exception e) {
			System.err.println("Error Occurred while Validating Input : " + input);
			System.err.println("Exception Message : " + e.getMessage());
		}

		return status;
	}

	public String getValidCommand(Commands myCommand) {
		
		HashMap<String, String> commands = myCommand.getallCommands();
		
		String value = null;
		
		for (Map.Entry element : commands.entrySet()) {
		
			
		 if(	element.getValue().toString().equals(this.command)) {
			 value = element.getKey().toString();
			 break;
		 }
			
		}
		
		if(isNullOrEmpty(value)) {
			return this.command;
		}
		else
		{
			return value;
		}
	}

	public static boolean isNullOrEmpty(String str) {
		if (str != null && !str.isEmpty())
			return false;
		return true;
	}

	public static String getFileExtenstion(String fileNamewithExtension) {
		String extension = "";
		if (!isNullOrEmpty(fileNamewithExtension)) {
			int index = fileNamewithExtension.lastIndexOf('.');
			if (index > 0) {
				extension = fileNamewithExtension.substring(index + 1);
			}
		}
		return extension;
	}

	public String getBetweenStrings(String text, String textFrom, String textTo) {

		String result = "";

		try {
			result = text.substring(text.indexOf(textFrom) + 1, text.indexOf(textTo));
		} catch (Exception e) {
			System.err.println("Error Occurred while Validating Input String");
		}
		return result;
	}

	public static boolean isPathValid(String path) {

        try {

            Paths.get(path);

        } catch (Exception ex) {
            return false;
        }

        return true;
    }
	
}
