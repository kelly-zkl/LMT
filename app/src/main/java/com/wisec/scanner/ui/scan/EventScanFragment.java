package com.wisec.scanner.ui.scan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wisec.scanner.R;
import com.wisec.scanner.adapter.ItemEventAdapter;
import com.wisec.scanner.bean.ClearScanner;
import com.wisec.scanner.bean.EventBean;
import com.wisec.scanner.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventScanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventScanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "EventScanFragment";

    private View view;
    private RecyclerView recyclerView;
    private List<EventBean> eventBeans;
    private ItemEventAdapter adapter;

    public EventScanFragment() {
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
    public static EventScanFragment newInstance(String param1, String param2) {
        EventScanFragment fragment = new EventScanFragment();
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
        view = inflater.inflate(R.layout.fragment_scan_event, container, false);

        initView();
        return view;
    }

    private void initView() {
        eventBeans = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rcv_event);
        adapter = new ItemEventAdapter(eventBeans, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBean eventBean) {
        eventBean.setTime(DateUtils.getDefaultDate(System.currentTimeMillis()));
        Collections.reverse(eventBeans);
        eventBeans.add(eventBean);
        Collections.reverse(eventBeans);//倒叙显示
        adapter.notifyDataSetChanged();
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
