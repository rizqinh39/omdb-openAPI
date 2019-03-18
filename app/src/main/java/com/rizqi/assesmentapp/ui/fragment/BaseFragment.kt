package com.rizqi.assesmentapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rizqi.assesmentapp.App
import com.rizqi.assesmentapp.R

abstract class BaseFragment : Fragment() {

    private var mView: View? = null

    abstract val layoutResID: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(layoutResID, container, false)
        return mView
    }

    protected fun setToolbar(toolbarres: Int, @StringRes toolbarTitle: String, navigation: Boolean) {
        val toolbar = mView!!.findViewById<Toolbar>(toolbarres)
        toolbar.title = toolbarTitle
        toolbar.setTitleTextColor(ContextCompat.getColor(App.context, android.R.color.white))
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        if (navigation) {
            toolbar.navigationIcon = ContextCompat.getDrawable(App.context, R.drawable.ic_arrow_back)
        }
        setHasOptionsMenu(true)
    }


    protected fun addFragment(addedToStack: Boolean, @IdRes containerViewId: Int, fragment: Fragment, fragmentTag: String) {
        if (addedToStack) {
            fragmentManager!!.beginTransaction().add(containerViewId, fragment, fragmentTag).addToBackStack(fragmentTag).commit()
        } else {
            fragmentManager!!.beginTransaction().add(containerViewId, fragment, fragmentTag).addToBackStack(null).commit()
        }
    }

    protected fun replaceFragment(addedToStack: Boolean, @IdRes containerViewId: Int, fragment: Fragment, fragmentTag: String) {
        val ft = fragmentManager!!.beginTransaction()
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
}