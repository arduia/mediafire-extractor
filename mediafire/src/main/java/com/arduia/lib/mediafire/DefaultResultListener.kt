package com.arduia.lib.mediafire

class DefaultResultListener(private val listener:(MediaFire.Result)->Unit):ResultListener{
    override fun invoke(data: MediaFire.Result) {
        listener(data)
    }
}

