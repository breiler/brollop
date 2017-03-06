package brollop;

import java.util.Random;

/**
 * Created by joacim on 2017-03-04.
 */
public class StaticUtils {
    public static String getRandomHexString(int length) {
        Random r = new Random(System.currentTimeMillis());
        StringBuffer sb = new StringBuffer();
        while(sb.length() < length){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, length);
    }
}
