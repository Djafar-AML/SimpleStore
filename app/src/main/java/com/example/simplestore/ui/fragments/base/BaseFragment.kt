package com.example.simplestore.ui.fragments.base

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.example.simplestore.ui.activity.SimpleStoreActivity


abstract class BaseFragment : Fragment() {

    val TAG: String = this::class.java.simpleName

    val storeActivity: SimpleStoreActivity
        get() = activity as SimpleStoreActivity

    protected val navController by lazy { storeActivity.navController }


    protected fun navigateUp() {
        storeActivity.navController.navigateUp()
    }

    protected fun navigate(actionId: NavDirections) {
        storeActivity.navController.navigate(actionId)
    }

    fun showToastMessage(msg: String) {

        view?.apply {
            Toast.makeText(
                this.context,
                msg,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun showSoftKeyboard(view: View) {

        if (view.requestFocus()) {
            val imm =
                storeActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hideSoftKeyboard(view: View) {

        view.apply {
            val imm: InputMethodManager? =
                storeActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(this.windowToken, 0)
        }
    }

}