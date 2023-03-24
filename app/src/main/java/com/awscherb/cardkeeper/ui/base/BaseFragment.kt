package com.awscherb.cardkeeper.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.trello.rxlifecycle3.components.support.RxFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment<T: ViewBinding>(
    private val bindingInflater: (LayoutInflater) -> T
): RxFragment() {

    private val disposables = CompositeDisposable()

    lateinit var binding: T

    protected val baseActivity: BaseActivity
        get() = activity as BaseActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater(inflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun showSnackbar(message: Int) = showSnackbar(getString(message))

    fun showSnackbar(message: String) {
        view?.let {
            Snackbar.make(
                it,
                message,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}
