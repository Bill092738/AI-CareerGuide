// Class Info: This class is used to generate test data to /data/student.csv and /data/employRecord.csv

import java.util.Scanner;

public class testDataGenerator {

    public class student {
        String name;
        String studentId;
        String major;
        String email;
        String phoneNumber;

        public student(String name, String studentId, String major, String email, String phoneNumber) {
            //use setters to set data.
        }

        //setter must check if the data is unique.
    }

    public class employRecord {
        String studentId;
        String companyName;
        String position;
        String startDate;
        String endDate;

        public employRecord(String studentId, String companyName, String position, String startDate, String endDate) {
            //use setters to set data.
        }
        //setter must check if the data is unique.
    }
    

    public static void main(String[] args) {
        // Ask the user for the number of students and employ records to generate
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of students to generate: ");
        int numStudents = scanner.nextInt();
        System.out.print("Enter the number of employ records to generate: ");
        int numEmployRecords = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        
        // Create a list to store the students and employ records

    }

}