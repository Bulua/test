package cn.edu.aynu.main;

import cn.edu.aynu.entity.Building;
import cn.edu.aynu.entity.Elevator;
import cn.edu.aynu.entity.People;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.Period;
import java.util.*;

import javax.swing.JFrame;

public class Runner {

    public static void main(String[] args) throws InterruptedException {
        Building building = new Building();
        Elevator elevator = new Elevator();
        building.setElevator(elevator);
        //运行
        run(building);
    }

    private static void run(Building building) throws InterruptedException {
        //获取楼层电梯对象
        Elevator elevator = building.getElevator();
        List<People> peoples = null;
        Set<Integer> floors;
        System.out.println("该楼最高"+Building.TOP_FLOOR+"层，最低为"+Building.BOTTOM_FLOOR+"层");
        while (true) {
            System.out.println("当前是" + Elevator.floor + "楼，电梯状态：" + elevator.getStatus());
            //获取要去的楼层集合
            floors = getFloors();
            peoples = enterElevator();
            elevator.setPeoples(peoples);
            //电梯开始运行
            elevatorRun(elevator, floors);
//            System.out.println("电梯上的人：" + elevator.getPeoples());
            System.out.println("电梯总重量：" + Elevator.allWeight);
            System.out.println("输入的数：" + floors);
        }
    }

    /**
     * 电梯运行
     */
    private static void elevatorRun(Elevator elevator, Set<Integer> floors) throws InterruptedException {
        List<People> peoples = elevator.getPeoples();
        //判断电梯当前状态  0:向上    1:静止    2:向下
        switch (elevator.getStatus()){
            case 0:
                up(elevator, floors); break;
            case 1:
                //静止判断输入的第一个楼层
                if (floors.iterator().next() > Elevator.floor){
                    up(elevator, floors);
                }else if (floors.iterator().next() < Elevator.floor){
                    down(elevator, floors);
                }
                break;
            case 2:
                down(elevator, floors); break;
            default:
                System.out.println("发生了一个未知的错误"); break;
        }

    }

    /**
     * 电梯当前状态为上楼
     * @param elevator
     * @param floors
     */
    private static void up(Elevator elevator,Set<Integer> floors) throws InterruptedException {
        while (Elevator.floor <= Building.TOP_FLOOR){
            arrived(elevator,floors);
            Elevator.floor++;
            //任务已完成 || 到达顶层
            if (floors.size() <= 0 || Elevator.floor > Building.TOP_FLOOR){
                Elevator.floor--;
                break;
            }
        }

    }

    private static void arrived(Elevator elevator,Set<Integer> floors) throws InterruptedException {
        System.out.println("--"+ Elevator.floor +"--");
        System.out.println("---------");
        Thread.sleep(1000);
        if (floors.contains(Elevator.floor)){
            System.err.println("到达" + Elevator.floor + "楼，开门");
            elevator.setPeoples(enterElevator());
            floors.remove(Elevator.floor);
        }

    }

    /**
     * 电梯当前状态为下楼
     * @param elevator
     * @param floors
     */
    private static void down(Elevator elevator,Set<Integer> floors) throws InterruptedException {
        while (Elevator.floor >= Building.BOTTOM_FLOOR){
            arrived(elevator,floors);
            Elevator.floor--;
            //任务已完成 || 到达底层
            if (floors.size() <= 0 || Elevator.floor < Building.BOTTOM_FLOOR){
                Elevator.floor++;
                break;
            }
        }
    }

    private static Set<Integer> getFloors(){
        //这里选set是因为set是有序并且不可重复的存储集合
        Set<Integer> floors = new HashSet<>();
        Scanner scan = new Scanner(System.in);
        System.out.println("请输入您要去的楼层：");
        while (scan.hasNext()){
            int floor = scan.nextInt();
            if (floor <= 0) { break; }
            floors.add(floor);
        }
        if (floors.size() <= 0){ floors.add(Elevator.floor); }
        if (floors.contains(Elevator.floor)) { floors.remove(Elevator.floor); }
        return floors;
    }
    
    private static Set<Integer> getFloors2(){
    	Set<Integer> floors = new HashSet<>();
    	JFrame jFrame = new JFrame();
    	jFrame.setSize(800, 800);
    	jFrame.setLayout(new FlowLayout());
    	for(int i=Building.BOTTOM_FLOOR; i <= Building.TOP_FLOOR; i++){
    		jFrame.add(new Button(i+"楼"));
    	}
    	Button run = new Button("RUN"); 
    	jFrame.add(run);
    	jFrame.setVisible(true);
    	run.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				floors.add(1);
			}
		});
    	return floors;
    	
    }

    /**
     * 随机获取人和其重量，判断是否超重，超重下去一个人
     * @return  获取进入电梯的人们
     */
    private static List<People> enterElevator(){
        List<People> peoples = new LinkedList<>();
        Random random = new Random();
        //随机上电梯人数
        for (int i = 0; i < random.nextInt(12); i++){
            //随机产生一个people对象添加到集合中，体重在30~150之间
            double weight = random.nextInt(120) + 30d;
            peoples.add(new People(weight));
            Elevator.allWeight += weight;
        }
        if (Elevator.CAPACITY < Elevator.allWeight){
            System.out.println("当前乘客总重量为："+ Elevator.allWeight);
            System.out.println("超重了，乘客正在下。。。");
            //判断条件：当前电梯总重量不能大于电梯荷载重量
            while (Elevator.allWeight > Elevator.CAPACITY){
                People lastPeople = ((LinkedList<People>) peoples).getLast();
                if (lastPeople == null && peoples.size() <= 0){
                    break;
                }else {
                    Elevator.allWeight -= lastPeople.getWeight();
                    ((LinkedList<People>) peoples).removeLast();
                    System.out.println("下了一位乘客：" + lastPeople);
                }
            }
        }
        System.out.println("当前乘客总重量为："+ Elevator.allWeight + "\t电梯可以运行");
        return peoples;
    }

}
