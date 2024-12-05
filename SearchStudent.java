import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class SearchStudent {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SchoolDatabase schoolDatabase = new SchoolDatabase();

        try {
            // Завантаження даних із файлів
            schoolDatabase.loadStudents("list.txt");
            schoolDatabase.loadTeachers("teachers.txt");

            // Інструкції для користувача
            System.out.println("| PROGRAM MENU |");
            System.out.println("S [tudent]: <lastname>");
            System.out.println("S [tudent]: <lastname> B[us]");
            System.out.println("T [eacher]: <lastname>");
            System.out.println("C [lassroom]: <number>");
            System.out.println("C [lassroom]: <number> T[eacher]");
            System.out.println("G [rade]: <number>");
            System.out.println("G [rade]: <number> T[eacher]");
            System.out.println("B [us]: <number>");
            System.out.println("A [dd] S[tudent] <lastname> <firstname> <grade> <classroom> <bus>");
            System.out.println("A [dd] T[eacher] <lastname> <firstname> <classroom>");
            System.out.println("STAT");
            System.out.println("Q[uit] - для завершення програми");

            while (true) {
                System.out.print("> ");
                String command = scanner.nextLine().trim();

                if (command.equalsIgnoreCase("Q") || command.equalsIgnoreCase("Quit")) {
                    System.out.println("До побачення!");
                    break;
                }

                long startTime = System.nanoTime(); // Початок часу виконання

                try {
                    String[] parts = command.split("\\s+");

                    if (parts[0].equalsIgnoreCase("S")) {
                        // Пошук студентів за прізвищем
                        if (parts.length == 2) {
                            String lastName = parts[1];
                            List<Student> students = schoolDatabase.findStudentsByLastName(lastName);
                            schoolDatabase.printStudentInfo(students);
                        } else if (parts.length == 3 && parts[2].equalsIgnoreCase("B")) {
                            String lastName = parts[1];
                            List<Student> students = schoolDatabase.findStudentsByLastName(lastName);
                            schoolDatabase.printStudentBusInfo(students);
                        } else {
                            System.out.println("Невірний формат команди для S. Використовуйте: S <lastname> або S <lastname> B");
                        }

                    } else if (parts[0].equalsIgnoreCase("C")) {
                        if (parts.length == 2) {
                            // Пошук студентів за номером класу
                            int classroom = Integer.parseInt(parts[1]);
                            List<Student> students = schoolDatabase.findStudentsByClassroom(classroom);
                            schoolDatabase.printStudentInfo(students);

                        } else if (parts.length == 3 && parts[2].equalsIgnoreCase("T")) {
                            // Пошук викладачів за номером класу
                            int classroom = Integer.parseInt(parts[1]);
                            List<Teacher> teachers = schoolDatabase.findTeachersByClassroom(classroom);
                            for (Teacher teacher : teachers) {
                                System.out.println(teacher.getFirstName() + " " + teacher.getLastName());
                            }
                        } else {
                            System.out.println("Невірний формат команди для C. Використовуйте: C <number> або C <number> T");
                        }

                    } else if (parts[0].equalsIgnoreCase("T")) {
                        // Пошук викладачів за прізвищем
                        if (parts.length == 2) {
                            String lastName = parts[1];
                            List<Teacher> teachers = schoolDatabase.findTeachersByLastName(lastName);
                            if (teachers.isEmpty()) {
                                System.out.println("Немає викладачів з таким прізвищем.");
                            } else {
                                for (Teacher teacher : teachers) {
                                    System.out.println(teacher.getFirstName() + " " + teacher.getLastName() +
                                            ", класна кімната: " + teacher.getClassroom());
                                }
                            }
                        } else {
                            System.out.println("Невірний формат команди для T. Використовуйте: T <lastname>");
                        }

                    } else if (parts[0].equalsIgnoreCase("G")) {
                        if (parts.length == 2) {
                            // Пошук студентів за класом (Grade)
                            int grade = Integer.parseInt(parts[1]);
                            List<Student> students = schoolDatabase.findStudentsByGrade(grade);
                            schoolDatabase.printStudentInfo(students);

                        } else if (parts.length == 3 && parts[2].equalsIgnoreCase("T")) {
                            // Пошук викладачів за класом (Grade)
                            int grade = Integer.parseInt(parts[1]);
                            List<Teacher> teachers = schoolDatabase.findTeachersByGrade(grade);
                            for (Teacher teacher : teachers) {
                                System.out.println(teacher.getFirstName() + " " + teacher.getLastName());
                            }
                        } else {
                            System.out.println("Невірний формат команди для G. Використовуйте: G <number> або G <number> T");
                        }

                    } else if (parts[0].equalsIgnoreCase("B")) {
                        if (parts.length == 2) {
                            // Пошук студентів за автобусом
                            int busNumber = Integer.parseInt(parts[1]);
                            List<Student> students = schoolDatabase.findStudentsByBus(busNumber);
                            schoolDatabase.printStudentBusInfo(students);
                        } else {
                            System.out.println("Невірний формат команди для B. Використовуйте: B <number>");
                        }

                    } else if (parts[0].equalsIgnoreCase("A")) {
                        if (parts[1].equalsIgnoreCase("S")) {
                            // Додавання студента
                            String lastName = parts[2];
                            String firstName = parts[3];
                            int grade = Integer.parseInt(parts[4]);
                            int classroom = Integer.parseInt(parts[5]);
                            int bus = Integer.parseInt(parts[6]);
                            schoolDatabase.addStudent(lastName, firstName, grade, classroom, bus);

                        } else if (parts[1].equalsIgnoreCase("T")) {
                            // Додавання викладача
                            String lastName = parts[2];
                            String firstName = parts[3];
                            int classroom = Integer.parseInt(parts[4]);
                            schoolDatabase.addTeacher(lastName, firstName, classroom);
                        } else {
                            System.out.println("Невірна команда для A [dd].");
                        }

                    } else if (parts[0].equalsIgnoreCase("STAT")) {
                        // Показ статистики
                        schoolDatabase.showStatistics();

                    } else {
                        System.out.println("Невідома команда. Перевірте правильність вводу.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Помилка: номер класу, оцінки чи автобуса має бути цілим числом.");
                }

                long endTime = System.nanoTime(); // Кінець часу виконання
                System.out.println("Час виконання: " + (endTime - startTime) / 1_000_000 + " мс\n");
            }
        } catch (IOException e) {
            System.out.println("Помилка при завантаженні даних: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
