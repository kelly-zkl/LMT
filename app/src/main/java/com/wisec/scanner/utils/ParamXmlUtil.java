package com.wisec.scanner.utils;

import android.text.TextUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.wisec.scanner.bean.ParamXmlBean;

import java.util.ArrayList;
import java.util.List;

public class ParamXmlUtil {

    public static Packet getPacket(String content) {
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        xStream.alias("packet", Packet.class);
        xStream.alias("field", Field.class);
        return (Packet) xStream.fromXML(content);
    }

    /**
     * 重选参数列表
     */
    public static List<ParamXmlBean> getParamXmlBean(String text) {
        Packet packet = getPacket(text);

        if (packet == null) {
            System.out.println("xml 解析失败");
            return new ArrayList<>();
        }
        return parseInterFreqCarrierFreqInfo(packet);
    }

    public static List<ParamXmlBean> parseInterFreqCarrierFreqInfo(Packet packet) {
        List<ParamXmlBean> result = new ArrayList<>();
        Field interFreqCarrierFreqList = null;
        for (Field source : packet.getProto()) {
            interFreqCarrierFreqList = getFileByShowName(source, "interFreqCarrierFreqList");
            if (interFreqCarrierFreqList != null) break;
        }

        if (interFreqCarrierFreqList == null) {
            System.out.println("未找到name=lte-rrc.interFreqCarrierFreqList的节点");
            return result;
        }
        List<Field> field = interFreqCarrierFreqList.getField();
        if (field == null) {
            System.out.println("name=lte-rrc.interFreqCarrierFreqList的没有子节点");
            return result;
        }
        for (Field child : field) {
            child = getFileByShowName(child, "InterFreqCarrierFreqInfo");
            if (child == null) continue;
            ParamXmlBean paramXmlBean = parseInterFreqCarrierFreqInfoElement(child);
            if (paramXmlBean != null) {
                result.add(paramXmlBean);
            }
        }
        return result;
    }

    public static ParamXmlBean parseInterFreqCarrierFreqInfoElement(Field source) {
        if (source.getShowname().indexOf("InterFreqCarrierFreqInfo") != 0) {
            return null;
        }
        ParamXmlBean paramXmlBean = new ParamXmlBean();
        paramXmlBean.setDlCarrierFreq(getFileShowByName(source, "dl-CarrierFreq"));
        paramXmlBean.setCellReselectionPriority(getFileShowByName(source, "cellReselectionPriority"));
        paramXmlBean.setqRxLevMin(getFileShowByName(source, "q-RxLevMin"));
        paramXmlBean.setThreshXHigh(getFileShowByName(source, "threshX-High"));
        paramXmlBean.setThreshXLow(getFileShowByName(source, "threshX-Low"));
        paramXmlBean.setqOffsetFreq(getFileShowByName(source, "q-OffsetFreq"));
        return paramXmlBean;
    }

    public static int parseInteger(String str) {
        if (str == null || str.trim().length() == 0)
            return 0;
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getFileShowByName(Field source, String name) {
        Field fileByName = getFileByShowName(source, name);
        if (fileByName != null) {
            return parseInteger(fileByName.getShow());
        }
        return 0;
    }

    public static Field getFileByShowName(Field source, String name) {
        if (!TextUtils.isEmpty(source.getShowname()) && source.getShowname().indexOf(name) == 0) {
            return source;
        }
        if (source.getField() != null) {
            for (Field field : source.getField()) {
                Field result = getFileByShowName(field, name);
                if (result != null) return result;
            }
        }
        return null;
    }

    //解析xml为树状结构
    public static String treePacket(String xmlString) {
        Packet packet = getPacket(xmlString);
        StringBuilder sb = new StringBuilder();
//        sb.append(rootName);
        int deep = 1;
        for (Field field1 : packet.getProto()) {
            treeField(field1, sb, deep);
        }
        return sb.toString();
    }

    private static void treeField(Field field, StringBuilder sb, int deep) {
        if (field.getHide() == null) {
            field.setHide("no");
        }
        if (field.getName().equals("geninfo") || field.getName().equals("frame") ||
                field.getName().equals("user_dlt") || field.getName().equals("data") ||
                field.getHide().equals("yes") || field.getName().equals("aww")) {
            return;
        }
        sb.append("\n");
        for (int i = 0; i < deep; i++) {
            sb.append("  ");
        }
        if (field.getShowname() == null) {
            field.setShowname("");
        }
        sb.append(field.getShowname());
        List<Field> childFields = field.getField();
        if (childFields != null && !childFields.isEmpty()) {
            for (Field childField : childFields) {
                treeField(childField, sb, deep + 1);
            }
        }
    }

    @XStreamAlias("packet")
    public static class Packet {

        @XStreamImplicit(itemFieldName = "proto")
        private List<Field> proto;

        public List<Field> getProto() {
            return proto;
        }

        public void setProto(List<Field> proto) {
            this.proto = proto;
        }

        @Override
        public String toString() {
            return "" + proto;
        }
    }

    @XStreamAlias("field")
    public static class Field {
        @XStreamAsAttribute
        @XStreamAlias("name")
        private String name;
        @XStreamAsAttribute
        @XStreamAlias("pos")
        private String pos;
        @XStreamAsAttribute
        @XStreamAlias("size")
        private String size;
        @XStreamAsAttribute
        @XStreamAlias("showname")
        private String showname;
        @XStreamAsAttribute
        @XStreamAlias("show")
        private String show;
        @XStreamAsAttribute
        @XStreamAlias("value")
        private String value;
        @XStreamAsAttribute
        @XStreamAlias("hide")
        private String hide;
        @XStreamImplicit(itemFieldName = "field")
        private List<Field> field;
        @XStreamImplicit(itemFieldName = "proto")
        private List<Field> proto;

        public List<Field> getProto() {
            return proto;
        }

        public void setProto(List<Field> proto) {
            this.proto = proto;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public String getShowname() {
            return showname;
        }

        public void setShowname(String showname) {
            this.showname = showname;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<Field> getField() {
            return field;
        }

        public void setField(List<Field> field) {
            this.field = field;
        }

        public String getShow() {
            return show;
        }

        public void setShow(String show) {
            this.show = show;
        }

        public String getHide() {
            return hide;
        }

        public void setHide(String hide) {
            this.hide = hide;
        }

        @Override
        public String toString() {
            return "name='" + name + '\'' +
                    ", pos='" + pos + '\'' +
                    ", size='" + size + '\'' +
                    ", showname='" + showname + '\'' +
                    ", show='" + show + '\'' +
                    ", value='" + value + '\'' +
                    ", field=" + field;
        }
    }

}
