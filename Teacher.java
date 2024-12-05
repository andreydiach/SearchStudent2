public class Teacher {
    private String lastName;
    private String firstName;
    private int classroom;

    public Teacher(String lastName, String firstName, int classroom) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.classroom = classroom;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getClassroom() {
        return classroom;
    }
}

