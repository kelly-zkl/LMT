package com.wisec.scanner.utils;

import android.util.Log;
import android.util.Xml;

import com.wisec.scanner.bean.ParamXmlBean;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析xml数据
 * Created by kelly on 2018/4/12.
 */

public class XmlUtils {
    private static String TAG = "XmlParser";
    public static final String SHOW_NAME = "showname";
    public static final String SHOW = "show";
    private String xmlString;

    public String Parser(String xml, String keyName, String valueName) {

        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(new StringReader(xml));
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d(TAG, "XmlPullParserException: " + e);
        }

        try {
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if ("field".equals(parser.getName())) {
                            int count = parser.getAttributeCount();
                            for (int i = 0; i < count; i++) {
                                String key = parser.getAttributeName(i);
                                if (keyName.equals(key)) {
                                    if (parser.getAttributeValue(i).indexOf(valueName) == 0) {
                                        xmlString = parser.getAttributeValue(i);
                                        return xmlString;
                                    }
                                }
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e(TAG, "Exception: " + e);
            return "";
        }
        return "";
    }
}
