package com.mm.luna.base;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.library.utils.DensityUtil;
import com.mm.luna.ui.adapter.CommonFragmentAdapter;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ZMM on 2018/2/4.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    public P presenter;
    private Unbinder unbinder;
    public Context mContext;
    public BaseActivity mActivity;
    private CommonFragmentAdapter fragmentAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        presenter = initPresenter();
        initView(rootView);
        return rootView;
    }

    /**
     * 获取布局文件
     *
     * @return
     */
    public abstract int getLayoutId();

    protected abstract P initPresenter();

    protected abstract void initView(View view);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (presenter != null) {
            presenter.detach();
            presenter = null;
        }
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onError() {

    }

    public void createVPFragment( Fragment fragment, ViewPager viewPager, TabLayout tabLayout, List<String> titleList, List<Fragment> fragmentList) {
        viewPager.setAdapter(new CommonFragmentAdapter(fragment.getChildFragmentManager(), fragmentList, titleList));
        for (int i = 0; i < titleList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(titleList.get(i)));
        }
        setWidth(tabLayout, mActivity);  // 通过反射设置tabLayout的宽度
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setWidth(TabLayout tabLayout, BaseActivity mActivity) {
        //  源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(() -> {
            try {
                // 拿到tabLayout的mTabStrip属性
                Field mTabStripField = tabLayout.getClass().getDeclaredField("mTabStrip");
                mTabStripField.setAccessible(true);
                LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabLayout);
                int dp = DensityUtil.dip2px(mActivity, 16);
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

}
