package test.juc;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: cheng
 * @Date: 2020/8/18 00:45
 * @Description:
 */
public class CountDownLatchTest {
    volatile int i = 0;
    @Test
    public void test() throws Exception{
        CountDownLatch countDownLatch1 = new CountDownLatch(1);
        CountDownLatch countDownLatch2 = new CountDownLatch(1);
        Thread thread = new Thread(()->{
            if(i!=5){
                try {
                    System.out.println("thread 阻塞");
                    countDownLatch1.await();
                    System.out.println("thread end");
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    countDownLatch2.countDown();
                    System.out.println("thread1 start again");
                }
            }
        });
        thread.start();
        Thread.sleep(1000);
        Thread thread1 = new Thread(()->{
            for (int j = 0; j < 10; j++) {
                i++;
                System.out.println(i);
                if(i==5){
                    try {
                        System.out.println("thread1 阻塞");
                        countDownLatch1.countDown();
                        countDownLatch2.await();
                    }catch (Exception e){

                    }
                }
            }
        });
        thread1.start();
    }
}
