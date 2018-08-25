package com.test.xyz.demo.presentation.weather

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.test.xyz.demo.R
import com.test.xyz.demo.presentation.common.BaseFragment
import com.test.xyz.demo.presentation.common.di.DaggerApplication
import com.test.xyz.demo.presentation.common.util.UIHelper
import com.test.xyz.demo.presentation.mainlobby.MainActivity
import com.test.xyz.demo.presentation.weather.di.WeatherFragmentModule
import com.test.xyz.demo.presentation.weather.presenter.WeatherPresenter
import com.test.xyz.demo.presentation.weather.presenter.WeatherView
import javax.inject.Inject

class WeatherFragment : BaseFragment(), WeatherView {
    private lateinit var unbinder: Unbinder

    @BindView(R.id.userNameText) lateinit var userNameTxt: EditText
    @BindView(R.id.cityText) lateinit var cityTxt: EditText
    @BindView(R.id.btnShowInfo) lateinit var showInfoButton: Button
    @BindView(R.id.resultView) lateinit var resultView: TextView
    @BindView(R.id.progress) lateinit var progressBar: ProgressBar
    @BindView(R.id.weatherContainer) lateinit var weatherContainer: LinearLayout

    @Inject lateinit var presenter: WeatherPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        unbinder = ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        weatherContainer.setOnTouchListener { view, motionEvent ->
            UIHelper.hideKeyboard(activity)
            false
        }
        showInfoButton.setOnClickListener { view ->
            UIHelper.hideKeyboard(this.activity)
            presenter.requestWeatherInformation()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) {
            DaggerApplication[this.context]
                    .getAppComponent()
                    .plus(WeatherFragmentModule(this))
                    .inject(this)
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun getUserNameText(): String {
        return userNameTxt.text.toString()
    }

    override fun getCityText(): String {
        return cityTxt.text.toString()
    }

    override fun showUserNameError(messageId: Int) {
        userNameTxt.error = getString(messageId)
    }

    override fun showCityNameError(messageId: Int) {
        cityTxt.error = getString(messageId)
    }

    override fun showBusyIndicator() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideBusyIndicator() {
        progressBar.visibility = View.GONE
    }

    override fun showResult(result: String) {
        resultView.text = result
    }

    override fun showGenericError(messageID: Int) {
        UIHelper.showToastMessage(this.activity, getString(messageID))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }

    companion object {

        fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }
    }
}