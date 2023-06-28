import java.util.ArrayList;
import java.io.*;

// gets list of all users except the one running this program, and Administrator
public class GetUsers{
    // for each where matching case gets regex of User Profile Key String and puts it into an ArrayList, then another where those are deleted or in the same loop
    ArrayList<String> profiles = new ArrayList<String>();
    String dirCommand[] = {"cmd", "/c", "dir", "/b", "C:\\Users"};
    // gets currently logged in user, to exclude from list
    public String currentUser(){
        //Get-WMIObject -class Win32_ComputerSystem | select username | findstr /r '.*\\'
        String currentUserCommand[] = {"cmd", "/c", "powershell -command \"Get-WMIObject -class Win32_ComputerSystem | select username | findstr /r '.*\\\\'\"" };
        // see what the command is
        //for (String item : currentUserCommand){System.out.print(item + " ");} 
        //System.out.println();
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
        System.out.println("Current User, " + currentuser + ", Obtained");
        return currentuser;       
    }

    // gets all user profiles and returns a list
    public ArrayList<String> getUserProfiles(String currentuser){    
        // see what the command is
        //System.out.println("target users");
        //for (String item : dirCommand){System.out.print(item + " ");} 
        //System.out.println();    
        //String currentuser = currentUser(); // get the current user
        //lu.userToID(currentuser);
        //ArrayList<String> profiles = new ArrayList<String>();
        profiles.clear(); // start fresh
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
        System.out.println("All Users obtained:");
        for (String profile : profiles ) {System.out.println(profile);}
        System.out.println("---------------------------------------");
        return profiles;
    }       
}