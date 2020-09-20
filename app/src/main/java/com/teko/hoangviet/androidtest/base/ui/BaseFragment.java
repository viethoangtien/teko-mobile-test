package com.teko.hoangviet.androidtest.base.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.teko.hoangviet.androidtest.custom.view.ViewController;
import com.teko.hoangviet.androidtest.data.network.response.BaseError;
import com.teko.hoangviet.androidtest.data.network.response.ListLoadMoreResponse;
import com.teko.hoangviet.androidtest.data.network.response.ObjectLoadMoreResponse;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import retrofit2.HttpException;

public abstract class BaseFragment<T extends ViewDataBinding> extends DaggerFragment {

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    protected T binding;
    protected BaseLoadingDialog baseLoadingDialog;

    /**
     * The ViewController for control fragments in an activity
     */
    @Nullable
    protected ViewController mViewController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getClass().getAnnotation(LayoutId.class).value(), container, false);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initProgressDialog();
        initViewModel();
        initView();
        initData();
        initListener();
    }

    private void initProgressDialog() {
        baseLoadingDialog = BaseLoadingDialog.getInstance(requireContext());
    }

    public void setViewController(ViewController viewController) {
        this.mViewController = viewController;
    }

    public void setData(HashMap<String, Object> data) {
        if (data == null || data.isEmpty()) {
            setArguments(new Bundle());
            return;
        }
        Bundle bundle = new Bundle();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (entry.getValue() instanceof String) {
                bundle.putString(entry.getKey(), (String) entry.getValue());
            } else if (entry.getValue() instanceof Double) {
                bundle.putDouble(entry.getKey(), ((Double) entry.getValue()));
            } else if (entry.getValue() instanceof Integer) {
                bundle.putInt(entry.getKey(), (Integer) entry.getValue());
            } else if (entry.getValue() instanceof Float) {
                bundle.putFloat(entry.getKey(), ((Float) entry.getValue()));
            } else if (entry.getValue() instanceof Boolean) {
                bundle.putBoolean(entry.getKey(), ((Boolean) entry.getValue()));
            } else if (entry.getValue() instanceof Parcelable) {
                bundle.putParcelable(entry.getKey(), ((Parcelable) entry.getValue()));
            } else if (entry.getValue() instanceof String[]) {
                bundle.putStringArray(entry.getKey(), (String[]) entry.getValue());
            } else if (entry.getValue() instanceof ArrayList) {
                if (((ArrayList) entry.getValue()).size() > 0 && ((ArrayList) entry.getValue()).get(0) instanceof String) {
                    bundle.putStringArrayList(entry.getKey(), (ArrayList<String>) entry.getValue());
                } else if (((ArrayList) entry.getValue()).size() > 0 && ((ArrayList) entry.getValue()).get(0) instanceof Parcelable) {
                    bundle.putParcelableArrayList(entry.getKey(), (ArrayList<? extends Parcelable>) entry.getValue());
                }
            }
        }
        setArguments(bundle);
    }

    protected void handleListResponse(ListResponse<?> response) {
        switch (response.getType()) {
            case Define.ResponseStatus.LOADING:
                showLoading();
                break;
            case Define.ResponseStatus.SUCCESS:
                getListResponse(response.getData(), response.isRefresh(), response.isLoadingMore());
                hideLoading();
                break;
            case Define.ResponseStatus.ERROR:
                handleNetworkError(response.getError());
                hideLoading();
        }
    }

    protected void handleLoadMoreResponse(ObjectLoadMoreResponse<?> response) {
        switch (response.getType()) {
            case Define.ResponseStatus.LOADING:
                showLoading();
                break;
            case Define.ResponseStatus.SUCCESS:
                getObjectResponse(response.getData(), response.isRefresh(), response.isLoadingMore());
                hideLoading();
                break;
            case Define.ResponseStatus.ERROR:
                handleNetworkError(response.getError());
                hideLoading();
        }
    }

    protected <U> void handleObjectResponse(ObjectResponse<U> response) {
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
                handleNetworkError(response.getError());
        }
    }

    protected <U> void handleListLoadMoreResponse(ListLoadMoreResponse<U> response) {
        switch (response.getType()) {
            case Define.ResponseStatus.LOADING:
                showLoading();
                break;
            case Define.ResponseStatus.SUCCESS:
                hideLoading();
                getListResponse(response.getData().getData(), response.isRefresh(), response.isLoadingMore());
                break;
            case Define.ResponseStatus.ERROR:
                hideLoading();
                handleNetworkError(response.getError());
        }
    }

    public ViewController getViewController() {
        if (mViewController == null) {
            return ((BaseActivity) getActivity()).getViewController();
        } else {
            return mViewController;
        }
    }

    protected void getListResponse(List<?> data, boolean isRefresh, boolean isLoadingMore) {

    }

    protected <U> void getObjectResponse(U data) {

    }

    protected <U> void getObjectResponse(U data, boolean isRefresh, boolean isLoadingMore) {

    }

//    protected void handleNetworkError(Throwable throwable, boolean isShowDialog) {
//        if (getActivity() != null && getActivity() instanceof BaseActivity) {
//            ((BaseActivity) getActivity()).handleNetworkError(throwable, isShowDialog);
//        }
//    }

    private <T> T gsonFromJson(String json, Class<T> classOfT) throws Exception {
        try {
            return new Gson().fromJson(json, classOfT);
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Nullable
    public void handleNetworkError(Throwable throwable) {
        ApiError apiError;
        Boolean isShowToast = false;
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
            isShowToast = ((BaseError) throwable).isShowToast();
        } else {
            apiError = new ApiError(ApiConstant.HttpMessage.ERROR_TRY_AGAIN);
        }
        if (isShowToast) {
            if (apiError != null) {
                Toast.makeText(requireContext(), apiError.getApiException().getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            if (apiError != null) {
                ApiException apiException = apiError.getApiException();
                getError(apiException.getMessage(), apiException.getStatusCode());
            }
        }
    }

    protected void getError(String error, int code) {

    }

    protected void showLoading() {
        baseLoadingDialog.showLoadingDialog();
    }

    protected void hideLoading() {
        baseLoadingDialog.hideLoadingDialog();
    }

    public abstract void backFromAddFragment();

    public abstract boolean backPressed();

    public abstract void initView();

    protected abstract void initViewModel();

    public abstract void initData();

    public abstract void initListener();
}
