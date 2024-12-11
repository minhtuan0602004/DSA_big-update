

// Main.java
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManager studentManager = new StudentManager();

        while (true) {
            System.out.print("-Student management-");
            System.out.println("\n1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Sort Students by Marks");
            System.out.println("5. Sort Students by ID");
            System.out.println("6. Search Student");
            System.out.println("7. Display All Students");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
                continue;
            }

            scanner.nextLine();

            switch (choice) {
                case 1:
                    addStudent(scanner, studentManager);
                    break;
                case 2:
                    editStudent(scanner, studentManager);
                    break;
                case 3:
                    deleteStudent(scanner, studentManager);
                    break;
                case 4:
                    studentManager.sortByMarks();
                    System.out.println("Students sorted by marks.");
                    break;
                case 5:
                    studentManager.sortById();
                    System.out.println("Students sorted by ID.");
                    break;
                case 6:
                    searchStudent(scanner, studentManager);
                    break;
                case 7:
                    studentManager.displayStudents();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addStudent(Scanner scanner, StudentManager studentManager) {
        int id = -1;
        String name = "";
        double marks = -1;

        while (true) {
            try {
                System.out.print("Enter ID: ");
                id = scanner.nextInt();
                validateId(id);
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number for ID.");
                scanner.nextLine();
            } catch (InvalidIdException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Enter Name: ");
                name = scanner.nextLine();
                validateName(name);
                break;
            } catch (InvalidNameException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("Enter Marks: ");
                marks = scanner.nextDouble();
                validateMarks(marks);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number for Marks.");
                scanner.nextLine();
            } catch (InvalidMarksException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        studentManager.addStudent(new Student(id, name, marks));
        System.out.println("Student added successfully.");
    }

    private static void editStudent(Scanner scanner, StudentManager studentManager) {
        int id = -1;
        String name = "";
        double marks = -1;

        while (true) {
            try {
                System.out.print("Enter ID of student to edit: ");
                id = scanner.nextInt();
                validateId(id);
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number for ID.");
                scanner.nextLine();
            } catch (InvalidIdException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        System.out.print("Enter new Name (or press Enter to skip): ");
        name = scanner.nextLine();
        if (!name.isEmpty()) {
            try {
                validateName(name);
            } catch (InvalidNameException e) {
                System.out.println("ERROR: " + e.getMessage());
                return;
            }
        }

        System.out.print("Enter new Marks (or -1 to skip): ");
        try {
            marks = scanner.nextDouble();
            if (marks != -1) {
                validateMarks(marks);
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid number for Marks.");
            scanner.nextLine();
            return;
        } catch (InvalidMarksException e) {
            System.out.println("ERROR: " + e.getMessage());
            return;
        }

        if (studentManager.editStudent(id, name.isEmpty() ? null : name, marks)) {
            System.out.println("Student edited successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void deleteStudent(Scanner scanner, StudentManager studentManager) {
        int id = -1;

        while (true) {
            try {
                System.out.print("Enter ID of student to delete: ");
                id = scanner.nextInt();
                validateId(id);
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number for ID.");
                scanner.nextLine();
            } catch (InvalidIdException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        if (studentManager.deleteStudent(id)) {
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void searchStudent(Scanner scanner, StudentManager studentManager) {
        int id = -1;

        System.out.println("\nChoose search method:");
        System.out.println("1. Linear Search");
        System.out.println("2. Binary Search");
        System.out.print("Choose an option: ");
        int searchMethod = scanner.nextInt();
        scanner.nextLine();

        while (true) {
            try {
                System.out.print("Enter ID of student to search: ");
                id = scanner.nextInt();
                validateId(id);
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number for ID.");
                scanner.nextLine();
            } catch (InvalidIdException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        Student student = null;
        switch (searchMethod) {
            case 1:
                student = studentManager.searchStudentLinear(id);
                break;
            case 2:
                student = studentManager.searchStudentBinary(id);
                break;
            default:
                System.out.println("Invalid search method.");
                return;
        }

        if (student != null) {
            System.out.println("Student found: " + student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void validateId(int id) throws InvalidIdException {
        if (id <= 0) {
            throw new InvalidIdException("Invalid ID. ID must be a positive number.");
        }
    }

    private static void validateName(String name) throws InvalidNameException {
        if (!name.matches("[a-zA-Z\\s]+")) {
            throw new InvalidNameException("Invalid name. The name cannot contain numbers or special characters.");
        }
    }

    private static void validateMarks(double marks) throws InvalidMarksException {
        if (marks < 0 || marks > 10) {
            throw new InvalidMarksException("Invalid score. Scores must be between 0 and 10.");
        }
    }
}

class InvalidIdException extends Exception {
    public InvalidIdException(String message) {
        super(message);
    }
}

class InvalidNameException extends Exception {
    public InvalidNameException(String message) {
        super(message);
    }
}

class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}
