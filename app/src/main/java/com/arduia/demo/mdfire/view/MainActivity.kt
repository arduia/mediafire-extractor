package com.arduia.demo.mdfire.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import com.arduia.demo.mdfire.R
import com.arduia.lib.mediafire.MediaFireOld

class MainActivity : AppCompatActivity() {

    private val url = "http://www.mediafire.com/file/h5z7dwzjf9mnsel/test.txt/file"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MediaFireOld.extract(url).setOnSuccessListener(object :MediaFireOld.OnSuccessListener{
                override fun onResult(url: String) {
                    d("My_MainActivity","Result -> $url")
                }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        MediaFireOld.release()
    }
    companion object{
        private const val TAG = "MY_MainActivity"
    }
}
