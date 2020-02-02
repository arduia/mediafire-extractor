package com.arduia.lib.mediafire

import okhttp3.*
import java.io.IOException
import java.lang.Exception
import java.util.regex.Pattern

object MediaFire{

    private var onSuccessListener:OnSuccessListener? = null
    private var onFailureListener:OnFailureListener? = null
    private var okHttpClient:OkHttpClient? = null
    private var regex:String? =  "aria-label=\"Download file\"\\n.+href=\"(.*)\""
    private var header:String? = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.99 Safari/537.36"

    @JvmStatic
    fun extract(url:String):MediaFire{
        try {
        if(header!=null){
            val request = createRequest(url)

                request?.let { req->
                    callback?.let {
                        okHttpClient?.newCall(req)?.enqueue(it)
                    }
                }

        }
        }catch (io:SecurityException){
            onFailureListener?.onResult(io,0)
        }
        return this
    }

    @JvmStatic
    fun extractNow(url:String):String{
        val request = createRequest(url = url)
        request?.let {req->
               val response =  okHttpClient?.newCall(req)?.execute()
                if(response!=null){
                    if(response.isSuccessful){
                        return extractToUrl(response.body?.string()?: throw Exception("Request Body Not Found- ${response.code}"))
                    }else{
                        throw Exception("Response is No Successful, error code - ${response.code}")
                    }
                }else{
                    throw Exception("Request No Found")
                }
        }

         throw Exception("Url No Found")
    }

    @JvmStatic
    fun setOnSuccessListener(listener:OnSuccessListener):MediaFire{
        this.onSuccessListener = listener
        return this
    }

    @JvmStatic
    fun setOnFailureListener(listener: OnFailureListener):MediaFire{
        this.onFailureListener = listener
        return this
    }

    @JvmStatic
    fun setClient(client:OkHttpClient){
        this.okHttpClient = client
    }

    @JvmStatic
    fun release(){
        this.onFailureListener=null
        this.onSuccessListener=null
        this.okHttpClient= null
        this.callback = null
        this.regex = null
    }

    interface OnSuccessListener{
        fun onResult(url:String)
    }

    interface OnFailureListener{
        fun onResult(e: Exception,errorCode:Int)
    }

    private fun createRequest(url:String):Request? =
        if(header!=null){
           Request.Builder()
                .url(url)
                .addHeader("User-Agent", header!!)
                .build()
        }else{
            null
        }

    private var callback:Callback? = object :Callback{
        override fun onFailure(call: Call, e: IOException) {
           onFailureListener?.onResult(e,0)
        }

        override fun onResponse(call: Call, response: Response) {
            if(response.isSuccessful){
               val body =  response.body?.string()
                if(body==null){
                    onFailureListener?.onResult(Exception("BodyNotFound!"),0)
                }else{
                    val url= extractToUrl(body)
                    if(url.isBlank()) {
                        onFailureListener?.onResult(Exception("No Url Found"),1)
                    }else{
                        onSuccessListener?.onResult(url)
                    }
                }

            }else{
                onFailureListener?.onResult(Exception("Failure"),response.code)
            }
        }
    }

    private fun extractToUrl(body:String):String{
        regex?.let {
            val pattern =Pattern.compile(it)
            val matcher =pattern.matcher(body)
            if(matcher.find()){
                return matcher.group(1)
            }
        }
        return ""
    }

    private fun init(){
        try {
            okHttpClient = OkHttpClient.Builder()
                .build()
        }catch (e:Exception){
            onFailureListener?.onResult(e,0)
        }

    }

    init {
       init()
    }

}