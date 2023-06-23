// gets registry entry list of all users to delete registry entries for those user
// points to C:\Users\ so it can delete as many user profile directories as selected, along with RegEdits
import java.util.ArrayList;
import java.io.*;

public class DeleteUserProfileAlgorithm{

    // for each where matching case gets regex of User Profile Key String and puts it into an ArrayList, then another where those are deleted or in the same loop
    ArrayList<String> profiles = new ArrayList<String>();
    
    String dirCommand[] = {"cmd", "/c", "dir", "/b", "C:\\Users"};
    

    // gets all user profiles and returns a list
    public ArrayList<String> getUserProfiles(){
/*
        for (String item : dirCommand){
            System.out.print(item + " ");
        } 
        System.out.println();
 */
        ArrayList<String> profiles = new ArrayList<String>();

        try {            
            Process process = Runtime.getRuntime().exec(dirCommand);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s = null;

            while ((s = stdInput.readLine()) != null) {
                if (! s.equals("Administrator") && ! s.equals("Public")){
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
    
    // may need get user profile key method

    //delete profiles
    public void deleteUserProfiles(String[] profiles){
        for (String profile : profiles){
            //System.out.println(profile);
            String queryCommand[] = {"cmd", "/c","reg query \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\ProfileList\" /s /f \"" + profile + "\" | findstr /r ProfileList"};
/*          // spits out each command that will run
            for (String item : queryCommand){
                System.out.print(item + " ");
            } 
            System.out.println();            
*/
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

        String deleteCommand[] = {"cmd","/c", "reg delete " + profile + " /f && rmdir \"C:\\Users\\" + name + " /s /q"};

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

 */