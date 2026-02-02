package com.univ.util;

public class CourseFullException extends Exception {
    public String toString() {
        return "Cannot remove course. Active enrollments exist.";
    }
}
