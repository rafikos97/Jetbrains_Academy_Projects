package tracker;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OverallStatistics {
    private static final Map<String, Integer> enrolledStudents = new HashMap<>();
    private static final Map<String, Integer> coursesPointsSum = new HashMap<>();
    private static final Map<String, Integer> overallTasks = new HashMap<>();
    private static final Map<String, Double> averagePoints = new HashMap<>();

    private static final Map<Integer, Student> studentsDB = StudentsHandler.getStudents();

    static {
        int javaStudents = (int) studentsDB.values().stream()
                .filter(i -> i.getJavaPoints() > 0)
                .count();

        enrolledStudents.put("Java", javaStudents);

        int dbStudents = (int) studentsDB.values().stream()
                .filter(i -> i.getDbPoints() > 0)
                .count();

        enrolledStudents.put("Databases", dbStudents);

        int dsaStudents = (int) studentsDB.values().stream()
                .filter(i -> i.getDsaPoints() > 0)
                .count();

        enrolledStudents.put("DSA", dsaStudents);

        int springStudents = (int) studentsDB.values().stream()
                .filter(i -> i.getSpringPoints() > 0)
                .count();

        enrolledStudents.put("Spring", springStudents);

        int javaSum = studentsDB.values().stream()
                .map(Student::getJavaPoints)
                .filter(javaPoints -> javaPoints > 0)
                .reduce(0, Integer::sum);

        coursesPointsSum.put("Java", javaSum);

        int dbSum = studentsDB.values().stream()
                .map(Student::getDbPoints)
                .filter(dbPoints -> dbPoints > 0)
                .reduce(0, Integer::sum);

        coursesPointsSum.put("Databases", dbSum);

        int dsaSum = studentsDB.values().stream()
                .map(Student::getDsaPoints)
                .filter(dsaPoints -> dsaPoints > 0)
                .reduce(0, Integer::sum);

        coursesPointsSum.put("DSA", dsaSum);

        int springSum = studentsDB.values().stream()
                .map(Student::getSpringPoints)
                .filter(springPoints -> springPoints > 0)
                .reduce(0, Integer::sum);

        coursesPointsSum.put("Spring", springSum);

        int javaTaskSum = studentsDB.values().stream()
                .map(Student::getJavaTasks)
                .filter(javaTasks -> javaTasks > 0)
                .reduce(0, Integer::sum);

        overallTasks.put("Java", javaTaskSum);

        int dsaTaskSum = studentsDB.values().stream()
                .map(Student::getDsaTasks)
                .filter(dsaTasks -> dsaTasks > 0)
                .reduce(0, Integer::sum);

        overallTasks.put("DSA", dsaTaskSum);

        int dbTaskSum = studentsDB.values().stream()
                .map(Student::getDbTasks)
                .filter(dbTasks -> dbTasks > 0)
                .reduce(0, Integer::sum);

        overallTasks.put("Databases", dbTaskSum);

        int springTaskSum = studentsDB.values().stream()
                .map(Student::getSpringTasks)
                .filter(springTasks -> springTasks > 0)
                .reduce(0, Integer::sum);

        overallTasks.put("Spring", springTaskSum);

        for (Map.Entry<String, Integer> entry : enrolledStudents.entrySet()) {
            if (entry.getValue() > 0) {
                String key = entry.getKey();
                double avg = (double) coursesPointsSum.get(key) / entry.getValue();
                averagePoints.put(key, avg);
            }
        }
    }

    private String findMostPopularCourse() {
        int maxValueInMap = (Collections.max(enrolledStudents.values()));
        StringBuilder sb = new StringBuilder();

        String SEPARATOR = "";
        for (Map.Entry<String, Integer> entry : enrolledStudents.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                sb.append(SEPARATOR);
                sb.append(entry.getKey());
                SEPARATOR = ", ";
            }
        }

        String result = sb.toString();
        return result.equals("") ? "n/a" : result;
    }

    private String findLeastPopularCourse() {
        int minValueInMap = (Collections.min(enrolledStudents.values()));
        StringBuilder sb = new StringBuilder();

        String SEPARATOR = "";
        for (Map.Entry<String, Integer> entry : enrolledStudents.entrySet()) {
            if (entry.getValue() == minValueInMap && !findMostPopularCourse().contains(entry.getKey())) {
                sb.append(SEPARATOR);
                sb.append(entry.getKey());
                SEPARATOR = ", ";
            }
        }

        String result = sb.toString();
        return result.equals("") ? "n/a" : result;
    }

    private String findHighestActivityCourse() {
        int maxValueInMap = (Collections.max(overallTasks.values()));
        StringBuilder sb = new StringBuilder();

        String SEPARATOR = "";
        for (Map.Entry<String, Integer> entry : overallTasks.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                sb.append(SEPARATOR);
                sb.append(entry.getKey());
                SEPARATOR = ", ";
            }
        }

        String result = sb.toString();
        return result.equals("") ? "n/a" : result;
    }

    private String findLowestActivityCourse() {
        int minValueInMap = (Collections.min(overallTasks.values()));
        StringBuilder sb = new StringBuilder();

        String SEPARATOR = "";
        for (Map.Entry<String, Integer> entry : overallTasks.entrySet()) {
            if (entry.getValue() == minValueInMap && !findHighestActivityCourse().contains(entry.getKey())) {
                sb.append(SEPARATOR);
                sb.append(entry.getKey());
                SEPARATOR = ", ";
            }
        }

        String result = sb.toString();
        return result.equals("") ? "n/a" : result;
    }

    private String findEasiestCourse() {
        double maxValueInMap = (Collections.max(averagePoints.values()));
        StringBuilder sb = new StringBuilder();

        String SEPARATOR = "";
        for (Map.Entry<String, Double> entry : averagePoints.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                sb.append(SEPARATOR);
                sb.append(entry.getKey());
                SEPARATOR = ", ";
            }
        }

        String result = sb.toString();
        return result.equals("") ? "n/a" : result;
    }

    private String findHardestCourse() {
        double minValueInMap = (Collections.min(averagePoints.values()));
        StringBuilder sb = new StringBuilder();

        String SEPARATOR = "";
        for (Map.Entry<String, Double> entry : averagePoints.entrySet()) {
            if (entry.getValue() == minValueInMap && !findEasiestCourse().contains(entry.getKey())) {
                sb.append(SEPARATOR);
                sb.append(entry.getKey());
                SEPARATOR = ", ";
            }
        }

        String result = sb.toString();
        return result.equals("") ? "n/a" : result;
    }

    public void printStatistics() {
        String mostPopular;
        String leastPopular;
        String highestActivity;
        String lowestActivity;
        String easiest;
        String hardest;

        if (Collections.max(enrolledStudents.values()) > 0) {
            mostPopular = findMostPopularCourse();
            leastPopular = findLeastPopularCourse();
            highestActivity = findHighestActivityCourse();
            lowestActivity = findLowestActivityCourse();
            easiest = findEasiestCourse();
            hardest = findHardestCourse();
        } else {
            mostPopular = "n/a";
            leastPopular = "n/a";
            highestActivity = "n/a";
            lowestActivity = "n/a";
            easiest = "n/a";
            hardest = "n/a";
        }

        System.out.println("Most popular: " + mostPopular);
        System.out.println("Least popular: " + leastPopular);
        System.out.println("Highest activity: " + highestActivity);
        System.out.println("Lowest activity: " + lowestActivity);
        System.out.println("Easiest course: " + easiest);
        System.out.println("Hardest course: " + hardest);
    }
}
