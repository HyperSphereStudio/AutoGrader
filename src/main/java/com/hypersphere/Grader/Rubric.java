package com.hypersphere.Grader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Rubric {

    private int grade;
    private String name;
    private File source;
    private final List<Integer> selectedFeedback = new ArrayList<>();

    public int getGrade() {
        return grade;
    }

    public void setGrade(int current_grade) {
        this.grade = current_grade;
    }

    public List<Integer> getSelectedFeedback() {
        return selectedFeedback;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getSource() {
        return source;
    }

    public void setSource(File source) {
        this.source = source;
    }
}
