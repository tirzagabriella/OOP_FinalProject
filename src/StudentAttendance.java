import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

// page for teachers for students' attendance
public class StudentAttendance extends JFrame implements ActionListener{
    JButton addBtn;
    JButton backBtn;
    JPanel buttonPanel;
    JPanel formPanel;
    JLabel userIdLabel;
    JTextField userIdTextField;
    JTable userTable;
    String tableHeader[] = {"Name", "ID", "Role"};
    DefaultTableModel tableModel;
    SchoolEntity currentEntity;

    StudentAttendance(SchoolEntity e){
        super("[" + e.name + "] Student List");
        setSize(1500,800);

        // save logged in entity to local class attribute
        this.currentEntity = e;

        // get student list
        tableModel = new DefaultTableModel(tableHeader, 0);
        getStudentList();

        // buttons
        addBtn = new JButton("Check-in Student");
        addBtn.addActionListener(this);
        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
        buttonPanel = new JPanel();
        buttonPanel.add(addBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // add table to frame
        JScrollPane sp = new JScrollPane(userTable);
        add(sp, BorderLayout.WEST);

        // form panel
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);

        // form fields and labels
        userIdLabel = new JLabel("User_ID");
        userIdTextField = new JTextField();
        userIdTextField.setPreferredSize(new Dimension(100,30));
        formPanel.add(userIdLabel);
        formPanel.add(userIdTextField);

        // add form panel to north of jframe
        add(formPanel, BorderLayout.CENTER);
    }

    void getStudentList(){
        try{
            // get students
            conn c1  = new conn();

            // query to fetch data (Aliases are usually used to just give a different naming to the table just for
            // ease of use later (for example when accessing the table's fields, we can use a shorter name as an alias)
            String s1 = "select u.user_name, u.user_id, u.user_role from `user` u where u.user_role = 'student'";
            ResultSet rs  = c1.s.executeQuery(s1);

            // reset table
            tableModel.setRowCount(0);

            // add new table datas
            while(rs.next()){
                Object[] data = {
                        rs.getString("user_name"),
                        rs.getString("user_id"),
                        rs.getString("user_role"),
                };

                tableModel.addRow(data);
            }

            // update table data and repaint
            userTable = new JTable(tableModel);
            userTable.repaint();
        }catch(Exception err){
            err.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae){
        String msg = ae.getActionCommand();
        // System.out.println("Action command: " + msg);

        if(msg.equals("Back")) { // go back to home
            System.out.println("Going back");
            new Attendance(this.currentEntity).setVisible(true);
            this.setVisible(false);
        } else if(msg.equals("Check-in Student")){ // redirect to add-user page
            String userId = userIdTextField.getText();

            Teacher tch = new Teacher(currentEntity.id, currentEntity.name, currentEntity.role);
            if(tch.StudentCheckIn(userId)){
                JOptionPane.showMessageDialog(null,"Attendance Data Saved");

                // return to attendance list page
                new Attendance(this.currentEntity).setVisible(true);
                this.setVisible(false);
            }
        }
    }
}