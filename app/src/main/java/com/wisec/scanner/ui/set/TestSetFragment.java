package com.wisec.scanner.ui.set;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wisec.scanner.R;
import com.wisec.scanner.ui.testset.AnswerSetActivity;
import com.wisec.scanner.ui.testset.AttachSetActivity;
import com.wisec.scanner.ui.testset.PingSetActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TestSetFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TestSetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestSetFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;

    public TestSetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestSetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestSetFragment newInstance(String param1, String param2) {
        TestSetFragment fragment = new TestSetFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_test_set, container, false);

        initView();
        return view;
    }

    /**
     * 初始化view
     */
    private void initView() {
        view.findViewById(R.id.rl_attach).setOnClickListener(this);
        view.findViewById(R.id.rl_ping).setOnClickListener(this);
        view.findViewById(R.id.rl_answer).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_attach:
                startActivity(new Intent(getActivity(), AttachSetActivity.class));
                break;
            case R.id.rl_ping:
                startActivity(new Intent(getActivity(), PingSetActivity.class));
                break;
            case R.id.rl_answer:
                startActivity(new Intent(getActivity(), AnswerSetActivity.class));
                break;
        }
    }
}
