package com.arduia.lib.mediafire

import okhttp3.OkHttpClient
import java.lang.Exception
import java.util.regex.Pattern

object MediaFire {

    private val okHttpClient:OkHttpClient? by lazy { OkHttpClient()  }
    private var regex:String? =  "aria-label=\"Download file\"\\n.+href=\"(.*)\""
    private var header:String? = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.99 Safari/537.36"

    private fun createRequest(url:String): okhttp3.Request? =
        header?.let {
            okhttp3.Request.Builder()
                .url(url)
                .addHeader("User-Agent",header!!)
                .build()
        }

    @JvmStatic
    fun extract(url:String,listener: ResultListener){
            if( header !=null){
                val request = createRequest(url)
               try{
                     MediaFireTask(Pattern.compile(regex!!), okHttpClient!!,request!!,listener =listener)
                         .send()
                }catch (e:Exception){
                   e.printStackTrace()
               }
            }
    }

    class Request {

        fun send(){

        }

        fun listenNow(listener: (Result) -> Unit){

        }

        fun setListener(listener: (Result) -> Unit){

        }

    }

    class Result( val isSuccessful:Boolean= false,
                  val exception: Exception?=null,
                  val data:String?=null)

}

