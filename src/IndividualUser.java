public class IndividualUser extends User {
    private String studentId;

    public IndividualUser(String email, String displayName) {
        super(email, displayName);
    }

    public IndividualUser(String email, String displayName, String password) {
        super(email, displayName);
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public boolean IsStudent() {
        return studentId != null && !studentId.isEmpty();
    }

    @Override
    public String toString() {
        return "IndividualUser[ email=" + getEmail() +
                ", displayName=" + getDisplayName() + ", studentId=" + studentId;
    }
}

