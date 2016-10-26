package com.betterda.xsnano.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.animation.MeiTuanRefreshFirstStepView;
import com.betterda.xsnano.animation.MeiTuanRefreshSecondStepView;
import com.betterda.xsnano.animation.MeiTuanRefreshThirdStepView;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 自定义的下拉刷新,上啦加载
 *
 * @author Administrator
 */
public class RefreshListView extends ListView implements OnScrollListener {

    private LinearLayout mHeaderView; // 整个头布局对象
    private View mCustomHeaderView; // 添加的自定义头布局

    private int mPullDownHeaderViewHeight; // 下拉头布局的高度


    private static final int DONE = 0;
    private static final int PULL_TO_REFRESH = 1;// 下拉刷新
    private static final int RELEASE_TO_REFRESH = 2; // 释放刷新
    private static final int REFRESHING = 3;// 正在刷新中


    private int state; // 当前下拉头布局的状态,
    private int mListViewYOnScreen = -1; // ListView在屏幕中y轴的值

    private OnRefreshListener mOnRefreshListener; // 下拉刷新和加载更多的回调接口

    private int footerViewHeight; // 脚布局的高度
    private boolean isEnabledPullDownRefresh ; // 是否启用下拉刷新,默认启用


    private TextView tv_refresh_footer;//脚布局的文字


    private MeiTuanRefreshFirstStepView mFirstView;
    private MeiTuanRefreshSecondStepView mSecondView;
    private AnimationDrawable secondAnim;
    private MeiTuanRefreshThirdStepView mThirdView;
    private AnimationDrawable thirdAnim;
    private TextView tv_pull_to_refresh; //下拉刷新文字

    private static final int RATIO = 3;
    private int mFirstVisibleItem;
    private boolean isRecord;
    private float startY;
    private float offsetY;
    private int downY = -1;

    private View footerView;
    private int loadstate;//上拉加载状态
    private boolean isScrollLast;//是否滑动到底部
    private int totalcount;//item总数量
    private static final int LOAD_DONE = 4;//上拉加载完成
    private static final int PULL_TO_LOAD = 5;//上拉中（上拉高度未超出footerview高度）
    private static final int RELEASE_TO_LOAD = 6;//上拉中（上拉高度超出footerview高度）
    private static final int LOADING = 7;//加载中
    private static final int LOAD_ERROR = 8;//加载失败
    private static final int LOAD_EMPTY = 9;//加载成功,但数据为空
    private static final float LOAD_RATIO = 3;//上拉系数
    private float startFY,//手指落点
            offsetFY;//手指滑动的距离
    private boolean isLoadable;//是否启用上拉加载
    private OnLoadMoreListener mOnLoadMoreListener;//加载更多的回调
    private boolean isScrollFirst;
    private onScrollHUIdiaoListener scrollHUIdiaoListener;


    public RefreshListView(Context context) {
        super(context);
        initHeader();
        initFooter();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeader();
        initFooter();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initHeader();
        initFooter();
    }

    /**
     * 初始化脚布局
     */
    private void initFooter() {

        footerView = View.inflate(getContext(), R.layout.refresh_footer_view, null);
        tv_refresh_footer = (TextView) footerView.findViewById(R.id.tv_refresh_footer);
        measureView(footerView);
        addFooterView(footerView);
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, 0, 0, -footerViewHeight);
        //初始化加载状态
        loadstate = LOAD_DONE;
        isLoadable = true;
    }

    /**
     * 初始化下拉刷新头布局
     */
    private void initHeader() {
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setOnScrollListener(this);

        mHeaderView = (LinearLayout) View.inflate(getContext(), R.layout.refresh_header_view, null);

        mFirstView = (MeiTuanRefreshFirstStepView) mHeaderView.findViewById(R.id.first_view);
        tv_pull_to_refresh = (TextView) mHeaderView.findViewById(R.id.tv_pull_to_refresh);
        mSecondView = (MeiTuanRefreshSecondStepView) mHeaderView.findViewById(R.id.second_view);
        mSecondView.setBackgroundResource(R.drawable.pull_to_refresh_second_anim);
        secondAnim = (AnimationDrawable) mSecondView.getBackground();
        mThirdView = (MeiTuanRefreshThirdStepView) mHeaderView.findViewById(R.id.third_view);
        mThirdView.setBackgroundResource(R.drawable.pull_to_refresh_third_anim);
        thirdAnim = (AnimationDrawable) mThirdView.getBackground();

        // 测量下拉刷新头的高度.
        measureView(mHeaderView);
        // 得到下拉刷新头布局的高度
        mPullDownHeaderViewHeight = mHeaderView.getMeasuredHeight();

        // 隐藏头布局
        mHeaderView.setPadding(0, -mPullDownHeaderViewHeight, 0, 0);

        this.addHeaderView(mHeaderView);

        //默认启用下拉刷新
        isEnabledPullDownRefresh = true;


    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            //用户按下
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                startFY = ev.getY();
                //如果当前是在listview顶部并且没有记录y坐标
                if (mFirstVisibleItem == 0 && !isRecord) {
                    //将isRecord置为true，说明现在已记录y坐标
                    isRecord = true;
                    //将当前y坐标赋值给startY起始y坐标
                    startY = ev.getY();
                }
                break;
            //用户滑动
            case MotionEvent.ACTION_MOVE:
                if(downY == -1) {
                    downY = (int) ev.getY();
                }

                offsetFY = ev.getY() - startFY;
                // 如果没有启用下拉刷新功能, 直接跳出switch
                if (!isEnabledPullDownRefresh) {
                    break;
                }



                if (state == REFRESHING) {
                    // 当前正在刷新中, 跳出switch
                    break;
                }

                // 判断添加的轮播图是否完全显示了, 如果没有完全显示,
                // 不执行下面下拉头的代码, 跳转switch语句, 执行父元素的touch事件.
                if (mCustomHeaderView != null) {
                    int[] location = new int[2]; // 0位是x轴的值, 1位是y轴的值

                    if (mListViewYOnScreen == -1) {
                        // 获取Listview在屏幕中y轴的值.
                        this.getLocationOnScreen(location);
                        mListViewYOnScreen = location[1];
//					System.out.println("ListView在屏幕中的y轴的值: " + mListViewYOnScreen);
                    }

                    // 获取mCustomHeaderView在屏幕y轴的值.
                    mCustomHeaderView.getLocationOnScreen(location);
                    int mCustomHeaderViewYOnScreen = location[1];
//				System.out.println("mCustomHeaderView在屏幕中的y轴的值: " + mCustomHeaderViewYOnScreen);

                    if (mListViewYOnScreen > mCustomHeaderViewYOnScreen) {
//					System.out.println("没有完全显示.");
                        break;
                    }
                }
                int moveY = (int) ev.getY();
                // 移动的差值
                int diffY = moveY - downY;
                //下拉刷新
                if (isScrollFirst&&diffY>0&&loadstate!=LOADING) {

                    //再次得到y坐标，用来和startY相减来计算offsetY位移值
                    float tempY = ev.getY();
                    //再起判断一下是否为listview顶部并且没有记录y坐标
                    if (mFirstVisibleItem == 0 && !isRecord) {

                        isRecord = true;
                        startY = tempY;
                    }
                    //如果当前状态不是正在刷新的状态，并且已经记录了y坐标
                    if (state != REFRESHING && isRecord) {
                        //计算y的偏移量
                        offsetY = tempY - startY;
                        //计算当前滑动的高度
                        float currentHeight = (-mPullDownHeaderViewHeight + offsetY / 3);
                        //用当前滑动的高度和头部headerView的总高度进行比 计算出当前滑动的百分比 0到1
                        float currentProgress = 1 + currentHeight / mPullDownHeaderViewHeight;
                        //如果当前百分比大于1了，将其设置为1，目的是让第一个状态的椭圆不再继续变大
                        if (currentProgress >= 1) {
                            currentProgress = 1;
                        }
                        //如果当前的状态是放开刷新，并且已经记录y坐标
                        if (state == RELEASE_TO_REFRESH && isRecord) {
                            	setSelection(0);
                            //如果当前滑动的距离小于headerView的总高度
                            if (-mPullDownHeaderViewHeight + offsetY / RATIO < 0) {
                                //将状态置为下拉刷新状态
                                state = PULL_TO_REFRESH;
                                //根据状态改变headerView，主要是更新动画和文字等信息
                                changeHeaderByState(state);

                            } else if (offsetY <= 0) {
                                //如果当前y的位移值小于0，即为headerView隐藏了,且要第一个可见是这个头布局

                                //将状态变为done
                                state = DONE;
                                //根据状态改变headerView，主要是更新动画和文字等信息
                                changeHeaderByState(state);


                            }
                        }
                        //如果当前状态为下拉刷新并且已经记录y坐标
                        if (state == PULL_TO_REFRESH && isRecord) {
                            setSelection(0);
                            //如果下拉距离大于等于headerView的总高度
                            if (-mPullDownHeaderViewHeight + offsetY / RATIO >= 0) {
                                //将状态变为放开刷新
                                state = RELEASE_TO_REFRESH;
                                //根据状态改变headerView，主要是更新动画和文字等信息
                                changeHeaderByState(state);
                                //如果当前y的位移值小于0，即为headerView隐藏了
                            } else if (offsetY <= 0) {

                                //将状态变为done
                                state = DONE;
                                //根据状态改变headerView，主要是更新动画和文字等信息
                                changeHeaderByState(state);
                            }
                        }
                        //如果当前状态为done并且已经记录y坐标
                        if (state == DONE && isRecord) {
                            //如果位移值大于0
                            if (offsetY >= 0) {
                                //将状态改为下拉刷新状态
                                state = PULL_TO_REFRESH;
                            }
                        }
                        //如果为下拉刷新状态
                        if (state == PULL_TO_REFRESH) {
                            //则改变headerView的padding来实现下拉的效果

                            //防止过度拉伸,且消去拉出来,在拉回去还刷新的bug
                            int paddingTop = (int) (-mPullDownHeaderViewHeight + offsetY / RATIO);
                            if (paddingTop > 0) {
                                paddingTop = 0;
                            }
                            mHeaderView.setPadding(0, paddingTop, 0, 0);
                            //给第一个状态的View设置当前进度值
                            mFirstView.setCurrentProgress(currentProgress);
                            //重画
                            mFirstView.postInvalidate();
                        }
                        //如果为放开刷新状态
                        if (state == RELEASE_TO_REFRESH) {
                            int paddingTop = (int) (-mPullDownHeaderViewHeight + offsetY / RATIO);
                            if (paddingTop > 0) {
                                paddingTop = 0;
                            }
                            //改变headerView的padding值
                            mHeaderView.setPadding(0, paddingTop, 0, 0);
                            //给第一个状态的View设置当前进度值
                            mFirstView.setCurrentProgress(currentProgress);
                            //重画
                            mFirstView.postInvalidate();
                        }
                    }
                }


                /**
                 * 上拉加载更多
                 */
                if (isLoadable&&offsetFY < 0 && state != REFRESHING && isScrollLast && loadstate != LOADING) {

                    float footerViewShowHeight = -offsetFY / LOAD_RATIO;
                    switch (loadstate) {
                        case LOAD_DONE:
                            loadstate = PULL_TO_LOAD;
                            break;
                        case PULL_TO_LOAD:
                            setSelection(totalcount);
                            if (footerViewShowHeight - footerViewHeight >= 0) {
                                loadstate = RELEASE_TO_LOAD;
                                changeFooterByState(loadstate);
                            }
                            break;
                        case RELEASE_TO_LOAD:
                            setSelection(totalcount);
                            if (footerViewShowHeight - footerViewHeight < 0) {
                                loadstate = PULL_TO_LOAD;
                                changeFooterByState(loadstate);
                            }
                            break;
                        case LOAD_EMPTY:
                            loadstate = LOAD_DONE;
                            changeFooterByState(loadstate);
                            break;
                        case LOAD_ERROR :
                            loadstate = LOAD_DONE;
                            changeFooterByState(loadstate);
                            break;
                    }

                    if (loadstate == PULL_TO_LOAD || loadstate == RELEASE_TO_LOAD) {
                        footerView.setPadding(0, 0, 0, (int) (footerViewShowHeight - footerViewHeight));
                    }

                }


                break;
            //当用户手指抬起时
            case MotionEvent.ACTION_UP:

                //下拉刷新
                if(state == PULL_TO_REFRESH) {
                    // 当前状态是下拉刷新状态, 把头布局隐藏.

                    mHeaderView.setPadding(0, -mPullDownHeaderViewHeight, 0, 0);
                    changeHeaderByState(state);
                } else if(state == RELEASE_TO_REFRESH) {

                    // 当前状态是释放刷新, 把头布局完全显示, 并且进入到正在刷新中状态
                    mHeaderView.setPadding(0, 0, 0, 0);
                    state = REFRESHING;
                    changeHeaderByState(state);

                    // 调用用户的回调接口
                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onRefresh();
                    }

                }



                /**
                 * 上拉加载
                 */
                if (isLoadable){
                    if (loadstate == PULL_TO_LOAD) {
                        loadstate = LOAD_DONE;
                        changeFooterByState(loadstate);
                    }
                    if (loadstate == RELEASE_TO_LOAD) {
                        loadstate = LOADING;
                        changeFooterByState(loadstate);
                        if (null != mOnLoadMoreListener) {

                            mOnLoadMoreListener.onLoadMore();
                        }
                    }
                }


                //这一套手势执行完，一定别忘了将记录y坐标的isRecord改为false，以便于下一次手势的执行
                isRecord = false;
                break;
        }


        return super.onTouchEvent(ev);
    }


    /**
     * 加载更多监听
     */
    public interface OnLoadMoreListener {
        void onLoadMore();
    }


    /**
     * 设置加载更多监听
     *
     * @param onLoadMoreListener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    /**
     * 加载更多完成
     * 4表示成功
     * 8失败
     * 9数据为空
     */
    public void setOnLoadMoreComplete(int ltate) {
        loadstate = ltate;
        changeFooterByState(loadstate);
    }

    //设置是否启用上啦加载
    public void IsLoadable(boolean isLoadable) {
        this.isLoadable = isLoadable;
    }

    /**
     * 当数据刷新完成时调用此方法,注意这方法更新了UI
     */
    public void onRefreshFinish() {

            // 当前是下拉刷新完成的操作, 隐藏头布局和复位变量.
            state = DONE;
            changeHeaderByState(state);


    }



    /**
     * 获取当前时间, 格式为: 1990-09-09 09:09:09
     *
     * @return
     */
    private String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * 设置刷新的监听事件
     *
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mOnRefreshListener = listener;
    }

    /**
     * @author andong
     *         刷新回调接口
     */
    public interface OnRefreshListener {

        /**
         * 当下拉刷新时 触发此方法, 实现此方法是抓取数据.
         */
        public void onRefresh();


    }

    /**
     * 当滚动的状态改变时触发此方法.
     * scrollState 当前的状态
     * <p/>
     * SCROLL_STATE_IDLE 停滞
     * SCROLL_STATE_TOUCH_SCROLL 触摸滚动
     * SCROLL_STATE_FLING 惯性滑动(猛的一滑)
     */

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {



    }

    /**
     * 当滚动时触发此方法
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;

        totalcount = totalItemCount;
        if (firstVisibleItem == 0) {
            isScrollFirst = true;//滑动到顶部
        } else {
            isScrollFirst = false;
        }
        if (firstVisibleItem + visibleItemCount == totalItemCount) {
            isScrollLast = true;//滑动到底部
        } else {
            isScrollLast = false;
        }

        if (null != scrollHUIdiaoListener) {
            scrollHUIdiaoListener.scroll(firstVisibleItem);
        }

    }

    public interface onScrollHUIdiaoListener{
        public  void scroll(int firstVisibleItem);
    }

    public void setScrollHUIdiaoListener(onScrollHUIdiaoListener scrollHUIdiaoListener) {
        this.scrollHUIdiaoListener = scrollHUIdiaoListener;
    }

    /**
     * 是否启用下拉刷新的功能
     *
     * @param isEnabled true 启用
     */
    public void isEnabledPullDownRefresh(boolean isEnabled) {
        isEnabledPullDownRefresh = isEnabled;
    }



    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 根据状态改变headerView的动画和文字显示
     *
     * @param state
     */
    private void changeHeaderByState(int state) {
        switch (state) {
            case DONE://如果的隐藏的状态
                tv_pull_to_refresh.setText("下拉刷新");
                //设置headerView的padding为隐藏
                mHeaderView.setPadding(0, -mPullDownHeaderViewHeight, 0, 0);
                //第一状态的view显示出来
                mFirstView.setVisibility(View.VISIBLE);
                //第二状态的view隐藏起来
                mSecondView.setVisibility(View.GONE);
                //停止第二状态的动画
                secondAnim.stop();
                //第三状态的view隐藏起来
                mThirdView.setVisibility(View.GONE);
                //停止第三状态的动画
                thirdAnim.stop();
                break;
            case RELEASE_TO_REFRESH://当前状态为放开刷新
                //文字显示为放开刷新
                tv_pull_to_refresh.setText("放开刷新");
                //第一状态view隐藏起来
                mFirstView.setVisibility(View.GONE);
                //第二状态view显示出来
                mSecondView.setVisibility(View.VISIBLE);
                //播放第二状态的动画
                secondAnim.start();
                //第三状态view隐藏起来
                mThirdView.setVisibility(View.GONE);
                //停止第三状态的动画
                thirdAnim.stop();
                break;
            case PULL_TO_REFRESH://当前状态为下拉刷新
                //设置文字为下拉刷新
                tv_pull_to_refresh.setText("下拉刷新");
                //第一状态view显示出来
                mFirstView.setVisibility(View.VISIBLE);
                //第二状态view隐藏起来
                mSecondView.setVisibility(View.GONE);
                //第二状态动画停止
                secondAnim.stop();
                //第三状态view隐藏起来
                mThirdView.setVisibility(View.GONE);
                //第三状态动画停止
                thirdAnim.stop();
                break;
            case REFRESHING://当前状态为正在刷新
                //文字设置为正在刷新
                tv_pull_to_refresh.setText("正在刷新...");
                //第一状态view隐藏起来
                mFirstView.setVisibility(View.GONE);
                //第三状态view显示出来
                mThirdView.setVisibility(View.VISIBLE);
                //第二状态view隐藏起来
                mSecondView.setVisibility(View.GONE);
                //停止第二状态动画
                secondAnim.stop();
                //启动第三状态view
                thirdAnim.start();
                break;
            default:
                break;
        }
    }


    /**
     * 改变footerview状态
     *
     * @param loadstate
     */
    private void changeFooterByState(int loadstate) {
        switch (loadstate) {
            case LOAD_DONE:
                footerView.setPadding(0, 0, 0, -footerViewHeight);
                tv_refresh_footer.setText("上拉加载更多");
                break;
            case RELEASE_TO_LOAD:
                tv_refresh_footer.setText("松开加载更多");
                break;
            case PULL_TO_LOAD:
                tv_refresh_footer.setText("上拉加载更多");
                break;
            case LOADING:
                tv_refresh_footer.setText("正在加载...");
                footerView.setPadding(0, 0, 0, 0);
                break;
            case LOAD_ERROR:
                tv_refresh_footer.setText("加载失败");
                footerView.setPadding(0, 0, 0, 0);
                break;
            case LOAD_EMPTY:
                tv_refresh_footer.setText("没有更多数据");
                footerView.setPadding(0, 0, 0, 0);
                break;
            default:
                break;
        }
    }

}
