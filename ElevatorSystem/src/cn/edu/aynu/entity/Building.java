package cn.edu.aynu.entity;

public class Building {

    //楼房最低层
    public static final Integer BOTTOM_FLOOR = 1;

    //楼房最高层
    public static final Integer TOP_FLOOR = 12;

    //楼房中的电梯
    private Elevator elevator;

    public Elevator getElevator() {
        return elevator;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }
}
