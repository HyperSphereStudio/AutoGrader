package com.hypersphere.Grader.rubrics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Rubric implements Cloneable{

    private double grade;
    private String name;
    private File source;
    private final List<RubricRule> rules = new ArrayList<>();

    public double getGrade() {
        return grade;
    }

    public void setGrade(double current_grade) {
        this.grade = current_grade;
    }



    public List<RubricRule> getRules(){
        return rules;
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
