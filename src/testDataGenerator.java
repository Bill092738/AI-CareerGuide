// Class Info:
// This class take data from /data/demoData/possibleFirstNames.csv, /data/demoData/possibleLastNames.csv, /data/demoData/possibleMajors.csv, /data/demoData/possibleEmails.csv, /data/demoData/possiblePhoneNumbers.csv, /data/demoData/possibleNations.csv, /data/demoData/possibleCompanyNames.csv, /data/demoData/possiblePositions.csv 
// This class is used to generate test data to /data/student.csv and /data/employRecord.csv
// For other info like university ranking, please refer to sql file in /data/demoData

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class testDataGenerator {

    // Define HashSets to store unique data.
    static HashSet<String> studentIdSet = new HashSet<>();
    static HashSet<String> employIdSet = new HashSet<>();

    // Data lists for random selection
    static List<String> firstNames = new ArrayList<>();
    static List<String> lastNames = new ArrayList<>();
    static List<String> majors = new ArrayList<>();
    static List<String> emails = new ArrayList<>();
    static List<String> nationalities = new ArrayList<>();
    static List<String> companyNames = new ArrayList<>();
    static List<String> positions = new ArrayList<>();

    // Cloud map loader for logical data generation
    static cloudMapLoader cloudMap;

    public class student {
        String name;
        String studentId;
        String major;
        String email;
        String nationality;
        int homeLand;
        int score; // Add score property
        int gender;

        public student(String name, String studentId, String major, String email, String nationality, int homeLand, int score, int gender) {
            setName(name);
            setStudentId(studentId);
            setMajor(major);
            setEmail(email);
            setNationality(nationality);
            setHomeLand(homeLand);
            setScore(score);
            setGender(gender);
        }

        public void setName(String name) {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Name cannot be null or empty");
            }
            this.name = name;
        }
        public void setStudentId(String studentId) {
            if (studentId == null || studentId.isEmpty()) {
                Random random = new Random();
                while (true) {
                    studentId = "S" + random.nextInt(1000000);
                    if (!studentIdSet.contains(studentId)) {
                        break;
                    }
                }
            }
            if (studentIdSet.contains(studentId)) {
                throw new IllegalArgumentException("Student ID must be unique");
            }
            this.studentId = studentId;
            studentIdSet.add(studentId);
        }
        public void setMajor(String major) { this.major = major; }
        public void setEmail(String email) { this.email = email; }
        public void setNationality(String nationality) { this.nationality = nationality; }
        public void setHomeLand(int homeLand) {
            if (homeLand < 0 || homeLand > 2) {
                Random random = new Random();
                homeLand = random.nextInt(3);
            }
            this.homeLand = homeLand;
        }
        public void setScore(int score) {
            if (score < 0) score = 0;
            if (score > 100) score = 100;
            this.score = score;
        }
        public void setGender(int gender) {
        if (gender != 0 && gender != 1) {
            Random random = new Random();
            gender = random.nextInt(2);
        }
        this.gender = gender;
        }
        public int getGender() {
        return gender;
        }
    }

    public class employRecord {
        String employId;
        String studentId;
        String companyName;
        String position;
        int salary;
        Date startDate;
        Date endDate;
        String nationality;
        int destination;

        public employRecord(String employId, String studentId, String companyName, String position, int salary, Date startDate, Date endDate, String nationality, int destination) {
            setEmployId(employId);
            setStudentId(studentId);
            setCompanyName(companyName);
            setPosition(position);
            setSalary(salary);
            setStartDate(startDate);
            setEndDate(endDate, new Date());
            setNationality(nationality);
            setDestination(destination);
        }

        public void setEmployId(String employId) {
            if (employId == null || employId.isEmpty()) {
                Random random = new Random();
                while (true) {
                    employId = "E" + random.nextInt(1000000);
                    if (!employIdSet.contains(employId)) {
                        break;
                    }
                }
            }
            if (employIdSet.contains(employId)) {
                throw new IllegalArgumentException("Employ ID must be unique");
            }
            this.employId = employId;
            employIdSet.add(employId);
        }
        public void setStudentId(String studentId) {
            if (!studentIdSet.contains(studentId)) {
                throw new IllegalArgumentException("Student ID must exist");
            }
            this.studentId = studentId;
        }
        public void setCompanyName(String companyName) { this.companyName = companyName; }
        public void setPosition(String position) { this.position = position; }
        public void setSalary(int salary) {
            if (salary < 0) {
                salary = salaryGenerator();
            }
            this.salary = salary;
        }
        public void setStartDate(Date startDate) { this.startDate = startDate; }
        public void setEndDate(Date endDate, Date currentDate) {
            if (endDate != null && startDate != null && endDate.before(startDate)) {
                throw new IllegalArgumentException("End date cannot be before start date");
            }
            //if (endDate != null && endDate.after(currentDate)) {
            //    throw new IllegalArgumentException("End date cannot be in the future");
            //}
            this.endDate = endDate;
        }
        public void setNationality(String nationality) { this.nationality = nationality; }
        public void setDestination(int destination) {
            if (destination < 0 || destination > 2) {
                throw new IllegalArgumentException("Destination must be 0, 1 or 2");
            }
            this.destination = destination;
        }
        int salaryGenerator() {
            Random random = new Random();
            return 30000 + random.nextInt(70001);
        }
    }

    // Helper: map value from one range to another
    static int mapRange(double value, double srcMin, double srcMax, int dstMin, int dstMax) {
        if (srcMax - srcMin == 0) return dstMin;
        double norm = (value - srcMin) / (srcMax - srcMin);
        return dstMin + (int)Math.round(norm * (dstMax - dstMin));
    }

    // Find min/max for x and y in cloud map
    static double[] findMinMax(List<double[]> points, int idx) {
        double min = Double.MAX_VALUE, max = -Double.MAX_VALUE;
        for (double[] p : points) {
            if (p[idx] < min) min = p[idx];
            if (p[idx] > max) max = p[idx];
        }
        return new double[]{min, max};
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of students to generate: ");
        int numStudents = scanner.nextInt();
        System.out.print("Enter the number of employ records to generate: ");
        int numEmployRecords = scanner.nextInt();
        scanner.nextLine();

        // Load CSV data You should change the path to your own data
        readFromCSV("C:/Users/31169/Desktop/AI-CareerGuide/AI-CareerGuide/data/demoData/possibleFirstNames.csv", firstNames);
        readFromCSV("C:/Users/31169/Desktop/AI-CareerGuide/AI-CareerGuide/data/demoData/possibleLastNames.csv", lastNames);
        readFromCSV("C:/Users/31169/Desktop/AI-CareerGuide/AI-CareerGuide/data/demoData/possibleMajors.csv", majors);
        readFromCSV("C:/Users/31169/Desktop/AI-CareerGuide/AI-CareerGuide/data/demoData/possibleEmails.csv", emails);
        readFromCSV("C:/Users/31169/Desktop/AI-CareerGuide/AI-CareerGuide/data/demoData/possibleNations.csv", nationalities);
        readFromCSV("C:/Users/31169/Desktop/AI-CareerGuide/AI-CareerGuide/data/demoData/possibleCompanyNames.csv", companyNames);
        readFromCSV("C:/Users/31169/Desktop/AI-CareerGuide/AI-CareerGuide/data/demoData/possiblePositions.csv", positions);

        // Load cloud map (choose one or random)
        cloudMap = new cloudMapLoader("C:/Users/31169/Desktop/AI-CareerGuide/AI-CareerGuide/src/pointCoordinatesForDemo.csv");

        // Get all points from cloudMap for min/max calculation
        List<double[]> allPoints = cloudMap.getPoints();
        double[] xMinMax = findMinMax(allPoints, 0);
        double[] yMinMax = findMinMax(allPoints, 1);

        ArrayList<student> students = new ArrayList<>();
        ArrayList<employRecord> employRecords = new ArrayList<>();

        // Generate students using cloud map for logical distribution
        ArrayList<double[]> usedPoints = new ArrayList<>();
        for (int i = 0; i < numStudents; i++) {
            double[] point = cloudMap.samplePoint();
            usedPoints.add(point); // Save for employRecord
            String name = randomFromList(firstNames) + " " + randomFromList(lastNames);
            String studentId = null;
            String major = majors.get((int)(point[0]) % majors.size());
            String email = randomFromList(emails);
            String nationality = nationalities.get((int)(point[1]) % nationalities.size());
            int homeLand = (int)(point[1]) % 3;
            int score = mapRange(point[0], xMinMax[0], xMinMax[1], 0, 100); // Map x to score
            Random random = new Random();
            int gender = random.nextInt(2);
            students.add(new testDataGenerator().new student(name, studentId, major, email, nationality, homeLand, score, gender));
        }

        // Generate employ records using cloud map for logical distribution
        Random rand = new Random();
        for (int i = 0; i < numEmployRecords; i++) {
            int idx = rand.nextInt(students.size());
            double[] point = usedPoints.get(idx); // Use same point as student for 1-1 mapping
            String employId = null;
            String studentId = students.get(idx).studentId;
            String companyName = companyNames.get((int)(point[0]) % companyNames.size());
            String position = positions.get((int)(point[1]) % positions.size());
            int salary = mapRange(point[1], yMinMax[0], yMinMax[1], 30000, 100000); // Map y to salary
            Date startDate = randomDate(2018, 2022);
            Date endDate = randomDate(2023, 2025);
            String nationality = nationalities.get((int)(point[1]) % nationalities.size());
            int destination = (int)(point[0]) % 3;
            employRecords.add(new testDataGenerator().new employRecord(employId, studentId, companyName, position, salary, startDate, endDate, nationality, destination));
        }

        writeToCSVstudent("C:/Users/31169/Desktop/AI-CareerGuide/AI-CareerGuide/data/student.csv", students);
        writeToCSVemployRecord("C:/Users/31169/Desktop/AI-CareerGuide/AI-CareerGuide/data/employRecord.csv", employRecords);

        System.out.println("Data generation complete.");
    }

    // Helper: pick random from list
    static String randomFromList(List<String> list) {
        if (list.isEmpty()) return "";
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    // Helper: random date
    static Date randomDate(int startYear, int endYear) {
        Calendar cal = Calendar.getInstance();
        int year = startYear + new Random().nextInt(endYear - startYear + 1);
        int dayOfYear = 1 + new Random().nextInt(365);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_YEAR, dayOfYear);
        return cal.getTime();
    }

    // Read CSV into list
    static void readFromCSV(String filePath, List<String> list) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    list.add(line.split(",")[0]);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read: " + filePath);
        }
    }

    // Write students to CSV
    static void writeToCSVstudent(String filePath, ArrayList<student> students) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("name,studentId,major,email,nationality,homeLand,score,gender");
            for (student s : students) {
                pw.printf("%s,%s,%s,%s,%s,%d,%d,%d\n", s.name, s.studentId, s.major, s.email, s.nationality, s.homeLand, s.score, s.gender);
            }
        } catch (IOException e) {
            System.err.println("Failed to write students: " + filePath);
        }
    }

    // Write employRecords to CSV
    static void writeToCSVemployRecord(String filePath, ArrayList<employRecord> employRecords) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("employId,studentId,companyName,position,salary,startDate,endDate,nationality,destination");
            for (employRecord e : employRecords) {
                pw.printf("%s,%s,%s,%s,%d,%s,%s,%s,%d\n",
                    e.employId, e.studentId, e.companyName, e.position, e.salary,
                    sdf.format(e.startDate), sdf.format(e.endDate), e.nationality, e.destination);
            }
        } catch (IOException ex) {
            System.err.println("Failed to write employRecords: " + filePath);
        }
    }
}