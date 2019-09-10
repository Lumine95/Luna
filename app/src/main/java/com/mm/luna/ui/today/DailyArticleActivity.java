package com.mm.luna.ui.today;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mm.luna.R;
import com.mm.luna.base.BaseActivity;
import com.mm.luna.bean.FictionBean;
import com.mm.luna.view.statusLayoutView.StatusLayoutManager;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by ZMM on 2019/2/28 11:29.
 */
public class DailyArticleActivity extends BaseActivity<ArticleContract.Presenter> implements ArticleContract.View {
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_author) TextView tvAuthor;
    @BindView(R.id.tv_word_count) TextView tvWordCount;
    @BindView(R.id.iv_top) ImageView ivTop;
    @BindView(R.id.scroll_view) ScrollView scrollView;
    @BindView(R.id.fab_date) FloatingActionButton fabDate;
    @BindView(R.id.tv_article) TextView tvArticle;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private StatusLayoutManager statusLayoutManager;
    private String date;
    private int mYear = Calendar.getInstance().get(Calendar.YEAR);
    private int mMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    @Override
    public int getLayoutId() {
        return R.layout.activity_daily_article;
    }

    @Override
    public ArticlePresenter initPresenter() {
        return new ArticlePresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        menu.findItem(R.id.action_random).setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_random:
                statusLayoutManager.showLoadingLayout();
                presenter.getRandomArticle();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showArticle(FictionBean bean) {
        date = bean.getData().getDate().getCurr();
        tvTitle.setText(bean.getData().getTitle());
        tvAuthor.setText(bean.getData().getAuthor());
        tvWordCount.setText(String.format("%s%s%s", getString(R.string.article_end), bean.getData().getWc(), getString(R.string.word)));
        // tvArticle.setText(Html.fromHtml(bean.getData().getContent()));
        // 使用JSoup添加首行缩进
        Document document = Jsoup.parse(bean.getData().getContent());
        Elements elements = document.getElementsByTag("p");
        for (Element e : elements) {
            e.prepend("&emsp;&emsp;");
        }
        tvArticle.setText(Html.fromHtml(document.outerHtml()));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        setStatusBarColor();
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        // date = DateUtil.getCurrentStrDate("yyyyMMdd");
        presenter.getArticle(date);
        statusLayoutManager = new StatusLayoutManager.Builder(scrollView)
                .setOnStatusChildClickListener(v -> {
                    presenter.getArticle(date);
                }).build();
        statusLayoutManager.showLoadingLayout();
        fabDate.setOnClickListener(v -> selectData());
        scrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            fabDate.setVisibility(scrollY > 50 ? View.GONE : View.VISIBLE);
        });
        ivTop.setOnClickListener(v -> scrollView.smoothScrollTo(0, 0));
    }

    private void selectData() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay);
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance((view, year, monthOfYear, dayOfMonth) -> {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            Calendar temp = Calendar.getInstance();
            temp.clear();
            temp.set(year, monthOfYear, dayOfMonth);
            @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("yyyyMMdd").format(new Date(temp.getTimeInMillis()));
            statusLayoutManager.showLoadingLayout();
            presenter.getArticle(date);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        // 设置日历可选区间
        datePickerDialog.setMaxDate(Calendar.getInstance());
        Calendar minDate = Calendar.getInstance();
        minDate.set(2011, 3, 6);
        datePickerDialog.setMinDate(minDate);
        datePickerDialog.vibrate(false);
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }

    @Override
    public void onFinish() {
        fabDate.setVisibility(View.VISIBLE);
        statusLayoutManager.showSuccessLayout();
        scrollView.smoothScrollTo(0, 0);
    }

    @Override
    public void onError() {
        statusLayoutManager.showErrorLayout();
        fabDate.setVisibility(View.GONE);
    }
}
