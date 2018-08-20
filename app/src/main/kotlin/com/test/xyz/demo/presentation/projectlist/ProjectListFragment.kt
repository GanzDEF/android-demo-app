package com.test.xyz.demo.presentation.projectlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
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
import com.test.xyz.demo.presentation.projectlist.di.ProjectListFragmentModule
import com.test.xyz.demo.presentation.projectlist.presenter.ProjectListPresenter
import com.test.xyz.demo.presentation.projectlist.presenter.ProjectListView
import java.util.*
import javax.inject.Inject

class ProjectListFragment : BaseFragment(), ProjectListView {
    private lateinit var unbinder: Unbinder;
    private var projectList: List<GitHubRepo> = emptyList()

    @BindView(R.id.projectList) lateinit var repoListView: ListView
    @BindView(R.id.noAvlProjects) lateinit var noAvlRepos: TextView

    @Inject lateinit var presenter: ProjectListPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_projectlist, container, false)
        unbinder = ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadProjectList()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) {
            DaggerApplication[this.context]
                    .getAppComponent()!!
                    .plus(ProjectListFragmentModule(this))
                    .inject(this)
        }
    }

    override fun showProjectList(projectList: List<GitHubRepo>) {
        this.projectList = projectList
        displayResults(projectList)
    }

    override fun showError(errorMessage: Int) {
        UIHelper.showToastMessage(this@ProjectListFragment.activity, getString(errorMessage))
        displayResults(object : ArrayList<GitHubRepo>() {

        })
    }

    override fun onStop() {
        super.onStop()
        presenter!!.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
    }

    private fun loadProjectList() {
        if (this.projectList.isEmpty()) {
            presenter!!.requestProjectList(UIHelper.Constants.PROJECT_OWNER)
            return
        }

        showProjectList(this.projectList)
    }

    private fun displayResults(gitHubRepos: List<GitHubRepo>) {
        val values = mapRepoList(gitHubRepos)

        val adapter = ArrayAdapter(this@ProjectListFragment.activity,
                android.R.layout.simple_list_item_1, android.R.id.text1, values)

        repoListView!!.adapter = adapter
        repoListView!!.emptyView = noAvlRepos

        repoListView!!.setOnItemClickListener { parent, view, position, id ->
            (activity as MainActivity).loadProjectDetailsFragment(values[position])
        }
    }

    companion object {

        fun newInstance(): ProjectListFragment {
            return ProjectListFragment()
        }
    }
}
