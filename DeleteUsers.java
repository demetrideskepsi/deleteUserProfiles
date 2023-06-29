// gets registry entry list of all users to delete registry entries for those user
// points to C:\Users\ so it can delete as many user profile directories as selected, along with RegEdits
import java.io.*;
import java.util.Hashtable;
import java.util.Map;

public class DeleteUsers{
    //delete profiles
    public void deleteUserProfiles(String[] usernames, Map<String,String> userToID){
        Map<String, String> map = userToID;
        for ( String user : usernames ){
            if (map.containsKey(user)){
                String profileID = map.get(user);
                System.out.println(user + "'s profileID exists");
                System.out.println("User: " + user + " ID: " + profileID);                    
                deleteProfile(profileID, user); // delete the user profile ID
                deleteDirectory(user); // delete the user directory 
            }
            else {                    
                System.out.println(user + "'s profileID does not exist");
                System.out.println(user);
                deleteDirectory(user); //delete the user directory
            }
        }
        System.out.println("Selected Users Deleted");
    }
    // get profileID method
    public Map<String,String> getProfileIDs(String[] usernames){
        Map<String,String> usernamesToProfileIDs = new Hashtable<>();
        for (String username : usernames){
            String queryCommand[] = {"cmd", "/c","reg query \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\ProfileList\" /s /f \"*\\" + username + "\" | findstr /r ProfileList"};
            // see what command will run
            System.out.println("Query Target User(s) Registry:");  
            //for (String item : queryCommand){System.out.print(item + " ");} 
            System.out.println();            
            try {           
                Process process = Runtime.getRuntime().exec(queryCommand);
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String profileID = null; // profile registry ID
                while ((profileID = stdInput.readLine()) != null) {
                    System.out.println(profileID);
                    usernamesToProfileIDs.put(username,profileID);
                }
                while ((profileID = stdError.readLine()) != null) {
                    System.out.println(profileID);
                }
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        System.out.println("Usernames to User Profile IDs obtained:");
        System.out.println();
        return usernamesToProfileIDs;
    }
    // delete profile
    public void deleteProfile(String profileID, String username){
        String deleteProfileCommand[] = {"cmd","/c", "reg delete " + "\"" + profileID + "\" /f"};
        // print deleteCommand
        System.out.println("Delete User's ProfileID commands:");
        for (String item : deleteProfileCommand){System.out.print(item + " ");}
        System.out.println("Deleting " + username + "'s Profile ID: " + profileID);
        try {           
            Process process = Runtime.getRuntime().exec(deleteProfileCommand);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }            
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    // delete directory
    public void deleteDirectory(String username){
        String deleteDirCommand[] = {"cmd","/c", "rmdir \"C:\\Users" + "\\" + username + "\"" + " /s /q"};
        //System.out.println("Delete User's Directory commands:");
        //for (String item : deleteDirCommand){System.out.print(item + " ");}
        System.out.println("Deleting " + username + "'s Home Directory");
        try {           
            Process process = Runtime.getRuntime().exec(deleteDirCommand);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }            
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}

/*
SOURCES:

https://stackoverflow.com/questions/5711084/java-runtime-getruntime-getting-output-from-executing-a-command-line-program

https://stackoverflow.com/questions/49122102/passing-pipes-into-shell-commands-via-java-application

https://www.rgagnon.com/javadetails/java-0480.html

https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/reg-query

https://stackoverflow.com/questions/1795808/and-and-or-in-if-statements

https://stackoverflow.com/questions/5642892/java-getruntime-exec-an-exe-that-requires-uac#:~:text=To%20elevate%2C%20you%20have%20to%20use%20ShellExecute%20or,use%20runas%20verb%2Foperation%20to%20force%20UAC%20confirmation%20dialog.

https://stackoverflow.com/questions/30082838/elevate-java-application-while-running

https://stackoverflow.com/questions/31294747/elevate-process-with-uac-java

https://www.geeksforgeeks.org/java-util-dictionary-class-java/

 */