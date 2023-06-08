import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class AddAnnouncement extends JFrame implements ActionListener {
    JLabel title;
    JLabel announcementTitleLabel, announcementDescLabel;
    JLabel latestTitle;
    JTextField announcementTitleTextfield, announcementDescTextfield;
    JLabel latestAnnouncement;
    JPanel announcementFormPanel;
    JButton add_announcement, back_button;
    JPanel announcementButtonPanel;
    String tableHeader[] = {"Id", "Title", "Description", "User", "Role"};
    DefaultTableModel tableModel;
    JTable announcementTable;
    SchoolEntity currentEntity;

    AddAnnouncement(SchoolEntity e){
        super("Announcement Menu : " + e.name + "'s");
        getContentPane().setBackground(Color.WHITE);
        setSize(1500,800);

        // save logged in entity to local class attribute
        this.currentEntity = e;

        // title
        title = new JLabel("Announcement");
        title.setFont(new Font("Senserif",Font.PLAIN,26)); // set title font size and font style

        //Move the label to center
        title.setHorizontalAlignment(JLabel.CENTER); // set title in the center
        add(title,"North");

        // latest announcement
        latestAnnouncement = new JLabel("");
        JPanel laPanel = new JPanel();

        // fetch announcement list data to table
        tableModel = new DefaultTableModel(tableHeader, 0);
        getAnnouncementList();

        // form
        announcementTitleLabel = new JLabel("Announcement Title");
        announcementTitleTextfield = new JTextField();
        announcementDescLabel = new JLabel("Announcement Description");
        announcementDescTextfield = new JTextField();
        latestTitle = new JLabel("Latest Announcement");

        // add form fields to form panel
        announcementFormPanel = new JPanel();
        announcementFormPanel.setLayout(new GridLayout(5,2,30,30));
        announcementFormPanel.add(announcementTitleLabel);
        announcementFormPanel.add(announcementTitleTextfield);
        announcementFormPanel.add(announcementDescLabel);
        announcementFormPanel.add(announcementDescTextfield);
        announcementFormPanel.add(latestTitle);
        announcementFormPanel.add(latestAnnouncement);

        // initialize buttons
        add_announcement = new JButton("Add New Announcement");
        back_button = new JButton("Back");

        // add listener for the buttons
        add_announcement.addActionListener(this);
        back_button.addActionListener(this);

        // add button to panel for button
        announcementButtonPanel = new JPanel();
        announcementButtonPanel.add(add_announcement);
        announcementButtonPanel.add(back_button);

        // add announcement form panel to jFrame
        add(announcementFormPanel, "Center");

        // add button panel to jFrame
        add(announcementButtonPanel,"South");

        // Add table to scrollPane
        JScrollPane sp = new JScrollPane(announcementTable);
        add(sp, "West");

    }

    void getAnnouncementList(){
        try{
            conn c1  = new conn();
            String s1 = "select a.announcement_id , a.announcement_title , a.announcement_description , u.user_name , u.user_role from announcement a join `user` u on a.user_id = u.user_id";

            ResultSet rs  = c1.s.executeQuery(s1);

            // reset table
            tableModel.setRowCount(0);

            while(rs.next()){
                // add new data(s)
                Object[] data = {
                        rs.getString("announcement_id"),
                        rs.getString("announcement_title"),
                        rs.getString("announcement_description"),
                        rs.getString("user_name"),
                        rs.getString("user_role"),
                };
                tableModel.addRow(data);

                // setting latest announcement made
                System.out.println("Setting latest announcement to : " + rs.getString("announcement_title"));
                latestAnnouncement.setText("<html>" + rs.getString("announcement_description") + "</html>");
            }

            // update table data and repaint
            announcementTable = new JTable(tableModel);
            announcementTable.repaint();
        }catch(Exception err){
            err.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae){
        String msg = ae.getActionCommand();
        // System.out.println("Action command: " + msg); // for testing

        if(msg.equals("Add New Announcement")) {
            String title = announcementTitleTextfield.getText();
            String desc = announcementDescTextfield.getText();
            String creator = this.currentEntity.id;

            if(title.equals("") || desc.equals("") || creator.equals("")) {
                JOptionPane.showMessageDialog(null,"All fields are required!");
            } else {
                if (currentEntity.role.equals("admin")) {
                    Admin adm = new Admin(currentEntity.id, currentEntity.name, currentEntity.role);

                    if (adm.Announce(title, desc, creator)) {
                        JOptionPane.showMessageDialog(null, "Announcement Created!");
                        new Announcement(this.currentEntity).setVisible(true);
                        this.setVisible(false);
                    }
                } else if (currentEntity.role.equals("teacher")){
                    Teacher tch = new Teacher(currentEntity.id, currentEntity.name, currentEntity.role);

                    if (tch.Announce(title, desc, creator)){
                        JOptionPane.showMessageDialog(null, "Announcement Created!");
                        new Announcement(this.currentEntity).setVisible(true);
                        this.setVisible(false);
                    }
                }
            }
        } else if(msg.equals("Back")) { // go back to Announcement page
            System.out.println("Going back");
            new Announcement(this.currentEntity).setVisible(true);
            this.setVisible(false);
        }
    }
}