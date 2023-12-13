package utils;

import java.sql.Timestamp;
import java.util.Arrays;

public class TimerUtil {

    /**
     * Thực hiện hàm và đo thời gian thực thi, sau đó in ra kết quả.
     *
     * @param function   Hàm cần thực hiện và đo thời gian.
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

    /**
     * Tạo ra hai giá trị Timestamp đại diện cho một khoảng thời gian.
     *
     * @return Một mảng chứa hai giá trị Timestamp [start_time, expired_time].
     */
    public static Timestamp[] generateStartAndExpiredTimes() {
        // Lấy timestamp hiện tại làm thời điểm bắt đầu
        Timestamp startTime = new Timestamp(System.currentTimeMillis());

        // Tính thời điểm hết hạn bằng cách cộng thêm một khoảng thời gian cố định (ví dụ: 0.75 giây)
        long fixedDuration = 1000; // 1000 mili giây = 1 giây
        Timestamp expiredTime = new Timestamp(startTime.getTime() + fixedDuration);

        // Trả về kết quả dưới dạng mảng
        return new Timestamp[]{startTime, expiredTime};
    }

    public static void main(String[] args) {

        Arrays.stream(generateStartAndExpiredTimes()).forEach(item -> {
            System.out.println(item.toString());
        });
    }

}
