// gets registry entry list of all users to delete registry entries for those user
// points to C:\Users\ so it can delete as many user profile directories as selected, along with RegEdits
import java.io.*;

public class DeleteUsers{
/*     // for each where matching case gets regex of User Profile Key String and puts it into an ArrayList, then another where those are deleted or in the same loop
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
        
        // see what the command is
        System.out.println("target users");
        for (String item : dirCommand){
            System.out.print(item + " ");
        } 
        System.out.println();
        
        String currentuser = currentUser(); // get the current user
        userToID(currentuser);
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
    

    // session ID to user dict
    public Dictionary<String,Integer> userToID(String currentuser){
        ///Pattern pattern = Pattern.compile("[0-9]  +Disc");
        Dictionary<String,Integer> dict = new Hashtable<>();
        // $session = ((quser | ? { $_ -notmatch $username -and $_ -notmatch 'SESSIONNAME' }) -split '([0-9]  +Disc)')[1] -replace ' +Disc.*'
        String[] loggedInUsers = { "cmd", "/c", "powershell -command \"(quser | ? { $_ -notmatch \'" + currentuser + "\' -and $_ -notmatch 'SESSIONNAME' })\"" };
        
        // see what the command is
        System.out.println("logged in users");
        for (String item : loggedInUsers){
            System.out.print(item + " ");
        } 
        System.out.println();
        
        ArrayList<String> quserOutput = new ArrayList<>();
        try {            
            Process process = Runtime.getRuntime().exec(loggedInUsers);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s = null;

            while ((s = stdInput.readLine()) != null) {
                quserOutput.add(s.toString());
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

        // read out each user line and put the username and ID into the dict
        for (String user : quserOutput ){
            String temp1 = "";
            String temp2 = "";
            temp1 = user.split(" {5,}")[0];
            temp1 = temp1.stripLeading();
            //System.out.println(temp1);
            temp2 = user.split(" {5,}")[1];
            temp2 = temp2.split(" +")[0];
            //System.out.println(temp2);
            dict.put(temp1,Integer.parseInt(temp2));
        }
        
        return dict;
    }

    // logout user method
    public void logoutUser(){

        // will add a method in here that uses dictionaries to grab the username with the Session ID


        // logs out users before deleting their profiles
        
    }
 */
 
    //delete profiles
    public void deleteUserProfiles(String[] usernames){
        for (String username : usernames){
            //System.out.println(profile);
            String queryCommand[] = {"cmd", "/c","reg query \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\ProfileList\" /s /f \"" + username + "\" | findstr /r ProfileList"};
            // see what command will run
            //System.out.println("Query Target User(s) Registry");  
            //for (String item : queryCommand){System.out.print(item + " ");} 
            //System.out.println();            
            try {           
                Process process = Runtime.getRuntime().exec(queryCommand);
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s = null;
                while ((s = stdInput.readLine()) != null) {
                    // read profile key and then delete
                    //System.out.println(s);
                    deleteProfile(s,username);
                }                
                while ((s = stdError.readLine()) != null) {
                    System.out.println(s);
                }
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        System.out.println("Selected Users Deleted");
    }

    public void deleteProfile(String profile, String name){
        // if I can't get this one liner to delete the reg entry and folder independently I'll make two commands for it
        // will also need a way to check for orphaned reg keys and delete them
        String deleteCommand[] = {"cmd","/c", "reg delete " + "\"" + profile + "\"" + " /f && rmdir \"C:\\Users" + "\\" + name + "\"" + " /s /q"};
        // print deleteCommand
        for (String item : deleteCommand){System.out.print(item + " ");}
        System.out.println("Deleting User: " + name);
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

https://www.geeksforgeeks.org/java-util-dictionary-class-java/

 */