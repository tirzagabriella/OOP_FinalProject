import javax.swing.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class Teacher extends Admin implements AdminInterface{
    public Teacher(String id, String name, String role) {
        super(id, name, role);
    }

    public boolean StudentCheckIn(String studentId) {
        try{

            conn c1 = new conn();

            // check if student exists
            String s1 = "select u.user_id from `user` u where u.user_id = '"+studentId+"' and u.user_role = 'student'";
            ResultSet rs  = c1.s.executeQuery(s1);
            int i = 0;
            while(rs.next()){
                i++;
            }

            if(i > 0) {
                // timestamp for checking-in
                String currentTimeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
                String q1 = "insert into attendance values('"+studentId+"','"+currentTimeStamp+"')";

                try{
                    // check in student
                    int res = c1.s.executeUpdate(q1);

                    return true;
                }catch(Exception ex){
                    ex.printStackTrace();

                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null,"User doesn't exist");

                return false;
            }
        }catch(Exception err){
            err.printStackTrace();

            return false;
        }
    }

    public boolean Add(String name, String id, String password) {
        // query to insert new user data
        String q1 = "insert into user values('"+name+"','"+id+"','student', '"+password+"')";

        try{
            // execute query
            conn c1 = new conn();
            c1.s.executeUpdate(q1);
            JOptionPane.showMessageDialog(null,"User Created!");

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
            String query = "select * from user where user_id = '"+userId+"' and user_role = 'student'";
            ResultSet rs  = c1.s.executeQuery(query);
            int i = 0;
            while(rs.next()){
                i++;
            }

            if(i > 0) {
                // proceed to removal if user exists
                query = "delete from user where user_id = '"+userId+"' and user_role = 'student'";

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
        } catch(Exception ex) {
            ex.printStackTrace();

            return false;
        }

    };

    public boolean AddAssignment(String title, String desc, String posterId, String deadline){
        // query to add new assignment
        String query = "insert into assignment (assignment_title, assignment_desc, teacher_id, assignment_deadline) values('"+title+"','"+desc+"','"+posterId+"','"+deadline+"')";

        try {
            // execute query
            conn c1 = new conn();
            c1.s.executeUpdate(query);

            return true;
        } catch (Exception ex){
            ex.printStackTrace();

            return false;
        }
    }

    public boolean ScoreAssignment(String score, String feedback, String ID, String studID){
        try {
            conn c1 = new conn();

            // check if assignment to score exists
            String query = "select * from submitted_assignment sa where sa.assignment_id = '"+ID+"' and sa.user_id = '"+studID+"'";
            ResultSet rs  = c1.s.executeQuery(query);
            int i = 0;
            while(rs.next()){
                i++;
            }

            if(i > 0) {
                // scoring flow
                // query to update assignment
                query = "UPDATE submitted_assignment sa " +
                        "set " +
                        "sa.assignment_status = 'scored', " +
                        "sa.assignment_score = '"+score+"', " +
                        "sa.assignment_feedback = '"+feedback+"' " +
                        "WHERE " +
                        "sa.assignment_id = '"+ID+"' " +
                        "and sa.user_id = '"+studID+"'";

                try {
                    // update assignment

                    c1.s.executeUpdate(query);
                    JOptionPane.showMessageDialog(null,"Assignment Submitted!");

                    return true;
                } catch (Exception ex){
                    ex.printStackTrace();

                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null,"Assignment doesn't exist!");
                return false;
            }

        } catch(Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }
    @Override
    public boolean Announce(String title, String desc, String creator){
        // query to insert new announcement data
        String query = "insert into announcement (announcement_title, announcement_description, user_id) values('Teacher announcement : "+title+"','"+desc+"','"+creator+"')";

        try {
            conn c1 = new conn();
            c1.s.executeUpdate(query);

            return true;
        } catch (Exception ex){
            ex.printStackTrace();

            return false;
        }
    }

    @Override
    public boolean RemoveAnnouncement(String announcementId) {
        try {
            conn c1 = new conn();

            // check if teacher's announcement exist
            String query = "select * from announcement where announcement_id = '"+announcementId+"' and user_id = '"+this.id+"'";
            ResultSet rs  = c1.s.executeQuery(query);
            int i = 0;
            while(rs.next()){
                i++;
            }

            if(i>0){
                // query to delete teacher's announcement
                String q1 = "delete from announcement where announcement_id = '"+announcementId+"' and user_id = '"+this.id+"'";

                try{
                    // delete announcement
                    c1.s.executeUpdate(q1);
                    JOptionPane.showMessageDialog(null,"Teacher's Announcement Deleted!");

                    return true;
                }catch(Exception ex){
                    ex.printStackTrace();

                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null,"Teacher's Announcement doesn't exist!");
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }
}
