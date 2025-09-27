public class StudentUser extends User {

    private final String name;

    public StudentUser(String email, String name) {
        super(email, name);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}