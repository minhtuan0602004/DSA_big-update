
public class StudentManager {

    private Student[] students;
    private int count;

    public StudentManager(int capacity) {
        students = new Student[capacity];
        count = 0;
    }

    // Add a student
    public boolean addStudent(Student student) {
        if (count >= students.length) {
            return false;  // Array is full
        }
        students[count++] = student;
        return true;
    }

    // Edit student information
    public boolean editStudent(int id, String name, double marks) {
        for (int i = 0; i < count; i++) {
            if (students[i].getId() == id) {
                if (name != null && !name.isEmpty()) {
                    students[i].setName(name);
                }
                if (marks >= 0) {
                    students[i].setMarks(marks);
                }
                return true;
            }
        }
        return false;
    }

    // Delete a student
    public boolean deleteStudent(int id) {
        for (int i = 0; i < count; i++) {
            if (students[i].getId() == id) {
                students[i] = students[--count];  // Replace with the last student
                students[count] = null;  // Clear the last element
                return true;
            }
        }
        return false;
    }

    // Sort students by marks in descending order using Bubble Sort
    public void sortByMarks() {
        if (students == null || count == 0) {
            return;
        }
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (students[j].getMarks() < students[j + 1].getMarks()) {
                    // Swap students[j] and students[j + 1]
                    Student temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }
    }

    // Sort students by ID in ascending order using Insertion Sort
    public void sortById() {
        for (int i = 1; i < count; i++) {
            Student key = students[i];
            int j = i - 1;
            while (j >= 0 && students[j].getId() > key.getId()) {
                students[j + 1] = students[j];
                j--;
            }
            students[j + 1] = key;
        }
    }

    // Search for a student by ID using linear search
    public Student searchStudentLinear(int id) {
        for (int i = 0; i < count; i++) {
            if (students[i].getId() == id) {
                return students[i];
            }
        }
        return null;
    }

    // Search for a student by ID using binary search
    public Student searchStudentBinary(int id) {
        int left = 0;
        int right = count - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (students[mid].getId() == id) {
                return students[mid];
            }
            if (students[mid].getId() < id) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    // Display all students
    public void displayStudents() {
        for (int i = 0; i < count; i++) {
            System.out.println(students[i]);
        }
    }
}
