package diag.codec;

import com.wisec.scanner.base.BaseActivity;

import diag.binary_dumper;

/**
 * Created by xxx on 2018/3/19.
 */

public class asn1_decoder {

    public static String decode(byte[] asn1) {
        binary_dumper.dump(asn1, asn1.length);
        return BaseActivity.getBaseActivity().stringFromJNI(asn1);
    }
}
