package com.hypersphere.Grader.rubrics;

public class RubricRule implements Cloneable{

    private String name, desc;
    private int points;

    public RubricRule(String name, String desc, int points){
        this.name = name;
        this.desc = desc;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String toString(){
        return "Rule[" + name + "," + points + ", " + desc + "]";
    }

    public RubricRule clone(){
        try{
            return (RubricRule) super.clone();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
