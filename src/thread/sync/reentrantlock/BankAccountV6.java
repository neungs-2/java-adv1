package thread.sync.reentrantlock;

import thread.sync.BankAccount;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * Reentrant Lock - tryLock(시간) 사용 예시
 */
public class BankAccountV6 implements BankAccount {
    private int balance;
    private final Lock lock = new ReentrantLock();

    public BankAccountV6(int initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public boolean withdraw(int amount) throws InterruptedException {
        log("거래 시작: " + getClass().getSimpleName());

        try {
            if (!lock.tryLock(1500, TimeUnit.MILLISECONDS)) {
                log("[진입 실패] 이미 처리 중인 작업이 존재합니다.");
                return false;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            // 검증 단계: 출금액과 잔액 비교
            log("[검증 시작] 출금액: " + amount + ", 잔액: " + balance);
            if (balance < amount) {
                log("[검증 실패] 출금액: " + amount + ", 잔액: " + balance);
                return false;
            }

            // 출금 단계: 잔액에서 출금액 차감
            log("[검증 완료] 출금액: " + amount + ", 잔액: " + balance);
            sleep(1000); // 출금 시간
            balance -= amount;
            log("[출금 완료] 출금액: " + amount + ", 잔액: " + balance);
        } finally {
            lock.unlock();
        }

        log("거래 종료");
        return true;
    }

    @Override
    public int getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}
