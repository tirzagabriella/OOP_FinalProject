import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.HashMap;

public class Assignment extends JFrame implements ActionListener {
    JButton addNewAssignmentBtn;
    JButton openSubmittedAssignmentsBtn;
    JButton backBtn;
    JPanel buttonPanel;
    SchoolEntity currentEntity;
    JTable assignmentsTable;
    String tableHeader[] = {"Id", "Title", "Desc", "Teacher", "Deadline"};
    DefaultTableModel tableModel;
    HashMap<String, String> assignmentsMap;

    Assignment(SchoolEntity e){
        super("Assignments");
        setSize(1500,800);

        // save logged in entity to local class attribute
        this.currentEntity = e;

        // fetch assignments data
        tableModel = new DefaultTableModel(tableHeader, 0);
        getAssignmentList();

        // add table to scroll pane
        JScrollPane sp = new JScrollPane(assignmentsTable);
        add(sp, BorderLayout.NORTH);

        // buttons
        buttonPanel = new JPanel();
        if(currentEntity.role.equals("teacher")) { // only for teachers to add
            addNewAssignmentBtn = new JButton("Add new assignment");
            addNewAssignmentBtn.addActionListener(this);
            buttonPanel.add(addNewAssignmentBtn);
        }
        openSubmittedAssignmentsBtn = new JButton("Submitted assignments");
        openSubmittedAssignmentsBtn.addActionListener(this);
        buttonPanel.add(openSubmittedAssignmentsBtn);
        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    void getAssignmentList(){
        try{
            conn c1  = new conn();
            String s1 = "select a.assignment_id, a.assignment_title , a.assignment_desc , u.user_name teacher , a.assignment_deadline from `assignment` a join `user` u on a.teacher_id = u.user_id;";

            // fetch the assignment datas
            ResultSet rs  = c1.s.executeQuery(s1);

            // reset table
            tableModel.setRowCount(0);

            // clear assignments map
            if(assignmentsMap != null){
                assignmentsMap.clear();
            } else {
                assignmentsMap = new HashMap<>();
            }

            // insert the assignment datas to table
            while(rs.next()){
                Object[] data = {
                    rs.getString("assignment_id"),
                    rs.getString("assignment_title"),
                    rs.getString("assignment_desc"),
                    rs.getString("teacher"),
                    rs.getString("assignment_deadline"),
                };

                tableModel.addRow(data);

                assignmentsMap.put(rs.getString("assignment_id"),rs.getString("assignment_title"));
            }

            // update table data and repaint
            assignmentsTable = new JTable(tableModel);
            assignmentsTable.repaint();
        }catch(Exception err){
            err.printStackTrace();
        }
    }

     @Override
    public void actionPerformed(ActionEvent ae) {
        String msg = ae.getActionCommand();
        System.out.println("Action command: " + msg);

        // page redirections
        if(msg.equals("Add new assignment")) {
            new AddNewAssignment(this.currentEntity).setVisible(true);
            this.setVisible(false);
        } else if(msg.equals("Submitted assignments")){
            new SubmittedAssignments(this.currentEntity, this.assignmentsMap).setVisible(true);
            this.setVisible(false);
        } else if(msg.equals("Back")) {
            System.out.println("Going back");
            new Home(this.currentEntity).setVisible(true);
            this.setVisible(false);
        }
    }
}
