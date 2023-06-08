import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class Attendance extends JFrame implements ActionListener {
    JButton attendBtn;
    JButton backBtn;
    JButton studentAttendanceBtn;
    JPanel tablePanel;
    JPanel buttonPanel;
    JTable attendanceTable;
    String tableHeader[] = {"Name", "ID", "Role", "Check In Time"};
    DefaultTableModel tableModel;
    SchoolEntity currentEntity;

    Attendance(SchoolEntity e){
        super("Attendance Menu : " + e.name + "'s");
        setSize(1500,800);

        // save logged in entity to local class attribute
        this.currentEntity = e;

        // fetch attendance data
        tableModel = new DefaultTableModel(tableHeader, 0);
        getAttendanceData();

        // check-in button
        attendBtn = new JButton("Check-in");
        attendBtn.addActionListener(this);

        // if the role equals teacher then add new button for student's attendance by the teacher
        if (currentEntity.role.equals("teacher")){
            studentAttendanceBtn = new JButton("Student Attendance");
            studentAttendanceBtn.addActionListener(this);
        }

        // button to return to previous page
        backBtn = new JButton("Back");
        backBtn.addActionListener(this);

        // add buttons to the panel
        buttonPanel = new JPanel();
        buttonPanel.add(attendBtn);
        if (currentEntity.role.equals("teacher")){
            buttonPanel.add(studentAttendanceBtn);
        }
        buttonPanel.add(backBtn);

        // add button panel to bottom
        add(buttonPanel, BorderLayout.SOUTH);

        // add table to scroll-pane
        JScrollPane sp = new JScrollPane(attendanceTable);
        add(sp);
    }

    void getAttendanceData(){
        try{
            conn c1  = new conn();

            // query to get attendance data
            String s1 = "select u.user_name, u.user_id, u.user_role, a.checkin_datetime from attendance a join `user` u on a.user_id = u.user_id";

            if(currentEntity.role.equals("teacher")){ // don't show admin attendance if current user is a teacher
                s1 += " where u.user_id = '" + currentEntity.id + "' or u.user_role = 'student'";
            } else if (currentEntity.role.equals("student")){ // students can only see their own attendance
                s1 += " where u.user_id = '" + currentEntity.id + "'";
            }

            ResultSet rs  = c1.s.executeQuery(s1);

            // reset table
            tableModel.setRowCount(0);

            // fill table with data
            while(rs.next()){
                Object[] data = {
                    rs.getString("user_name"),
                    rs.getString("user_id"),
                    rs.getString("user_role"),
                    rs.getString("checkin_datetime")
                };

                tableModel.addRow(data);
            }

            // update attendance table data and repaint
            attendanceTable = new JTable(tableModel);
            attendanceTable.repaint();
        }catch(Exception err){
            err.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae){
        String msg = ae.getActionCommand();
        // System.out.println("Action command: " + msg); // for testing

        if(msg.equals("Check-in")){
            if(this.currentEntity.CheckIn()){
                JOptionPane.showMessageDialog(null,"Attendance Data Saved");
                getAttendanceData(); // re-fetch attendance data
            }
        } else if(msg.equals("Back")) {
            new Home(this.currentEntity).setVisible(true);
            this.setVisible(false);
        } else if (msg.equals("Student Attendance")){
            new StudentAttendance(this.currentEntity).setVisible(true);
            this.setVisible(false);
        }
    }
}