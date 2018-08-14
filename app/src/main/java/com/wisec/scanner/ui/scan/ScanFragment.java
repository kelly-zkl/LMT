package com.wisec.scanner.ui.scan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wisec.scanner.R;
import com.wisec.scanner.bean.ClearScanner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import diag.codec.log_packet;
import diag.codec.log_packet_field;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ScanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanFragment extends Fragment implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private List<Fragment> fragments;
    private TextView mTvEarfcn, mTvPci;

    public ScanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScanFragment newInstance(String param1, String param2) {
        ScanFragment fragment = new ScanFragment();
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
        view = inflater.inflate(R.layout.fragment_main_scan, container, false);

        initView();
        return view;
    }

    private void initView() {
        VillageScanFragment villageScanFragment = VillageScanFragment.newInstance("1", "2");
        ParamScanFragment paramScanFragment = ParamScanFragment.newInstance("1", "2");
        SignallingScanFragment signallingScanFragment = SignallingScanFragment.newInstance("1", "2");
        EventScanFragment eventScanFragment = EventScanFragment.newInstance("1", "2");
        fragments = new ArrayList<>();
        fragments.add(villageScanFragment);
        fragments.add(paramScanFragment);
        fragments.add(signallingScanFragment);
        fragments.add(eventScanFragment);
        viewPager = view.findViewById(R.id.vp_scan);
        radioGroup = view.findViewById(R.id.rg_scan);

        mTvEarfcn = view.findViewById(R.id.tv_earfcn);
        mTvPci = view.findViewById(R.id.tv_pci);

        initViewPager();
    }

    private void initViewPager() {
        ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Fragment fragment = (Fragment) super.instantiateItem(container, position);
                fragments.set(position, fragment);
                return fragment;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
            }
        };
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0, true);
        viewPager.setOnPageChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbtn_village://扫频
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.rbtn_param://重选参数
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.rbtn_signalling://信令
                viewPager.setCurrentItem(2, true);
                break;
            case R.id.rbtn_event://事件
                viewPager.setCurrentItem(3, true);
                break;
        }
    }

    /**
     * 服务小区信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(log_packet packet) {
        //服务小区信息
        if (packet.log_code == 0xB193) {//LOG_LTE_RRC_MIB 服务小区bandWidth(带宽)、pci、earfcn
            log_packet_field earfcn = packet.get_field("SubPacket E-ARFCN");
            log_packet_field pci = packet.get_field("SubPacket PCI");
            mTvEarfcn.setText(earfcn.value_int + "");
            mTvPci.setText(pci.value_int + "");
        }
        //服务小区信息
        if (packet.log_code == 0xB0C1) {//LOG_LTE_RRC_MIB 服务小区bandWidth(带宽)、pci、earfcn
            log_packet_field earfcn = packet.get_field("EARFCN");
            log_packet_field pci = packet.get_field("PCI");
            mTvEarfcn.setText(earfcn.value_int + "");
            mTvPci.setText(pci.value_int + "");
        }
    }

    /**
     * 清空扫频数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ClearScanner clear) {
        mTvEarfcn.setText("--");
        mTvPci.setText("--");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
