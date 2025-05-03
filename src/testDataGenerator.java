// Class Info: This class is used to generate test data to /data/student.csv and /data/employRecord.csv

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class testDataGenerator {

    //define HashSets to store unique data.
    //Student ID must be unique.
    //employ ID must be unique.

    static HashSet<String> studentIdSet = new HashSet<>();
    static HashSet<String> employIdSet = new HashSet<>();

    public class student {
        String name;
        String studentId;
        String major;
        String email;
        String phoneNumber;

        public student(String name, String studentId, String major, String email, String phoneNumber) {
            //use setters to set data.
            setName(name);
            setStudentId(studentId);
            setMajor(major);
            setEmail(email);
            setPhoneNumber(phoneNumber);
        }

        //setter must check if the data is unique.
        public void setName(String name) {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Name cannot be null or empty");
            }
            this.name = name;
        }
        public void setStudentId(String studentId) {
            if (studentIdSet.contains(studentId)) {
                throw new IllegalArgumentException("Student ID must be unique");
            }
            this.studentId = studentId;
            studentIdSet.add(studentId);
        }
        public void setMajor(String major) {
            this.major = major;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }

    public class employRecord {
        String employId;
        String studentId;
        String companyName;
        String position;
        String startDate;
        String endDate; // -1 for current position

        public employRecord(String employId, String studentId, String companyName, String position, String startDate, String endDate, String currentDate) {
            //use setters to set data.
            setEmployId(employId);
            setStudentId(studentId);
            setCompanyName(companyName);
            setPosition(position);
            setStartDate(startDate);
            setEndDate(endDate, currentDate);
        }
        //setter must check if the data is unique.
        public void setEmployId(String employId) {
            if (employIdSet.contains(employId)) {
                throw new IllegalArgumentException("Employ ID must be unique");
            }
            this.employId = employId;
            employIdSet.add(employId);
        }
        public void setStudentId(String studentId) {
            // student must exist in studentIdSet.
            if (!studentIdSet.contains(studentId)) {
                throw new IllegalArgumentException("Student ID must exist");
            }
            this.studentId = studentId;
        }
        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
        public void setPosition(String position) {
            this.position = position;
        }
        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }
        public void setEndDate(String endDate, String currentDate) {
            // -1 for current position
            // if endDate greater than current time, replace it with -1
            if (endDate.equals("-1")) {
                this.endDate = endDate;
            } else {
                // check if endDate is greater than current time
                if (endDate.compareTo(currentDate) > 0) {
                    this.endDate = "-1"; // replace with -1
                } else {
                    this.endDate = endDate;
                }
            }
        }
    }
    

    public static void main(String[] args) {
        // Ask the user for the number of students and employ records to generate
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of students to generate: ");
        int numStudents = scanner.nextInt();
        System.out.print("Enter the number of employ records to generate: ");
        int numEmployRecords = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Get current date
        LocalDate currentDate = LocalDate.now();

        // Create a list to store the students and employ records
        ArrayList<student> students = new ArrayList<>();
        ArrayList<employRecord> employRecords = new ArrayList<>();

        // Generate random student data for testing
        // not finished yet

    }

}