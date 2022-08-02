package com.example.optisoltask

import com.example.optisoltask.view.ui.activity.LoginActivityTest
import com.example.optisoltask.view.ui.activity.MainActivityTest
import com.example.optisoltask.view.ui.activity.SplashScreenActivityTest
import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    SplashScreenActivityTest::class,
    LoginActivityTest::class,
    MainActivityTest::class
)
class ActivityTestSuite {
}