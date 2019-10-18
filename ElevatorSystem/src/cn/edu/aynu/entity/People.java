package cn.edu.aynu.entity;

public class People {

    private Double weight;

    public People() {
    }

    public People(Double weight) {
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "People{" +
                "weight=" + weight +
                '}';
    }
}
