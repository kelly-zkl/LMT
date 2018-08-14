package diag.codec.rrc;

import diag.codec.log_packet;
import diag.codec.log_packet_field;

import static diag.codec.log_packet_field.FIELD_TYPE_INT;

/**
 * Created by xxx on 2018/2/7.
 */

public class log_lte_rrc_mib {

    static log_packet_field[] field_fmt = {
            new log_packet_field(FIELD_TYPE_INT, "VERSION", 0, 1),
            new log_packet_field(FIELD_TYPE_INT, "PCI", 1, 2),
            new log_packet_field(FIELD_TYPE_INT, "EARFCN", 3, 4),
            new log_packet_field(FIELD_TYPE_INT, "SFN", 7, 2),
            new log_packet_field(FIELD_TYPE_INT, "Number of TX Antennas", 9, 1),
            new log_packet_field(FIELD_TYPE_INT, "DL Bandwidth", 10, 1),
    };

    static public void decode(log_packet packet) {
        System.out.println("log_lte_rrc_mib");
        packet.log_name = "log_lte_rrc_mib";
        packet.add_fields(field_fmt);
    }

    /*
    static public void decode(log_packet packet) {

        System.out.prFIELD_TYPE_INTln("log_lte_rrc_mib");
/*
        FIELD_TYPE_INT VERSION = packet.decoder.getByteValue();
        FIELD_TYPE_INT PHYSICAL_CELL_ID = packet.decoder.getShortValue();
        FIELD_TYPE_INT FREQ = packet.decoder.getFIELD_TYPE_INTValue();
        FIELD_TYPE_INT SFN = packet.decoder.getBitValue(10);
        FIELD_TYPE_INT RESERVED = packet.decoder.getBitValue(6);
        byte Number_of_TX_Antennas = packet.decoder.getByteValue();
        FIELD_TYPE_INT DL_Bandwidth = packet.decoder.getByteValue();

        System.out.prFIELD_TYPE_INTf("VERSION: %04x", VERSION);
        System.out.prFIELD_TYPE_INTln();
        System.out.prFIELD_TYPE_INTf("PHYSICAL_CELL_ID: %04x", PHYSICAL_CELL_ID);
        System.out.prFIELD_TYPE_INTln();
        System.out.prFIELD_TYPE_INTf("FREQ: %08x", FREQ);
        System.out.prFIELD_TYPE_INTln();
        System.out.prFIELD_TYPE_INTf("SFN: %04x", SFN);
        System.out.prFIELD_TYPE_INTln();
        System.out.prFIELD_TYPE_INTf("RESERVED: %02x", RESERVED);
        System.out.prFIELD_TYPE_INTln();
        System.out.prFIELD_TYPE_INTf("Number_of_TX_Antennas: %02x", Number_of_TX_Antennas);
        System.out.prFIELD_TYPE_INTln();
        System.out.prFIELD_TYPE_INTf("DL_Bandwidth: %02x", DL_Bandwidth);
        System.out.prFIELD_TYPE_INTln();

        packet.add_pair("mib.pci", PHYSICAL_CELL_ID);
        packet.add_pair("mib.earfcn", FREQ);
        packet.add_pair("mib.sfn", SFN);
        packet.add_pair("mib.DL_Bandwidth", DL_Bandwidth);
    }*/
}
