package com.ertugrulkoc.habercim

data class Root(val totalResults: Int = 0,
                val articles: List<ArticlesItem>?,
                val status: String = "")