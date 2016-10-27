package com.example.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.physicaltests.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 体能测试
 */
public class TestFragment extends Fragment {
    private RecyclerView recycler_view;
    private TestAdapter mAdapter;
    private List<String> mDatas;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        recycler_view = (RecyclerView)view.findViewById(R.id.recycler_view);
        initData();
        recycler_view.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        recycler_view.setAdapter(mAdapter = new TestAdapter());
        return view;
    }

    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();
        return fragment;
    }

    protected void initData()
    {
        mDatas = new ArrayList<String>();
        mDatas.add("体型");
        mDatas.add("俯卧撑");
        mDatas.add("仰卧起坐");
        mDatas.add("10*5往返跑");
        mDatas.add("跑步");
    }

    class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    getActivity()).inflate(R.layout.recyclerview_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView tv;

            public MyViewHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }


}
