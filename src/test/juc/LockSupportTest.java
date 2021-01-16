package test.juc;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * @Auther: cheng
 * @Date: 2020/8/17 23:57
 * @Description:
 */
public class LockSupportTest {
    volatile int i = 0;
    static Thread thread2 = null;
    static Thread thread3 = null;

    @Test
    public void test() throws Exception {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                if (i == 2) {
                    LockSupport.park();
                    System.out.println("-----");
                }
                System.out.println(i);
            }
        });
        thread.start();
        Thread.sleep(8000);
        LockSupport.unpark(thread);
    }

    @Test
    public void test2() throws Exception {
        Thread thread = new Thread(() -> {
            if (i != 5) {
                System.out.println("thread block");
                LockSupport.park();
                System.out.println("thread start again");
            }
        });
        thread.start();
        Thread.sleep(2000);
        Thread thread1 = new Thread(() -> {
            for (int j = 0; j < 10; j++) {
                i++;
                if (i == 5) {
                    System.out.println("唤醒thread");
                    LockSupport.unpark(thread);
                }
            }
        });
        thread1.start();
    }

    @Test
    public void test3() {
        thread2 = new Thread(() -> {
            LockSupport.park();
            System.out.println("thread2 start again");
            LockSupport.unpark(thread3);
        });
        thread3 = new Thread(() -> {
            for (int j = 0; j < 10; j++) {
                i++;
                System.out.println(i);
                if (i == 5) {
                    LockSupport.unpark(thread2);
                    LockSupport.park();
                    System.out.println("thread3 start again");
                }
            }
        });
        thread2.start();
        thread3.start();
    }

}
