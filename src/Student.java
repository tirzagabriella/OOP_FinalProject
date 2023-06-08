import javax.swing.*;

public class Student extends SchoolEntity {
    public Student(String id, String name, String role) {
        super(id, name, role);
    }

    public boolean SubmitAssignment(String ID, String data, String submitterId){
        String query = "insert into submitted_assignment (assignment_id, assignment_data, assignment_status, user_id) values('"+ID+"','"+data+"', 'pending','"+submitterId+"')";
        try {
            // add assignment
            conn c1 = new conn();
            c1.s.executeUpdate(query);
            JOptionPane.showMessageDialog(null,"Assignment Submitted!");

            return true;
        } catch (Exception ex){
            ex.printStackTrace();

            return false;
        }
    }
}
