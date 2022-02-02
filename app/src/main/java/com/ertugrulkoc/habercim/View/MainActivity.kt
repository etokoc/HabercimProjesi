package com.ertugrulkoc.habercim.View

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ertugrulkoc.habercim.databinding.ActivityMainBinding
import org.w3c.dom.Document
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//       val factory = DocumentBuilderFactory.newInstance()
//        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING,true)
//        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
//        val input = factory
//            .newDocumentBuilder()
//            .parse()


        val queue = Volley.newRequestQueue(this)
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
        val doc  = docBuilder.parse(InputSource(StringReader(response)))
        val nList :NodeList= doc.getElementsByTagName("channel")
        for( i in 0..nList.length)
        {
            if (nList.item(0).nodeType == Node.ELEMENT_NODE){
                val element  = nList.item(i) as Element
                Log.i("cevap", "parseXml: " +getNodeValue("description",element) )
            }
        }
    }
    protected fun getNodeValue(tag: String?, element: Element): String? {
        val nodeList: NodeList = element.getElementsByTagName(tag)
        val node = nodeList.item(0)
        if (node != null) {
            if (node.hasChildNodes()) {
                val child = node.firstChild
                while (child != null) {
                    if (child.nodeType == Node.TEXT_NODE) {
                        return child.nodeValue
                    }
                }
            }
        }
        return ""
    }
}