package com.wisec.scanner.ui.test;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wisec.scanner.R;
import com.wisec.scanner.adapter.ItemTestAdapter;
import com.wisec.scanner.bean.FailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PingTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PingTestFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private RecyclerView recyclerView;
    private List<FailBean> failBeans;
    private ItemTestAdapter adapter;

    public PingTestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PingTestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PingTestFragment newInstance(String param1, String param2) {
        PingTestFragment fragment = new PingTestFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_test_ping, container, false);

        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView() {
        failBeans = new ArrayList<>();
        adapter = new ItemTestAdapter(failBeans, getActivity());

        recyclerView = view.findViewById(R.id.rcv_test);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        View serView = LayoutInflater.from(getContext()).inflate(R.layout.layout_test_header, recyclerView, false);
        adapter.setHeaderView(serView);
        recyclerView.setAdapter(adapter);
    }
}
