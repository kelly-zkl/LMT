package com.wisec.scanner.ui.scan;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wisec.scanner.R;
import com.wisec.scanner.adapter.ItemVillageAdapter;
import com.wisec.scanner.bean.ClearScanner;
import com.wisec.scanner.bean.VillageBean;
import com.wisec.scanner.customview.ProgressView;
import com.wisec.scanner.utils.BandUtils;
import com.wisec.scanner.utils.ConstUtils;
import com.wisec.scanner.utils.LogUtils;
import com.wisec.scanner.utils.XmlUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import diag.codec.log_packet;
import diag.codec.log_packet_field;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VillageScanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VillageScanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "VillageScanFragment";

    private View view;
    private RecyclerView recyclerView;
    private List<VillageBean> villageBeans;
    private ItemVillageAdapter adapter;
    private View serView;

    public VillageScanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VillageScanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VillageScanFragment newInstance(String param1, String param2) {
        VillageScanFragment fragment = new VillageScanFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scan_village, container, false);

        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView() {
        villageBeans = new ArrayList<>();
        adapter = new ItemVillageAdapter(villageBeans, getActivity());

        recyclerView = view.findViewById(R.id.rcv_village);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        serView = LayoutInflater.from(getContext()).inflate(R.layout.layout_villager_header, recyclerView, false);
        adapter.setHeaderView(serView);
        recyclerView.setAdapter(adapter);

        mHandler.sendEmptyMessageDelayed(ConstUtils.CLEAR_VILLAGE, ConstUtils.SEC * 15);
    }


    /**
     * 清空扫频数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ClearScanner clear) {
        //清空服务小区信息
        ((TextView) serView.findViewById(R.id.tv_band)).setText("--");
        ((TextView) serView.findViewById(R.id.tv_earfcn)).setText("--");
        ((TextView) serView.findViewById(R.id.tv_pci)).setText("--");
        ((TextView) serView.findViewById(R.id.tv_bandwidth)).setText("--");
        ((TextView) serView.findViewById(R.id.tv_tac)).setText("--");
        ((TextView) serView.findViewById(R.id.tv_enb)).setText("--");
        ((TextView) serView.findViewById(R.id.tv_cellid)).setText("--");
        ((TextView) serView.findViewById(R.id.tv_peibi)).setText("--");
        ((TextView) serView.findViewById(R.id.tv_zhen)).setText("--");
        ((TextView) serView.findViewById(R.id.tv_rs)).setText("--");
        ((ProgressView) serView.findViewById(R.id.pb_rsrp)).setProgressBar(0, "--");
        ((ProgressView) serView.findViewById(R.id.pb_rsrq)).setProgressBar(0, "--");
        //清空小区列表
        adapter.clearAll();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(log_packet packet) {
        XmlUtils xmlUtils = new XmlUtils();
        //服务小区信息
        if (packet.log_code == 0xB0C1) {//LOG_LTE_RRC_MIB 服务小区bandWidth(带宽)、pci、earfcn
            log_packet_field earfcn = packet.get_field("EARFCN");
            log_packet_field pci = packet.get_field("PCI");
            log_packet_field bandwidth = packet.get_field("DL Bandwidth");
            LogUtils.I(TAG, "服务小区：earfcn = " + earfcn.value_int + ",pci = " + pci.value_int + ",bandWidth = " + bandwidth.value_int);
            ((TextView) serView.findViewById(R.id.tv_band)).setText(BandUtils.getBand(earfcn.value_int) + "");
            ((TextView) serView.findViewById(R.id.tv_earfcn)).setText(earfcn.value_int + "");
            ((TextView) serView.findViewById(R.id.tv_pci)).setText(pci.value_int + "");
            ((TextView) serView.findViewById(R.id.tv_bandwidth)).setText((bandwidth.value_int / 5) + " MHz");
        }
        if (packet.log_code == 0xB0C0) {//LOG_LTE_RRC_OTA 服务小区enb、tac、cellid上下行配比
            log_packet_field sib = packet.get_field("SIB_MASK_IN_SI");//sib1 = 0x02
            if (sib.value_int == 0x02) {//sib1
                String xml = packet.decoded_xml;
//                System.out.println(xml);
                String trackingAreaCode = xmlUtils.Parser(xml, XmlUtils.SHOW_NAME, "trackingAreaCode").trim();
                if (!TextUtils.isEmpty(trackingAreaCode)) {//TAC
                    String tac = trackingAreaCode.substring(trackingAreaCode.indexOf(":") + 2, trackingAreaCode.indexOf("[") - 1);
                    LogUtils.I(TAG, "trackingAreaCode = " + trackingAreaCode + ",tac = " + tac);
                    ((TextView) serView.findViewById(R.id.tv_tac)).setText(Integer.parseInt(tac, 16) + "");
                }
                String cellIdentity = xmlUtils.Parser(xml, XmlUtils.SHOW_NAME, "cellIdentity").trim();
                if (!TextUtils.isEmpty(cellIdentity)) {//ENB、Cell Id
                    String cell = cellIdentity.substring(cellIdentity.indexOf(":") + 2, cellIdentity.indexOf("[") - 1);
                    LogUtils.I(TAG, "cellIdentity = " + cellIdentity + ",cell = " + cell);
                    ((TextView) serView.findViewById(R.id.tv_enb)).setText(Integer.parseInt(cell.substring(0, 5), 16) + "");
                    ((TextView) serView.findViewById(R.id.tv_cellid)).setText(Integer.parseInt(cell.substring(5, 7), 16) + "");
                }
                String subframeAssignment = xmlUtils.Parser(xml, XmlUtils.SHOW_NAME, "subframeAssignment").trim();
                if (!TextUtils.isEmpty(subframeAssignment)) {//上下行配比
                    String sub = subframeAssignment.substring(subframeAssignment.indexOf("(") + 1, subframeAssignment.indexOf(")"));
                    LogUtils.I(TAG, "subframeAssignment = " + subframeAssignment + ",sub = " + sub);
                    ((TextView) serView.findViewById(R.id.tv_peibi)).setText(sub);
                }
                String specialSubframePatterns = xmlUtils.Parser(xml, XmlUtils.SHOW_NAME, "specialSubframePatterns").trim();
                if (!TextUtils.isEmpty(specialSubframePatterns)) {//特殊子帧配置
                    String special = specialSubframePatterns.substring(specialSubframePatterns.indexOf("(") + 1, specialSubframePatterns.indexOf(")"));
                    LogUtils.I(TAG, "specialSubframePatterns = " + specialSubframePatterns + ",special = " + special);
                    ((TextView) serView.findViewById(R.id.tv_zhen)).setText(special);
                }
                String referenceSignalPower = xmlUtils.Parser(xml, XmlUtils.SHOW_NAME, "referenceSignalPower").trim();
                if (!TextUtils.isEmpty(referenceSignalPower)) {//RS
                    String refer = referenceSignalPower.substring(referenceSignalPower.indexOf(":") + 1, referenceSignalPower.length());
                    LogUtils.I(TAG, "referenceSignalPower = " + referenceSignalPower + ", refer = " + refer);
                    ((TextView) serView.findViewById(R.id.tv_rs)).setText(refer);
                }
            }
        }

        //小区列表信息
        if (packet.log_code == 0xB193) {//LOG_LTE_ML1_Idle_Serving_Cell_Meas_Response
            log_packet_field earfcn = packet.get_field("SubPacket E-ARFCN");
            log_packet_field pci = packet.get_field("SubPacket PCI");
            log_packet_field rsrp = packet.get_field("Inst RSRP");
            log_packet_field rsrq = packet.get_field("Inst RSRQ");
            VillageBean bean = new VillageBean(VillageBean.LOCAL_CILLAGE, BandUtils.getBand(earfcn.value_int),
                    pci.value_int, earfcn.value_int, rsrp.value_double, rsrq.value_double, System.currentTimeMillis());
            LogUtils.I(TAG, "pci = " + bean.getPci() + ",earfcn = " + bean.getEarfcn() + ",rsrp = " +
                    bean.getRsrp() + ",rsrq = " + bean.getRsrq());
            ((TextView) serView.findViewById(R.id.tv_band)).setText(BandUtils.getBand(earfcn.value_int) + "");
            ((TextView) serView.findViewById(R.id.tv_earfcn)).setText(earfcn.value_int + "");
            ((TextView) serView.findViewById(R.id.tv_pci)).setText(pci.value_int + "");
            int rsrq_pr = (int) Math.round(((30 + rsrq.value_double) / 60) * 100);
            ((ProgressView) serView.findViewById(R.id.pb_rsrp)).setProgressBar((int) (140 + rsrp.value_double), (int) rsrp.value_double + "dBm");
            ((ProgressView) serView.findViewById(R.id.pb_rsrq)).setProgressBar(rsrq_pr, (int) rsrq.value_double + "dB");
            //本小区列表信息
            if (villageBeans.size() > 0) {
                adapter.updateItem(0, bean);
            } else {
                villageBeans.add(bean);
                adapter.notifyDataSetChanged();
            }
        }
        //2.邻区相关信息：连接态同频邻区信息在B195；连接态异频邻区信息在B192；idle态同频、异频邻区信息均在B192
        if (packet.log_code == 0xB192) {//LOG_LTE_ML1_Idle_Neighbor_Cell_Meas_Request_Response
            log_packet_field earfcn = packet.get_field("SubPacket E-ARFCN");
            log_packet_field pci = packet.get_field("SubPacket PCI");
            log_packet_field rsrp = packet.get_field("Inst RSRP");
            log_packet_field rsrq = packet.get_field("Inst RSRQ");

            if (earfcn != null && pci != null && rsrp != null && rsrq != null) {
                LogUtils.I(TAG, "pci = " + pci.value_int + ",earfcn = " + earfcn.value_int + ",rsrp = " +
                        rsrp.value_double + ",rsrq = " + rsrq.value_double);
                VillageBean bean = new VillageBean(VillageBean.NEAR_CILLAGE, BandUtils.getBand(earfcn.value_int),
                        pci.value_int, earfcn.value_int, rsrp.value_double, rsrq.value_double, System.currentTimeMillis());
                if (update(bean)) {//存在数据，只需刷新
                    adapter.updateItem(pos, bean);
                } else {//不存在，添加
                    villageBeans.add(bean);
                    adapter.notifyDataSetChanged();
                }
            }
        }

        if (packet.log_code == 0xB195) {//LOG_LTE_ML1_Connected_Neighbor_Meas_Request_Response
            log_packet_field earfcn = packet.get_field("SubPacket E-ARFCN");
            log_packet_field pci = packet.get_field("SubPacket PCI");
            log_packet_field rsrp = packet.get_field("Inst RSRP");
            log_packet_field rsrq = packet.get_field("Inst RSRQ");

            if (earfcn != null && pci != null && rsrp != null && rsrq != null) {
                LogUtils.I(TAG, "pci = " + pci.value_int + ",earfcn = " + earfcn.value_int + ",rsrp = " +
                        rsrp.value_double + ",rsrq = " + rsrq.value_double);
                VillageBean bean = new VillageBean(VillageBean.NEAR_CILLAGE, BandUtils.getBand(earfcn.value_int),
                        pci.value_int, earfcn.value_int, rsrp.value_double, rsrq.value_double, System.currentTimeMillis());
                if (update(bean)) {//存在数据，只需刷新
                    adapter.updateItem(pos, bean);
                } else {//不存在，添加
                    villageBeans.add(bean);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    private int pos = 0;

    /**
     * 是否更新列表
     */
    private boolean update(VillageBean villageBean) {
        boolean isExit = false;
        if (villageBeans.size() > 1) {
            for (int i = 1; i < villageBeans.size(); i++) {
                VillageBean bean = villageBeans.get(i);
                if (villageBean.getPci() == bean.getPci() && villageBean.getEarfcn() == bean.getEarfcn()) {
                    isExit = true;
                    pos = i;
                    break;
                } else {
                    isExit = false;
                }
            }
        }
        return isExit;
    }

    //邻区列表的信息5s未更新，就删除该列表的记录
    private void filterData() {
        Iterator<VillageBean> paramIter = villageBeans.iterator();
        if (villageBeans.size() > 0) {
            while (paramIter.hasNext()) {
                VillageBean bean = paramIter.next();
                if (bean.getType() == VillageBean.NEAR_CILLAGE && (System.currentTimeMillis() - bean.getStamp() > 10 * 1000)) {
                    LogUtils.I(TAG, "时间差 = " + (System.currentTimeMillis() - bean.getStamp()));
                    paramIter.remove();
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstUtils.CLEAR_VILLAGE:
                    filterData();
                    mHandler.sendEmptyMessageDelayed(ConstUtils.CLEAR_VILLAGE, ConstUtils.SEC * 15);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
