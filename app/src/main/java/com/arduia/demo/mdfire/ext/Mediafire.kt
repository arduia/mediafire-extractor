package com.arduia.demo.mdfire.ext

import android.os.Handler
import android.os.Looper
import com.arduia.lib.mediafire.MediaFire
import com.arduia.lib.mediafire.ResultListener

fun mediafire(url:String,listener:(MediaFire.Result)->Unit){
    MediaFire.extract(url, MainResultListener(listener))
}

class MainResultListener(private val listener: (MediaFire.Result) -> Unit):ResultListener{

    private val handler = Handler(Looper.getMainLooper())

    override fun invoke(data: MediaFire.Result) {
       handler.post{
           listener(data)
       }
    }

}
