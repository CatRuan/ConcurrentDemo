package lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 不使用Reentrantlock,使用
 */
public class ReentrantlockTest2 {

    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Writer writer = new Writer(buffer);
        Reader reader = new Reader(buffer);
        writer.start();
        reader.start();
        try {
            Thread.sleep(500);
            System.out.println("不等了，尝试中断");
            reader.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Buffer {
        ReentrantLock lock;

        public Buffer() {
            lock = new ReentrantLock();
        }

        public String read() throws InterruptedException {
            try {
                lock.lockInterruptibly();
                long startTime = System.currentTimeMillis();
                System.out.println("开始读……");
                while (System.currentTimeMillis() - startTime < 1000) {

                }
            } finally {
                lock.unlock();
            }
            return "";
        }


        public void write() throws InterruptedException {
            try {
                lock.lockInterruptibly();
                long startTime = System.currentTimeMillis();
                System.out.println("开始写……");
                while (System.currentTimeMillis() - startTime < 2000) {
//                    System.out.println("写" + (System.currentTimeMillis() - startTime));
                }
            } finally {
                lock.unlock();
            }
        }
    }

    static class Reader extends Thread {
        private Buffer buffer;

        public Reader(Buffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            try {
                buffer.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("读完了");
        }
    }

    static class Writer extends Thread {
        private Buffer buffer;

        public Writer(Buffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            try {
                buffer.write();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("写完了");
        }
    }
}
