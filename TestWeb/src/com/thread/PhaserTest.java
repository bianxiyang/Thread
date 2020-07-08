package com.thread;


import java.util.concurrent.Phaser;
/**
 * Phaser，是一个阶段，让线程按照阶段，来进行执行的
 * @author bian_xy
 *
 */
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class PhaserTest {
	static Random r = new Random();
    static MarriagePhaser phaser = new MarriagePhaser();
 
    static class MarriagePhaser extends Phaser {
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            // 定义4个阶段，一个阶段结束时该方法被自动调用
            switch (phase) {
                case 0:
                    System.out.println("所有人到齐！" + registeredParties + "\n");
                    return false; // 流程未结束，继续
                case 1:
                    System.out.println("所有人都吃完饭了！" + registeredParties + "\n");
                    return false; // 流程未结束，继续
                case 2:
                    System.out.println("客人都离开了！" + registeredParties + "\n");
                    return false; // 流程未结束，继续
                case 3:
                    System.out.println("婚礼结束!" + registeredParties + "\n");
                    return true; // 流程结束
                default:
                    return true;
 
            }
        }
    }
    // 定义Persion类，实现Runnable ，重写run方法参加婚礼
    static class Persion implements Runnable {
 
        String name;
 
        public Persion(String name){
            this.name = name;
        }
 
        // 提取sleep方法
        public void secondsSleep(int seconds) {
            try {
                TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
 
        // 阶段 1 行为
        public void arrive() {
            secondsSleep(2);
            System.out.println(name + "到达现场！");
            phaser.arriveAndAwaitAdvance(); // 阶段结束放行
        }
 
        // 阶段 2 行为
        public void eat() {
            secondsSleep(2);
            System.out.println(name + "吃完饭！");
            phaser.arriveAndAwaitAdvance(); // 阶段结束放行
        }
 
        // 阶段 3 行为
        public void leave() {
            if ("新郎".equals(name) || "新娘".equals(name)) {
                secondsSleep(2);
                System.out.println(name + "留下！");
                phaser.arriveAndAwaitAdvance(); // 阶段结束放行
            } else {
                System.out.println(name + "离开！");
                /*
                 * 客人离开后从phaser中注销，不再受阶段控制
                 *
                 * 注意：这时客人的线程并没有结束，只是不再受phaser影响可自由往下执行
                 *
                 * 注销后可减少下一阶段中需要提前加入的线程，提高效率
                 */
                phaser.arriveAndDeregister(); // 从phaser中注销
            }
 
        }
 
        // 阶段 4 行为 
        public void hug() {
            if ("新郎".equals(name) || "新娘".equals(name)) {
                secondsSleep(2);
                System.out.println(name + "拥抱！");
                phaser.arriveAndAwaitAdvance();
            }
        }
 
        @Override
        public void run() {
            arrive();
            eat();
            leave();
            hug();
        }
    }
 
    public static void main(String[] args) {
        // 注册7个线程
        phaser.bulkRegister(7);
 
        for (int i = 0; i < 5; i++) {
            new Thread(new Persion("客人" + i)).start();
        }
        new Thread(new Persion("新郎")).start();
        new Thread(new Persion("新娘")).start();
    }

	
	
}
