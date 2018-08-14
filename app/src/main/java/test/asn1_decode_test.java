package test;

import diag.codec.asn1_decoder;

import static test.test_util.check_equal;

//import static diag.ui_cmd_agent.ans1_decode_req;

/**
 * Created by xxx on 2018/3/4.
 */

public class asn1_decode_test {

    private static String expected_xml = "<packet>\n" +
            "  <proto name=\"geninfo\" pos=\"0\" showname=\"General information\" size=\"31\">\n" +
            "    <field name=\"num\" pos=\"0\" show=\"0\" showname=\"Number\" value=\"0\" size=\"31\"/>\n" +
            "    <field name=\"len\" pos=\"0\" show=\"31\" showname=\"Frame Length\" value=\"1f\" size=\"31\"/>\n" +
            "    <field name=\"caplen\" pos=\"0\" show=\"31\" showname=\"Captured Length\" value=\"1f\" size=\"31\"/>\n" +
            "    <field name=\"timestamp\" pos=\"0\" show=\"Jan  1, 1970 08:00:00.000000000 CST\" showname=\"Captured Time\" value=\"0.000000000\" size=\"31\"/>\n" +
            "  </proto>\n" +
            "<proto name=\"frame\" showname=\"Frame 0: 31 bytes on wire (248 bits), 31 bytes captured (248 bits)\" size=\"31\" pos=\"0\">\n" +
            "<field name=\"frame.encap_type\" showname=\"Encapsulation type: USER 1 (46)\" size=\"0\" pos=\"0\" show=\"46\"/>\n" +
            "<field name=\"frame.number\" showname=\"Frame Number: 0\" size=\"0\" pos=\"0\" show=\"0\"/>\n" +
            "<field name=\"frame.len\" showname=\"Frame Length: 31 bytes (248 bits)\" size=\"0\" pos=\"0\" show=\"31\"/>\n" +
            "<field name=\"frame.cap_len\" showname=\"Capture Length: 31 bytes (248 bits)\" size=\"0\" pos=\"0\" show=\"31\"/>\n" +
            "<field name=\"frame.marked\" showname=\"Frame is marked: False\" size=\"0\" pos=\"0\" show=\"0\"/>\n" +
            "<field name=\"frame.ignored\" showname=\"Frame is ignored: False\" size=\"0\" pos=\"0\" show=\"0\"/>\n" +
            "<field name=\"frame.protocols\" showname=\"Protocols in frame: user_dlt:data\" size=\"0\" pos=\"0\" show=\"user_dlt:data\"/>\n" +
            "</proto>\n" +
            "<proto name=\"user_dlt\" showname=\"DLT: 148, Payload: aww (Automator Wireshark Wrapper)\" size=\"31\" pos=\"0\"/>\n" +
            "<proto name=\"aww\" showname=\"Automator Wireshark Wrapper\" size=\"31\" pos=\"0\">\n" +
            "<field name=\"aww.proto\" showname=\"Protocol: 204\" size=\"4\" pos=\"0\" show=\"204\" value=\"000000cc\"/>\n" +
            "<field name=\"aww.data_len\" showname=\"Data length: 23\" size=\"4\" pos=\"4\" show=\"23\" value=\"00000017\"/>\n" +
            "</proto>\n" +
            "<proto name=\"fake-field-wrapper\">\n" +
            "<field name=\"lte-rrc.DL_CCCH_Message_element\" showname=\"DL-CCCH-Message\" size=\"23\" pos=\"8\" show=\"\" value=\"\">\n" +
            "<field name=\"per.choice_index\" showname=\"Choice Index: 0\" hide=\"yes\" size=\"1\" pos=\"8\" show=\"0\" value=\"68\"/>\n" +
            "<field name=\"lte-rrc.message\" showname=\"message: c1 (0)\" size=\"23\" pos=\"8\" show=\"0\" value=\"6812980a5dd22183c0ba007e12cb908f86551321424074\">\n" +
            "<field name=\"per.choice_index\" showname=\"Choice Index: 3\" hide=\"yes\" size=\"1\" pos=\"8\" show=\"3\" value=\"68\"/>\n" +
            "<field name=\"lte-rrc.c1\" showname=\"c1: rrcConnectionSetup (3)\" size=\"23\" pos=\"8\" show=\"3\" value=\"6812980a5dd22183c0ba007e12cb908f86551321424074\">\n" +
            "<field name=\"lte-rrc.rrcConnectionSetup_element\" showname=\"rrcConnectionSetup\" size=\"23\" pos=\"8\" show=\"\" value=\"\">\n" +
            "<field name=\"lte-rrc.rrc_TransactionIdentifier\" showname=\"rrc-TransactionIdentifier: 1\" size=\"1\" pos=\"8\" show=\"1\" value=\"68\"/>\n" +
            "<field name=\"per.choice_index\" showname=\"Choice Index: 0\" hide=\"yes\" size=\"1\" pos=\"8\" show=\"0\" value=\"68\"/>\n" +
            "<field name=\"lte-rrc.criticalExtensions\" showname=\"criticalExtensions: c1 (0)\" size=\"23\" pos=\"8\" show=\"0\" value=\"6812980a5dd22183c0ba007e12cb908f86551321424074\">\n" +
            "<field name=\"per.choice_index\" showname=\"Choice Index: 0\" hide=\"yes\" size=\"1\" pos=\"8\" show=\"0\" value=\"68\"/>\n" +
            "<field name=\"lte-rrc.c1\" showname=\"c1: rrcConnectionSetup-r8 (0)\" size=\"23\" pos=\"8\" show=\"0\" value=\"6812980a5dd22183c0ba007e12cb908f86551321424074\">\n" +
            "<field name=\"lte-rrc.rrcConnectionSetup_r8_element\" showname=\"rrcConnectionSetup-r8\" size=\"22\" pos=\"9\" show=\"\" value=\"\">\n" +
            "<field name=\"per.optional_field_bit\" showname=\".0.. .... Optional Field Bit: False (nonCriticalExtension is NOT present)\" hide=\"yes\" size=\"1\" pos=\"9\" show=\"0\" value=\"0\" unmaskedvalue=\"12\"/>\n" +
            "<field name=\"lte-rrc.radioResourceConfigDedicated_element\" showname=\"radioResourceConfigDedicated\" size=\"22\" pos=\"9\" show=\"\" value=\"\">\n" +
            "<field name=\"per.extension_bit\" showname=\"..0. .... Extension Bit: False\" hide=\"yes\" size=\"1\" pos=\"9\" show=\"0\" value=\"0\" unmaskedvalue=\"12\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\"...1 .... Optional Field Bit: True (srb-ToAddModList is present)\" hide=\"yes\" size=\"1\" pos=\"9\" show=\"1\" value=\"1\" unmaskedvalue=\"12\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\".... 0... Optional Field Bit: False (drb-ToAddModList is NOT present)\" hide=\"yes\" size=\"1\" pos=\"9\" show=\"0\" value=\"0\" unmaskedvalue=\"12\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\".... .0.. Optional Field Bit: False (drb-ToReleaseList is NOT present)\" hide=\"yes\" size=\"1\" pos=\"9\" show=\"0\" value=\"0\" unmaskedvalue=\"12\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\".... ..1. Optional Field Bit: True (mac-MainConfig is present)\" hide=\"yes\" size=\"1\" pos=\"9\" show=\"1\" value=\"1\" unmaskedvalue=\"12\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\".... ...0 Optional Field Bit: False (sps-Config is NOT present)\" hide=\"yes\" size=\"1\" pos=\"9\" show=\"0\" value=\"0\" unmaskedvalue=\"12\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\"1... .... Optional Field Bit: True (physicalConfigDedicated is present)\" hide=\"yes\" size=\"1\" pos=\"10\" show=\"1\" value=\"1\" unmaskedvalue=\"98\"/>\n" +
            "<field name=\"per.sequence_of_length\" showname=\"Sequence-Of Length: 1\" hide=\"yes\" size=\"1\" pos=\"10\" show=\"1\" value=\"98\"/>\n" +
            "<field name=\"lte-rrc.srb_ToAddModList\" showname=\"srb-ToAddModList: 1 item\" size=\"6\" pos=\"10\" show=\"1\" value=\"980a5dd22183\">\n" +
            "<field name=\"\" show=\"Item 0\" size=\"6\" pos=\"10\" value=\"980a5dd22183\">\n" +
            "<field name=\"lte-rrc.SRB_ToAddMod_element\" showname=\"SRB-ToAddMod\" size=\"6\" pos=\"10\" show=\"\" value=\"\">\n" +
            "<field name=\"per.extension_bit\" showname=\"..0. .... Extension Bit: False\" hide=\"yes\" size=\"1\" pos=\"10\" show=\"0\" value=\"0\" unmaskedvalue=\"98\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\"...1 .... Optional Field Bit: True (rlc-Config is present)\" hide=\"yes\" size=\"1\" pos=\"10\" show=\"1\" value=\"1\" unmaskedvalue=\"98\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\".... 1... Optional Field Bit: True (logicalChannelConfig is present)\" hide=\"yes\" size=\"1\" pos=\"10\" show=\"1\" value=\"1\" unmaskedvalue=\"98\"/>\n" +
            "<field name=\"lte-rrc.srb_Identity\" showname=\"srb-Identity: 1\" size=\"1\" pos=\"10\" show=\"1\" value=\"98\"/>\n" +
            "<field name=\"per.choice_index\" showname=\"Choice Index: 0\" hide=\"yes\" size=\"1\" pos=\"10\" show=\"0\" value=\"98\"/>\n" +
            "<field name=\"lte-rrc.rlc_Config\" showname=\"rlc-Config: explicitValue (0)\" size=\"4\" pos=\"10\" show=\"0\" value=\"980a5dd2\">\n" +
            "<field name=\"per.extension_bit\" showname=\".... ...0 Extension Bit: False\" hide=\"yes\" size=\"1\" pos=\"10\" show=\"0\" value=\"0\" unmaskedvalue=\"98\"/>\n" +
            "<field name=\"per.choice_index\" showname=\"Choice Index: 0\" hide=\"yes\" size=\"1\" pos=\"11\" show=\"0\" value=\"0a\"/>\n" +
            "<field name=\"lte-rrc.explicitValue\" showname=\"explicitValue: am (0)\" size=\"4\" pos=\"10\" show=\"0\" value=\"980a5dd2\">\n" +
            "<field name=\"lte-rrc.am_element\" showname=\"am\" size=\"3\" pos=\"11\" show=\"\" value=\"\">\n" +
            "<field name=\"lte-rrc.ul_AM_RLC_element\" showname=\"ul-AM-RLC\" size=\"2\" pos=\"11\" show=\"\" value=\"\">\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 10\" hide=\"yes\" size=\"1\" pos=\"11\" show=\"10\" value=\"0a\"/>\n" +
            "<field name=\"lte-rrc.t_PollRetransmit\" showname=\"t-PollRetransmit: ms55 (10)\" size=\"1\" pos=\"11\" show=\"10\" value=\"0a\"/>\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 2\" hide=\"yes\" size=\"1\" pos=\"12\" show=\"2\" value=\"5d\"/>\n" +
            "<field name=\"lte-rrc.pollPDU\" showname=\"pollPDU: p16 (2)\" size=\"1\" pos=\"12\" show=\"2\" value=\"5d\"/>\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 14\" hide=\"yes\" size=\"1\" pos=\"12\" show=\"14\" value=\"5d\"/>\n" +
            "<field name=\"lte-rrc.pollByte\" showname=\"pollByte: kBinfinity (14)\" size=\"1\" pos=\"12\" show=\"14\" value=\"5d\"/>\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 7\" hide=\"yes\" size=\"1\" pos=\"12\" show=\"7\" value=\"5d\"/>\n" +
            "<field name=\"lte-rrc.maxRetxThreshold\" showname=\"maxRetxThreshold: t32 (7)\" size=\"1\" pos=\"12\" show=\"7\" value=\"5d\"/>\n" +
            "</field>\n" +
            "<field name=\"lte-rrc.dl_AM_RLC_element\" showname=\"dl-AM-RLC\" size=\"1\" pos=\"13\" show=\"\" value=\"\">\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 9\" hide=\"yes\" size=\"1\" pos=\"13\" show=\"9\" value=\"d2\"/>\n" +
            "<field name=\"lte-rrc.t_Reordering\" showname=\"t-Reordering: ms45 (9)\" size=\"1\" pos=\"13\" show=\"9\" value=\"d2\"/>\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 4\" hide=\"yes\" size=\"1\" pos=\"13\" show=\"4\" value=\"d2\"/>\n" +
            "<field name=\"lte-rrc.t_StatusProhibit\" showname=\"t-StatusProhibit: ms20 (4)\" size=\"1\" pos=\"13\" show=\"4\" value=\"d2\"/>\n" +
            "</field>\n" +
            "</field>\n" +
            "</field>\n" +
            "</field>\n" +
            "<field name=\"per.choice_index\" showname=\"Choice Index: 0\" hide=\"yes\" size=\"1\" pos=\"14\" show=\"0\" value=\"21\"/>\n" +
            "<field name=\"lte-rrc.logicalChannelConfig\" showname=\"logicalChannelConfig: explicitValue (0)\" size=\"2\" pos=\"14\" show=\"0\" value=\"2183\">\n" +
            "<field name=\"lte-rrc.explicitValue_element\" showname=\"explicitValue\" size=\"2\" pos=\"14\" show=\"\" value=\"\">\n" +
            "<field name=\"per.extension_bit\" showname=\".... ..0. Extension Bit: False\" hide=\"yes\" size=\"1\" pos=\"14\" show=\"0\" value=\"0\" unmaskedvalue=\"21\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\".... ...1 Optional Field Bit: True (ul-SpecificParameters is present)\" hide=\"yes\" size=\"1\" pos=\"14\" show=\"1\" value=\"1\" unmaskedvalue=\"21\"/>\n" +
            "<field name=\"lte-rrc.ul_SpecificParameters_element\" showname=\"ul-SpecificParameters\" size=\"1\" pos=\"15\" show=\"\" value=\"\">\n" +
            "<field name=\"per.optional_field_bit\" showname=\"1... .... Optional Field Bit: True (logicalChannelGroup is present)\" hide=\"yes\" size=\"1\" pos=\"15\" show=\"1\" value=\"1\" unmaskedvalue=\"83\"/>\n" +
            "<field name=\"lte-rrc.priority\" showname=\"priority: 1\" size=\"1\" pos=\"15\" show=\"1\" value=\"83\"/>\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 7\" hide=\"yes\" size=\"1\" pos=\"15\" show=\"7\" value=\"83\"/>\n" +
            "<field name=\"lte-rrc.prioritisedBitRate\" showname=\"prioritisedBitRate: infinity (7)\" size=\"1\" pos=\"15\" show=\"7\" value=\"83\"/>\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 4\" hide=\"yes\" size=\"1\" pos=\"16\" show=\"4\" value=\"c0\"/>\n" +
            "<field name=\"lte-rrc.bucketSizeDuration\" showname=\"bucketSizeDuration: ms500 (4)\" size=\"1\" pos=\"16\" show=\"4\" value=\"c0\"/>\n" +
            "<field name=\"lte-rrc.logicalChannelGroup\" showname=\"logicalChannelGroup: 0\" size=\"1\" pos=\"16\" show=\"0\" value=\"c0\"/>\n" +
            "</field>\n" +
            "</field>\n" +
            "</field>\n" +
            "</field>\n" +
            "</field>\n" +
            "</field>\n" +
            "<field name=\"per.choice_index\" showname=\"Choice Index: 0\" hide=\"yes\" size=\"1\" pos=\"16\" show=\"0\" value=\"c0\"/>\n" +
            "<field name=\"lte-rrc.mac_MainConfig\" showname=\"mac-MainConfig: explicitValue (0)\" size=\"4\" pos=\"16\" show=\"0\" value=\"c0ba007e\">\n" +
            "<field name=\"lte-rrc.explicitValue_element\" showname=\"explicitValue\" size=\"4\" pos=\"16\" show=\"\" value=\"\">\n" +
            "<field name=\"per.extension_bit\" showname=\".... ...0 Extension Bit: False\" hide=\"yes\" size=\"1\" pos=\"16\" show=\"0\" value=\"0\" unmaskedvalue=\"c0\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\"1... .... Optional Field Bit: True (ul-SCH-Config is present)\" hide=\"yes\" size=\"1\" pos=\"17\" show=\"1\" value=\"1\" unmaskedvalue=\"ba\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\".0.. .... Optional Field Bit: False (drx-Config is NOT present)\" hide=\"yes\" size=\"1\" pos=\"17\" show=\"0\" value=\"0\" unmaskedvalue=\"ba\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\"..1. .... Optional Field Bit: True (phr-Config is present)\" hide=\"yes\" size=\"1\" pos=\"17\" show=\"1\" value=\"1\" unmaskedvalue=\"ba\"/>\n" +
            "<field name=\"lte-rrc.ul_SCH_Config_element\" showname=\"ul-SCH-Config\" size=\"2\" pos=\"17\" show=\"\" value=\"\">\n" +
            "<field name=\"per.optional_field_bit\" showname=\"...1 .... Optional Field Bit: True (maxHARQ-Tx is present)\" hide=\"yes\" size=\"1\" pos=\"17\" show=\"1\" value=\"1\" unmaskedvalue=\"ba\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\".... 1... Optional Field Bit: True (periodicBSR-Timer is present)\" hide=\"yes\" size=\"1\" pos=\"17\" show=\"1\" value=\"1\" unmaskedvalue=\"ba\"/>\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 4\" hide=\"yes\" size=\"1\" pos=\"17\" show=\"4\" value=\"ba\"/>\n" +
            "<field name=\"lte-rrc.maxHARQ_Tx\" showname=\"maxHARQ-Tx: n5 (4)\" size=\"1\" pos=\"17\" show=\"4\" value=\"ba\"/>\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 0\" hide=\"yes\" size=\"1\" pos=\"18\" show=\"0\" value=\"00\"/>\n" +
            "<field name=\"lte-rrc.periodicBSR_Timer\" showname=\"periodicBSR-Timer: sf5 (0)\" size=\"1\" pos=\"18\" show=\"0\" value=\"00\"/>\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 0\" hide=\"yes\" size=\"1\" pos=\"18\" show=\"0\" value=\"00\"/>\n" +
            "<field name=\"lte-rrc.retxBSR_Timer\" showname=\"retxBSR-Timer: sf320 (0)\" size=\"1\" pos=\"18\" show=\"0\" value=\"00\"/>\n" +
            "<field name=\"lte-rrc.ttiBundling\" showname=\"0... .... ttiBundling: False\" size=\"1\" pos=\"19\" show=\"0\" value=\"7e\"/>\n" +
            "</field>\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 7\" hide=\"yes\" size=\"1\" pos=\"19\" show=\"7\" value=\"7e\"/>\n" +
            "<field name=\"lte-rrc.timeAlignmentTimerDedicated\" showname=\"timeAlignmentTimerDedicated: infinity (7)\" size=\"1\" pos=\"19\" show=\"7\" value=\"7e\"/>\n" +
            "<field name=\"per.choice_index\" showname=\"Choice Index: 1\" hide=\"yes\" size=\"1\" pos=\"19\" show=\"1\" value=\"7e\"/>\n" +
            "<field name=\"lte-rrc.phr_Config\" showname=\"phr-Config: setup (1)\" size=\"1\" pos=\"19\" show=\"1\" value=\"7e\">\n" +
            "<field name=\"lte-rrc.setup_element\" showname=\"setup\" size=\"1\" pos=\"19\" show=\"\" value=\"\">\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 6\" hide=\"yes\" size=\"1\" pos=\"19\" show=\"6\" value=\"7e\"/>\n" +
            "<field name=\"lte-rrc.periodicPHR_Timer\" showname=\"periodicPHR-Timer: sf1000 (6)\" size=\"1\" pos=\"19\" show=\"6\" value=\"7e\"/>\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 0\" hide=\"yes\" size=\"1\" pos=\"20\" show=\"0\" value=\"12\"/>\n" +
            "<field name=\"lte-rrc.prohibitPHR_Timer\" showname=\"prohibitPHR-Timer: sf0 (0)\" size=\"1\" pos=\"20\" show=\"0\" value=\"12\"/>\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 2\" hide=\"yes\" size=\"1\" pos=\"20\" show=\"2\" value=\"12\"/>\n" +
            "<field name=\"lte-rrc.dl_PathlossChange\" showname=\"dl-PathlossChange: dB6 (2)\" size=\"1\" pos=\"20\" show=\"2\" value=\"12\"/>\n" +
            "</field>\n" +
            "</field>\n" +
            "</field>\n" +
            "</field>\n" +
            "<field name=\"lte-rrc.physicalConfigDedicated_element\" showname=\"physicalConfigDedicated\" size=\"11\" pos=\"20\" show=\"\" value=\"\">\n" +
            "<field name=\"per.extension_bit\" showname=\".... .0.. Extension Bit: False\" hide=\"yes\" size=\"1\" pos=\"20\" show=\"0\" value=\"0\" unmaskedvalue=\"12\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\".... ..1. Optional Field Bit: True (pdsch-ConfigDedicated is present)\" hide=\"yes\" size=\"1\" pos=\"20\" show=\"1\" value=\"1\" unmaskedvalue=\"12\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\".... ...0 Optional Field Bit: False (pucch-ConfigDedicated is NOT present)\" hide=\"yes\" size=\"1\" pos=\"20\" show=\"0\" value=\"0\" unmaskedvalue=\"12\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\"1... .... Optional Field Bit: True (pusch-ConfigDedicated is present)\" hide=\"yes\" size=\"1\" pos=\"21\" show=\"1\" value=\"1\" unmaskedvalue=\"cb\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\".1.. .... Optional Field Bit: True (uplinkPowerControlDedicated is present)\" hide=\"yes\" size=\"1\" pos=\"21\" show=\"1\" value=\"1\" unmaskedvalue=\"cb\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\"..0. .... Optional Field Bit: False (tpc-PDCCH-ConfigPUCCH is NOT present)\" hide=\"yes\" size=\"1\" pos=\"21\" show=\"0\" value=\"0\" unmaskedvalue=\"cb\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\"...0 .... Optional Field Bit: False (tpc-PDCCH-ConfigPUSCH is NOT present)\" hide=\"yes\" size=\"1\" pos=\"21\" show=\"0\" value=\"0\" unmaskedvalue=\"cb\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\".... 1... Optional Field Bit: True (cqi-ReportConfig is present)\" hide=\"yes\" size=\"1\" pos=\"21\" show=\"1\" value=\"1\" unmaskedvalue=\"cb\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\".... .0.. Optional Field Bit: False (soundingRS-UL-ConfigDedicated is NOT present)\" hide=\"yes\" size=\"1\" pos=\"21\" show=\"0\" value=\"0\" unmaskedvalue=\"cb\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\".... ..1. Optional Field Bit: True (antennaInfo is present)\" hide=\"yes\" size=\"1\" pos=\"21\" show=\"1\" value=\"1\" unmaskedvalue=\"cb\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\".... ...1 Optional Field Bit: True (schedulingRequestConfig is present)\" hide=\"yes\" size=\"1\" pos=\"21\" show=\"1\" value=\"1\" unmaskedvalue=\"cb\"/>\n" +
            "<field name=\"lte-rrc.pdsch_ConfigDedicated_element\" showname=\"pdsch-ConfigDedicated\" size=\"1\" pos=\"22\" show=\"\" value=\"\">\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 4\" hide=\"yes\" size=\"1\" pos=\"22\" show=\"4\" value=\"90\"/>\n" +
            "<field name=\"lte-rrc.p_a\" showname=\"p-a: dB0 (4)\" size=\"1\" pos=\"22\" show=\"4\" value=\"90\"/>\n" +
            "</field>\n" +
            "<field name=\"lte-rrc.pusch_ConfigDedicated_element\" showname=\"pusch-ConfigDedicated\" size=\"1\" pos=\"22\" show=\"\" value=\"\">\n" +
            "<field name=\"lte-rrc.betaOffset_ACK_Index\" showname=\"betaOffset-ACK-Index: 8\" size=\"1\" pos=\"22\" show=\"8\" value=\"90\"/>\n" +
            "<field name=\"lte-rrc.betaOffset_RI_Index\" showname=\"betaOffset-RI-Index: 4\" size=\"1\" pos=\"22\" show=\"4\" value=\"90\"/>\n" +
            "<field name=\"lte-rrc.betaOffset_CQI_Index\" showname=\"betaOffset-CQI-Index: 7\" size=\"1\" pos=\"23\" show=\"7\" value=\"8f\"/>\n" +
            "</field>\n" +
            "<field name=\"lte-rrc.uplinkPowerControlDedicated_element\" showname=\"uplinkPowerControlDedicated\" size=\"3\" pos=\"23\" show=\"\" value=\"\">\n" +
            "<field name=\"per.optional_field_bit\" showname=\".... ...1 Optional Field Bit: True (filterCoefficient is present)\" hide=\"yes\" size=\"1\" pos=\"23\" show=\"1\" value=\"1\" unmaskedvalue=\"8f\"/>\n" +
            "<field name=\"lte-rrc.p0_UE_PUSCH\" showname=\"p0-UE-PUSCH: 0dB\" size=\"1\" pos=\"24\" show=\"0\" value=\"86\"/>\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 0\" hide=\"yes\" size=\"1\" pos=\"24\" show=\"0\" value=\"86\"/>\n" +
            "<field name=\"lte-rrc.deltaMCS_Enabled\" showname=\"deltaMCS-Enabled: en0 (0)\" size=\"1\" pos=\"24\" show=\"0\" value=\"86\"/>\n" +
            "<field name=\"lte-rrc.accumulationEnabled\" showname=\".... .1.. accumulationEnabled: True\" size=\"1\" pos=\"24\" show=\"1\" value=\"86\"/>\n" +
            "<field name=\"lte-rrc.p0_UE_PUCCH\" showname=\"p0-UE-PUCCH: 1dB\" size=\"1\" pos=\"24\" show=\"1\" value=\"86\"/>\n" +
            "<field name=\"lte-rrc.pSRS_Offset\" showname=\"pSRS-Offset: 5\" size=\"1\" pos=\"25\" show=\"5\" value=\"55\"/>\n" +
            "<field name=\"per.extension_present_bit\" showname=\".... ..0. Extension Present Bit: False\" hide=\"yes\" size=\"1\" pos=\"25\" show=\"0\" value=\"0\" unmaskedvalue=\"55\"/>\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 8\" hide=\"yes\" size=\"1\" pos=\"25\" show=\"8\" value=\"55\"/>\n" +
            "<field name=\"lte-rrc.filterCoefficient\" showname=\"filterCoefficient: fc8 (8)\" size=\"1\" pos=\"25\" show=\"8\" value=\"55\"/>\n" +
            "</field>\n" +
            "<field name=\"lte-rrc.cqi_ReportConfig_element\" showname=\"cqi-ReportConfig\" size=\"1\" pos=\"26\" show=\"\" value=\"\">\n" +
            "<field name=\"per.optional_field_bit\" showname=\"...1 .... Optional Field Bit: True (cqi-ReportModeAperiodic is present)\" hide=\"yes\" size=\"1\" pos=\"26\" show=\"1\" value=\"1\" unmaskedvalue=\"13\"/>\n" +
            "<field name=\"per.optional_field_bit\" showname=\".... 0... Optional Field Bit: False (cqi-ReportPeriodic is NOT present)\" hide=\"yes\" size=\"1\" pos=\"26\" show=\"0\" value=\"0\" unmaskedvalue=\"13\"/>\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 3\" hide=\"yes\" size=\"1\" pos=\"26\" show=\"3\" value=\"13\"/>\n" +
            "<field name=\"lte-rrc.cqi_ReportModeAperiodic\" showname=\"cqi-ReportModeAperiodic: rm30 (3)\" size=\"1\" pos=\"26\" show=\"3\" value=\"13\"/>\n" +
            "<field name=\"lte-rrc.nomPDSCH_RS_EPRE_Offset\" showname=\"nomPDSCH-RS-EPRE-Offset: 0dB (0)\" size=\"1\" pos=\"27\" show=\"0\" value=\"21\"/>\n" +
            "</field>\n" +
            "<field name=\"per.choice_index\" showname=\"Choice Index: 0\" hide=\"yes\" size=\"1\" pos=\"27\" show=\"0\" value=\"21\"/>\n" +
            "<field name=\"lte-rrc.antennaInfo\" showname=\"antennaInfo: explicitValue (0)\" size=\"1\" pos=\"27\" show=\"0\" value=\"21\">\n" +
            "<field name=\"lte-rrc.explicitValue_element\" showname=\"explicitValue\" size=\"1\" pos=\"27\" show=\"\" value=\"\">\n" +
            "<field name=\"per.optional_field_bit\" showname=\".... 0... Optional Field Bit: False (codebookSubsetRestriction is NOT present)\" hide=\"yes\" size=\"1\" pos=\"27\" show=\"0\" value=\"0\" unmaskedvalue=\"21\"/>\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 1\" hide=\"yes\" size=\"1\" pos=\"27\" show=\"1\" value=\"21\"/>\n" +
            "<field name=\"lte-rrc.transmissionMode\" showname=\"transmissionMode: tm2 (1)\" size=\"1\" pos=\"27\" show=\"1\" value=\"21\"/>\n" +
            "<field name=\"per.choice_index\" showname=\"Choice Index: 0\" hide=\"yes\" size=\"1\" pos=\"28\" show=\"0\" value=\"42\"/>\n" +
            "<field name=\"lte-rrc.ue_TransmitAntennaSelection\" showname=\"ue-TransmitAntennaSelection: release (0)\" size=\"1\" pos=\"28\" show=\"0\" value=\"42\">\n" +
            "<field name=\"lte-rrc.release_element\" showname=\"release: NULL\" size=\"1\" pos=\"28\" show=\"\" value=\"\"/>\n" +
            "</field>\n" +
            "</field>\n" +
            "</field>\n" +
            "<field name=\"per.choice_index\" showname=\"Choice Index: 1\" hide=\"yes\" size=\"1\" pos=\"28\" show=\"1\" value=\"42\"/>\n" +
            "<field name=\"lte-rrc.schedulingRequestConfig\" showname=\"schedulingRequestConfig: setup (1)\" size=\"3\" pos=\"28\" show=\"1\" value=\"424074\">\n" +
            "<field name=\"lte-rrc.setup_element\" showname=\"setup\" size=\"3\" pos=\"28\" show=\"\" value=\"\">\n" +
            "<field name=\"lte-rrc.sr_PUCCH_ResourceIndex\" showname=\"sr-PUCCH-ResourceIndex: 72\" size=\"2\" pos=\"28\" show=\"72\" value=\"4240\"/>\n" +
            "<field name=\"lte-rrc.sr_ConfigIndex\" showname=\"sr-ConfigIndex: 14\" size=\"1\" pos=\"29\" show=\"14\" value=\"40\">\n" +
            "<field name=\"lte-rrc.sr_Periodicity\" showname=\"Periodicity: 10\" size=\"1\" pos=\"29\" show=\"10\" value=\"40\"/>\n" +
            "<field name=\"lte-rrc.sr_SubframeOffset\" showname=\"Subframe Offset: 9\" size=\"1\" pos=\"29\" show=\"9\" value=\"40\"/>\n" +
            "</field>\n" +
            "<field name=\"per.enum_index\" showname=\"Enumerated Index: 4\" hide=\"yes\" size=\"1\" pos=\"30\" show=\"4\" value=\"74\"/>\n" +
            "<field name=\"lte-rrc.dsr_TransMax\" showname=\"dsr-TransMax: n64 (4)\" size=\"1\" pos=\"30\" show=\"4\" value=\"74\"/>\n" +
            "</field>\n" +
            "</field>\n" +
            "</field>\n" +
            "</field>\n" +
            "</field>\n" +
            "</field>\n" +
            "</field>\n" +
            "</field>\n" +
            "</field>\n" +
            "</field>\n" +
            "</field>\n" +
            "</proto>\n" +
            "<proto name=\"fake-field-wrapper\">\n" +
            "<field name=\"data\" value=\"000000cc000000176812980a5dd22183c0ba007e12cb908f86551321424074\">\n" +
            "<field name=\"data.data\" showname=\"Data: 000000cc000000176812980a5dd22183c0ba007e12cb908f...\" size=\"31\" pos=\"0\" show=\"00:00:00:cc:00:00:00:17:68:12:98:0a:5d:d2:21:83:c0:ba:00:7e:12:cb:90:8f:86:55:13:21:42:40:74\" value=\"000000cc000000176812980a5dd22183c0ba007e12cb908f86551321424074\"/>\n" +
            "<field name=\"data.len\" showname=\"Length: 31\" size=\"0\" pos=\"0\" show=\"31\"/>\n" +
            "</field>\n" +
            "</proto>\n" +
            "</packet>\n" +
            "\n";

    public static void do_test() {

        System.out.println(" ===== start asn1_decode test =====");
        byte[] rrc_conn_setup = {0x00, 0x00, 0x00, (byte) 0xcc, 0x00, 0x00, 0x00, 0x17, 0x68, 0x12, (byte) 0x98, 0x0a, 0x5d, (byte) 0xd2, 0x21, (byte) 0x83,
                (byte) 0xc0, (byte) 0xba, 0x00, 0x7e, 0x12, (byte) 0xcb, (byte) 0x90, (byte) 0x8f,
                (byte) 0x86, 0x55, 0x13, 0x21, 0x42, 0x40, 0x74};
        String recv_xml = asn1_decoder.decode(rrc_conn_setup);

        if (!check_equal(recv_xml, expected_xml)) {
            System.out.println(" ===== start asn1_decode failed =====");
            System.out.printf("expected xml: %s", expected_xml);
            System.out.printf("received xml: %s", recv_xml);
            return;
        }

        System.out.println(" ===== asn1_decode ok! =====");
    }
}
