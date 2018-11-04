package com.demo.ike.doordashproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.presenter = MainActivityPresenter(RetrofitInstance.retrofit)
        presenter.onViewAttached(
            MainActivityView(
                this,
                window.decorView.findViewById(android.R.id.content)
            )
        )
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroyView()
        presenter.onDestroy()
    }
}