public class Main {
	public static void main(String[] args) {
		GraphicalInterface gui = new GraphicalInterface(); 
		//DeleteUserProfileInterface ui = new DeleteUserProfileInterface();
		gui.runGUI();
	}

}

// make executable jar file 'jar cfm deleteuserprofiles.jar manifest.txt *.class'
// make exe jpackage -t exe -i . -n deleteuserprofiles --app-version 1.0 --win-dir-chooser --win-shortcut --win-per-user-install --main-jar deleteuserprofiles.jar
// had trouble with this last bit, left it out --add-launcher DeleteUserProfiles=file.properties

/*
SOURCES:

https://stackoverflow.com/questions/5258159/how-to-make-an-executable-jar-file

 */