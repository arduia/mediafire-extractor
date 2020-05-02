package com.arduia.lib.mediafire


interface ResultListener{
    operator fun invoke(data:MediaFire.Result)
}
