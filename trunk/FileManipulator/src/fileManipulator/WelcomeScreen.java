package fileManipulator;

public class WelcomeScreen {
	
	public void DisplayWelcomeText() {
		
		displayApplicationName();
		
		displayDeveloperDetails();
		
		displayApplicationOperationalCommands();
		
	}

	private void displayApplicationName() {
		//Application Name
		System.out.println("File Manipulator");
	}
	
	private void displayDeveloperDetails() {
		//Developer Details
		System.out.println("\n\n\nDeveloper Details :");
		System.out.println("\nName        : S Kumaraguru");
		System.out.println("\nCompany     : Lockers Pvt. Ltd");
		System.out.println("\nDesignation : Full Stack Developer");
		System.out.println("\nEmail ID    : kumaraguru1995.chn@gmail.com");
		
	}
	
	private void displayApplicationOperationalCommands() {
		//The details of the user interface such as options displaying the user interaction information 
		System.out.println("\n\n\nDetails of the User Interface Options are as Follows : ");
		System.out.println("\nfile information of a file    : FileInfo[filepath with extension]");
		System.out.println("\ndisplays all the files        : MyFiles");
		System.out.println("\nAdd a file                    : Add[filepath with extension]");
		System.out.println("\nDelete a file                 : Delete[Full filepath with extension]");
		System.out.println("\nSearch a file                 : Find[keywords of the filename]");
		System.out.println("\nSearch a file by Extension    : FindbyExtension[extension of file]");
		System.out.println("\nRename a file                 : Rename[Oldfilepath with extension,NewfilePath with Extension]");
		System.out.println("\nSort filed in a directory     : Sort[Directory Path] or SortA[Directory Path] for Ascending and SortD[Directory Path] for Descending Sort");
		System.out.println("\nHistory of the commands       : History");
		System.out.println("\nBack to Main Menu             : Main");
		System.out.println("\nClose the Application         : Close");
		System.out.println("\nReset the Application         : Reset");
		
		System.out.println("\n");
		System.out.println("\nSurprise!!! now you can even Customize your Command Names as per your wish :)");
		System.out.println("\nChange Commands as you prefer : EditCommandName[add,append]");
		System.out.println("\nFor Example : in the Above Example you can change the default add Command to append or anyother name you prefer");
	}
	
}
