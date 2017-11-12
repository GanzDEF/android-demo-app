package com.test.xyz.daggersample.ui.weather;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.xyz.daggersample.ui.common.di.DaggerApplication;
import com.test.xyz.daggersample.ui.common.BaseFragment;
import com.test.xyz.daggersample.ui.weather.mvp.WeatherPresenter;
import com.test.xyz.daggersample.R;
import com.test.xyz.daggersample.ui.common.util.CommonUtils;
import com.test.xyz.daggersample.ui.weather.di.WeatherFragmentModule;
import com.test.xyz.daggersample.ui.weather.mvp.WeatherView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WeatherFragment extends BaseFragment implements WeatherView, View.OnClickListener {
    private static String TAG = WeatherFragment.class.getName();

    @Inject
    WeatherPresenter presenter;

    @InjectView(R.id.userNameText)
    EditText userNameText;

    @InjectView(R.id.cityText)
    EditText cityText;

    @InjectView(R.id.btnShowInfo)
    Button showInfoButton;

    @InjectView(R.id.resultView)
    TextView resultView;

    @InjectView(R.id.progress)
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    protected void initializeFragment(Bundle savedInstanceState) {
        DaggerApplication.get(this.getContext())
                .getAppComponent()
                .plus(new WeatherFragmentModule(this))
                .inject(this);

        showInfoButton.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnShowInfo) {
            presenter.requestWeatherInformation();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "WeatherFragment is destroyed ...");
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
        CommonUtils.showDefaultAlert(WeatherFragment.this.getActivity(), error);
    }
}