import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveUser extends JFrame implements ActionListener {
    JLabel userIdLabel;
    JTextField userIdTextField;
    JButton deleteButton,cancelButton;
    JPanel buttonpanel;
    SchoolEntity currentEntity;

    RemoveUser(SchoolEntity e) {
        super("Remove User");
        setLocation(350, 200);
        setSize(1500,800);

        // save logged in entity to local class attribute
        this.currentEntity = e;

        // form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(9, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);

        // form fields and labels
        userIdLabel = new JLabel("User_ID");
        userIdTextField = new JTextField();
        formPanel.add(userIdLabel);
        formPanel.add(userIdTextField);

        // add form panel to north of jframe
        add(formPanel, BorderLayout.NORTH);

        // initialize buttons
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
    }

    public void actionPerformed(ActionEvent ae){
        String msg = ae.getActionCommand();
        // System.out.println("Action command: " + msg);

        if(msg.equals("Delete")){
            String userId = userIdTextField.getText();

            if(currentEntity.role.equals("admin")) {
                Admin adm = new Admin(currentEntity.id, currentEntity.name, currentEntity.role);

                if(adm.Remove(userId)) {
                    JOptionPane.showMessageDialog(null,"User Deleted!");

                    // return to user list
                    new UserList(this.currentEntity, true).setVisible(true);
                    this.setVisible(false);
                }
            } else if(currentEntity.role.equals("teacher")) {
                Teacher tch = new Teacher(currentEntity.id, currentEntity.name, currentEntity.role);

                if(tch.Remove(userId)) {
                    JOptionPane.showMessageDialog(null,"Student Deleted!");

                    // return to user list
                    new UserList(this.currentEntity, true).setVisible(true);
                    this.setVisible(false);
                }
            }
        } else if (msg.equals("Cancel")) {
            new UserList(this.currentEntity, true).setVisible(true); // return to user list
            this.setVisible(false);
        }


    }
}