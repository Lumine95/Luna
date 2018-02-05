package com.mm.luna.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        unbinder = ButterKnife.bind(rootView);
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
    public void ShowLoadingDialog(String msg) {

    }

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (presenter != null) {
            presenter.detach();
            presenter = null;
        }
    }
}
