package util;

import java.util.Random;

public class OTPUtil {

    public static String generateOTP() {
        Random rand = new Random();
        int otp = 1000 + rand.nextInt(9000); // 4-digit OTP
        return String.valueOf(otp);
    }
}