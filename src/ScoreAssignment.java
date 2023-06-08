import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ScoreAssignment extends JFrame implements ActionListener {
    JLabel IDLabel, studIDLabel, scoreLabel, feedbackLabel;
    JTextField IDTf, studIDTf, scoreTf, feedbackTf;
    JButton add, cancel;
    JPanel formPanel, buttonPanel;
    SchoolEntity currentEntity;
    HashMap<String, String> assignmentsMap;

    ScoreAssignment(SchoolEntity e, HashMap<String, String> assignmentsMap){
        super("Submit assignment");
        setSize(500,500);

        // save logged in entity to local class attribute
        this.currentEntity = e;

        // add assignments map
        this.assignmentsMap = assignmentsMap;

        // form
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4,2,30,30));

        // assignment ID
        IDLabel = new JLabel("Assignment ID");
        formPanel.add(IDLabel);
        IDTf = new JTextField();
        formPanel.add(IDTf);

        // student ID
        studIDLabel = new JLabel("Student ID");
        formPanel.add(studIDLabel);
        studIDTf = new JTextField();
        formPanel.add(studIDTf);

        // score
        scoreLabel = new JLabel("Score");
        formPanel.add(scoreLabel);
        scoreTf = new JTextField();
        formPanel.add(scoreTf);

        // feedback
        feedbackLabel = new JLabel("Feedback");
        formPanel.add(feedbackLabel);
        feedbackTf = new JTextField();
        formPanel.add(feedbackTf);

        // add form panel to center
        add(formPanel, BorderLayout.CENTER);

        // buttons
        buttonPanel = new JPanel();
        add = new JButton("Add");
        add.addActionListener(this);
        buttonPanel.add(add);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        buttonPanel.add(cancel);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String msg = ae.getActionCommand();
        // System.out.println("Action command: " + msg);

        if(msg.equals("Add")){
            String ID = IDTf.getText();
            String studID = studIDTf.getText();
            String score = scoreTf.getText();
            String feedback = feedbackTf.getText();

            if(ID.equals("") || studID.equals("") || score.equals("") || feedback.equals("")) {
                JOptionPane.showMessageDialog(null,"All fields are required!");
            } else {
                Teacher tch = new Teacher(currentEntity.id, currentEntity.name, currentEntity.role);
                if(tch.ScoreAssignment(score, feedback, ID, studID)) {
                    // return to submitted assignments page
                    new SubmittedAssignments(this.currentEntity, this.assignmentsMap).setVisible(true);
                    this.setVisible(false);
                }
            }
        } else if(msg.equals("Cancel")) {
            new SubmittedAssignments(this.currentEntity, this.assignmentsMap).setVisible(true); // return to submitted assignments page
            this.setVisible(false);
        }
    }
}
