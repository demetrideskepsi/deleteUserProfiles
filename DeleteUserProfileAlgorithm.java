// gets registry entry list of all users to delete registry entries for those user
// points to C:\Users\ so it can delete as many user profile directories as selected, along with RegEdits
import java.util.ArrayList;
import java.io.*;

public class DeleteUserProfileAlgorithm{

    // for each where matching case gets regex of User Profile Key String and puts it into an ArrayList, then another where those are deleted or in the same loop
    ArrayList<String> profiles = new ArrayList<String>();
    
    String dirCommand[] = {"cmd", "/c", "dir", "/b", "C:\\Users"};
    
    // gets currently logged in user, to exclude from list
    public String currentUser(){
        //Get-WMIObject -class Win32_ComputerSystem | select username | findstr /r '.*\\'
        String currentUserCommand[] = {"cmd", "/c", "powershell -command \"Get-WMIObject -class Win32_ComputerSystem | select username | findstr /r '.*\\\\'\"" };
      // see what the command is
        for (String item : currentUserCommand){
            System.out.print(item + " ");
        } 
        System.out.println();
        String currentuser = ""; 
        try {            
            Process process = Runtime.getRuntime().exec(currentUserCommand);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s = null;

            while ((s = stdInput.readLine()) != null) {
                currentuser = s.split("\\\\")[1];
                //System.out.println(currentuser);
            }
            
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
        return currentuser;
        
    }

    // gets all user profiles and returns a list
    public ArrayList<String> getUserProfiles(){
        /*
        // see what the command is
        System.out.println("target users");
        for (String item : dirCommand){
            System.out.print(item + " ");
        } 
        System.out.println();
        */
        String currentuser = currentUser(); // get the current user
        //ArrayList<String> profiles = new ArrayList<String>();
        profiles.clear();

        try {            
            Process process = Runtime.getRuntime().exec(dirCommand);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s = null;

            while ((s = stdInput.readLine()) != null) {
                if (! s.equals("Administrator") && ! s.equals("Public") && ! s.equals(currentuser)){
                    profiles.add(s);
                }
                //System.out.println(s);
            }
            
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }

        return profiles;
    }
    
    // logout user method
    public void logoutUser(){
        // logs out users before deleting their profiles
    }

    //delete profiles
    public void deleteUserProfiles(String[] profiles){
        for (String profile : profiles){
            //System.out.println(profile);
            String queryCommand[] = {"cmd", "/c","reg query \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\ProfileList\" /s /f \"" + profile + "\" | findstr /r ProfileList"};
            // see what command will run
            System.out.println("query target user(s) reg");  
            for (String item : queryCommand){
                System.out.print(item + " ");
            } 
            System.out.println();            

            try {           
                Process process = Runtime.getRuntime().exec(queryCommand);
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s = null;

                while ((s = stdInput.readLine()) != null) {
                    // read profile key and then delete
                    //System.out.println(s);
                    deleteProfile(s,profile);
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

    public void deleteProfile(String profile, String name){
        // if I can't get this one liner to delete the reg entry and folder independently I'll make two commands for it
        // will also need a way to check for orphaned reg keys and delete them
        String deleteCommand[] = {"cmd","/c", "reg delete " + "\"" + profile + "\"" + " /f && rmdir \"C:\\Users" + "\\" + name + "\"" + " /s /q"};
        for (String item : deleteCommand){
                System.out.print(item + " ");
        } 

        try {           
            Process process = Runtime.getRuntime().exec(deleteCommand);
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

 */