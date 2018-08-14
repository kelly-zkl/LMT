//
// Created by xxx on 2018/3/13.
//

#ifndef WS_DISSECTOR_WS_WRAPPER_H
#define WS_DISSECTOR_WS_WRAPPER_H

int ws_asn_decode(uint32_t in_len, const char* asn1_in, uint32_t* out_len, char* xml_out);

#endif //WS_DISSECTOR_WS_WRAPPER_H
