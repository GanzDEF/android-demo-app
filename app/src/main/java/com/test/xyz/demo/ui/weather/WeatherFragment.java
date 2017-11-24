package com.test.xyz.demo.ui.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.xyz.demo.R;
import com.test.xyz.demo.ui.common.BaseFragment;
import com.test.xyz.demo.ui.common.di.DaggerApplication;
import com.test.xyz.demo.ui.common.util.CommonUtils;
import com.test.xyz.demo.ui.weather.di.WeatherFragmentModule;
import com.test.xyz.demo.ui.weather.vp.WeatherPresenter;
import com.test.xyz.demo.ui.weather.vp.WeatherView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WeatherFragment extends BaseFragment implements WeatherView {
    private Unbinder unbinder;

    @Inject WeatherPresenter presenter;
    @BindView(R.id.userNameText) EditText userNameText;
    @BindView(R.id.cityText) EditText cityText;
    @BindView(R.id.btnShowInfo) Button showInfoButton;
    @BindView(R.id.resultView) TextView resultView;
    @BindView(R.id.progress) ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initializeFragment(Bundle savedInstanceState) {
        DaggerApplication.get(this.getContext())
                .getAppComponent()
                .plus(new WeatherFragmentModule(this))
                .inject(this);

        showInfoButton.setOnClickListener((view) -> presenter.requestWeatherInformation());
    }

    @Override
    public String getUserNameText() {
        return userNameText.getText().toString();
    }

    @Override
    public String getCityText() {
        return cityText.getText().toString();
    }

    @Override
    public void showUserNameError(final int messageId) {
        userNameText.setError(getString(messageId));
    }

    @Override
    public void showCityNameError(final int messageId) {
        cityText.setError(getString(messageId));
    }

    @Override
    public void showBusyIndicator() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBusyIndicator() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showResult(final String result) {
        resultView.setText(result);
    }

    @Override
    public void showError(final String error) {
        CommonUtils.showToastMessage(WeatherFragment.this.getActivity(), error);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}