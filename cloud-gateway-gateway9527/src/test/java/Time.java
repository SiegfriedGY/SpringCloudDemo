import java.time.ZonedDateTime;

public class Time {
    public static void main(String[] args) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(zonedDateTime);
        // 2020-10-28T18:41:30.118+08:00[Asia/Shanghai]
    }
}
