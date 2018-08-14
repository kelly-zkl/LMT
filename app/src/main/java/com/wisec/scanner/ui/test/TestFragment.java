package com.wisec.scanner.ui.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wisec.scanner.R;
import com.wisec.scanner.ui.scan.EventScanFragment;
import com.wisec.scanner.ui.scan.ParamScanFragment;
import com.wisec.scanner.ui.scan.ScanFragment;
import com.wisec.scanner.ui.scan.VillageScanFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestFragment extends Fragment implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
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


    public TestFragment() {
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
    public static TestFragment newInstance(String param1, String param2) {
        TestFragment fragment = new TestFragment();
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
        view = inflater.inflate(R.layout.fragment_main_test, container, false);

        initView();
        return view;
    }

    private void initView() {
        AttachTestFragment attachTestFragment = AttachTestFragment.newInstance("1", "2");
        PingTestFragment pingTestFragment = PingTestFragment.newInstance("1", "2");
        AnswerTestFragment answerTestFragment = AnswerTestFragment.newInstance("1", "2");
        fragments = new ArrayList<>();
        fragments.add(attachTestFragment);
        fragments.add(pingTestFragment);
        fragments.add(answerTestFragment);
        viewPager = view.findViewById(R.id.vp_test);
        radioGroup = view.findViewById(R.id.rg_test);

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
        viewPager.setOffscreenPageLimit(3);
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
            case R.id.rbtn_attach:
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.rbtn_ping:
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.rbtn_answer:
                viewPager.setCurrentItem(2, true);
                break;
        }
    }
}
