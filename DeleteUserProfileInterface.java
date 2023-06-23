import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DeleteUserProfileInterface{
    
    public void runGui(){
        JFrame frame = new JFrame();
        frame.setTitle("Delete User Profile(s)");
        frame.setSize(400, 400);
        //frame.setLayout(new BoxLayout());      
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Profile(s)"); // below this will be the available profiles for deletion
        label.setBounds(0,0, 100,30);  
        
        JButton delete = new JButton("Delete Profile(s)");
        //delete.setSize()


        // get user profiles
        DeleteUserProfileAlgorithm da = new DeleteUserProfileAlgorithm();
        //System.out.println(da.getUserProfiles());

        //String[] profiles = {"matt", "joan", "alex","jim","teri" }; // will need a function that grabs all the profiles and puts them into this array, or use arraylist
        ArrayList<String> profiles = new ArrayList<String>(da.getUserProfiles());
        DefaultListModel<String> item = new DefaultListModel<>();  

        // go through profiles array and create jlist items for each
        for (String profile : profiles){
            item.addElement(profile);
        }

        JList<String> list = new JList<>(item);         
        list.setBounds(100,100,75,75);
        //list.addListenerSelectionListener(new ListSelectionListener);

        delete.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println(list.getSelectedValuesList());
                }
            }
        );

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(20,20,50,100);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1));

        //add to panel
        panel.add(label);
        panel.add(scrollPane);
        panel.add(delete);

        // add to frame
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

 */