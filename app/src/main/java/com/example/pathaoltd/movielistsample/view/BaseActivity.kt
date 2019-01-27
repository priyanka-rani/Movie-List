package com.example.pathaoltd.movielistsample.view

import android.app.ProgressDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.pathaoltd.movielistsample.R
import com.example.pathaoltd.movielistsample.network.ApiClient
import com.example.pathaoltd.movielistsample.network.ApiInterface
import com.example.pathaoltd.movielistsample.util.Utils


open class BaseActivity : AppCompatActivity() {
    private val TAG = BaseActivity::class.java.simpleName
    private var pDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pDialog = ProgressDialog(this@BaseActivity)
        pDialog!!.setCancelable(true)
        pDialog!!.setCanceledOnTouchOutside(false)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // other Code
    fun showToast(message: String) {
        showToast(message, false)
    }

    private fun showToast(message: String, isLong: Boolean) { // message was chaged final
        runOnUiThread { Toast.makeText(this@BaseActivity, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show() }
    }

    fun callRetrofit(showProgress: Boolean): ApiInterface {
//        Utils.hideKeyboard(this)
        if (showProgress) showProgressDialog(true)
        return ApiClient().callRetrofit(this@BaseActivity)
    }


    open fun showProgressDialog(isCancelable: Boolean) {
        showProgressDialog(getString(R.string.loading), isCancelable)
    }

    fun showProgressDialog(message: String, isCancelable: Boolean) {
        runOnUiThread {
            try {
                pDialog!!.setMessage(message)
                pDialog!!.setCancelable(isCancelable)
                pDialog!!.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    open fun dismissProgressDialog() {
        try {
            pDialog!!.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun showErrorSnack(message: String) {
        Utils.showErrorSnack(findViewById<View>(android.R.id.content), message, Snackbar.LENGTH_SHORT)
    }

}
