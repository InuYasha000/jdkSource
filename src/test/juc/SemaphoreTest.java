package test.juc;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @Auther: cheng
 * @Date: 2020/8/17 22:05
 * @Description:
 */
public class SemaphoreTest {
    @Test
    public void test1() throws Exception{
        //信号量，只允许 3个线程同时访问
        Semaphore semaphore = new Semaphore(3);
        Thread[] threads = new Thread[5];
        for(int i=0;i<5;i++){
            threads[i] = new Thread(()->{
                try{
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"拿到了许可，正在执行");
                    Thread.sleep(new Random().nextInt(1000));//模拟随机执行时间
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    System.out.println(Thread.currentThread().getName()+"释放许可");
                    semaphore.release();
                }
            });
        }
        for (Thread thread:threads){
            thread.start();
        }
    }

    @Test
    public void testfair(){
        //信号量，只允许 3个线程同时访问
        Semaphore semaphore = new Semaphore(2,true);
        Thread[] threads = new Thread[5];
        Runnable run = ()->{
            for (int i = 0; i < 10; i++) {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"获得了许可");
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            }
        };
        Thread thread1 = new Thread(run);
        Thread thread2 = new Thread(run);

        thread1.start();
        thread2.start();
    }
}
