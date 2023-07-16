package utils;

import java.util.Random;

public class DataUtils {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String generateRandomString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // max length is 15, just to keep the random string short
        for (int i = 0; i <= 15; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
