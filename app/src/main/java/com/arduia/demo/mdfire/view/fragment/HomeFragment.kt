package com.arduia.demo.mdfire.view.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import com.arduia.demo.mdfire.R
import com.arduia.demo.mdfire.ext.mediafire
import com.arduia.demo.mdfire.view.MainActivity
import com.arduia.demo.mdfire.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    private  val  viewModel by viewModels<HomeViewModel>()
    private val url = "http://www.mediafire.com/file/h5z7dwzjf9mnsel/test.txt/file"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        gen_button.setOnClickListener {
            d(TAG,"onClick")
            mediafire(url){
              d(TAG,"result ${Thread.currentThread()}-> ${it.data} ")
                edt_home.setText("${it.data}")

            }
        }
    }

    companion object{
        private const val TAG = "MY_HomeFragment"
    }
}
