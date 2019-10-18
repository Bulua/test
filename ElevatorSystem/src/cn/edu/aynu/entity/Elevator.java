package cn.edu.aynu.entity;

import java.util.List;

public class Elevator {

    //当前电梯所在楼层
    public static int floor = 1;

    //电梯最大承受重量
    public static final Double CAPACITY = 1000d;

    //当前电梯承受重量=人的重量+...+人的重量
    public static Double allWeight = 0d;

    //电梯当前运行状态:
    //  0:向上    1:静止    2:向下
    private Integer status;

    //当前电梯里面的人
    private List<People> peoples;

    public Elevator(){
        this.status = 1;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<People> getPeoples() {
        return peoples;
    }

    public void setPeoples(List<People> peoples) {
        this.peoples = peoples;
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "floor=" + floor +
                ", allWeight=" + allWeight +
                ", status=" + status +
                ", peoples=" + peoples +
                '}';
    }
}
