package com.victor.nesthabit.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victor.nesthabit.R;
import com.victor.nesthabit.adapters.BirdCaseFragAdapter;
import com.victor.nesthabit.data.BirdCageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BirdCageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BirdCageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BirdCageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Activity mActivity;

    private RecyclerView mRecyclerView;

    private List<BirdCageInfo> mBirdCageInfos = new ArrayList<>();

    private BirdCaseFragAdapter mBirdCaseFragAdapter;
    private View rootview;




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BirdCageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BirdCageFragment newInstance(String param1, String param2) {
        BirdCageFragment fragment = new BirdCageFragment();
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
        mActivity = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootview == null) {
            rootview = mActivity.getLayoutInflater().inflate(R.layout.fragment_bird_cage, null);
        } else {
            ViewGroup parent = (ViewGroup) rootview.getParent();
            if (parent != null) {
                parent.removeView(rootview);
            }
        }
        initView();
        initData();

        // Inflate the layout for this fragment
        return rootview;
    }


    private void initView() {
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.birdcage_recyclerview);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            BirdCageInfo info = new BirdCageInfo();
            info.setDay_insist(7);
            info.setDay_total(100);
            info.setInfo("早起的鸟儿有虫吃");
            info.setPeople(15);
            mBirdCageInfos.add(info);
        }

        mBirdCaseFragAdapter = new BirdCaseFragAdapter(mActivity, mRecyclerView, mBirdCageInfos);
        mRecyclerView.setAdapter(mBirdCaseFragAdapter);
        mBirdCaseFragAdapter.notifyDataSetChanged();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
