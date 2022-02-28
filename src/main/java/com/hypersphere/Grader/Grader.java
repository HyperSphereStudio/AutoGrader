package com.hypersphere.Grader;

import javax.swing.*;
import java.util.List;

public class Grader {

    private final RubricPanel rubricPanel;
    private final List<Rubric> rubrics;

    private int current_rubric_index;
    private Rubric current_rubric;

    private int totalGradeSum;

    public Grader(List<Rubric> rubrics){
        this.rubrics = rubrics;
        this.rubricPanel = new RubricPanel(this);
        setRubric(-1);
    }

    public void setRubric(int rubric){
        if(rubric == -1){
            setRubric(null);
        }else{
            current_rubric_index = rubric;
            setRubric(rubrics.get(rubric));
        }
    }

    public void setGrade(int grade){

    }

    public int getGrade(){
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
