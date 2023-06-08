import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNewAssignment extends JFrame implements ActionListener {
    JLabel titleLabel, descLabel, deadlineLabel;
    JTextField titleTf, descTf, deadlineTf;
    JButton add, cancel;
    JPanel formPanel, buttonPanel;
    SchoolEntity currentEntity;

    AddNewAssignment(SchoolEntity e){
        super("Add new assignment");
        setSize(500,500);

        // save logged in entity to local class attribute
        this.currentEntity = e;

        // form
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3,2,30,30));
        titleLabel = new JLabel("Title");
        formPanel.add(titleLabel);
        titleTf = new JTextField();
        formPanel.add(titleTf);
        descLabel = new JLabel("Description");
        formPanel.add(descLabel);
        descTf = new JTextField();
        formPanel.add(descTf);
        deadlineLabel = new JLabel("Deadline (YYYY-MM-DD)");
        formPanel.add(deadlineLabel);
        deadlineTf = new JTextField();
        formPanel.add(deadlineTf);
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
            String title = titleTf.getText();
            String desc = descTf.getText();
            String deadline = deadlineTf.getText();

            if(title.equals("") || desc.equals("") || deadline.equals("")) {
                JOptionPane.showMessageDialog(null,"All fields are required!");
            } else {
                Teacher tch = new Teacher(currentEntity.id, currentEntity.name, currentEntity.role);
                if(tch.AddAssignment(title, desc, currentEntity.id, deadline)) {
                    JOptionPane.showMessageDialog(null,"Assignment Created!");

                    // return to assignments page
                    new Assignment(this.currentEntity).setVisible(true);
                    this.setVisible(false);
                }
            }
        } else if(msg.equals("Cancel")) {
            new Assignment(this.currentEntity).setVisible(true); // return to assignments page
            this.setVisible(false);
        }
    }
}
