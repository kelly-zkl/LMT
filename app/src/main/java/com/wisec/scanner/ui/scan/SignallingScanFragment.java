package com.wisec.scanner.ui.scan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wisec.scanner.R;
import com.wisec.scanner.adapter.ItemSignalAdapter;
import com.wisec.scanner.bean.ClearScanner;
import com.wisec.scanner.bean.SignalBean;
import com.wisec.scanner.utils.DateUtils;
import com.wisec.scanner.utils.LogUtils;
import com.wisec.scanner.utils.ParamXmlUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import diag.codec.log_packet;
import diag.codec.log_packet_field;

/**
 * 信令列表
 * Created by qwe on 2018/6/20.
 */

public class SignallingScanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "SignallingScanFragment";

    private View view;
    private RecyclerView recyclerView;
    private List<SignalBean> signalBeans;
    private ItemSignalAdapter adapter;
    private String rntiTypes[] = {"C_RNTI", "SPS_RNTI", "P_RNTI", "RA_RNTI", "TEMP_C_RNTI", "SI_RNTI",
            "TPC_PUSCH_RNTI", "TPC_PUCCH_RNTI", "MBMS_RNTI"};

    public SignallingScanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventScanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignallingScanFragment newInstance(String param1, String param2) {
        SignallingScanFragment fragment = new SignallingScanFragment();
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
        view = inflater.inflate(R.layout.fragment_scan_signalling, container, false);

        initView();
        return view;
    }

    private void initView() {
        signalBeans = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rcv_signal);
        adapter = new ItemSignalAdapter(signalBeans, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(log_packet packet) {
        String log_code = String.format("%04x", packet.log_code);
        if (packet.log_code == 0xB0C0) {//log_lte_rrc_ota
            SignalBean signalBean = new SignalBean();

            log_packet_field sib = packet.get_field("SIB_MASK_IN_SI");
            log_packet_field pum = packet.get_field("PDU_NUM");
            log_packet_field earfcn = packet.get_field("EARFCN");
            log_packet_field pci = packet.get_field("PCI");

            LogUtils.D(TAG, "sib = " + sib.value_int);
            LogUtils.D(TAG, "pum = " + pum.value_int);

            signalBean.setType("RRC");

            System.out.println(packet.decoded_xml);
            String content = ParamXmlUtil.treePacket(packet.decoded_xml);
//            LogUtils.D(TAG, "xml->实体类 = " + content);
            String[] ss = content.split("\n");
            String s = ss[5];
            if (!s.trim().equals("paging")) {
                signalBean.setName(s.trim() + "(" + earfcn.value_int + " " + pci.value_int + ")");

                signalBean.setContent(content);
                signalBean.setTime(DateUtils.getDefaultDate(System.currentTimeMillis()));

                Collections.reverse(signalBeans);//正序
                signalBeans.add(signalBean);
                Collections.reverse(signalBeans);//倒叙显示
                if (!recyclerView.isComputingLayout()) {
                    adapter.notifyDataSetChanged();
                }
            }
        } else if (packet.log_code == 0xB0E2 || packet.log_code == 0xB0E3 ||
                packet.log_code == 0xB0EC || packet.log_code == 0xB0ED) {//nas消息
            SignalBean signalBean = new SignalBean();

            System.out.println(packet.decoded_xml);
            String content = ParamXmlUtil.treePacket(packet.decoded_xml);
            String[] ss = content.split("\n");
            String s = ss[4];
            String title = s.trim().substring(s.trim().indexOf(":") + 1, s.trim().indexOf("("));
//            LogUtils.D(TAG, "xml->实体类 = " + content);

            signalBean.setName(title);
            signalBean.setContent(content);
            signalBean.setTime(DateUtils.getDefaultDate(System.currentTimeMillis()));

            Collections.reverse(signalBeans);//正序
            signalBeans.add(signalBean);
            Collections.reverse(signalBeans);//倒叙显示
            if (!recyclerView.isComputingLayout()) {
                adapter.notifyDataSetChanged();
            }
        } else if (packet.log_code == 0xB167) {//msg1
            getMsg1Data(packet);
        } else if (packet.log_code == 0xB168) {//MSG2
            getMsg2Data(packet);
        } else if (packet.log_code == 0xB169) {//MSG3
            getMsg3Data(packet);
        } else if (packet.log_code == 0xB16A) {//MSG4
            getMsg4Data(packet);
        }
    }

    /**
     * 解析MSG1的信令内容
     *
     * @param packet 信令包
     */
    private void getMsg1Data(log_packet packet) {
        SignalBean signalBean = new SignalBean();
        StringBuilder sb = new StringBuilder();

        log_packet_field ps = packet.get_field("Preamble Sequence");
        log_packet_field pri = packet.get_field("Physical Root Index");
        log_packet_field cs = packet.get_field("Cyclic Shift");
        log_packet_field ptp = packet.get_field("PRACH Tx Power");
        log_packet_field bp = packet.get_field("Beta PRACH");
        log_packet_field pfo = packet.get_field("PRACH Frequency Offset");
        log_packet_field pf = packet.get_field("Preamble Format");
        log_packet_field dm = packet.get_field("Duplex Mode");
        log_packet_field dp = packet.get_field("Density Per 10 ms");
        log_packet_field pts = packet.get_field("PRACH Timing SFN");
        log_packet_field ptsf = packet.get_field("PRACH Timing Sub-fn");
        log_packet_field pwss = packet.get_field("PRACH Window Start SFN");
        log_packet_field pwssf = packet.get_field("PRACH Window Start Sub-fn");
        log_packet_field pwes = packet.get_field("PRACH Window End SFN");
        log_packet_field pwesf = packet.get_field("PRACH Window End Sub-fn");
        log_packet_field rr = packet.get_field("RA RNTI");
        log_packet_field patp = packet.get_field("PRACH Actual Tx Power");

        String dmStr = (dm.value_int == 0 ? "FDD" : "TDD");

        sb.append("\tPreamble Sequence：" + ps.value_int + "\n").append("\tPhysical Root Index：" + pri.value_int + "\n").
                append("\tCyclic Shift：" + cs.value_int + "\n").append("\tPRACH Tx Power：" + ptp.value_int + " dbm\n").
                append("\tBeta PRACH：" + bp.value_int + "\n").append("\tPRACH Frequency Offset：" + pfo.value_int + "\n").
                append("\tPreamble Format：" + pf.value_int + "\n").append("\tDuplex Mode：" + dmStr + "\n").
                append("\tDensity Per 10 ms：" + dp.value_int + "\n").append("\tPRACH Timing SFN：" + pts.value_int + "\n").
                append("\tPRACH Timing Sub-fn：" + ptsf.value_int + "\n").append("\tPRACH Window Start SFN：" + pwss.value_int + "\n").
                append("\tPRACH Window Start Sub-fn：" + pwssf.value_int + "\n").append("\tPRACH Window End SFN：" + pwes.value_int + "\n").
                append("\tPRACH Window End Sub-fn：" + pwesf.value_int + "\n").append("\tRA RNTI：" + rr.value_int + "\n").
                append("\tPRACH Actual Tx Power：" + patp.value_int + " dbm");

        signalBean.setName("MSG1");
        signalBean.setContent(sb.toString());
        signalBean.setTime(DateUtils.getDefaultDate(System.currentTimeMillis()));

        Collections.reverse(signalBeans);//正序
        signalBeans.add(signalBean);
        Collections.reverse(signalBeans);//倒叙显示
        if (!recyclerView.isComputingLayout()) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 解析MSG2的信令内容
     *
     * @param packet 信令包
     */
    private void getMsg2Data(log_packet packet) {
        SignalBean signalBean = new SignalBean();
        StringBuilder sb = new StringBuilder();

        log_packet_field sfn = packet.get_field("SFN");
        log_packet_field sub = packet.get_field("Sub-fn");
        log_packet_field ta = packet.get_field("Timing Advance");
        log_packet_field tai = packet.get_field("Timing Advance Included");
        log_packet_field rpt = packet.get_field("RACH Procedure Type");
        log_packet_field rpm = packet.get_field("RACH Procedure Mode");
        log_packet_field rv = packet.get_field("RNTI Value");
        log_packet_field rt = packet.get_field("RNTI Type");

        String taiStr = (tai.value_int == 0 ? "Not Included" : "Included");
        String rptStr = (rpt.value_int == 0 ? "Contention Free" : "Contention Based");
        String rpmStr = (rpm.value_int == 0 ? "Initial Access" : "Connected Mode RACH Procedure");
        String rtStr = rntiTypes[rt.value_int];

        sb.append("\tSFN：" + sfn.value_int + "\n").append("\tSub-fn：" + sub.value_int + "\n").
                append("\tTiming Advance：" + ta.value_int + "\n").append("\tTiming Advance Included：" + taiStr + "\n").
                append("\tRACH Procedure Type：" + rptStr + "\n").append("\tRACH Procedure Mode：" + rpmStr + "\n").
                append("\tRNTI Type：" + rtStr + "\n").append("\tRNTI Value：" + rv.value_int);

        signalBean.setName("MSG2");
        signalBean.setContent(sb.toString());
        signalBean.setTime(DateUtils.getDefaultDate(System.currentTimeMillis()));

        Collections.reverse(signalBeans);//正序
        signalBeans.add(signalBean);
        Collections.reverse(signalBeans);//倒叙显示
        if (!recyclerView.isComputingLayout()) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 解析MSG3的信令内容
     *
     * @param packet 信令包
     */
    private void getMsg3Data(log_packet packet) {
        SignalBean signalBean = new SignalBean();
        StringBuilder sb = new StringBuilder();

        log_packet_field tpc = packet.get_field("TPC");
        log_packet_field mcs = packet.get_field("MCS");
        log_packet_field riv = packet.get_field("RIV");
        log_packet_field cqi = packet.get_field("CQI");
        log_packet_field ud = packet.get_field("UL Delay");
        log_packet_field hf = packet.get_field("Hopping Flag");
        log_packet_field sfn = packet.get_field("SFN");
        log_packet_field sub = packet.get_field("Sub-fn");
        log_packet_field srb = packet.get_field("Starting Resource Block");
        log_packet_field nrb = packet.get_field("Num Resource Blocks");
        log_packet_field tbsi = packet.get_field("Transport Block Size Index");
        log_packet_field mt = packet.get_field("Modulation Type");
        log_packet_field rvi = packet.get_field("Redundancy Version Index");
        log_packet_field hi = packet.get_field("HARQ ID");

        String cqiStr = (cqi.value_int == 0 ? "Disabled" : "Enabled");
        String udStr = (ud.value_int == 0 ? "Don't Delay" : "Delay UL Transmission");
        String hfStr = (hf.value_int == 0 ? "Disabled" : "Enabled");
        String mtStr = (mt.value_int == 0 ? "BPSK" : mt.value_int == 1 ? "QPSK" : mt.value_int == 2 ? "16 QAM" : "64 QAM");

        sb.append("\tTPC：" + tpc.value_int + "\n").append("\tMCS：" + mcs.value_int + "\n").
                append("\tRIV：" + riv.value_int + "\n").append("\tCQI：" + cqiStr + "\n").
                append("\tUL Delay：" + udStr + "\n").append("\tHopping Flag：" + hfStr + "\n").
                append("\tSFN：" + sfn.value_int + "\n").append("\tSub-fn：" + sub.value_int + "\n").
                append("\tStarting Resource Block：" + srb.value_int + "\n").append("\tNum Resource Blocks：" + nrb.value_int + "\n").
                append("\tTransport Block Size Index：" + tbsi.value_int + "\n").append("\tModulation Type：" + mtStr + "\n").
                append("\tRedundancy Version Index：" + rvi.value_int + "\n").append("\tHARQ ID：" + hi.value_int);

        signalBean.setName("MSG3");
        signalBean.setContent(sb.toString());
        signalBean.setTime(DateUtils.getDefaultDate(System.currentTimeMillis()));

        Collections.reverse(signalBeans);//正序
        signalBeans.add(signalBean);
        Collections.reverse(signalBeans);//倒叙显示
        if (!recyclerView.isComputingLayout()) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 解析MSG4的信令内容
     *
     * @param packet 信令包
     */
    private void getMsg4Data(log_packet packet) {
        SignalBean signalBean = new SignalBean();
        StringBuilder sb = new StringBuilder();

        log_packet_field sfn = packet.get_field("SFN");
        log_packet_field sub = packet.get_field("Sub-fn");
        log_packet_field cr = packet.get_field("Contention Result");
        log_packet_field uatf = packet.get_field("UL ACK Timing SFN");
        log_packet_field uatff = packet.get_field("UL ACK Timing Sub-fn");

        String crStr = (cr.value_int == 0 ? "Fail" : "Pass");

        sb.append("\tSFN：" + sfn.value_int + "\n").append("\tSub-fn：" + sub.value_int + "\n").
                append("\tContention Result：" + crStr + "\n").append("\tUL ACK Timing SFN：" + uatf.value_int + "\n").
                append("\tUL ACK Timing Sub-fn：" + uatff.value_int + "\n");

        signalBean.setName("MSG4");
        signalBean.setContent(sb.toString());
        signalBean.setTime(DateUtils.getDefaultDate(System.currentTimeMillis()));

        Collections.reverse(signalBeans);//正序
        signalBeans.add(signalBean);
        Collections.reverse(signalBeans);//倒叙显示
        if (!recyclerView.isComputingLayout()) {
            adapter.notifyDataSetChanged();
        }
    }


    /**
     * 清空扫频数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ClearScanner clear) {
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
