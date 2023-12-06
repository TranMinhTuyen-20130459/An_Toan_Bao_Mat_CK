package model;
import lombok.Builder;
import lombok.Data;
import java.sql.Timestamp;
@Data
@Builder
public class PublicKey {
    private int id_user;
    private String public_key;
    private Timestamp start_time;
    private Timestamp expired_time;
    private byte is_valid;
}
