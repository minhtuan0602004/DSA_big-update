

// StudentManager.java
import java.util.LinkedList;

public class StudentManager {
    private LinkedList<Student> students;

    public StudentManager() {
        students = new LinkedList<>();
    }

    public boolean addStudent(Student student) {
        return students.add(student);
    }

    public boolean editStudent(int id, String name, double marks) {
        for (Student student : students) {
            if (student.getId() == id) {
                if (name != null && !name.isEmpty()) {
                    student.setName(name);
                }
                if (marks >= 0) {
                    student.setMarks(marks);
                }
                return true;
            }
        }
        return false;
    }

    public boolean deleteStudent(int id) {
        return students.removeIf(student -> student.getId() == id);
    }

    public void sortByMarks() {
        students.sort((s1, s2) -> Double.compare(s2.getMarks(), s1.getMarks()));
    }

    public void sortById() {
        students.sort((s1, s2) -> Integer.compare(s1.getId(), s2.getId()));
    }

    public Student searchStudentLinear(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public Student searchStudentBinary(int id) {
        sortById();
        int left = 0;
        int right = students.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Student midStudent = students.get(mid);

            if (midStudent.getId() == id) {
                return midStudent;
            } else if (midStudent.getId() < id) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    public void displayStudents() {
        for (Student student : students) {
            System.out.println(student);
        }
    }
}