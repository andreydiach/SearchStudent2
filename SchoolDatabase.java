import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SchoolDatabase {
    private List<Student> students;
    private List<Teacher> teachers;

    public SchoolDatabase() {
        students = new ArrayList<>();
        teachers = new ArrayList<>();
    }

    // Завантаження студентів із файлу list.txt
    public void loadStudents(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 5) {
                    String lastName = fields[0].trim();
                    String firstName = fields[1].trim();
                    int grade = Integer.parseInt(fields[2].trim());
                    int classroom = Integer.parseInt(fields[3].trim());
                    int bus = Integer.parseInt(fields[4].trim());
                    students.add(new Student(lastName, firstName, grade, classroom, bus));
                }
            }
        }
    }

    // Завантаження викладачів із файлу teacher.txt
    public void loadTeachers(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 3) {
                    String lastName = fields[0].trim();
                    String firstName = fields[1].trim();
                    int classroom = Integer.parseInt(fields[2].trim());
                    teachers.add(new Teacher(lastName, firstName, classroom));
                }
            }
        }
    }

    public List<Student> findStudentsByLastName(String lastName) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getLastName().equalsIgnoreCase(lastName)) {
                result.add(student);
            }
        }
        return result;
    }

    public List<Student> findStudentsByClassroom(int classroom) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getClassroom() == classroom) {
                result.add(student);
            }
        }
        return result;
    }

    public List<Teacher> findTeachersByClassroom(int classroom) {
        List<Teacher> result = new ArrayList<>();
        for (Teacher teacher : teachers) {
            if (teacher.getClassroom() == classroom) {
                result.add(teacher);
            }
        }
        return result;
    }

    public List<Student> findStudentsByGrade(int grade) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getGrade() == grade) {
                result.add(student);
            }
        }
        return result;
    }

    public List<Teacher> findTeachersByGrade(int grade) {
        List<Teacher> result = new ArrayList<>();
        for (Teacher teacher : teachers) {
            for (Student student : students) {
                if (student.getGrade() == grade && student.getClassroom() == teacher.getClassroom()) {
                    result.add(teacher);
                    break;
                }
            }
        }
        return result;
    }

    public List<Student> findStudentsByBus(int bus) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getBus() == bus) {
                result.add(student);
            }
        }
        return result;
    }

    public void printStudentInfo(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("Немає студентів за заданим критерієм.");
        } else {
            for (Student student : students) {
                System.out.println(student.getFirstName() + " " + student.getLastName() +
                        ", класна кімната: " + student.getClassroom() +
                        ", клас: " + student.getGrade() +
                        ", автобус: " + student.getBus());
            }
        }
    }

    public void printStudentBusInfo(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("Немає студентів за заданим критерієм.");
        } else {
            for (Student student : students) {
                System.out.println(student.getFirstName() + " " + student.getLastName() + ", автобус: " + student.getBus());
            }
        }
    }

    public List<Teacher> findTeachersByLastName(String lastName) {
        List<Teacher> result = new ArrayList<>();
        for (Teacher teacher : teachers) {
            if (teacher.getLastName().equalsIgnoreCase(lastName)) {
                result.add(teacher);
            }
        }
        return result;
    }

    public void addStudent(String lastName, String firstName, int grade, int classroom, int bus) {
        students.add(new Student(lastName, firstName, grade, classroom, bus));
        System.out.println("Студента додано: " + firstName + " " + lastName);
    }

    public void addTeacher(String lastName, String firstName, int classroom) {
        teachers.add(new Teacher(lastName, firstName, classroom));
        System.out.println("Викладача додано: " + firstName + " " + lastName);
    }

    public void showStatistics() {
        System.out.println("Кількість студентів: " + students.size());
        System.out.println("Кількість викладачів: " + teachers.size());
    }
}
