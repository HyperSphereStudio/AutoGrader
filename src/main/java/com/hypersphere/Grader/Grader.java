package com.hypersphere.Grader;

import com.hypersphere.Grader.rubrics.Rubric;
import com.hypersphere.Grader.rubrics.RubricPanel;
import com.hypersphere.Grader.rubrics.RubricRule;

import java.util.List;

public class Grader {

    private final RubricPanel rubricPanel;
    private final List<Rubric> rubrics;
    private final List<RubricRule> base_rules;

    private final double base_grade;

    private int current_rubric_index;
    private Rubric current_rubric;
    private double totalGradeSum;

    public Grader(List<RubricRule> base_rules, List<Rubric> rubrics, double base_grade){
        this.rubrics = rubrics;
        this.base_rules = base_rules;
        this.base_grade = base_grade;
        this.rubricPanel = new RubricPanel(this);
        setRubric(-1);
    }

    public void updateGrade(){
        if(current_rubric != null){
            current_rubric.setGrade(base_grade);
            for(RubricRule rule : current_rubric.getRules())
                current_rubric.setGrade(current_rubric.getGrade() + rule.getPoints());
        }
    }

    public void setRubric(int rubric){
        if(rubric == -1){
            setRubric(null);
        }else{
            current_rubric_index = rubric;
            setRubric(rubrics.get(rubric));
        }
    }

    public void applyRule(int rule, boolean apply){
        applyRule(base_rules.get(rule), apply);
    }

    public void applyRule(RubricRule rule, boolean apply){
        System.out.println("Applying Grading Rule:[" + rule + "," + apply);
        if(apply){
            current_rubric.getRules().add(rule);
        }else{
            current_rubric.getRules().remove(rule);
        }
        updateGrade();
    }

    public double getGrade(){
        return current_rubric.getGrade();
    }

    public void setRubric(Rubric r){
        if(current_rubric != r || r == null){
            this.current_rubric = r;
            if(r != null && !rubrics.contains(r))
                rubrics.add(r);
            updateStats();
        }
    }

    public void save(){
        System.out.println("Saving Rubric...");

    }

    private void reCalcAverage(){
        totalGradeSum = 0;
        for(Rubric r : rubrics)
            totalGradeSum += r.getGrade();
    }

    public double getAverageGrade(){
        if(rubrics.size() == 0)
            return 0;
        return ((double) totalGradeSum / rubrics.size());
    }

    public void updateStats(){
        reCalcAverage();

        StringBuilder sb = new StringBuilder();

        sb.append("Average Grade:").append(getAverageGrade()).append("%");

        if(current_rubric != null){
            sb.append("\nRubric:")
                    .append(current_rubric.getName()).
                    append(" (#").
                    append(current_rubric_index).
                    append(")");
        }else{
            sb.append("\nRubric:")
                    .append("\nGrade:").
                    append(0).
                    append("%");
        }

        rubricPanel.getSelectionPanel().getStatsLabel().setText(sb.toString());
        rubricPanel.repaint();
    }


    public List<Rubric> getRubrics(){
        return rubrics;
    }

    public int getCurrentRubricIndex() {
        return current_rubric_index;
    }

    public RubricPanel getRubricPanel() {
        return rubricPanel;
    }
}
