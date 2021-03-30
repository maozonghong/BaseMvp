package com.lib_common.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lib_common.observer.IBaseView

abstract class BaseMvpActivity<P : BasePresenter<V>, V : IBaseView> :
    AppCompatActivity(), IBaseView {

     var presenter: P?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter=createPresenter()

        presenter?.attachView(this as V)

        presenter?.let {
            lifecycle.addObserver(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.let {
            lifecycle.removeObserver(it)
        }
    }

    open override fun showErrorMessage(err: String?) {

    }

    open override fun showErrorMessage(idRes: Int) {

    }

    open override fun getActivityContext(): AppCompatActivity {
        return this
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
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}