package fileManipulator;

import java.util.*;

public class Commands {

	HashMap<String, String> commandNames = new HashMap<String, String>();

	public Commands() {
		commandNames.put("MYFILES", "");
		commandNames.put("ADD", "");
		commandNames.put("DELETE", "");
		commandNames.put("FIND", "");
		commandNames.put("FINDBYEXTENSION", "");
		commandNames.put("FILEINFO", "");
		commandNames.put("SORT", "");
		commandNames.put("SORTA", "");
		commandNames.put("SORTD", "");
		commandNames.put("RENAME", "");
		commandNames.put("EDITCOMMANDNAME", "");
		commandNames.put("MAIN", "");
		commandNames.put("HISTORY", "");
		commandNames.put("RESET", "");
		commandNames.put("CLOSE", "");
	}
	
	public HashMap<String, String> getallCommands(){
		return this.commandNames;
	}
	
	public boolean isOneOfDefaultCommands(String command) {
		boolean status = false;
		
		if(this.commandNames.containsKey(command.toUpperCase())) {
			status = true;
		}
		
		return status;
	}

	public List<String> getMyCommands() {
		List<String> newCommandsLst = new ArrayList<String>();

		for (Map.Entry value : commandNames.entrySet()) {

			String defaultCommand = value.getKey().toString();

			String newcommand = commandNames.get(defaultCommand);

			if (!Validator.isNullOrEmpty(newcommand)) {
				newCommandsLst.add(newcommand);
			} else {
				newCommandsLst.add(defaultCommand);
			}
		}
		return (newCommandsLst);
	}
	
	public boolean changeCommandName(String oldCommandName, String newCommandName) {
		boolean status=false;
		
		if(!Validator.isNullOrEmpty(oldCommandName)&& !Validator.isNullOrEmpty(newCommandName)) {
			
			if(commandNames.containsKey(oldCommandName)) {
				commandNames.remove(oldCommandName);
				commandNames.put(oldCommandName, newCommandName);
				status = true;
			}
		}
		return status;
	}
	
	
}
