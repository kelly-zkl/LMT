package diag.codec;

import java.util.HashMap;
import java.util.Map;

import static diag.codec.log_packet_decoder.is_debug_on;
import static diag.codec.log_packet_field.FIELD_TYPE_BYTE_STREAM;
import static diag.codec.log_packet_field.FIELD_TYPE_DOUBLE;
import static diag.codec.log_packet_field.FIELD_TYPE_INT;

/**
 * Created by xxx on 2018/2/9.
 */

public class log_packet {

    public int log_code;
    public int log_len;
    public String log_name;
    public String decoded_xml;
    binary_decoder decoder;
    Map<String, log_packet_field> field_map = new HashMap<String, log_packet_field>();

    public log_packet(int log_code, int log_len, binary_decoder decoder) {
        this.log_code = log_code;
        this.log_len = log_len;
        this.decoder = decoder;
    }

    public void add_field(log_packet_field field) {

        if (field.type == FIELD_TYPE_INT) {
            log_packet_field_extractor.extract_int(decoder, field);
        }

        if (field.type == FIELD_TYPE_DOUBLE) {
            log_packet_field_extractor.extract_double(decoder, field);
        }

        if (field.type == FIELD_TYPE_BYTE_STREAM) {
            log_packet_field_extractor.extract_bytestream(decoder, field);
        }

        if (is_debug_on) {
            System.out.println(field.toString());
        }

        field_map.put(field.name, field);
    }

    public void add_fields(log_packet_field[] fields) {

        for (int i = 0; i < fields.length; ++i) {
            add_field(fields[i]);
        }
    }

    public log_packet_field get_field(String name) {
        return field_map.get(name);
    }

    public String toString() {
        String str = new String();

        for (Map.Entry<String, log_packet_field> entry : field_map.entrySet()) {
            str += "Key = " + entry.getKey() + ", Value = " + entry.getValue();
        }

        return str;
    }
}
