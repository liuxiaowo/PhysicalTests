package com.example.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
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
    private List<Integer> mColors;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        recycler_view = (RecyclerView)view.findViewById(R.id.recycler_view);
        initData();
        recycler_view.setLayoutManager(new GridLayoutManager(getActivity(),3));
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
        mColors = new ArrayList<Integer>();
        mColors.add(R.color.blue);
        mColors.add(R.color.brown);
        mColors.add(R.color.orange);
        mColors.add(R.color.btn_color);
        mColors.add(R.color.colorAccent);
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
            holder.list_item.setBackgroundResource(mColors.get(position));
            WindowManager wm = (WindowManager) getContext()
                    .getSystemService(getActivity().WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            int item_width= (width-30*6)/3;
            holder.list_item.setMinimumHeight(item_width);
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView tv;
            RelativeLayout list_item;

            public MyViewHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
                list_item = (RelativeLayout) view.findViewById(R.id.list_item);


            }
        }
    }


}
