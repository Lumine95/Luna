package com.mm.luna.ui.orange;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.mm.luna.R;
import com.mm.luna.base.BaseFragment;
import com.mm.luna.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ZMM on 2018/8/16 15:31.
 */
public class OrangeFragment extends BaseFragment {

    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.pager) ViewPager viewPager;

    private List<String> titleList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_douban;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }


    @Override
    protected void initView(View view) {
        titleList.add("美图美句");
        titleList.add("手写美句");
        titleList.add("影视对白");


        for (int i = 0; i < titleList.size(); i++) {
            fragmentList.add(createFragments(i));
        }
        createVPFragment(this, viewPager, tabLayout, titleList, fragmentList);
    }

    private Fragment createFragments(int type) {
        SentenceFragment fragment = new SentenceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

}
