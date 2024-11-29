package thread.control.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import static util.MyLogger.log;

/**
 * 인터럽트 상태를 직접 확인하여 work 변수 제거하도록 개선
 */
public class MyPrinter3 {
    public static void main(String[] args) throws InterruptedException{
        Printer printer = new Printer();
        Thread printerThread = new Thread(printer, "printer");
        printerThread.start();

        Scanner userInput = new Scanner(System.in);
        while (true) {
            System.out.println("프린터할 문서를 입력하세요. 종료 (q): ");
            String input = userInput.nextLine();

            if (input.equals("q")) {
//                printer.work = false;
                printerThread.interrupt(); // interrupt 호출 코드 추가
                break;
            }

            printer.addJob(input);
        }
    }

    static class Printer implements Runnable {
//        volatile boolean work = true;
        Queue<String> jobQueue = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            while(!Thread.interrupted()) { // 직접 확인, 상태 변경하도록 수정
                if (jobQueue.isEmpty()) {
                    continue;
                }

                try {
                    String job = jobQueue.poll();
                    log("출력 시작: " +  job + ", 대기문서: " + jobQueue);
                    Thread.sleep(3000);
                    log("출력 완료: " + job);
                } catch (InterruptedException e) { // 인터럽트 처리 구문 추가
                    log("인터럽트 !");
                    break;
                }
            }
            log("프린터 종료");
        }

        public void addJob(String input) {
            jobQueue.offer(input);
        }
    }
}
