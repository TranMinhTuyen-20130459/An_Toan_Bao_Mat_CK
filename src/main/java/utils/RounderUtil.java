package utils;

import java.sql.Timestamp;

public class RounderUtil {

    public static Timestamp roundTimestampToSeconds(Timestamp timestamp) {
        long roundedTimeMillis = (timestamp.getTime() / 1000) * 1000; // Làm tròn xuống đến giây
        return new Timestamp(roundedTimeMillis);
    }

    public static void main(String[] args) {
        // Ví dụ sử dụng hàm làm tròn
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Timestamp roundedTimestamp = roundTimestampToSeconds(currentTimestamp);
        System.out.println("Original Timestamp: " + currentTimestamp);
        System.out.println("Rounded Timestamp: " + roundedTimestamp);
    }

}
