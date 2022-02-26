package tracker;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private int javaPoints;
    private int dsaPoints;
    private int dbPoints;
    private int springPoints;
    private int javaTasks = 0;
    private int dsaTasks = 0;
    private int dbTasks = 0;
    private int springTasks = 0;
    private static final int JAVA_MAX_POINTS = 600;
    private static final int DB_MAX_POINTS = 480;
    private static final int DSA_MAX_POINTS = 400;
    private static final int SPRING_MAX_POINTS = 550;
    private static int idCounter = 9999;
    List<String> notifyToSend = new ArrayList<>();

    public Student(String firstName, String lastName, String email) {
        this.id = ++idCounter;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.javaPoints = 0;
        this.dsaPoints = 0;
        this.dbPoints = 0;
        this.springPoints = 0;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public int getJavaPoints() {
        return javaPoints;
    }

    public int getDsaPoints() {
        return dsaPoints;
    }

    public int getDbPoints() {
        return dbPoints;
    }

    public int getSpringPoints() {
        return springPoints;
    }

    public int getJavaTasks() {
        return javaTasks;
    }

    public int getDsaTasks() {
        return dsaTasks;
    }

    public int getDbTasks() {
        return dbTasks;
    }

    public int getSpringTasks() {
        return springTasks;
    }

    public void addStudentPoints(String pointsInput) {
        String[] inputArray = pointsInput.split("\\s");
        int java = Integer.parseInt(inputArray[0]);
        int dsa = Integer.parseInt(inputArray[1]);
        int db = Integer.parseInt(inputArray[2]);
        int spring = Integer.parseInt(inputArray[3]);

        if (java > 0) {
            this.javaPoints += java;
            javaTasks++;
            if (this.javaPoints >= JAVA_MAX_POINTS) {
                notifyToSend.add("Java");
            }
        }

        if (dsa > 0) {
            this.dsaPoints += dsa;
            dsaTasks++;
            if (this.dsaPoints >= DSA_MAX_POINTS) {
                notifyToSend.add("DSA");
            }
        }

        if (db > 0) {
            this.dbPoints += db;
            dbTasks++;
            if (this.dbPoints >= DB_MAX_POINTS) {
                notifyToSend.add("Databases");
            }
        }

        if (spring > 0) {
            this.springPoints += spring;
            springTasks++;
            if (this.springPoints >= SPRING_MAX_POINTS) {
                notifyToSend.add("Spring");
            }
        }
    }

    public void printPoints() {
        System.out.printf("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d", this.id, this.javaPoints,
                this.dsaPoints, this.dbPoints, this.springPoints);
        System.out.println();
    }

    public int printNotify() {
        int flag = 0;
        if (notifyToSend.size() > 0) {
            for (String notify : notifyToSend) {
                System.out.println("To: " + this.email);
                System.out.println("Re: Your Learning Progress");
                System.out.println("Hello, " + this.firstName + " " + this.lastName +
                        "! You have accomplished our " + notify + " course!");
            }
            flag++;
        }

        notifyToSend.clear();
        return flag;
    }
}