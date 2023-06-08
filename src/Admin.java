import javax.swing.*;
import java.sql.ResultSet;

// class for admin object
public class Admin extends SchoolEntity implements AdminInterface{

    public Admin(String id, String name, String role) {
        super(id, name, role);
    }

    public boolean Add(String name, String id, String role, String password) {
        // query to insert new user data
        String q1 = "insert into user values('"+name+"','"+id+"','"+role+"', '"+password+"')";

        try{
            // execute query
            conn c1 = new conn();
            c1.s.executeUpdate(q1);

            return true;
        }catch(Exception ex){
            ex.printStackTrace();

            return false;
        }
    }

    public boolean Remove(String userId) {
        try {
            conn c1 = new conn();

            // check user existence
            String query = "select * from user where user_id = '"+userId+"'";
            ResultSet rs  = c1.s.executeQuery(query);
            int i = 0;
            while(rs.next()){
                i++;
            }

            if(i > 0) {
                // user removal flow
                query = "delete from user where user_id = '"+userId+"'";

                try{
                    // delete user
                    c1.s.executeUpdate(query);

                    return true;
                }catch(Exception ex){
                    ex.printStackTrace();

                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null,"User doesn't exist!");

                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }

    };

    public boolean Announce(String title, String desc, String creator){
        // query to insert new announcement data
        String query = "insert into announcement (announcement_title, announcement_description, user_id) values('Admin announcement : "+title+"','"+desc+"','"+creator+"')";

        try {
            conn c1 = new conn();
            c1.s.executeUpdate(query);

            return true;
        } catch (Exception ex){
            ex.printStackTrace();

            return false;
        }
    }

    public boolean RemoveAnnouncement(String announcementId){
        try {
            conn c1 = new conn();

            // check if announcement exist
            String query = "select * from announcement where announcement_id = '"+announcementId+"'";
            ResultSet rs  = c1.s.executeQuery(query);
            int i = 0;
            while(rs.next()){
                i++;
            }

            if(i>0){
                // query to delete announcement
                String q1 = "delete from announcement where announcement_id = '"+announcementId+"'";

                try{
                    // delete announcement
                    c1.s.executeUpdate(q1);
                    JOptionPane.showMessageDialog(null,"Announcement Deleted!");

                    return true;
                }catch(Exception ex){
                    ex.printStackTrace();

                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null,"Announcement doesn't exist!");
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }

    }
}
