import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;

public class GraphicalInterface{
    GetUsers gu = new GetUsers(); // gets user profiles
    LogoutUsers lu = new LogoutUsers(); // logs user out
    DeleteUsers du = new DeleteUsers(); // delete user profiles
    String currentuser = gu.currentUser(); // gets user that's currently logged out, to exclude from list
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    ArrayList<String> profiles = new ArrayList<String>(); // get all the user profiles put them into profiles ArrayList
    DefaultListModel<String> items = new DefaultListModel<>(); 
    JList<String> list = new JList<>(items);  
    JScrollPane scrollPane = new JScrollPane(list);    
    JLabel label = new JLabel("Profile(s)"); // below this will be the available profiles for deletion
    JButton delete = new JButton("Delete Profile(s)");
    // create and reset profiles listed in interface
    public void listProfiles(){
        profiles.clear();
        profiles.addAll(gu.getUserProfiles(currentuser)); // run getUserProfiles() again
        items.removeAllElements();
        // go through profiles array and create jlist items for each
        for (String profile : profiles){
            items.addElement(profile);
        }
        list.setModel(items);
        System.out.println("User listing complete");
        System.out.println("---------------------------------------");
    }
    // start ui
    public void runGUI(){        
        frame.setTitle("Delete User Profile(s)");
        frame.setSize(400, 400); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        label.setBounds(0,0, 100,30); 
        listProfiles(); // list out profiles in interface
        //delete profiles
        delete.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // it won't run unless at least one user is selected
                String[] userList = list.getSelectedValuesList().toArray(new String[0]);
                if (list.getSelectedValuesList().toArray(new String[0]).length > 0){
                    Map<String,Integer> usersToID = lu.userToID(currentuser); // dictionary of users and IDs
                    lu.logoutUser(usersToID, userList); // logs out target users
                    Map<String,String> usernamesToProfileIDs = du.getProfileIDs(userList);
                    du.deleteUserProfiles(userList,usernamesToProfileIDs); // deletes target users
                    listProfiles();
                }
            }
        });
        list.setBounds(100,100,75,75);
        scrollPane.setBounds(20,20,50,100);
        panel.setLayout(new GridLayout(3,1));
        panel.add(label);
        panel.add(scrollPane);
        panel.add(delete);       
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}

/*
 SOURCES:

https://www.javatpoint.com/java-jlist
 
https://www.codeproject.com/Questions/1238094/How-can-I-access-windows-registry-by-java#:~:text=If%20you%20want%20to%20access%20the%20windows%20registry,class.%20This%20code%20uses%20the%20m%24%20reg.exe%20tool.

https://stackoverflow.com/questions/13621261/add-a-jlist-to-a-jscrollpane

https://alvinalexander.com/java/jbutton-listener-pressed-actionlistener/

https://stackoverflow.com/questions/3604407/java-check-for-selection-on-jlist

https://learn.microsoft.com/en-us/previous-versions/bb756929(v=msdn.10)?redirectedfrom=MSDN

https://docs.oracle.com/javase/7/docs/api/java/awt/Checkbox.html

https://stackoverflow.com/questions/4262669/refresh-jlist-in-a-jframe

 */