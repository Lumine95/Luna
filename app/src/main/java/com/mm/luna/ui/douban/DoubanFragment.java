package com.mm.luna.ui.douban;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.library.utils.DensityUtil;
import com.mm.luna.R;
import com.mm.luna.base.BaseFragment;
import com.mm.luna.ui.adapter.CommonFragmentAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.support.design.widget.TabLayout.MODE_FIXED;

/**
 * Created by ZMM on 2018/5/3 11:43.
 */

public class DoubanFragment extends BaseFragment {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager viewPager;
    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private CommonFragmentAdapter fragmentAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_douban;
    }


    @Override
    protected DoubanContract.Presenter initPresenter() {
        return null;
    }


    @Override
    protected void initView(View view) {
        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tab_layout);
        mTitleList.add("正在热映");
        mTitleList.add("即将上映");
        mTitleList.add("TOP250");
        for (int i = 0; i < mTitleList.size(); i++) {
            fragmentList.add(createFragments(i));
        }
        if (fragmentAdapter == null) {
            fragmentAdapter = new CommonFragmentAdapter(mActivity.getSupportFragmentManager(), fragmentList, mTitleList);
        } else {
           fragmentAdapter.setFragments(mActivity.getSupportFragmentManager(), fragmentList);
        }
        viewPager.setAdapter(fragmentAdapter);
        // 设置TabLayout的模式
        tabLayout.setTabMode(MODE_FIXED);
        for (int i = 0; i < mTitleList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(mTitleList.get(i)));
        }
        setWidth();  // 通过反射设置tabLayout的宽度
        tabLayout.setupWithViewPager(viewPager);
//        viewPager.setOffscreenPageLimit(3);
    }

    private MovieFragment createFragments(int position) {
        MovieFragment fragment = new MovieFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void setWidth() {
        //  源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(() -> {
            try {
                // 拿到tabLayout的mTabStrip属性
                Field mTabStripField = tabLayout.getClass().getDeclaredField("mTabStrip");
                mTabStripField.setAccessible(true);
                LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabLayout);
                int dp = DensityUtil.dip2px(mContext, 24);
                for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                    View tabView = mTabStrip.getChildAt(i);

                    // 拿到tabView的mTextView属性
                    Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                    mTextViewField.setAccessible(true);

                    TextView mTextView = (TextView) mTextViewField.get(tabView);

                    tabView.setPadding(0, 0, 0, 0);

                    // 因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                    int width = 0;
                    width = mTextView.getWidth();
                    if (width == 0) {
                        mTextView.measure(0, 0);
                        width = mTextView.getMeasuredWidth();
                    }

                    // 设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                    params.width = width;
                    params.leftMargin = dp;
                    params.rightMargin = dp;
                    tabView.setLayoutParams(params);

                    tabView.invalidate();
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onError() {

    }
}
