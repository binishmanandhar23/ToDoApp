package com.binish.todoapp.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.transition.Slide

object AppUtils {
    fun startFragment(
        fragment: Fragment,
        fragmentManager: FragmentManager,
        containerViewId: Int,
        replace: Boolean,
        addToBackStack: String?
    ) {
        val transaction = fragmentManager.beginTransaction()
        fragment.enterTransition = Slide(Gravity.END).apply { duration = 100 }
        fragment.exitTransition = Slide(Gravity.START).apply { duration = 100 }
        if (replace)
            transaction.replace(containerViewId, fragment)
        else
            transaction.add(containerViewId, fragment)
        transaction.addToBackStack(addToBackStack)
        transaction.commit()
    }

    fun performHapticFeedback(view: View, context: Context) {
        view.isHapticFeedbackEnabled = true
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
    }

    fun changeStatusBarColor(activity: Activity, iconsForLightBar: Boolean, color: Int) {
        val window = activity.window
        val decor = window?.decorView
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (iconsForLightBar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                decor?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            else
                decor?.systemUiVisibility = 0
        } else
            decor?.systemUiVisibility = 0

        window?.statusBarColor = ContextCompat.getColor(
            activity,
            color
        )
    }
}