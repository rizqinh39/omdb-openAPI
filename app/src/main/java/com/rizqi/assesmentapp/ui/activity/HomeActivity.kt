 package com.rizqi.assesmentapp.ui.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.NonNull
import com.rizqi.assesmentapp.R
import com.rizqi.assesmentapp.ui.fragment.HomeFragment

 class HomeActivity : BaseActivity() {
     override val layoutResID: Int
         get() = R.layout.activity_main


     companion object {
         @JvmStatic
         val TAG = HomeActivity::class.java.simpleName

         @JvmStatic
         fun navigate(@NonNull activity: Activity) {
             val i = Intent(activity, HomeActivity::class.java)
             activity.startActivity(i)
         }
     }

     override fun onPostCreate(savedInstanceState: Bundle?) {
         super.onPostCreate(savedInstanceState)
         initView()
     }

     private fun initView() {
         addFragment(R.id.container_home, HomeFragment.newInstance(), HomeFragment.TAG)
     }

 }
