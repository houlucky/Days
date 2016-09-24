package com.houxy.days.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.houxy.days.R;

/**
 * Created by Houxy on 2016/9/2.
 */
public class LoadMoreRecyclerView extends RecyclerView{

    public int currentStatus = NO_MORE;

    public static final int NO_MORE = 100;
    public static final int LOAD = 101;
    public static final int LOAD_FAIL = 102;

    private AutoLoadAdapter autoLoadAdapter;
    private LoadMoreListener loadMoreListener;

    // 是否正在加载更多，防止多次调用接口
    private boolean isLoadingMore = false;


    public LoadMoreRecyclerView(Context context) {
        super(context);
        init();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 初始化，并且添加滚动监听器
     */
    private void init() {
        super.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);//dy>0 说明正在下拉，dy表示滑动的距离
                if (loadMoreListener != null && !isLoadingMore && currentStatus == LOAD && dy > 0) {
                    if ((getLastVisiblePosition() + 1) == autoLoadAdapter.getItemCount()) {
                        isLoadingMore = true;
                        loadMoreListener.onLoadMore();
                    }
                }
            }
        });
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter != null) {
            autoLoadAdapter = new AutoLoadAdapter(adapter);
        }
        super.setAdapter(autoLoadAdapter);
    }

    private int getLastVisiblePosition() {
        int position;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else if (getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    /**
     * 获得最大的位置
     */
    private int getMaxPosition(int[] positions) {
        int maxPosition = Integer.MIN_VALUE;
        for (int position : positions) {
            maxPosition = Math.max(maxPosition, position);
        }
        return maxPosition;
    }


    class AutoLoadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private RecyclerView.Adapter adapter;

        private final static int TYPE_NORMAL = 0;
        private final static int TYPE_FOOT = 21;

        public AutoLoadAdapter(RecyclerView.Adapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == (getItemCount() - 1)) {
                return TYPE_FOOT;
            } else {
                return adapter.getItemViewType(position);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_FOOT) {
                return new FooterViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.loadmorerecycler_foot, parent, false));
            } else {
                return adapter.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (getItemViewType(position) == TYPE_FOOT) {
                FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
                if (currentStatus == NO_MORE) {
                    footerViewHolder.load.setVisibility(View.GONE);
                    footerViewHolder.loadFail.setVisibility(View.GONE);
                    footerViewHolder.noMore.setVisibility(View.VISIBLE);
                } else if (currentStatus == LOAD) {
                    footerViewHolder.load.setVisibility(View.VISIBLE);
                    footerViewHolder.loadFail.setVisibility(View.GONE);
                    footerViewHolder.noMore.setVisibility(View.GONE);
                } else {
                    footerViewHolder.load.setVisibility(View.GONE);
                    footerViewHolder.loadFail.setVisibility(View.VISIBLE);
                    footerViewHolder.noMore.setVisibility(View.GONE);
                }
            } else {
                adapter.onBindViewHolder(holder, position);
            }
        }

        @Override
        public int getItemCount() {
            return adapter.getItemCount() + 1;
        }

        class FooterViewHolder extends RecyclerView.ViewHolder {

            RelativeLayout load;
            RelativeLayout noMore;
            RelativeLayout loadFail;

            public FooterViewHolder(View itemView) {
                super(itemView);
                load = (RelativeLayout) itemView.findViewById(R.id.foot_load);
                noMore = (RelativeLayout) itemView.findViewById(R.id.foot_no_more);
                loadFail = (RelativeLayout) itemView.findViewById(R.id.foot_load_fail);

                loadFail.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isLoadingMore) {
                            isLoadingMore = true;
                            loadMoreListener.onLoadMore();
                            currentStatus = LOAD;
                            load.setVisibility(View.VISIBLE);
                            loadFail.setVisibility(View.GONE);
                            noMore.setVisibility(View.GONE);
                        }
                    }
                });
            }
        }
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public interface LoadMoreListener {
        void onLoadMore();
    }

    /**
     * 通知recycleView数据改变
     *
     * @param currentPage 当前共有几页数据
     * @param rows 总共有多少条数据
     */
    public void notifyDataChange(int currentPage, int rows) {
        notifyDataChange(isLoadNoMore(currentPage, rows));
    }

    /**
     * 通知recycleView数据改变
     *
     * @param status foot的状态
     */
    public void notifyDataChange(int status) {
        if (autoLoadAdapter != null) {
            if (status != LOAD_FAIL || isLoadingMore) {
                currentStatus = status;
            }

//            autoLoadAdapter.notifyDataSetChanged();
            //以前使用的全局更新会造成图片闪动
            //现在只有最后一个才闪动
            autoLoadAdapter.notifyItemInserted(getLastVisiblePosition() + 1 + 1);
            isLoadingMore = false;
        }
    }

    /**
     * 根据当前页面数据和服务器上的数据判断出foot应该的状态
     */
    public int isLoadNoMore(int currentPage, int rows) {
        // 每页的大小
        int PAGE_SIZE = 10;

        if (currentPage * PAGE_SIZE < rows) {
            return LoadMoreRecyclerView.LOAD;
        }

        return LoadMoreRecyclerView.NO_MORE;
    }
}
