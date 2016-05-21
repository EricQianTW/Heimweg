package com.hmwg.control.SlidingAdapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hmwg.bean.OrderInfoAPI;
import com.hmwg.eric.R;

/**
 * Created by MJJ on 2015/7/25.
 */
public class SlidingRightAdapter extends RecyclerView.Adapter<SlidingRightAdapter.MyViewHolder> implements SlidingButtonView.IonSlidingButtonListener {

    private Context mContext;

    private IonSlidingViewClickListener mIDeleteBtnClickListener;

    private List<OrderInfoAPI> mDatas = new ArrayList<OrderInfoAPI>();

    private SlidingButtonView mMenu = null;

    public SlidingRightAdapter(Context context,List<OrderInfoAPI> data) {

        mContext = context;
//        mIDeleteBtnClickListener = (IonSlidingViewClickListener) context;
        mDatas.addAll(data);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.tv_danhao.setText(String.valueOf(mDatas.get(position).getId()));
        holder.tv_wangdian.setText(mDatas.get(position).getAddress());
        holder.tv_chejiahao.setText(mDatas.get(position).getCardNo());
        //设置内容布局的宽为屏幕宽度
        holder.layout_content.getLayoutParams().width = Utils.getScreenWidth(mContext);

//        holder.tv_danhao.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //判断是否有删除菜单打开
//                if (menuIsOpen()) {
//                    closeMenu();//关闭菜单
//                } else {
//                    int n = holder.getLayoutPosition();
//                    mIDeleteBtnClickListener.onItemClick(v, n);
//                }
//
//            }
//        });
        holder.btn_Delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = holder.getLayoutPosition();
                mIDeleteBtnClickListener.onDeleteBtnCilck(v, n);
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.searchlist_adp, arg0,false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView btn_Delete;
        public TextView tv_danhao,tv_wangdian,tv_chejiahao;
        public ViewGroup layout_content;
        public MyViewHolder(View itemView) {
            super(itemView);
            btn_Delete = (TextView) itemView.findViewById(R.id.tv_delete);
            tv_danhao = (TextView) itemView.findViewById(R.id.tv_danhao);
            tv_wangdian = (TextView) itemView.findViewById(R.id.tv_wangdian);
            tv_chejiahao = (TextView) itemView.findViewById(R.id.tv_chejiahao);
            layout_content = (ViewGroup) itemView.findViewById(R.id.layout_content);

            ((SlidingButtonView) itemView).setSlidingButtonListener(SlidingRightAdapter.this);
        }
    }

    public void addData(int position,OrderInfoAPI info) {
        mDatas.add(position, info);
        notifyItemInserted(position);
    }

    public void addAll(List<OrderInfoAPI> info) {
        mDatas.clear();
        mDatas.addAll(info);
        notifyDataSetChanged();
    }

    public void removeData(int position){
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    /**
     * 滑动或者点击了Item监听
     * @param slidingButtonView
     */
    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if(menuIsOpen()){
            if(mMenu != slidingButtonView){
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }
    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if(mMenu != null){
            return true;
        }
        Log.i("asd","mMenu为null");
        return false;
    }



    public interface IonSlidingViewClickListener {
        void onItemClick(View view,int position);
        void onDeleteBtnCilck(View view,int position);
    }
}

