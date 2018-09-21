package utilpacket.view;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import utilpacket.adapter.wrapper.HeaderAndFooterWrapper;


/**
 * @author koma
 * @date 2018/1/6
 * @describe
 */

public class JrweidRrcyclerView extends RecyclerView {

    private HeaderAndFooterWrapper wrapper;

    private View defaultFootView;

    private int lastVisibleItem;

    private int firstVisibleItem;

    private LinearLayoutManager layoutManager;

    private OnLoadMoreListener listener;

    private boolean isScroll = false;

    private boolean canLoadMore = false;

    private int scrollCount = 0;

    /**
     * 是否显示加载更多视图
     */
    private boolean isShowFooter = true;



    public JrweidRrcyclerView(Context context) {
        this(context, null);
    }

    public JrweidRrcyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JrweidRrcyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    private void init(){


        this.setLayoutManager(layoutManager);
        this.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                wrapper.showFootView(isShowFooter);
                if(isShowFooter){
                    if(canLoadMore){
                        if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                                lastVisibleItem + 1 == wrapper.getItemCount() && !isScroll) {
                            isScroll = true;
                            if(listener != null){
                                listener.onLoadMore();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        isScroll = false;
                                    }
                                }, 500);

                            }
                        }
                    }
                }else{
                    wrapper.removeFootView();
                }

                if(newState == RecyclerView.SCROLL_STATE_IDLE &&
                        firstVisibleItem  == 0){
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
            }
        });
        if(wrapper != null) {
            this.setAdapter(wrapper);
        }
    }

    /**
     * 设置适配器
     * @param wrapper
     */
    public void setWrapper(HeaderAndFooterWrapper wrapper){
        this.wrapper = wrapper;
        if(defaultFootView != null){
            this.wrapper.addFootView(defaultFootView);
            init();
        }
    }

    /**
     * 设置布局管理器
     * @param layoutManager
     */
    public void setLayout(LinearLayoutManager layoutManager){
        this.layoutManager = layoutManager;
        init();
    }

    /**
     * 设置加载更多视图
     * @param view
     */
    public void setFooterView(View view){
        this.defaultFootView = view;
    }

    /**
     * 是否显示加载更多视图
     * @param isShow
     */
    public void showFooterView(boolean isShow){
        this.isShowFooter = isShow;
        if(defaultFootView != null){
            if(!isShow){
                defaultFootView.setVisibility(View.GONE);
            }else{
                defaultFootView.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setLoadEnable(boolean canLoadMore){
        this.canLoadMore = canLoadMore;
    }


    /**
     * 设置加载更多事件
     * @param loadMoreListener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener){
        this.listener = loadMoreListener;
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }


}

