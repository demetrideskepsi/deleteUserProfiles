import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.io.*;

public class LogoutUsers{

    // session ID to user dictionary
    public Map<String,Integer> userToID(String currentuser){
        Map<String,Integer> usersToID = new Hashtable<>();
        // $session = ((quser | ? { $_ -notmatch $username -and $_ -notmatch 'SESSIONNAME' }) -split '([0-9]  +Disc)')[1] -replace ' +Disc.*'
        String[] loggedInUsers = { "cmd", "/c", "powershell -command \"(quser | ? { $_ -notmatch \'" + currentuser + "\' -and $_ -notmatch 'SESSIONNAME' })\"" };    
        // see what the command is
        System.out.println("Getting Logged in Users");
        //for (String item : loggedInUsers){System.out.print(item + " ");} 
        //System.out.println();   
        ArrayList<String> quserOutput = new ArrayList<>();
        try {            
            Process process = Runtime.getRuntime().exec(loggedInUsers);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                quserOutput.add(s.toString());
                System.out.println(s);
            }            
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
        System.out.println("Logged in Users found:");
        // read out each user line and put the username and ID into the dictionary
        for (String user : quserOutput ){
            System.out.println(user);
            String temp1 = "";
            String temp2 = "";
            temp1 = user.split(" {5,}")[0]; // possible bug if username is too long, max length is 256, will need to test this out
            temp1 = temp1.stripLeading();
            //System.out.println(temp1);
            temp2 = user.split(" {5,}")[1];
            temp2 = temp2.split(" +")[0];
            //System.out.println(temp2);
            usersToID.put(temp1,Integer.parseInt(temp2));
        }
        System.out.println("Users and Session ID's compiled");
        return usersToID;
    }

    // logout user method
    public void logoutUser(Map<String,Integer> userToID, String[] selectedUsersList){  
        System.out.println("start logout user");
        // logs out users before deleting their profiles
        Map<String, Integer> map = userToID;
        ArrayList<Integer> IDs = new ArrayList<>();
        // key == username, value == ID
        for ( Map.Entry<String, Integer> entry : map.entrySet() ) {

            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("User: " + key + " Session ID: " + value);
            for ( String user : selectedUsersList ){
                System.out.println("Check if " + key + " == " + user);
                if (key.equals(user)){
                    System.out.println("User: " + user + " ID: " + value);
                    IDs.add(value); // adds the Session ID
                }
            }
        }
        for (int id : IDs){
            String[] logoff = {"cmd","/c", "logoff " + id};
            System.out.println("User ID"); 
            for (String item : logoff){System.out.print(item + " ");} 
            System.out.println();         
            try {            
                Process process = Runtime.getRuntime().exec(logoff);
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s = null;
                while ((s = stdInput.readLine()) != null) {
                    //print which user has been logged out
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
        System.out.println("Selected Users Logged Off");
    }
    
}

/*
SOURCES:

https://stackoverflow.com/questions/9371667/foreach-loop-in-java-for-dictionary

https://stackoverflow.com/questions/18280373/iterate-dictionary-in-java

*/