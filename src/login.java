

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class login extends JFrame implements ActionListener
{
    JLabel l1,l2,l3;
    JTextField tf1;
    JPasswordField pf2;
    JButton b1;
    JPanel formPanel,btnPanel;

    login()
    {
        super("Login Page");

        // labels and text fields for login form
        l1=new JLabel("User Name");
        l2=new JLabel("Password");
        tf1=new JTextField(15);
        pf2=new JPasswordField(15);

        // login button
        ImageIcon ic1=new ImageIcon(ClassLoader.getSystemResource("images/login.png"));
        Image i1=ic1.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        b1=new JButton("Login",new ImageIcon(i1));
        b1.addActionListener(this);

        // login page image
        ImageIcon ic3=new ImageIcon(ClassLoader.getSystemResource("images/pop.png"));
        Image i3=ic3.getImage().getScaledInstance(340,370,Image.SCALE_DEFAULT);
        ImageIcon icc3=new ImageIcon(i3);
        l3=new JLabel(icc3);

        // set frame layout
        setLayout(new BorderLayout());

        // add login page image to the left side
        add(l3,BorderLayout.WEST);

        // initialize panels
        formPanel=new JPanel();
        btnPanel=new JPanel();

        // add labels and text fields to form panel
        formPanel.add(l1);
        formPanel.add(tf1);
        formPanel.add(l2);
        formPanel.add(pf2);

        // add form panel to center of frame
        add(formPanel,BorderLayout.CENTER);

        // add login btn to btn panel
        btnPanel.add(b1);

        // add btn panel at the bottom of the frame
        add(btnPanel,BorderLayout.SOUTH);

        // set some bg color for each panel
        formPanel.setBackground(Color.WHITE);
        btnPanel.setBackground(Color.WHITE);

        // frame size and location
        setSize(640,450);
        setLocation(600,400);
        setVisible(true);
    }


    public void actionPerformed(ActionEvent ae){

        try{
            conn c1 = new conn();
            String a  = tf1.getText();
            String b  = pf2.getText();
            String q  = "select * from user where user_name = '"+a+"' and user_password = '"+b+"'";
            ResultSet rs = c1.s.executeQuery(q);
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Successfully logged in!!! Welcome " + rs.getString("user_name"));
                if(rs.getString("user_role").equals("admin")){
                    SchoolEntity loggedInEntity = new Admin(rs.getString("user_id"), rs.getString("user_name"), rs.getString("user_role"));
                    new Home(loggedInEntity).setVisible(true);
                    this.setVisible(false);
                } else if (rs.getString("user_role").equals("teacher")){
                    SchoolEntity loggedInEntity = new Teacher(rs.getString("user_id"), rs.getString("user_name"), rs.getString("user_role"));
                    new Home(loggedInEntity).setVisible(true);
                    this.setVisible(false);
                } else if (rs.getString("user_role").equals("student")){
                    SchoolEntity loggedInEntity = new Teacher(rs.getString("user_id"), rs.getString("user_name"), rs.getString("user_role"));
                    new Home(loggedInEntity).setVisible(true);
                    this.setVisible(false);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Invalid login");
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("error: "+e);
        }
    }

    public static void main(String[] args){
        new login().setVisible(true);
    }

}

