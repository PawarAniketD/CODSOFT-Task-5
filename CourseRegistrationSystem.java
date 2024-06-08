import java.util.ArrayList;
import java.util.List;

class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private String schedule;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getCapacity() {
        return capacity;
    }
}

class Student {
    private int id;
    private String name;
    private List<Course> registeredCourses;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<Course>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }
}

public class CourseRegistrationSystem {
    private List<Course> courses;
    private List<Student> students;

    public CourseRegistrationSystem() {
        this.courses = new ArrayList<Course>();
        this.students = new ArrayList<Student>();
    }

    public void displayAvailableCourses() {
        for (Course course : courses) {
            int availableSeats = course.getCapacity();
            for (Student student : students) {
                boolean isRegistered = false;
                for (Course registeredCourse : student.getRegisteredCourses()) {
                    if (registeredCourse.getCode().equals(course.getCode())) {
                        isRegistered = true;
                        break;
                    }
                }
                if (!isRegistered) {
                    availableSeats--;
                }
            }
            System.out.println(course.getCode() + ": " + course.getTitle() + " - " + availableSeats + " seats available");
        }
    }

    public void registerCourse(int studentId, String courseCode) {
        Student student = findStudentById(studentId);
        Course course = findCourseByCode(courseCode);
        if (student == null || course == null) {
            throw new IllegalArgumentException("Registration failed. Please check course availability or student ID.");
        }
        if (student.getRegisteredCourses().size() >= 3) {
            throw new IllegalArgumentException("Registration failed. Student can only register for a maximum of 3 courses.");
        }
        for (Course registeredCourse : student.getRegisteredCourses()) {
            if (registeredCourse.getCode().equals(courseCode)) {
                throw new IllegalArgumentException("Student is already registered for this course.");
            }
        }
        student.getRegisteredCourses().add(course);
        System.out.println(student.getName() + " registered for " + course.getTitle());
    }

    public void dropCourse(int studentId, String courseCode) {
        Student student = findStudentById(studentId);
        Course course = findCourseByCode(courseCode);
        if (student == null || course == null || !student.getRegisteredCourses().contains(course)) {
            throw new IllegalArgumentException("Course drop failed. Please check student ID or course registration.");
        }
        student.getRegisteredCourses().remove(course);
        System.out.println(student.getName() + " dropped " + course.getTitle());
    }

    private Student findStudentById(int studentId) {
        for (Student student : students) {
            if (student.getId() == studentId) {
                return student;
            }
        }
        return null;
    }

    private Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        CourseRegistrationSystem system = new CourseRegistrationSystem();
        Course course1 = new Course("PROG101", "Programming Fundamentals", "An introductory course to programming.", 30, "Mon/Wed 9:00 AM - 10:30 AM");
        Course course2 = new Course("JAVA201", "Java Programming", "A course on Java programming language.", 25, "Tue/Thu 10:00 AM - 11:30 AM");
        Course course3 = new Course("DATA301", "Data Science", "An introduction to data science concepts.", 20, "Wed/Fri 1:00 PM - 2:30 PM");
        system.courses.add(course1);
        system.courses.add(course2);
        system.courses.add(course3);

        Student student1 = new Student(1, "Kiran");
        Student student2 = new Student(2, "Nitin");
        system.students.add(student1);
        system.students.add(student2);

        System.out.println("Available Courses:");
        system.displayAvailableCourses();
        system.registerCourse(1, "PROG101");

        System.out.println("\nAvailable Courses:");
        system.displayAvailableCourses();

        system.dropCourse(1, "PROG101");

        System.out.println("\nAvailable Courses:");
        system.displayAvailableCourses();
    }
}
