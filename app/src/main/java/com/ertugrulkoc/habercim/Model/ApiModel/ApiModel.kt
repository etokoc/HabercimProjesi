package com.ertugrulkoc.habercim.Model.ApiModel

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

class content {
    var url: String? = null
    var type: String? = null
    var expression: String? = null
    var width = 0
    var height = 0
}

class enclosure {
    var url: String? = null
    var length = 0
    var type: String? = null
}

class item {
    var guid: String? = null
    var pubDate: String? = null
    var title: String? = null
    var description: String? = null
    var content: content? = null
    var enclosure: enclosure? = null
    var link: String? = null
    var author: String? = null
    var encoded: String? = null
}

class Channel {
    var lastBuildDate: String? = null
    var title: String? = null
    var description: String? = null
    var link: String? = null
    var language: String? = null
    var item: List<item>? = null
}
@Root(name = "rss",strict = false)
class Rss {
    var channel:Channel? = null
    var content: String? = null
    var media: String? = null
    var version = 0.0
    var text: String? = null
}

