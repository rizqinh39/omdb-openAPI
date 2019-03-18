package com.rizqi.assesmentapp.ui.activity

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rizqi.assesmentapp.R

abstract class BaseActivity: AppCompatActivity() {

    protected abstract val layoutResID: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResID)
    }

    protected fun addFragment(@IdRes containerViewId: Int, fragment: Fragment, fragmentTag: String) {
        supportFragmentManager.beginTransaction().add(containerViewId, fragment, fragmentTag).disallowAddToBackStack().commit()
    }

    protected fun replaceFragment(addedToStack: Boolean, @IdRes containerViewId: Int, fragment: Fragment, fragmentTag: String) {
        val ft = supportFragmentManager!!.beginTransaction()
        if (addedToStack) {
            ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_left)
            ft.replace(containerViewId, fragment, fragmentTag).addToBackStack(fragmentTag)
            ft.commitAllowingStateLoss()
        } else {
            ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_left)
            ft.replace(containerViewId, fragment, fragmentTag).addToBackStack(null)
            ft.commitAllowingStateLoss()
        }
    }

    protected fun setToolbar(toolbar: Toolbar, @StringRes toolbarTitle: Int) {
        toolbar.setTitle(toolbarTitle)
        toolbar.setTitleTextColor(ContextCompat.getColor(baseContext, android.R.color.white))
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }
}