package com.awscherb.cardkeeper.ui.base

import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.awscherb.cardkeeper.R
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity

abstract class BaseActivity: RxAppCompatActivity() {

    private var toolbar: Toolbar? = null

    //================================================================================
    // Lifecycle methods
    //================================================================================

    protected fun <T: ViewBinding> insertFragment(fragment: BaseFragment<T>) {
        val fragmentManager = supportFragmentManager
        val fragmentContainer = fragmentManager.findFragmentById(R.id.container)

        if (fragmentContainer == null) {
            fragmentManager.beginTransaction()
                .add(R.id.container, fragment)
                .commit()
        }
    }

    //================================================================================
    // Helper methods
    //================================================================================

    fun setUpToolbar() {
        val t = findViewById<Toolbar>(R.id.toolbar)
        toolbar = t

        setSupportActionBar(toolbar)
    }

    fun setupToolbarBack() {
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    fun setTitle(title: String) {
        toolbar?.title = title
    }
}
