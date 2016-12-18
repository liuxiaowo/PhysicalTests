package com.example.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.physicaltests.R;
import com.example.test.activity.PushUpActivity;
import com.example.test.activity.RunningActivity;
import com.example.test.activity.ShapeActivity;
import com.example.test.activity.ShuttleRunActivity;
import com.example.test.activity.SitUpActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 体能测试
 */
public class TestFragment extends Fragment implements MyItemClickListener {
    private RecyclerView recycler_view;
    private TestAdapter mAdapter;
    private List<String> mDatas;
    private List<Integer> mColors;
    private int []mImgs = {R.drawable.shape,R.drawable.push_up,R.drawable.sit_up,R.drawable.shuttle_run,R.drawable.running};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        recycler_view = (RecyclerView)view.findViewById(R.id.recycler_view);
        initData();
        recycler_view.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recycler_view.setAdapter(mAdapter = new TestAdapter());
        mAdapter.setOnItemClickListener(this);
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
        mColors.add(R.color.colorAccent);
        mColors.add(R.color.blue);
        mColors.add(R.color.purple);
        mColors.add(R.color.green);
        mColors.add(R.color.orange);
    }

    @Override
    public void onItemClick(View view, int postion) {
        switch (postion){
            case 0:
                //go to 体型测试页面
                Intent intent = new Intent(getActivity(), ShapeActivity.class);
                startActivity(intent);
                break;
            case 1:
                //go to 俯卧撑测试页面
                Intent intent2 = new Intent(getActivity(), PushUpActivity.class);
                startActivity(intent2);
                break;
            case 2:
                //go to 仰卧起坐测试页面
                Intent intent3 = new Intent(getActivity(), SitUpActivity.class);
                startActivity(intent3);
                break;
            case 4:
                //go to 10*5往返跑测试页面
                Intent intent4 = new Intent(getActivity(), ShuttleRunActivity.class);
                startActivity(intent4);
                break;
            case 5:
                //go to 跑步测试页面
                Intent intent5 = new Intent(getActivity(), RunningActivity.class);
                startActivity(intent5);
                break;
        }
    }

    class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder>
    {
        private MyItemClickListener mListener;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(
                    getActivity()).inflate(R.layout.recyclerview_item, parent,
                    false);
            MyViewHolder holder = new MyViewHolder(v,mListener);
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
            holder.img.setBackgroundResource(mImgs[position]);
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        /**
         * 设置Item点击监听
         * @param listener
         */
        public void setOnItemClickListener(MyItemClickListener listener){
            this.mListener = listener;
        }


        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {

            private TextView tv;
            private RelativeLayout list_item;
            private ImageView img;
            private MyItemClickListener mListener;

            public MyViewHolder(View view,MyItemClickListener listener)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
                list_item = (RelativeLayout) view.findViewById(R.id.list_item);
                img = (ImageView)view.findViewById(R.id.image_item);
                this.mListener = listener;
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onItemClick(v,getPosition());
                }
            }
        }
    }

}
//为RecyclerView添加onItemClick事件
interface MyItemClickListener {
    void onItemClick(View view,int postion);
}
