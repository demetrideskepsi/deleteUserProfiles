//create exe, this will be ui that you can target one or more users to delete their profile for a reset

public class Main {
	public static void main(String[] args) {
		DeleteUserProfileInterface ui = new DeleteUserProfileInterface();
		ui.runGui();
	}

}

//make executable jar file 'jar cfm deleteuserprofiles.jar manifest.txt *.class'

// make exe jpackage -t exe -i . -n deleteuserprofiles --app-version 1.0 --win-dir-chooser --win-shortcut --main-jar deleteuserprofiles.jar
// had trouble with this last bit, left it out --add-launcher DeleteUserProfiles=file.properties

/*
SOURCES:

https://stackoverflow.com/questions/5258159/how-to-make-an-executable-jar-file

 */