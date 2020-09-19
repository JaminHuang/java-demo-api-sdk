package com.demo.api.sdk.util;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @author jamin
 * @date 2020/9/19
 */
public class UUIDUtils {
    private static int length = 5;
    private static char[] charDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static String getID() {
        UUID uuid = UUID.randomUUID();
        return convertToHashStr(uuid.getMostSignificantBits(), length) + convertToHashStr(uuid.getLeastSignificantBits(), length);
    }

    public static String convertID(String uuidStr) {
        UUID uuid = UUID.fromString(uuidStr);
        return convertToHashStr(uuid.getMostSignificantBits(), length) + convertToHashStr(uuid.getLeastSignificantBits(), length);
    }

    private static String convertToHashStr(long hid, int len) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < len; ++i) {
            char c = charDigits[(int)((hid & 255L) % (long)charDigits.length)];
            sb.append(c);
            hid >>= 6;
        }
        return sb.toString();
    }
}
