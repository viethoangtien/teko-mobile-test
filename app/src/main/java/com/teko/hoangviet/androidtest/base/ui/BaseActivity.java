package com.teko.hoangviet.androidtest.base.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.beetech.productmanagement.di.annotation.LayoutId;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.teko.hoangviet.androidtest.common.BaseLoadingDialog;
import com.teko.hoangviet.androidtest.data.network.ApiConstant;
import com.teko.hoangviet.androidtest.data.network.ApiError;
import com.teko.hoangviet.androidtest.data.network.api.ApiException;
import com.teko.hoangviet.androidtest.data.network.api.NetworkConnectionInterceptor;
import com.teko.hoangviet.androidtest.data.network.response.ListResponse;
import com.teko.hoangviet.androidtest.data.network.response.ObjectResponse;
import com.teko.hoangviet.androidtest.utils.Define;
import com.teko.hoangviet.androidtest.custom.ViewController;
import com.teko.hoangviet.androidtest.data.network.response.BaseError;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import retrofit2.HttpException;

public abstract class BaseActivity<T extends ViewDataBinding> extends DaggerAppCompatActivity {

    protected T binding;

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;
    protected ViewController mViewController;
    protected BaseLoadingDialog baseLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getClass().getAnnotation(LayoutId.class).value());
        initProgressDialog();
        mViewController = new ViewController(getSupportFragmentManager(), getFragmentContainerId());
        initViewModel();
        initView();
        initData();
        initListener();
    }

    private void initProgressDialog() {
        baseLoadingDialog = BaseLoadingDialog.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public abstract int getFragmentContainerId();

    @Override
    public void onBackPressed() {
        if (mViewController != null && mViewController.getCurrentFragment() != null) {
            if (mViewController.getCurrentFragment().backPressed()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    public ViewController getViewController() {
        if (mViewController == null) {
            mViewController = new ViewController(getSupportFragmentManager(), getFragmentContainerId());
        }
        return mViewController;
    }

    @Nullable
    public void handleNetworkError(Throwable throwable, boolean isShowToast) {
        ApiError apiError;
        if (throwable instanceof NetworkConnectionInterceptor.NoConnectivityException) {
            apiError = new ApiError(throwable.getMessage());
        } else if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            try {
                apiError = gsonFromJson(
                        httpException.response().errorBody().toString(),
                        ApiError.class
                );
            } catch (JsonParseException jfe) {
                apiError = new ApiError(ApiConstant.HttpMessage.ERROR_TRY_AGAIN);
            } catch (IOException ioe) {
                apiError = new ApiError(ApiConstant.HttpMessage.ERROR_TRY_AGAIN);
            } catch (IllegalStateException ile) {
                apiError = new ApiError(ApiConstant.HttpMessage.ERROR_TRY_AGAIN);
            } catch (Exception e) {
                apiError = new ApiError(ApiConstant.HttpMessage.ERROR_TRY_AGAIN);
            }
        } else if (throwable instanceof ConnectException
                || throwable instanceof SocketTimeoutException
                || throwable instanceof UnknownHostException
                || throwable instanceof IOException) {
            apiError = new ApiError(ApiConstant.HttpMessage.TIME_OUT);
        } else if (throwable instanceof BaseError) {
            apiError = new ApiError(throwable.getMessage(), ((BaseError) throwable).getCode());
        } else {
            apiError = new ApiError(ApiConstant.HttpMessage.ERROR_TRY_AGAIN);
        }
        if (isShowToast) {
            if (apiError != null) {
                Toast.makeText(this, apiError.getApiException().getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            if (apiError != null) {
                ApiException apiException = apiError.getApiException();
                getError(apiException.getMessage(), apiException.getStatusCode());
            }
        }
    }

    private <T> T gsonFromJson(String json, Class<T> classOfT) throws Exception {
        try {
            return new Gson().fromJson(json, classOfT);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    protected void handleListResponse(ListResponse<?> response, boolean isShowToast) {
        switch (response.getType()) {
            case Define.ResponseStatus.LOADING:
                showLoading();
                break;
            case Define.ResponseStatus.SUCCESS:
                getListResponse(response.getData(), response.isRefresh(), response.isLoadingMore());
                hideLoading();
                break;
            case Define.ResponseStatus.ERROR:
                handleNetworkError(response.getError(), isShowToast);
                hideLoading();
        }
    }

    protected <U> void handleObjectResponse(ObjectResponse<U> response, boolean isShowToast) {
        switch (response.getType()) {
            case Define.ResponseStatus.LOADING:
                showLoading();
                break;
            case Define.ResponseStatus.SUCCESS:
                hideLoading();
                getObjectResponse(response.getData());
                break;
            case Define.ResponseStatus.ERROR:
                hideLoading();
                handleNetworkError(response.getError(), isShowToast);
        }
    }

    protected void getListResponse(List<?> data, boolean isRefresh, boolean canLoadMore) {

    }

    protected <U> void getObjectResponse(U data) {

    }

    protected void getError(String error, int code) {

    }

    protected void showLoading() {
        baseLoadingDialog.showLoadingDialog();
    }

    protected void hideLoading() {
        baseLoadingDialog.hideLoadingDialog();
    }

    protected abstract void initViewModel();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();
}
