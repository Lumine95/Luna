package com.mm.luna.ui.common;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jaeger.library.StatusBarUtil;
import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.ui.computer.ComputerFragment;
import com.mm.luna.ui.douban.DoubanFragment;
import com.mm.luna.ui.douban.MovieSearchActivity;
import com.mm.luna.ui.gank.GankMainFragment;
import com.mm.luna.ui.orange.OrangeFragment;
import com.mm.luna.ui.violet.VioletActivity;
import com.mm.luna.ui.wan.ArticleFragment;
import com.mm.luna.ui.zhihu.ZhiHuContract;
import com.mm.luna.ui.zhihu.ZhiHuFragment;

import butterknife.BindView;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

public class MainActivity extends BaseActivity {

    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    private int which = 0;
    private Fragment mContent;

    private ZhiHuFragment zhiHuFragment;
    private DoubanFragment doubanFragment;
    private GankMainFragment gankFragment;
    private MenuItem itemSearch;
    private ArticleFragment androidFragment;
    private OrangeFragment orangeFragment;
    private ComputerFragment computerFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public ZhiHuContract.Presenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        initRetrofitDomain();
        StatusBarUtil.setColorForDrawerLayout(this, drawer, Color.TRANSPARENT, 0);
        if (zhiHuFragment == null) zhiHuFragment = new ZhiHuFragment();
        switchContentFragment(zhiHuFragment);

        toolbar.setTitle(R.string.zhihu_daily);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawerToggle.syncState();
        drawer.addDrawerListener(drawerToggle);

        navigationView.setCheckedItem(R.id.drawer_zhihu);
        navigationView.getHeaderView(0).setOnClickListener(v -> violet());
        navigationView.setNavigationItemSelectedListener((MenuItem item) -> {
            itemSearch.setVisible(false);
            switch (item.getItemId()) {
                case R.id.drawer_zhihu:
                    toolbar.setTitle(R.string.zhihu_daily);
                    if (zhiHuFragment == null) zhiHuFragment = new ZhiHuFragment();
                    switchContentFragment(zhiHuFragment);
                    break;
                case R.id.drawer_douban:
                    toolbar.setTitle(R.string.douban_movie);
                    itemSearch.setVisible(true);
                    if (doubanFragment == null) doubanFragment = new DoubanFragment();
                    switchContentFragment(doubanFragment);
                    break;
                case R.id.drawer_orange:
                    toolbar.setTitle(R.string.orange_title);
                    if (orangeFragment == null) orangeFragment = new OrangeFragment();
                    switchContentFragment(orangeFragment);
                    break;
                case R.id.drawer_computer:
                    toolbar.setTitle(R.string.computer_fan);
                    if (computerFragment == null) computerFragment = new ComputerFragment();
                    switchContentFragment(computerFragment);
                    break;
                case R.id.drawer_wan_android:
                    toolbar.setTitle(R.string.wan_android);
                    if (androidFragment == null) androidFragment = new ArticleFragment();
                    switchContentFragment(androidFragment);
                    break;
                case R.id.drawer_gank:
                    toolbar.setTitle(R.string.gank_io);
                    if (gankFragment == null) gankFragment = new GankMainFragment();
                    switchContentFragment(gankFragment);
                    break;
            }
            drawer.closeDrawers();
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        itemSearch = menu.findItem(R.id.action_search);
        itemSearch.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(this, MovieSearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void violet() {
        String[] comicArr = {"Violet Evergarden", "Tokyo Ghoul Ⅲ"};
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.please_select)
                .setSingleChoiceItems(comicArr, which, (dialogInterface, i) -> which = i)
                .setPositiveButton(R.string.sure, (dialogInterface, i) -> {
                    startActivity(new Intent(MainActivity.this, VioletActivity.class).putExtra("tag", which));
                    dialogInterface.dismiss();
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.dismiss())
                .create().show();
    }

    /**
     * Switch fragment.
     *
     * @param to
     */
    private void switchContentFragment(Fragment to) {
        if (mContent == null) {
            mContent = new Fragment();
        }
        if (mContent != to) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {
                transaction.hide(mContent).add(R.id.fl_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mContent).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            mContent = to;
        }
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onError() {

    }

    private void initRetrofitDomain() {
        RetrofitUrlManager.getInstance().putDomain("zhihu", "https://news-at.zhihu.com/");
        RetrofitUrlManager.getInstance().putDomain("douban", "https://api.douban.com/");
        RetrofitUrlManager.getInstance().putDomain("gank", "http://gank.io/api/");
        RetrofitUrlManager.getInstance().putDomain("wan", "http://www.wanandroid.com/");
        RetrofitUrlManager.getInstance().putDomain("orange", "https://www.juzimi.com/");
        RetrofitUrlManager.getInstance().putDomain("cfan", "http://www.cfan.com.cn/");
    }
}