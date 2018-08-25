package com.test.xyz.demo.presentation.projectdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.test.xyz.demo.R
import com.test.xyz.demo.domain.model.github.GitHubRepo
import com.test.xyz.demo.presentation.common.BaseFragment
import com.test.xyz.demo.presentation.common.di.DaggerApplication
import com.test.xyz.demo.presentation.common.util.UIHelper
import com.test.xyz.demo.presentation.mainlobby.MainActivity
import com.test.xyz.demo.presentation.projectdetails.di.RepoDetailsFragmentModule
import com.test.xyz.demo.presentation.projectdetails.presenter.ProjectDetailsPresenter
import com.test.xyz.demo.presentation.projectdetails.presenter.ProjectDetailsView
import javax.inject.Inject

class ProjectDetailsFragment : BaseFragment(), ProjectDetailsView {
    private lateinit var projectTitle: String
    private lateinit var unbinder: Unbinder

    @BindView(R.id.projectDetails) lateinit var repoDetails: TextView
    @BindView(R.id.projectTitle) lateinit var repoTitle: TextView

    @Inject lateinit var presenter: ProjectDetailsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_projectdetails, container, false)
        unbinder = ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadProjectDetails()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) {
            DaggerApplication[this.context]
                    .getAppComponent()
                    .plus(RepoDetailsFragmentModule(this))
                    .inject(this)
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun showProjectDetails(gitHubRepo: GitHubRepo) {
        repoTitle.text = projectTitle
        repoDetails.text = gitHubRepo.description
    }

    override fun showError(errorMessage: Int) {
        UIHelper.showToastMessage(activity, getString(errorMessage))
        repoDetails.setText(R.string.project_details_ret_error)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }

    private fun loadProjectDetails() {
        projectTitle = arguments.getString(UIHelper.Constants.PROJECT_TITLE)
        presenter.requestProjectDetails(UIHelper.Constants.PROJECT_OWNER, projectTitle)
    }

    companion object {

        fun newInstance(projectTitle: String): ProjectDetailsFragment {
            val fragment = ProjectDetailsFragment()
            val args = Bundle()

            args.putString(UIHelper.Constants.PROJECT_TITLE, projectTitle)
            fragment.arguments = args

            return fragment
        }
    }
}
