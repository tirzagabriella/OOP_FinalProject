import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class UserList extends JFrame implements ActionListener {
    JButton addBtn;
    JButton removeBtn;
    JButton backBtn;
    JPanel tablePanel;
    JPanel buttonPanel;
    JTable userTable;
    String tableHeader[] = {"Name", "ID", "Role"};
    DefaultTableModel tableModel;
    SchoolEntity currentEntity;

    UserList(SchoolEntity e, boolean isEditable){
        super("[" + e.name + "] User List");
        setSize(1500,800);

        // save logged in entity to local class attribute
        this.currentEntity = e;

        // fetch user list data
        tableModel = new DefaultTableModel(tableHeader, 0);
        getUserList();

        // only if user list page is set to editable
        if(isEditable){
            addBtn = new JButton("Add new user");
            addBtn.addActionListener(this);
            removeBtn = new JButton("Remove existing user");
            removeBtn.addActionListener(this);
            backBtn = new JButton("Back");
            backBtn.addActionListener(this);

            // add buttons to panel
            buttonPanel = new JPanel();
            buttonPanel.add(addBtn);
            buttonPanel.add(removeBtn);
            buttonPanel.add(backBtn);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        JScrollPane sp = new JScrollPane(userTable);
        add(sp);
    }

    void getUserList(){
        // System.out.println("Fetching users data");
        try{
            conn c1  = new conn();
            String s1 = "select u.user_name, u.user_id, u.user_role from `user` u";
            if(this.currentEntity.role.equals("admin")){ // admin will only get list of users with roles other than 'admin'
                s1 = s1 + " where u.user_role <> 'admin'";
            } else if (currentEntity.role.equals("teacher")) { // teachers will get user list with the role 'student'
                s1 = s1 + " where u.user_role = 'student'";
            }
            ResultSet rs  = c1.s.executeQuery(s1);

            // reset table
            tableModel.setRowCount(0);

            // add table data
            while(rs.next()){
                Object[] data = {
                    rs.getString("user_name"),
                    rs.getString("user_id"),
                    rs.getString("user_role"),
                };

                tableModel.addRow(data);
            }

            // update user data and repaint
            userTable = new JTable(tableModel);
            userTable.repaint();
        }catch(Exception err){
            err.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae){
        String msg = ae.getActionCommand();
        System.out.println("Action command: " + msg);

        if(msg.equals("Back")) { // go back to home
            System.out.println("Going back");
            new Home(this.currentEntity).setVisible(true);
            this.setVisible(false);
        } else if(msg.equals("Add new user")){ // redirect to add-user page
            new AddNewUser(this.currentEntity).setVisible(true);
            this.setVisible(false);
        } else if (msg.equals("Remove existing user")) { // redirect to remove-user page
            new RemoveUser(this.currentEntity).setVisible(true);
            this.setVisible(false);
        }
    }
}