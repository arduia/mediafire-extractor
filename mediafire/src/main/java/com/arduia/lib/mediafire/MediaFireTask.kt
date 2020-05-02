package com.arduia.lib.mediafire

import okhttp3.*
import java.io.IOException
import java.lang.Exception
import java.util.regex.Pattern

internal class MediaFireTask(private val pattern: Pattern,
                             private val okHttpClient: OkHttpClient,
                             private val request:Request,
                             private val listener: ResultListener):Callback,Closeable{


    override fun onFailure(call: Call, e: IOException) {
        listener(MediaFire.Result(exception = e))
    }

    override fun onResponse(call: Call, response: Response) {
        if(response.isSuccessful){
            val body =  response.body?.string()
            if(body==null){

                listener(MediaFire.Result(exception = Exception("Body Not Found Exception")))

            }else{
                val url= extractToUrl(body)

                if(url.isNullOrBlank()) {
                    listener(MediaFire.Result(exception = Exception("No Url Found")))
                }else{
                    listener(MediaFire.Result(isSuccessful = true,data = url))
                }
            }

        }else{
            listener(MediaFire.Result(exception = Exception("Server Error")))
        }
    }

    private fun extractToUrl(body:String):String?{
        //getPattern
            val matcher = pattern.matcher(body)
            if(matcher.find()) {
                return matcher.group(1)
            }
        return null
    }

    fun send(){
        try {
            okHttpClient.newCall(request).enqueue(this)
        }catch(e:SecurityException){
            listener(MediaFire.Result(exception = e))
        }
    }

    override fun close() {

    }

}
