package thread.sync;

import thread.sync.reentrantlock.BankAccountV4;
import thread.sync.reentrantlock.BankAccountV5;
import thread.sync.reentrantlock.BankAccountV6;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BankMain {
    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccountV6(1000);

        Thread thread1 = new Thread(new WithdrawTask(account, 800), "t1");
        Thread thread2 = new Thread(new WithdrawTask(account, 800), "t2");
        thread1.start();
        thread2.start();

        sleep(500); // 검증 완료까지 대기
        log("t1 state: " + thread1.getState());
        log("t2 state: " + thread2.getState());

        thread1.join();
        thread2.join();

        log("최종 잔액: " + account.getBalance());
    }
}
