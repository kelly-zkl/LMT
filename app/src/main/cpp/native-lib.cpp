#include <jni.h>
#include <string>
#include <endian.h>
#include "ws_wrapper.h"
#include <sys/system_properties.h>
#include <unistd.h>

#define MAX_BUFF_OUT_LEN (1* 1024* 1024)

extern "C"
JNIEXPORT jstring

JNICALL
Java_com_wisec_scanner_base_BaseActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */,
        jbyteArray asn1) {

    jbyte *arrayBody = env->GetByteArrayElements(asn1, 0);
    jsize theArrayLengthJ = env->GetArrayLength(asn1);

    char *starter = (char *) arrayBody;

//    static char asn1[] = {0x00, 0x00, 0x00, 0xcb, 0x00, 0x00, 0x00, 0x2B, 0x00, 0x80, 0x5C, 0x31, 0x2A, 0x6F, 0xE0, 0xAB, 0x64, 0x31, 0x06, 0x90, 0x63, 0x50, 0x00, 0x02, 0x00, 0x56, 0x02, 0x4F, 0x58, 0xB5, 0x10, 0x22, 0x03, 0x00, 0xB2, 0xF0, 0x00, 0x60, 0xC7, 0x48, 0x26, 0x91, 0xAD, 0x49, 0xE0, 0x28, 0x27, 0x54, 0xA9, 0x24, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    static char xml[MAX_BUFF_OUT_LEN];
    uint32_t xml_out_len = 0;

    uint32_t cmd_code = ntohl(*((uint32_t *) starter));

    if ((cmd_code < 200 || cmd_code > 205) && (cmd_code != 250)) {
        return env->NewStringUTF("decode failed. cmd_code error.");
    }

    ws_asn_decode(theArrayLengthJ, starter, &xml_out_len, xml);
//    dump_dissect(theArrayLengthJ, starter, &xml_out_len, xml);

    if (xml_out_len >= MAX_BUFF_OUT_LEN) {
        return env->NewStringUTF("decode failed. cmd_code error.");
    }

    return env->NewStringUTF(xml);
}
