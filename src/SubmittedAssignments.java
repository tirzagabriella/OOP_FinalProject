import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.HashMap;

public class SubmittedAssignments extends JFrame implements ActionListener {
    JButton scoreAssignmentBtn; // btn for teachers
    JButton submitAssignmentBtn; // btn for students
    JButton backBtn;
    SchoolEntity currentEntity;
    JPanel buttonPanel;
    DefaultTableModel tableModel;
    JTable assignmentsTable;
    String tableHeader[] = {"Id", "Title", "Data", "Status", "Score", "Feedback", "UserId", "UserName"};
    HashMap<String, String> assignmentsMap;

    SubmittedAssignments(SchoolEntity e, HashMap<String, String> assignmentsMap){
        super("Submitted Assignments");
        setSize(1500,800);

        // save logged in entity to local class attribute
        this.currentEntity = e;

        // get assignments map
        this.assignmentsMap = assignmentsMap;

        // fetch submitted assignments data
        tableModel = new DefaultTableModel(tableHeader, 0);
        getSubmittedAssignments();
        JScrollPane sp = new JScrollPane(assignmentsTable);
        add(sp, BorderLayout.NORTH);

        // buttons
        buttonPanel = new JPanel();
        if(currentEntity.role.equals("teacher")){
            // for teachers to score student's assignment(s)
            scoreAssignmentBtn = new JButton("Score assignment");
            scoreAssignmentBtn.addActionListener(this);
            buttonPanel.add(scoreAssignmentBtn);
        } else {
            // for students to submit their assignment
            submitAssignmentBtn = new JButton("Submit assignment");
            submitAssignmentBtn.addActionListener(this);
            buttonPanel.add(submitAssignmentBtn);
        }
        // back btn
        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    void getSubmittedAssignments(){
        // System.out.println("Fetching assignments data");
        try{
            // fetch assignment data
            conn c1  = new conn();
            String s1 = "select " +
                    "a.assignment_id, " +
                    "a.assignment_title, " +
                    "sa.assignment_data, " +
                    "sa.assignment_status, " +
                    "sa.assignment_score, " +
                    "sa.assignment_feedback, " +
                    "sa.user_id, " +
                    "u.user_name " +
                    "from submitted_assignment sa " +
                    "join `assignment` a on sa.assignment_id = a.assignment_id " +
                    "join `user` u on u.user_id = sa.user_id;";
            ResultSet rs  = c1.s.executeQuery(s1);

            // reset table
            tableModel.setRowCount(0);

            // fill in table datas
            while(rs.next()){
                Object[] data = {
                    rs.getString("assignment_id"),
                    rs.getString("assignment_title"),
                    rs.getString("assignment_data"),
                    rs.getString("assignment_status"),
                    rs.getString("assignment_score"),
                    rs.getString("assignment_feedback"),
                    rs.getString("user_id"),
                    rs.getString("user_name")
                };

                tableModel.addRow(data);
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
        // System.out.println("Action command: " + msg);

        if (msg.equals("Score assignment")){
            new ScoreAssignment(this.currentEntity, this.assignmentsMap).setVisible(true);
            this.setVisible(false);
        } else if (msg.equals("Submit assignment")) {
            new SubmitAssignment(this.currentEntity, this.assignmentsMap).setVisible(true);
            this.setVisible(false);
        } else if(msg.equals("Back")) {
             new Assignment(this.currentEntity).setVisible(true);
             this.setVisible(false);
         }
    }
}
