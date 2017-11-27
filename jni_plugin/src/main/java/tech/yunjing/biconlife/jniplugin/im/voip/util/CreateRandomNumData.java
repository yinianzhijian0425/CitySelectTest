package tech.yunjing.biconlife.jniplugin.im.voip.util;

import java.util.Random;

/**
 * Created by  Chen.qi on 2017/8/30 0030.
 * 生成随机数
 */

public class CreateRandomNumData {
    /**
     * 生成6位随机数
     * @return
     */
    public static int getRandomNum6() {
        Random random = new Random();
        int x = random.nextInt(899999);
        return x + 100000;
    }
}
