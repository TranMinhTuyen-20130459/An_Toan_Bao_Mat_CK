package utils;

public class TimerUtil {

    /**
     * Thực hiện hàm và đo thời gian thực thi, sau đó in ra kết quả.
     *
     * @param function Hàm cần thực hiện và đo thời gian.
     * @param iterations Số lần lặp (ví dụ: 8000 trong trường hợp của bạn).
     */
    public static void timeExecution(Runnable function, int iterations) {
        long startTime = System.nanoTime();

        // Thực hiện hàm theo số lần lặp
        for (int i = 0; i < iterations; i++) {
            function.run();
        }

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        double elapsedTimeInSeconds = (double) elapsedTime / 1_000_000_000.0;

        System.out.println("Execution time: " + elapsedTimeInSeconds + " seconds");
    }

}
