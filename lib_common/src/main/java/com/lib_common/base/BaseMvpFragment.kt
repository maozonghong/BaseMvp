package com.lib_common.base

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lib_common.observer.IBaseView

/**
Created by maozonghong

on 3/30/21
 **/

 abstract class BaseMvpFragment<P:BasePresenter<V>,V:IBaseView> :Fragment(),IBaseView{

     var presenter: P?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        presenter=createPresenter()

        presenter?.let {
            lifecycle.addObserver(it)
            it.attachView(this as V)
        }
    }

    open override fun showErrorMessage(err: String?) {

    }

    open override fun showErrorMessage(idRes: Int) {

    }

    open override fun getActivityContext(): AppCompatActivity {
        return activityContext
    }

    open override fun showDialog() {

    }

    open override fun showDialog(msg: String?) {
    }

    open override fun hideDialog() {
    }


    open override fun shortShow(strId: Int) {

    }


    abstract fun createPresenter(): P?

    open override fun shortShow(msg: String?) {
        Toast.makeText(activityContext, msg, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.let {
            lifecycle.removeObserver(it)
        }
    }

}