package com.ertugrulkoc.habercim.View

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ertugrulkoc.habercim.Model.ApiModel.NewsItem
import com.ertugrulkoc.habercim.RecylerView.MyRecylerViewAdapter
import com.ertugrulkoc.habercim.databinding.ActivityMainBinding
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

//HABERLER.COM
//https://rss.haberler.com/rss.asp?kategori=sondakika

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val BASE_URL = "https://rss.haberler.com/"
    val url = BASE_URL + "rss.asp?kategori=sondakika"
    lateinit var queue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        queue = Volley.newRequestQueue(this)
        createXmlRequest()//Request yapma methodu.
    }

    private fun createXmlRequest() {
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            object : com.android.volley.Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    if (response != null) {
                        parseXml(response)
                    }
                }
            }, object : com.android.volley.Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Log.i("cevap", "onErrorResponse: " + error!!.localizedMessage)
                }
            })

        queue.add(stringRequest)
    }

    private fun parseXml(response: String) {
        val docBuildFactory = DocumentBuilderFactory.newInstance()
        val docBuilder = docBuildFactory.newDocumentBuilder()
        val doc = docBuilder.parse(InputSource(StringReader(response)))
        val nList: NodeList = doc.getElementsByTagName("item")
        var adet = 0
        var newsList = ArrayList<NewsItem>()
        for (i in 0..nList.length) {
            if (nList.item(0).nodeType == Node.ELEMENT_NODE) {
                val element = nList.item(i) as Element?
                if (element != null) {
                    Log.i("cevap", "" + getNodeValue("title", element))
                    Log.i("cevap", "" + getNodeValue("description", element))
                    Log.i("cevap", "" + getNodeValue("link", element))
                    Log.i("cevap", "" + getNodeValue("media:content", element))
                    Log.i("cevap", "------------------------------- ")
                    adet = i
                    val mNews = NewsItem()
                    mNews.title = getNodeValue("title", element) + ""
                    mNews.description = getNodeValue("description", element) + ""
                    mNews.link = getNodeValue("link", element) + ""
                    mNews.guid = getNodeValue("guid", element) + ""
                    mNews.pubDate = getNodeValue("pubDate", element) + ""
                    newsList.add(mNews)
                }
            }
        }
        initAdapter(newsList)
        Log.i("cevap", "adet: " + adet)
    }

    private fun initAdapter(newsList: ArrayList<NewsItem>) {
        binding.recylerView.adapter = MyRecylerViewAdapter(newsList)
        binding.recylerView.layoutManager = LinearLayoutManager(this)
    }

    protected fun getNodeValue(tag: String?, element: Element): String {
        val nodeList: NodeList = element.getElementsByTagName(tag)
        val node = nodeList.item(0)
        if (node != null) {
            if (node.hasChildNodes()) {
                val child = node.firstChild
                while (child != null) {
                    if (child.nodeType == Node.TEXT_NODE) {
                        if (tag == "media:content") {
                            return child.parentNode.attributes.getNamedItem("url").textContent//FOTOĞRAF
                        }
                        return cdataFilter(child.nodeValue)
                    } else {
                        return cdataFilter(child.nodeValue)
                    }
                }
            }
        }
        return ""
    }

    //Gelen String veriyi işler temizler ve geri döndürür.
    private fun cdataFilter(data: String): String {
        val islenmisData: String
        when {
            data.contains("&amp;#039;") -> {
                islenmisData = data.replace("&amp;#039;", "")
                return islenmisData
            }
            data.contains("|") -> {
                islenmisData = data.replace("|", "")
            }
            else -> {
                islenmisData = data
            }

        }
        return islenmisData
    }
}