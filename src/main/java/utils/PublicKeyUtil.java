package utils;

import model.PublicKey;

import java.sql.Timestamp;
import java.util.Random;

public class PublicKeyUtil {

    public static PublicKey generateObjectPublicKey(int id_user, String public_key, Timestamp start_time) {

        var random = new Random();
        var arr_timestamp = TimerUtil.generateStartAndExpiredTimes();

        return PublicKey.builder()
                .id_user(id_user)
                .public_key(public_key)
                .start_time(start_time)
                .expired_time(arr_timestamp[1])
                .is_valid((byte) (random.nextInt(2)))
                .build();
    }
}
