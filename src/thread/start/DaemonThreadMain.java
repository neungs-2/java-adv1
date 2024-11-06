package thread.start;

public class DaemonThreadMain {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + ": main() start");

        DaemonThread daemonThread = new DaemonThread();
        daemonThread.setDaemon(true); // Daemon true로 실행시 main 스레드 종료 시 그냥 shutdown.
        // daemonThread.setDaemon(true); // Daemon false로 실행시 main 스레드 종료되어도 모든 스레드 종료 후 프로세스 종료.
        daemonThread.start();
        System.out.println(Thread.currentThread().getName() + ": main() end");
    }

    static class DaemonThread extends Thread {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": run() start");

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ": run() end");
        }
    }
}
