import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class RemoveAnnouncement extends JFrame implements ActionListener {
    JLabel announcementIdLabel;
    JTextField announcementIdTextField;
    JButton deleteButton,cancelButton;
    JPanel buttonpanel;
    String tableHeader[] = {"Id", "Title", "Description", "User", "Role"};
    DefaultTableModel tableModel;
    JTable announcementTable;
    SchoolEntity currentEntity;

    RemoveAnnouncement(SchoolEntity e) {
        super("Remove Announcement");
        setSize(1500,800);

        // save logged in entity to local class attribute
        this.currentEntity = e;

        // form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(9, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);

        // fetch announcement list data to table
        tableModel = new DefaultTableModel(tableHeader, 0);
        getAnnouncementList();

        // form fields and labels
        announcementIdLabel = new JLabel("Announcement_ID");
        announcementIdTextField = new JTextField();
        formPanel.add(announcementIdLabel);
        formPanel.add(announcementIdTextField);

        // add form panel to north of jframe
        add(formPanel, BorderLayout.CENTER);

        // initialize delete and cancel button
        deleteButton = new JButton("Delete");
        cancelButton = new JButton("Cancel");

        // add action listener for button(s)
        deleteButton.addActionListener(this);
        cancelButton.addActionListener(this);

        // add button to buttonpanel
        buttonpanel = new JPanel();
        buttonpanel.add(deleteButton);
        buttonpanel.add(cancelButton);

        add(buttonpanel, BorderLayout.SOUTH); // add button panel at south (bottom)

        // Add table to scrollPane
        JScrollPane sp = new JScrollPane(announcementTable);
        add(sp, "West");
    }

    void getAnnouncementList(){
        try{
            // get all announcements
            conn c1  = new conn();
            String s1 = "select a.announcement_id , a.announcement_title , a.announcement_description , u.user_name , u.user_role from announcement a join `user` u on a.user_id = u.user_id";
            ResultSet rs  = c1.s.executeQuery(s1);

            // reset table
            tableModel.setRowCount(0);

            // save all announcement datas to table
            while(rs.next()){
                Object[] data = {
                        rs.getString("announcement_id"),
                        rs.getString("announcement_title"),
                        rs.getString("announcement_description"),
                        rs.getString("user_name"),
                        rs.getString("user_role"),
                };

                tableModel.addRow(data);
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
        // System.out.println("Action command: " + msg);

        if(msg.equals("Delete")){
            String announcementId = announcementIdTextField.getText();

            if(currentEntity.role.equals("admin")) {
                Admin adm = new Admin(currentEntity.id, currentEntity.name, currentEntity.role);
                if(adm.RemoveAnnouncement(announcementId)){
                    // return to announcements page
                    new Announcement(this.currentEntity).setVisible(true);
                    this.setVisible(false);
                }
            } else if (currentEntity.role.equals("teacher")) {
                Teacher tch = new Teacher(currentEntity.id, currentEntity.name, currentEntity.role);
                if(tch.RemoveAnnouncement(announcementId)){
                    // return to announcements page
                    new Announcement(this.currentEntity).setVisible(true);
                    this.setVisible(false);
                }
            }
        } else if (msg.equals("Cancel")) {
            new Announcement(this.currentEntity).setVisible(true); // return to announcements page
            this.setVisible(false);
        }


    }
}