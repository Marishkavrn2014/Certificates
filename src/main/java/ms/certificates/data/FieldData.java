package main.java.ms.certificates.data;

public class FieldData {
    private String firstName, lastName, level, hours, from, to, courseName;

    public String getCourseName() {
        return courseName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFrom() {
        return from;
    }

    public String getHours() {
        return hours;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLevel() {
        return level;
    }

    public String getTo() {
        return to;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
