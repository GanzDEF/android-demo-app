package com.test.xyz.demo.domain.model.github

import java.util.*

class GitHubRepo {
    var id: Long = 0
    lateinit var name: String
    lateinit var full_name: String
    lateinit var owner: GitHubUser
    lateinit var html_url: String
    var description: String? = null
    lateinit var url: String
    lateinit var created_at: Date
    lateinit var updated_at: Date
    lateinit var pushed_at: Date
    lateinit var git_url: String
    lateinit var ssh_url: String
    lateinit var clone_url: String
    lateinit var svn_url: String
    lateinit var homepage: String
    lateinit var language: String
    var has_issues: Boolean = false
    var has_downloads: Boolean = false
    var has_wiki: Boolean = false
    var has_pages: Boolean = false
    var stargazers_count: Int = 0
    var watchers_count: Int = 0
    var forks_count: Int = 0
    var open_issues_count: Int = 0
    var forks: Int = 0
    var open_issues: Int = 0
    var watchers: Int = 0
    lateinit var default_branch: String

    constructor() {}

    constructor(name: String) {
        this.name = name
    }
}
