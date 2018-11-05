package com.demo.ike.doordashproject

import android.view.View
import android.widget.TextView
import androidx.navigation.NavController
import com.nhaarman.mockito_kotlin.mock
import junit.framework.Assert.assertEquals
import kotlinx.android.synthetic.main.activity_main.view.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(
    RobolectricTestRunner::class
)
class MainActivityViewTest {
    init {
        MockitoAnnotations.initMocks(this)
    }

    private lateinit var mainActivityView: MainActivityView
    private lateinit var containerView: View
    private val navController: NavController = mock()


    @Before
    fun setUp() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        containerView = activity.window.decorView.findViewById(android.R.id.content) as View
        mainActivityView = MainActivityView(activity, containerView, navController)
    }

    @Test
    fun testToolbarSetProperly() {
        assertEquals(containerView.toolbar.title, "Discover")
    }

    @Test
    fun testSnackbarIsShowingWhenError() {
        mainActivityView.showError("Oops, something went wrong!")
        val snackbarTextView = containerView.findViewById<TextView>(R.id.snackbar_text)
        assertEquals(snackbarTextView.text, "Oops, something went wrong!")
    }


}