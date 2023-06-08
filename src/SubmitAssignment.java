import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class SubmitAssignment extends JFrame implements ActionListener {
    JLabel IDLabel, dataLabel, deadlineLabel;
    JTextField IDTf, dataTf, deadlineTf;
    JButton add, cancel;
    JPanel formPanel, buttonPanel;
    SchoolEntity currentEntity;
    HashMap<String, String> assignmentsMap;

    SubmitAssignment(SchoolEntity e, HashMap<String, String> assignmentsMap){
        super("Submit assignment");
        setSize(500,500);

        // save logged in entity to local class attribute
        this.currentEntity = e;

        // get assignments map
        this.assignmentsMap = assignmentsMap;

        // form
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3,2,30,30));

        // assignment ID
        IDLabel = new JLabel("Assignment ID");
        formPanel.add(IDLabel);
        IDTf = new JTextField();
        formPanel.add(IDTf);

        // Data
        dataLabel = new JLabel("Assignment Answer");
        formPanel.add(dataLabel);
        dataTf = new JTextField();
        formPanel.add(dataTf);

        // add form panel to center of frame
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
            String data = dataTf.getText();

            if(assignmentsMap.containsKey(ID)){
                Student std = new Student(currentEntity.id, currentEntity.name, currentEntity.role);
                if(std.SubmitAssignment(ID, data, currentEntity.id)) {
                    // return to submitted assignments page
                    new SubmittedAssignments(this.currentEntity, this.assignmentsMap).setVisible(true);
                    this.setVisible(false);
                }
            } else {
                JOptionPane.showMessageDialog(null,"Assignment doesn't exist!");
            }
        } else if(msg.equals("Cancel")) {
            new SubmittedAssignments(this.currentEntity, this.assignmentsMap).setVisible(true); // return to submitted assignments page
            this.setVisible(false);
        }
    }
}
