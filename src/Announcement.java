import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Announcement extends JFrame implements ActionListener {
    JLabel title;
    JButton add_announcement, remove_announcement, back_button;
    JPanel announcementsContainerPanel;
    JPanel announcementButtonPanel;
    String tableHeader[] = {"Title", "Description"};
    SchoolEntity currentEntity;

    Announcement(SchoolEntity e){
        super("Announcement Menu : " + e.name + "'s");
        setSize(1500,800);

        // save logged in entity to local class attribute
        this.currentEntity = e;

        // title
        title = new JLabel("Announcement");
        title.setFont(new Font("Senserif",Font.PLAIN,26)); // set title font size and font style
        title.setHorizontalAlignment(JLabel.CENTER); // set title in the center
        add(title,"North");

        // prepare main container panel
        announcementsContainerPanel = new JPanel();
        announcementsContainerPanel.setLayout(new BoxLayout(announcementsContainerPanel, BoxLayout.Y_AXIS));
        add(announcementsContainerPanel);
        getAnnouncementList();

        // initialize buttons
        add_announcement = new JButton("Add New Announcement");
        remove_announcement = new JButton("Remove Announcement");
        back_button = new JButton("Back");

        // add listener for the buttons
        add_announcement.addActionListener(this);
        remove_announcement.addActionListener(this);
        back_button.addActionListener(this);

        // add button to panel for buttons
        announcementButtonPanel = new JPanel();
        if(!currentEntity.role.equals("student")) {
            announcementButtonPanel.add(add_announcement);
            announcementButtonPanel.add(remove_announcement);
        }
        announcementButtonPanel.add(back_button);

        // add button panel to jFrame
        add(announcementButtonPanel,"South");

        // Add table to scrollPane
        JScrollPane sp = new JScrollPane(announcementsContainerPanel);
        add(sp);
    }

    void getAnnouncementList(){
        try{
            // fetch announcements
            conn c1  = new conn();
            String s1 = "select a.announcement_title , a.announcement_description from announcement a";
            ResultSet rs  = c1.s.executeQuery(s1);

            while(rs.next()){
                // create rows for announcements
                JPanel announcementRow = new JPanel();
                JLabel title = new JLabel(rs.getString("announcement_title") + " : ");
                title.setFont(new Font("Senserif",Font.PLAIN, 16));
                JLabel desc = new JLabel("<html>" + rs.getString("announcement_description") + "</html>");
                announcementRow.add(title);
                announcementRow.add(desc);

                // add announcement row to main container panel
                announcementsContainerPanel.add(announcementRow);
            }
        }catch(Exception err){
            err.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae){
        String msg = ae.getActionCommand();
        // System.out.println("Action command: " + msg); // for testing

        if(msg.equals("Add New Announcement")) { // page to add new announcement
            new AddAnnouncement(this.currentEntity).setVisible(true);
            this.setVisible(false);
        } else if(msg.equals("Back")) { // go back to home
            new Home(this.currentEntity).setVisible(true);
            this.setVisible(false);
        } else if (msg.equals("Remove Announcement")){ // go to a new page to delete announcement
            new RemoveAnnouncement(this.currentEntity).setVisible(true);
            this.setVisible(false);
        }
    }
}