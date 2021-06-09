package maksym.kruhovykh.app.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {
    private Utils() {
        throw new RuntimeException("Not today man");
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            log.error(message);
            throw new RuntimeException(message);
        }
    }
}
