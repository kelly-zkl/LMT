package com.wisec.scanner.ui.scan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wisec.scanner.R;
import com.wisec.scanner.adapter.ItemParamAdapter;
import com.wisec.scanner.bean.ClearScanner;
import com.wisec.scanner.bean.ParamBean;
import com.wisec.scanner.bean.ParamXmlBean;
import com.wisec.scanner.bean.Sib3Bean;
import com.wisec.scanner.utils.LogUtils;
import com.wisec.scanner.utils.ParamXmlUtil;
import com.wisec.scanner.utils.XmlUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import diag.codec.log_packet;
import diag.codec.log_packet_field;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParamScanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParamScanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "ParamScanFragment";

    private View view, paramView;

    private RecyclerView recyclerView;
    private List<ParamBean> paramBeans, showBeans;
    private List<Sib3Bean> sib3Beans;
    private ItemParamAdapter adapter;

    public ParamScanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParamScanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ParamScanFragment newInstance(String param1, String param2) {
        ParamScanFragment fragment = new ParamScanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scan_village, container, false);

        initView();
        return view;
    }

    private void initView() {
        paramBeans = new ArrayList<>();
        showBeans = new ArrayList<>();
        sib3Beans = new ArrayList<>();
        adapter = new ItemParamAdapter(showBeans, getActivity());

        recyclerView = view.findViewById(R.id.rcv_village);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        paramView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_scan_param, recyclerView, false);
        adapter.setHeaderView(paramView);
        recyclerView.setAdapter(adapter);
    }

    private int baseRx = 0, qHyst379 = 0, cellRe379 = 0, thresh379 = 0, c_earfcn = 0, sNonInt379 = 0,
            sIntra379 = 0, c_pci = 0, l_earfcn = 0, l_pci = 0;
    private boolean isCel379 = false, isBaseRx = false, isqHyst379 = false,
            issNonInt379 = false, isthresh379 = false, issIntra379 = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(log_packet packet) {
        XmlUtils xmlUtils = new XmlUtils();
        //服务小区信息
        if (packet.log_code == 0xB193) {//LOG_LTE_RRC_MIB 服务小区bandWidth(带宽)、pci、earfcn
            log_packet_field earfcn = packet.get_field("SubPacket E-ARFCN");
            log_packet_field pci = packet.get_field("SubPacket PCI");
            c_earfcn = earfcn.value_int;
            c_pci = pci.value_int;

            /**频点、pci变化时删除列表数据*/
            if (l_earfcn != c_earfcn || l_pci != c_pci) {
                LogUtils.I(TAG, "sib.value_int = 0xB193");
                l_earfcn = c_earfcn;
                l_pci = c_pci;
                /**刷新服务小区的信息和列表数据*/
                updateSib3();
            }
        }

        if (packet.log_code == 0xB0C0) {//LOG_LTE_RRC_OTA 服务小区enb、tac、cellid上下行配比
            log_packet_field sib = packet.get_field("SIB_MASK_IN_SI");//sib1 = 0x02
            LogUtils.I(TAG, "sib.value_int = " + sib.value_int);
            String xml = packet.decoded_xml;
            System.out.println(xml);
            log_packet_field sib_earfcn = packet.get_field("EARFCN");
            log_packet_field sib_pci = packet.get_field("PCI");

            Sib3Bean sib3Bean = new Sib3Bean();
            sib3Bean.setEarfcn(sib_earfcn.value_int);
            sib3Bean.setPci(sib_pci.value_int);
            if (sibFilter(sib3Bean)) {//存在数据，只需刷新
                sib3Bean = sib3Beans.get(sibPos);
            }

            if (sib.value_int == 0x02) {//sib1=2  服务小区的最小电平
                String RxLevMin = xmlUtils.Parser(xml, XmlUtils.SHOW_NAME, "q-RxLevMin").trim();
                if (!TextUtils.isEmpty(RxLevMin)) {//服务小区的最小电平
                    String baseRxLevMin = RxLevMin.substring(RxLevMin.indexOf("(") + 1, RxLevMin.indexOf(")"));
                    LogUtils.I(TAG, "RxLevMin = " + RxLevMin + ",baseRxLevMin = " + baseRxLevMin);
                    baseRx = Integer.parseInt(baseRxLevMin);
                    isBaseRx = true;
                    sib3Bean.setqRxLevMin(baseRx);
                }
            }
            //服务小区重选参数
            if (sib.value_int == 0x0C) {//sib3=12  37900的参数
                String qHyst = xmlUtils.Parser(xml, XmlUtils.SHOW_NAME, "q-Hyst").trim();
                if (!TextUtils.isEmpty(qHyst)) {//
                    String _QHyst = qHyst.substring(qHyst.indexOf("(") + 1, qHyst.indexOf(")"));
                    LogUtils.I(TAG, "qHyst = " + qHyst + ",_QHyst = " + _QHyst);
                    qHyst379 = Integer.parseInt(_QHyst);
                    isqHyst379 = true;
                    sib3Bean.setqHyst(qHyst379);
                }

                String sNonInt = xmlUtils.Parser(xml, XmlUtils.SHOW_NAME, "s-NonIntraSearch").trim();
                if (!TextUtils.isEmpty(sNonInt)) {//
                    String _sNonInt = sNonInt.substring(sNonInt.indexOf("(") + 1, sNonInt.indexOf(")"));
                    LogUtils.I(TAG, "sNonInt = " + sNonInt + ",_sNonInt = " + _sNonInt);
                    sNonInt379 = Integer.parseInt(_sNonInt);
                    issNonInt379 = true;
                    sib3Bean.setsNonIntraSearch(sNonInt379);
                }

                String thresh = xmlUtils.Parser(xml, XmlUtils.SHOW_NAME, "threshServingLow").trim();
                if (!TextUtils.isEmpty(thresh)) {//
                    String _thresh = thresh.substring(thresh.indexOf("(") + 1, thresh.indexOf(")"));
                    LogUtils.I(TAG, "thresh = " + thresh + ",_thresh = " + _thresh);
                    thresh379 = Integer.parseInt(_thresh);
                    isthresh379 = true;
                    sib3Bean.setThreshServingLow(thresh379);
                }

                String cellRe = xmlUtils.Parser(xml, XmlUtils.SHOW_NAME, "cellReselectionPriority").trim();
                if (!TextUtils.isEmpty(cellRe)) {//
                    String _cellRe = cellRe.substring(cellRe.indexOf(":") + 2, cellRe.length());
                    LogUtils.I(TAG, "cellRe = " + cellRe + ",_cellRe = " + _cellRe);
                    cellRe379 = Integer.parseInt(_cellRe);
                    isCel379 = true;
                    sib3Bean.setCellReselectionPriority(cellRe379);
                }

                String sIntra = xmlUtils.Parser(xml, XmlUtils.SHOW_NAME, "s-IntraSearch").trim();
                if (!TextUtils.isEmpty(sIntra)) {//
                    String _sIntra = sIntra.substring(sIntra.indexOf("(") + 1, sIntra.indexOf(")"));
                    LogUtils.I(TAG, "sIntra = " + sIntra + ",_sIntra = " + _sIntra);
                    sIntra379 = Integer.parseInt(_sIntra);
                    issIntra379 = true;
                    sib3Bean.setsIntraSearch(sIntra379);
                }
            }

            if (sib.value_int == 0x02 || sib.value_int == 0x0C) {//服务小区的参数
                if (sib3Beans.size() > 0) {
                    if (sibFilter(sib3Bean)) {//存在数据，只需刷新
                        sib3Beans.set(sibPos, sib3Bean);
                    } else {//不存在，添加
                        sib3Beans.add(sib3Bean);
                    }
                    LogUtils.D(TAG, "服务小区的信息" + sib3Bean.toString());
                } else {
                    sib3Beans.add(sib3Bean);
                    LogUtils.D(TAG, "服务小区的信息" + sib3Bean.toString());
                }

                /**刷新服务小区的信息和列表数据*/
                updateSib3();
            }


            //各个频点重选参数
//            if (sib.value_int == 0x20) {//sib5=32

            List<ParamXmlBean> paramXmlBeans = ParamXmlUtil.getParamXmlBean(xml);
            if (paramXmlBeans.size() > 0) {//sib5=32
                int cellRe389 = 0, qRxLev389 = 0, threH389 = 0, threL389 = 0, qOffset389 = 0, earfcn = 0;
                for (int i = 0; i < paramXmlBeans.size(); i++) {
                    ParamXmlBean bean = paramXmlBeans.get(i);
                    earfcn = bean.getDlCarrierFreq();
                    cellRe389 = bean.getCellReselectionPriority();
                    qRxLev389 = bean.getqRxLevMin();
                    threH389 = bean.getThreshXHigh();
                    threL389 = bean.getThreshXLow();
                    qOffset389 = bean.getqOffsetFreq();

                    String ploy = "--";
                    if (isCel379) {
                        ploy = showParam(cellRe379, cellRe389);
                    }
                    String high = "Mn>" + (qRxLev389 + threH389) * 2 + " dBm";
                    String equal = "--";
                    if (isqHyst379) {
                        equal = "Mn-Ms>" + (qHyst379 + qOffset389) + " dB";
                    }
                    String low = "--";
                    if (isBaseRx && isthresh379) {
                        low = "Ms<" + (baseRx + thresh379) * 2 + " dBm , Mn>" + (qRxLev389 + threL389) * 2 + " dBm";
                    }
                    boolean isShow = (sib_earfcn.value_int == c_earfcn && sib_pci.value_int == c_pci);

                    if (earfcn == 0 || earfcn == c_earfcn) {
                        return;
                    } else {
                        ParamBean paramBean = new ParamBean(sib_earfcn.value_int, sib_pci.value_int, cellRe379, earfcn,
                                ploy, high, low, equal, cellRe389, qRxLev389, threH389, qOffset389, threL389, isShow);

                        LogUtils.D(TAG, "添加列表" + paramBean.toString());
                        if (paramBeans.size() > 0) {
                            if (dataFilter(paramBean)) {//存在数据，只需刷新
                                LogUtils.D(TAG, "存在数据，只需刷新");
                                paramBeans.set(pos, paramBean);
                                updateData();
                            } else {//不存在，添加
                                LogUtils.D(TAG, "不存在，添加");
                                paramBeans.add(paramBean);
                                updateData();
                            }
                        } else {
                            LogUtils.D(TAG, "列表为空，添加");
                            paramBeans.add(paramBean);
                            updateData();
                        }
                    }
                }
            }
        }
    }

    /**
     * 更新sib3的参数
     */
    private void updateSib3() {
        baseRx = 0;
        qHyst379 = 0;
        cellRe379 = 0;
        thresh379 = 0;
        sNonInt379 = 0;
        sIntra379 = 0;
        for (int i = 0; i < sib3Beans.size(); i++) {
            Sib3Bean sib3Bean = sib3Beans.get(i);
            if (sib3Bean.getPci() == c_pci && sib3Bean.getEarfcn() == c_earfcn) {
                baseRx = sib3Bean.getqRxLevMin();
                qHyst379 = sib3Bean.getqHyst();
                cellRe379 = sib3Bean.getCellReselectionPriority();
                thresh379 = sib3Bean.getThreshServingLow();
                sNonInt379 = sib3Bean.getsNonIntraSearch();
                sIntra379 = sib3Bean.getsIntraSearch();
                /**刷新服务小区的信息*/
                refreshServiceCell();
                /**数据变化时更新列表的数据*/
                updateData();
            }
        }
    }

    /**
     * 更新服务小区的信息
     */
    private void refreshServiceCell() {
        if (isBaseRx) {
            ((TextView) paramView.findViewById(R.id.tv_service_lev)).setText((baseRx * 2) + " dBm");
            if (issNonInt379) {
                ((TextView) paramView.findViewById(R.id.tv_value_yi)).setText("Ms<" + (baseRx + sNonInt379) * 2 + " dBm");
            } else {
                ((TextView) paramView.findViewById(R.id.tv_value_yi)).setText("--");
            }
            if (issIntra379) {
                ((TextView) paramView.findViewById(R.id.tv_value_tong)).setText("Ms<" + (baseRx + sIntra379) * 2 + " dBm");
            } else {
                ((TextView) paramView.findViewById(R.id.tv_value_tong)).setText("--");
            }
        } else {
            ((TextView) paramView.findViewById(R.id.tv_service_lev)).setText("--");
            ((TextView) paramView.findViewById(R.id.tv_value_yi)).setText("--");
            ((TextView) paramView.findViewById(R.id.tv_value_tong)).setText("--");
        }
        if (isCel379) {
            ((TextView) paramView.findViewById(R.id.tv_value_gravity)).setText(cellRe379 + "");
        } else {
            ((TextView) paramView.findViewById(R.id.tv_value_gravity)).setText("--");
        }
        if (isqHyst379) {
            ((TextView) paramView.findViewById(R.id.tv_value_ploy)).setText("Mn-Ms>" + qHyst379 + " dB");
        } else {
            ((TextView) paramView.findViewById(R.id.tv_value_ploy)).setText("--");
        }
        LogUtils.I(TAG, "更新服务小区参数");
    }

    /**
     * 计算优先级
     */
    private String showParam(int cellRe379, int cellRe389) {
        String lev = "--";
        if (cellRe379 > cellRe389) {
            lev = "低优先级重选";
        } else if (cellRe379 < cellRe389) {
            lev = "高优先级重选";
        } else if (cellRe379 == cellRe389) {
            lev = "同等优先级重选";
        }
        return lev;
    }

    /**
     * 数据筛选
     */
    private int sibPos = 0;

    private boolean sibFilter(Sib3Bean sib3Bean) {
        boolean isExit = false;
        for (int i = 0; i < sib3Beans.size(); i++) {
            Sib3Bean item = sib3Beans.get(i);
            if (item.getEarfcn() == sib3Bean.getEarfcn() && item.getPci() == sib3Bean.getPci()) {
                isExit = true;
                sibPos = i;
                break;
            } else {
                isExit = false;
            }
        }
        return isExit;
    }

    /**
     * 数据筛选
     */
    private int pos = 0;

    private boolean dataFilter(ParamBean paramBean) {
        boolean isExit = false;
        for (int i = 0; i < paramBeans.size(); i++) {
            ParamBean item = paramBeans.get(i);
            if (item.getC_earfcn() == paramBean.getC_earfcn() && item.getC_pci() == paramBean.getC_pci() &&
                    item.getEarfcn() == paramBean.getEarfcn()) {
                isExit = true;
                pos = i;
                break;
            } else {
                isExit = false;
            }
        }
        return isExit;
    }

    /**
     * 数据筛选
     */
    private int posInd = 0;

    private boolean dataCopy(ParamBean paramBean) {
        boolean isExit = false;
        for (int i = 0; i < showBeans.size(); i++) {
            ParamBean item = showBeans.get(i);
            if (item.getC_earfcn() == paramBean.getC_earfcn() && item.getC_pci() == paramBean.getC_pci() &&
                    item.getEarfcn() == paramBean.getEarfcn()) {
                isExit = true;
                posInd = i;
                break;
            } else {
                isExit = false;
            }
        }
        return isExit;
    }

    /**
     * 服务小区的信息改变时，更新数据,只显示本小区的重选列表
     */
    private void updateData() {
        if (paramBeans.size() > 0) {
            for (int i = 0; i < paramBeans.size(); i++) {
                ParamBean paramBean = paramBeans.get(i);
                String ploy = "--";
                if (isCel379) {
                    ploy = showParam(cellRe379, paramBean.getLev());
                }
                String high = "Mn>" + (paramBean.getqRxLev389() + paramBean.getThreH389()) * 2 + " dBm";
                String equal = "--";
                if (isqHyst379) {
                    equal = "Mn-Ms>" + (qHyst379 + paramBean.getqOffset389()) + " dB";
                }
                String low = "--";
                if (isBaseRx && isthresh379) {
                    low = "Ms<" + (baseRx + thresh379) * 2 + " dBm , Mn>" +
                            (paramBean.getqRxLev389() + paramBean.getThreL389()) * 2 + " dBm";
                }
                boolean isShow = (paramBean.getC_earfcn() == c_earfcn && paramBean.getC_pci() == c_pci);

                paramBean.setC_cellRe(cellRe379);
                paramBean.setPloy(ploy);
                paramBean.setHigh(high);
                paramBean.setEqual(equal);
                paramBean.setLow(low);
                paramBean.setShow(isShow);
                LogUtils.D(TAG, "更新列表" + paramBean.toString());

                if (paramBean.getC_earfcn() == c_earfcn && paramBean.getC_pci() == c_pci && isShow) {
                    if (dataCopy(paramBean)) {
                        adapter.updateItem(posInd, paramBean);
                    } else {
                        adapter.addItem(paramBean);
                    }
                } else {
                    adapter.removeItem(paramBean);
                }
            }
        }
    }

    /**
     * 服务小区的频点变化时，删除之前频点的item
     */
    private void deleteData() {
//        Iterator<ParamBean> paramIter = paramBeans.iterator();
//        while (paramIter.hasNext()) {
//            ParamBean paramBean = paramIter.next();
//            if (paramBean.getC_earfcn() != c_earfcn || paramBean.getC_pci() != c_pci) {
//                paramIter.remove();
//                adapter.notifyDataSetChanged();
//            }
//        }
        if (paramBeans.size() > 0) {
            ParamBean paramBean = paramBeans.get(0);
            if (paramBean.getC_earfcn() != c_earfcn || paramBean.getC_pci() != c_pci) {
                adapter.clearAll();
            }
        }
    }

    /**
     * 清空扫频数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ClearScanner clear) {
        ((TextView) paramView.findViewById(R.id.tv_service_lev)).setText("--");
        ((TextView) paramView.findViewById(R.id.tv_value_yi)).setText("--");
        ((TextView) paramView.findViewById(R.id.tv_value_tong)).setText("--");
        ((TextView) paramView.findViewById(R.id.tv_value_gravity)).setText("--");
        ((TextView) paramView.findViewById(R.id.tv_value_ploy)).setText("--");
        adapter.clearAll();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
