package tracker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentsHandler {
    private static final Map<Integer, Student> students = new HashMap<>();

    public static void addStudent(String firstName, String lastName, String email) {
        Student student = new Student(firstName, lastName, email);
        students.put(student.getId(), student);
    }

    public static void printStudentsList() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Students:");
            for (Integer k : students.keySet()) {
                System.out.println(k);
            }
        }
    }

    public static List<String> getEmails() {
        return students.entrySet().parallelStream()
                .map(Map.Entry::getValue)
                .map(Student::getEmail)
                .collect(Collectors.toList());
    }

    public static Map<Integer, Student> getStudents() {
        return students;
    }
}