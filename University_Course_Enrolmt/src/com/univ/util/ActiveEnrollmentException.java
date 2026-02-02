package com.univ.util;

public class ActiveEnrollmentException extends Exception {
    public String toString() {
        return "Cannot remove course. Active enrollments exist.";
    }
}
