package diag.codec.ml1;

import diag.codec.log_packet;
import diag.codec.log_packet_field;

import static diag.codec.log_packet_field.FIELD_TYPE_DOUBLE;
import static diag.codec.log_packet_field.FIELD_TYPE_INT;
import static diag.codec.log_packet_field_dimension.TO_RSRP;
import static diag.codec.log_packet_field_dimension.TO_RSRQ;
import static diag.codec.log_packet_field_dimension.TO_VOID;

/**
 * Created by xxx on 2018/3/20.0xB195
 */

public class log_lte_ml1_connected_neighbor_cell_meas_request_response {
    private static String subpacket_name[] = {
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
            "17",
            "18",
            "19",
            "20",
            "21",
            "22",
            "23",
            "24",
            "25 - LTE_Ml1GenLog_ServingCellMeasurementResult",
            "26 - Idle Mode Neighbor CellMeasurement Request",
            "27",
            "28",
            "29",
            "30 - Connected Neighbor Meas Request",
            "31 - Connected Neighbor Meas Response"
    };

    private static log_packet_field[] field_fmt = {
            new log_packet_field(FIELD_TYPE_INT, "PACKET.VERSION", 0, 1),
            new log_packet_field(FIELD_TYPE_INT, "Number of SubPackets", 1, 1),
            new log_packet_field(FIELD_TYPE_INT, "SubPacket ID", 36, 1),
    };

    static public void decode(log_packet packet) {
        System.out.println("log_lte_ml1_connected_neighbor_cell_meas_request_response");
        packet.log_name = "log_lte_ml1_connected_neighbor_cell_meas_request_response";

        packet.add_fields(field_fmt);

        log_packet_field subPacketId = packet.get_field("SubPacket ID");

        if (subPacketId.value_int != 31) {
            System.out.println("unknown subpacket id.");
            return;
        }

        System.out.println("SubPacket Name: " + subpacket_name[subPacketId.value_int]);

        packet.add_field(new log_packet_field(FIELD_TYPE_INT, "SubPacket E-ARFCN", 40, 2));
        packet.add_field(new log_packet_field(FIELD_TYPE_INT, "SubPacket PCI", 48, 2, 0, 12, TO_VOID));
        packet.add_field(new log_packet_field(FIELD_TYPE_DOUBLE, "Inst RSRP", 52, 2, 0, 12, TO_RSRP));
        packet.add_field(new log_packet_field(FIELD_TYPE_DOUBLE, "Inst RSRQ", 60, 2, 0, 10, TO_RSRQ));

    }
}
