package com.wisec.scanner.utils;

/**
 * 根据频点EARFCN计算频段BAND
 * Created by qwe on 2018/5/11.
 */

public class BandUtils {

    /**
     * 根据频点获取频段
     */
    public static int getBand(int earfcn) {
        //bcc取值范围 GSM：移动900[1-94]1800[512-562],联通900[96-124]1800[686-735]FDD：1[0-599]3[1200-1949]
        //TDD：移动-38 [37750-38249],移动-39 [38250-38649],移动-40 [38650-39649],移动-41 [39650-41589]
        int band = 0;
        if (earfcn >= 0 && earfcn < 600) {
            band = 1;
        } else if (earfcn >= 1200 && earfcn < 1950) {
            band = 3;
        } else if (earfcn >= 37750 && earfcn < 38250) {
            band = 38;
        } else if (earfcn >= 38250 && earfcn < 38650) {
            band = 39;
        } else if (earfcn >= 38650 && earfcn < 39650) {
            band = 40;
        } else if (earfcn >= 39650 && earfcn < 41590) {
            band = 41;
        } else if (earfcn >= 41590 && earfcn < 43590) {
            band = 42;
        } else if (earfcn >= 43590 && earfcn < 45590) {
            band = 43;
        }
        return band;
    }
}
