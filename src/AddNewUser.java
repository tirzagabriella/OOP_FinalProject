import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNewUser extends JFrame implements ActionListener {
    JLabel nameLabel, idLabel, roleLabel, passwordLabel;
    JTextField nameTextfield, idTextfield;
    Choice roleChoice;
    JPasswordField passwordField;
    JButton addButton,cancelButton;
    JPanel buttonpanel;
    SchoolEntity currentEntity;

    AddNewUser(SchoolEntity e) {
        super("Add User");
        setLocation(350, 200);
        setSize(1500,800);

        // save logged in entity to local class attribute
        this.currentEntity = e;

        // form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(9, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);

        // form field and label for username
        nameLabel = new JLabel("User_Name");
        nameTextfield = new JTextField();
        formPanel.add(nameLabel);
        formPanel.add(nameTextfield);

        // form field and label for user id
        idLabel = new JLabel("User_ID ([yymmdd][ADM/TCH/STD][2-digit-num]). Example: 230507TCH01");
        idTextfield = new JTextField();
        formPanel.add(idLabel);
        formPanel.add(idTextfield);

        // form field (choice) and label for roles
        roleLabel = new JLabel ("User_Role");
        roleChoice = new Choice();
        if(currentEntity.role.equals("admin")){ // add additional roles if current logged in user's role is admin
            roleChoice.add("admin");
            roleChoice.add("teacher");
        }
        roleChoice.add("student");
        formPanel.add(roleLabel);
        formPanel.add(roleChoice);

        // form field and label for password
        passwordLabel = new JLabel("User_Password");
        passwordField = new JPasswordField();
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        // add form panel to north of jframe
        add(formPanel, BorderLayout.NORTH);

        // initialize buttons to add and cancel
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");

        // add action listener for button(s)
        addButton.addActionListener(this);
        cancelButton.addActionListener(this);

        // add buttons to button panel
        buttonpanel = new JPanel();
        buttonpanel.add(addButton);
        buttonpanel.add(cancelButton);

        // add button panel at south (bottom)
        add(buttonpanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent ae){
        String msg = ae.getActionCommand();
        // System.out.println("Action command: " + msg); // for testing

        if(msg.equals("Add")){
            String name = nameTextfield.getText();
            String id = idTextfield.getText();
            String role = roleChoice.getSelectedItem();
            String password = passwordField.getText();

            if(name.equals("") || id.equals("") || role.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(null,"All fields must be filled!");
            } else {
                if(currentEntity.role.equals("admin")){
                    Admin admin = new Admin(currentEntity.id, currentEntity.name, currentEntity.role);
                    if(admin.Add(name, id, role, password)){
                        JOptionPane.showMessageDialog(null,"User Created!");

                        // return to user list page
                        new UserList(this.currentEntity, true).setVisible(true);
                        this.setVisible(false);
                    }
                } else if(currentEntity.role.equals("teacher")) {
                    Teacher teacher = new Teacher(currentEntity.id, currentEntity.name, currentEntity.role);
                    if(teacher.Add(name, id, password)){
                        JOptionPane.showMessageDialog(null,"User Created!");

                        // return to user list page
                        new UserList(this.currentEntity, true).setVisible(true);
                        this.setVisible(false);
                    }
                }
            }
        } else if (msg.equals("Cancel")) {
            new UserList(this.currentEntity, true).setVisible(true); // return to user list page
            this.setVisible(false);
        }


    }
}
