package thread.sync;

public interface BankAccount {

    boolean withdraw(int amount) throws InterruptedException;
    int getBalance();
}
