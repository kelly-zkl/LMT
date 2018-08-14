package diag.codec.rrc;

/**
 * Created by xxx on 2018/2/7.
 */

import android.util.Log;

import java.nio.ByteBuffer;

import diag.codec.asn1_decoder;
import diag.codec.log_packet;
import diag.codec.log_packet_field;

import static diag.codec.log_packet_decoder.is_debug_on;
import static diag.codec.log_packet_field.FIELD_TYPE_BYTE_STREAM;
import static diag.codec.log_packet_field.FIELD_TYPE_INT;
import static diag.codec.log_packet_field.MAX_BYTESTREAM_LEN;

public class log_lte_rrc_ota {

    private static String PDU_NUM[] = {
            "0 - Unknown",
            "1 - Unknown",
            "2 - BCCH_DL_SCH Message",
            "3 - MCCH Message",
            "4 - PCCH Message",
            "5 - DL_CCCH Message",
            "6 - DL_DCCH Message",
            "7 - UL_CCCH Message",
            "8 - UL_DCCH Message",
            "9 - SystemInformationBlockType1",
            "10 - SystemInformationBlockType1_v8h0_IEs",
            "11 - SystemInformationBlockType2_v8h0_IEs",
            "12 - SystemInformationBlockType5_v8h0_IEs",
            "13 - SystemInformationBlockType6_v8h0_IEs",
            "14 - UE EUTRA Capability Message",
            "15 - UE EUTRA Capability v9a0_IEs",
            "16 - VarShortMAC_Input",
    };

    private static int pdu_signal_type[] = {
            -1,
            -1,
            203, // 2 BCCH_DL_SCH;
            -1,
            200, // 4 PCCH
            204, // 5 DL_CCCH
            201, // 6 DL_DCCH
            205, // 7 UL_CCCH
            202, // 8 UL_DCCH
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
            -1,
    };

    private static log_packet_field[] field_fmt = {
            new log_packet_field(FIELD_TYPE_INT, "VERSION", 0, 1),
            new log_packet_field(FIELD_TYPE_INT, "PCI", 4, 2),
            new log_packet_field(FIELD_TYPE_INT, "EARFCN", 6, 4),
            new log_packet_field(FIELD_TYPE_INT, "SFN", 10, 2),
            new log_packet_field(FIELD_TYPE_INT, "PDU_NUM", 12, 1),
            new log_packet_field(FIELD_TYPE_INT, "SIB_MASK_IN_SI", 13, 4),
            new log_packet_field(FIELD_TYPE_INT, "ENCODED_MSG_LEN", 17, 2),
    };

    static public void decode(log_packet packet) {
        System.out.println("log_lte_rrc_ota");
        packet.log_name = "log_lte_rrc_ota";

        packet.add_fields(field_fmt);

        log_packet_field encoded_msg_len = packet.get_field("ENCODED_MSG_LEN");

        if (encoded_msg_len.value_int >= MAX_BYTESTREAM_LEN) {
            System.out.print("err. " + packet);
            return;
        }

        packet.add_field(new log_packet_field(FIELD_TYPE_BYTE_STREAM, "ENCODED_MSG", 19, encoded_msg_len.value_int));

        decode_rrc_ota(packet);
    }

    static public void decode_rrc_ota(log_packet packet) {

        log_packet_field pdu_num = packet.get_field("PDU_NUM");
        log_packet_field encoded_msg = packet.get_field("ENCODED_MSG");
        log_packet_field SIB_MASK_IN_SI = packet.get_field("SIB_MASK_IN_SI");

        System.out.println("decode_rrc_ota. SIB_MASK_IN_SI: " + SIB_MASK_IN_SI.value_int);
        System.out.println("decode_rrc_ota. length: " + pdu_signal_type.length + ", pdu_num.value_int: " + pdu_num.value_int + ", type: " + pdu_signal_type[pdu_num.value_int]);

        if (pdu_num.value_int >= pdu_signal_type.length) {
            System.out.println("err. buffer too small.");
            return;
        }

        if (pdu_signal_type[pdu_num.value_int] != -1) {
            log_packet_field encoded_msg_len = packet.get_field("ENCODED_MSG_LEN");
            ByteBuffer buffer = ByteBuffer.allocate(8 + encoded_msg_len.value_int);
            buffer.putInt(pdu_signal_type[pdu_num.value_int]);
            buffer.putInt(encoded_msg_len.value_int);
            buffer.put(encoded_msg.value_bytestream, 0, encoded_msg_len.value_int);

            packet.decoded_xml = asn1_decoder.decode(buffer.array());

            if (is_debug_on) {
                Log.e("value_int:", "" + encoded_msg_len.value_int);
                Log.e("input:", "" + buffer.array().length);
                System.out.print(packet.decoded_xml);
            }
        }
    }
}