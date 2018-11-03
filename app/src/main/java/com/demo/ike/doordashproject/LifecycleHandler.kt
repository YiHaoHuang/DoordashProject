package com.demo.ike.doordashproject

internal interface LifecycleHandler<T> {
    fun onViewAttached(view: T)

    fun onResume()

    fun onPause()

    fun onDestroyView()

    fun onDestroy()
}