package com.mm.luna.ui.common;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jaeger.library.StatusBarUtil;
import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.ui.violet.VioletActivity;
import com.mm.luna.ui.zhihu.ZhiHuContract;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    private int which = 0;
    private ActionBarDrawerToggle drawerToggle;

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
        StatusBarUtil.setColorForDrawerLayout(this, drawer, Color.TRANSPARENT, 0);
        toolbar.setTitle(R.string.zhihu_daily);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);
        toolbar.setNavigationOnClickListener(v -> startActivity(new Intent(this, VioletActivity.class)));
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawerToggle.syncState();
        drawer.addDrawerListener(drawerToggle);
        MenuItem itemZhihu = navigationView.getMenu().findItem(R.id.drawer_zhihu);
        MenuItem itemComics = navigationView.getMenu().findItem(R.id.drawer_commics);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.drawer_zhihu:
                    itemZhihu.setChecked(true);
                    itemComics.setChecked(false);
                    drawer.closeDrawers();
                    break;
                case R.id.drawer_commics:
                    itemZhihu.setChecked(false);
                    itemComics.setChecked(true);
                    String[] comicArr = {"Violet Evergarden", "Tokyo Ghoul Ⅲ"};
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("请选择")
                            .setSingleChoiceItems(comicArr, which, (dialogInterface, i) -> which = i)
                            .setPositiveButton("确定", (dialogInterface, i) -> {
                                startActivity(new Intent(MainActivity.this, VioletActivity.class).putExtra("tag", which));
                                dialogInterface.dismiss();
                            })
                            .setNegativeButton("取消", (dialogInterface, i) -> dialogInterface.dismiss())
                            .create().show();
                    drawer.closeDrawers();
                    break;
            }
            return true;
        });
    }
}
