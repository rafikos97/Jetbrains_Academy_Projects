package tracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class SingleCourseStatistics {
    private static final Map<Integer, Student> studentsDB = StudentsHandler.getStudents();
    private static final int JAVA_MAX_POINTS = 600;
    private static final int DB_MAX_POINTS = 480;
    private static final int DSA_MAX_POINTS = 400;
    private static final int SPRING_MAX_POINTS = 550;

    public static void showJavaStatistics() {
        Map<Integer, Integer> mapIdGrades = new HashMap<>();

        for (Map.Entry<Integer, Student> entry : studentsDB.entrySet()) {
            int points = entry.getValue().getJavaPoints();
            if (points > 0) {
                mapIdGrades.put(entry.getKey(), points);
            }
        }

        System.out.println("Java");
        System.out.println("id points completed");
        showStatistics(mapIdGrades, JAVA_MAX_POINTS);
    }

    public static void showDBStatistics() {
        Map<Integer, Integer> mapIdGrades = new HashMap<>();

        for (Map.Entry<Integer, Student> entry : studentsDB.entrySet()) {
            int points = entry.getValue().getDbPoints();
            if (points > 0) {
                mapIdGrades.put(entry.getKey(), points);
            }
        }

        System.out.println("Databases");
        System.out.println("id points completed");
        showStatistics(mapIdGrades, DB_MAX_POINTS);
    }

    public static void showDSAStatistics() {
        Map<Integer, Integer> mapIdGrades = new HashMap<>();

        for (Map.Entry<Integer, Student> entry : studentsDB.entrySet()) {
            int points = entry.getValue().getDsaPoints();
            if (points > 0) {
                mapIdGrades.put(entry.getKey(), points);
            }
        }

        System.out.println("DSA");
        System.out.println("id points completed");
        showStatistics(mapIdGrades, DSA_MAX_POINTS);
    }

    public static void showSpringStatistics() {
        Map<Integer, Integer> mapIdGrades = new HashMap<>();

        for (Map.Entry<Integer, Student> entry : studentsDB.entrySet()) {
            int points = entry.getValue().getSpringPoints();
            if (points > 0) {
                mapIdGrades.put(entry.getKey(), points);
            }
        }

        System.out.println("Spring");
        System.out.println("id points completed");
        showStatistics(mapIdGrades, SPRING_MAX_POINTS);
    }

    private static void showStatistics(Map<Integer, Integer> map, int maxPoints) {
        Map<Integer, Double> sortedGrades = new LinkedHashMap<>();

        List<Map.Entry<Integer, Integer>> mapEntry = new ArrayList<>(map.entrySet());
        mapEntry.sort(Comparator.comparing(Map.Entry<Integer, Integer>::getValue).reversed().thenComparing(Map.Entry::getKey));
        mapEntry.forEach(entry -> sortedGrades.put(entry.getKey(), (double) entry.getValue() / maxPoints * 100));

        sortedGrades.forEach((key, value) -> {
            System.out.print(key + " ");
            System.out.print(map.get(key) + " ");
            System.out.print(BigDecimal.valueOf(value).setScale(1, RoundingMode.HALF_UP) + "%");
            System.out.println();
        });
    }
}
