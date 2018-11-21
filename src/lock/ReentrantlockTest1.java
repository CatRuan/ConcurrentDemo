package lock;

import java.util.BitSet;

/**
 * 不使用Reentrantlock,使用
 */
public class ReentrantlockTest1 {

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

        public String read() {
            synchronized (this) {
                long startTime = System.currentTimeMillis();
                System.out.println("开始读……");
                while (System.currentTimeMillis() - startTime < 1000) {

                }
                return "";
            }
        }

        public void write() {
            synchronized (this) {
                long startTime = System.currentTimeMillis();
                System.out.println("开始写……");
                while (System.currentTimeMillis() - startTime < 2000) {
//                    System.out.println("写" + (System.currentTimeMillis() - startTime));
                }
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
            buffer.read();
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
            buffer.write();
            System.out.println("写完了");
        }
    }
}
