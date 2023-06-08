import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Home extends JFrame implements ActionListener {
    JButton attendanceBtn;
    JButton addRemoveBtn;
    JButton newAnnounceBtn;
    JButton newAssignmentBtn;
    JButton logOutBtn;
    JPanel mainPanel;
    SchoolEntity currentEntity;
    Home(SchoolEntity e){
        super("School Management System: " + e.name);
        setSize(1500,800);

        // save logged in entity to local class attribute
        this.currentEntity = e;

        if(e.role.equals("admin")) { // home layout for admin user
            // admin home image
            ImageIcon ic =  new ImageIcon(ClassLoader.getSystemResource("images/AdminHome.png"));
            Image i3 = ic.getImage().getScaledInstance(1420, 720,Image.SCALE_DEFAULT);
            ImageIcon icc3 = new ImageIcon(i3);
            JLabel l1 = new JLabel(icc3);
            add(l1);

            // initialize button(s)
            attendanceBtn=new JButton("Attendance");
            addRemoveBtn= new JButton("User List");
            newAnnounceBtn= new JButton("Announcement");
            logOutBtn = new JButton("Log-Out");

            // add action listener for the button(s)
            attendanceBtn.addActionListener(this);
            addRemoveBtn.addActionListener(this);
            newAnnounceBtn.addActionListener(this);
            logOutBtn.addActionListener(this);

            // initialize main panel
            mainPanel=new JPanel();

            // add btn(s) to main panel
            mainPanel.add(attendanceBtn);
            mainPanel.add(addRemoveBtn);
            mainPanel.add(newAnnounceBtn);
            mainPanel.add(logOutBtn);

            // add panel to bottom
            add(mainPanel,BorderLayout.SOUTH);

        } else if (e.role.equals("teacher")) { // home layout for teachers
            // teacher home image
            ImageIcon ic =  new ImageIcon(ClassLoader.getSystemResource("images/TeacherHome.png"));
            Image i3 = ic.getImage().getScaledInstance(1420, 720,Image.SCALE_DEFAULT);
            ImageIcon icc3 = new ImageIcon(i3);
            JLabel l1 = new JLabel(icc3);
            add(l1);

            // initialize button(s)
            attendanceBtn=new JButton("Attendance");
            addRemoveBtn= new JButton("User List");
            newAnnounceBtn= new JButton("Announcement");
            newAssignmentBtn= new JButton("Assignment");
            logOutBtn = new JButton("Log-Out");

            // add action listener for the button(s)
            attendanceBtn.addActionListener(this);
            addRemoveBtn.addActionListener(this);
            newAnnounceBtn.addActionListener(this);
            newAssignmentBtn.addActionListener(this);
            logOutBtn.addActionListener(this);

            // initialize main panel
            mainPanel=new JPanel();

            // add btn(s) to main panel
            mainPanel.add(attendanceBtn);
            mainPanel.add(addRemoveBtn);
            mainPanel.add(newAnnounceBtn);
            mainPanel.add(newAssignmentBtn);
            mainPanel.add(logOutBtn);

            // add panel to bottom
            add(mainPanel,BorderLayout.SOUTH);

        } else if (e.role.equals("student")){ // home layout for students
            // student home image
            ImageIcon ic =  new ImageIcon(ClassLoader.getSystemResource("images/StudentHome.png"));
            Image i3 = ic.getImage().getScaledInstance(1420, 720,Image.SCALE_DEFAULT);
            ImageIcon icc3 = new ImageIcon(i3);
            JLabel l1 = new JLabel(icc3);
            add(l1);

            // initialize button(s)
            attendanceBtn=new JButton("Attendance");
            newAnnounceBtn= new JButton("Announcement");
            newAssignmentBtn= new JButton("Assignment");
            logOutBtn = new JButton("Log-Out");

            // add action listener for the button(s)
            attendanceBtn.addActionListener(this);
            newAnnounceBtn.addActionListener(this);
            newAssignmentBtn.addActionListener(this);
            logOutBtn.addActionListener(this);

            // initialize main panel
            mainPanel=new JPanel();

            // add btn(s) to main panel
            mainPanel.add(attendanceBtn);
            mainPanel.add(newAnnounceBtn);
            mainPanel.add(newAssignmentBtn);
            mainPanel.add(logOutBtn);

            // add panel to bottom
            add(mainPanel,BorderLayout.SOUTH);
        }
    }

    public void actionPerformed(ActionEvent ae){
        String msg = ae.getActionCommand();
        // System.out.println("Action command: " + msg); // for testing

        // redirections to each menu page
        if(msg.equals("Attendance")){
            new Attendance(this.currentEntity).setVisible(true);
            this.setVisible(false);
        } else if (msg.equals("User List")) {
            new UserList(this.currentEntity, true).setVisible(true);
            this.setVisible(false);
        } else if (msg.equals("Announcement")) {
            new Announcement(this.currentEntity).setVisible(true);
            this.setVisible(false);
        } else if (msg.equals("Assignment")) {
             new Assignment(this.currentEntity).setVisible(true);
             this.setVisible(false);
        } else if (msg.equals("Log-Out")){
            new login().setVisible(true);
            this.setVisible(false);
        }
    }
}
