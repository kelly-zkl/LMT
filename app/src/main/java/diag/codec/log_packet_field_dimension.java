package diag.codec;

/**
 * Created by xxx on 2018/2/22.
 */

public class log_packet_field_dimension {

    static public final int TO_VOID = -1;
    static public final int TO_RSRP = 0;
    static public final int TO_RSRQ = 1;
    static public final int TO_RSSI = 2;
    static public final int TO_Q_RXLEVMIN = 3;
    static public final int TO_P_MAX = 4;
    static public final int TO_S_SEARCH = 5;

    static public int convert(int type, int val) {
        switch (type) {
            case TO_RSRP:
                return to_rsrp(val);
            case TO_RSRQ:
                return to_rsrq(val);
            case TO_RSSI:
                return to_rssi(val);
            case TO_Q_RXLEVMIN:
                return to_Q_rxlevmin(val);
            case TO_P_MAX:
                return to_P_max(val);
            case TO_S_SEARCH:
                return to_S_Search(val);
            default:
                return val;
        }
    }

    static public int to_rsrp(int val) {
        return (val - 0x280) / 16 - 140;
    }

    static public int to_rsrq(int val) {
        return (val) / 16 - 30;
    }

    static public int to_rssi(int val) {
        return (val) / 16 - 140;
    }

    static public int to_Q_rxlevmin(int val) {
        return (val) * 2 - 140;
    }

    static public int to_P_max(int val) {
        return (val) - 30;
    }

    static public int to_S_Search(int val) {
        return (val) * 2;
    }
}
