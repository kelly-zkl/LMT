package diag.codec.nas;

import android.util.Log;

import java.nio.ByteBuffer;

import diag.codec.asn1_decoder;
import diag.codec.log_packet;
import diag.codec.log_packet_field;

import static diag.codec.log_packet_decoder.is_debug_on;
import static diag.codec.log_packet_field.FIELD_TYPE_BYTE_STREAM;
import static diag.codec.log_packet_field.FIELD_TYPE_INT;

/**
 * Created by xxx on 2018/2/7.
 */

public class log_lte_nas_esm_plain_ota_outgoing_msg {

    static log_packet_field[] field_fmt = {
            new log_packet_field(FIELD_TYPE_INT, "LOG_VERSION", 0, 1),
            new log_packet_field(FIELD_TYPE_INT, "STD_VERSION", 1, 1),
            new log_packet_field(FIELD_TYPE_INT, "STD_VERSION_MAJOR",  2, 1),
            new log_packet_field(FIELD_TYPE_INT, "STD_VERSION_MINOR",  3, 1),
    };

    static public void decode(log_packet packet) {
        System.out.println("log_lte_nas_esm_plain_ota_outgoing_msg");
        packet.log_name = "log_lte_nas_esm_plain_ota_outgoing_msg";
        packet.add_fields(field_fmt);
        log_packet_field raw_data = new log_packet_field(FIELD_TYPE_BYTE_STREAM, "Raw Data", 4, packet.log_len - 16);
        packet.add_field(raw_data);

        ByteBuffer buffer = ByteBuffer.allocate(8 + packet.log_len - 16);
        buffer.putInt(250);
        buffer.putInt(packet.log_len - 16);
        buffer.put(raw_data.value_bytestream, 0, packet.log_len - 16);

        packet.decoded_xml = asn1_decoder.decode(buffer.array());

        if (is_debug_on) {
            Log.e("packet.log_len:", "" + packet.log_len);
            Log.e("input:", "" + buffer.array().length);
            System.out.print(packet.decoded_xml);
        }
    }
}
