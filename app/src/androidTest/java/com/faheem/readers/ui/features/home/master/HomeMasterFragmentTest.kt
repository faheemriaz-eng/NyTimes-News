package com.faheem.readers.ui.features.home.master

import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.faheem.readers.HiltTestActivity
import com.faheem.readers.R
import com.faheem.readers.domain.models.Article
import com.faheem.readers.ui.features.home.HomeActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.not
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class HomeMasterFragmentTest {

    @get:Rule(order = 0)
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityScenarioRule = ActivityScenarioRule(HomeActivity::class.java)

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    var decorView: View? = null


    @Before
    fun init() {
        // Populate @Inject fields in test class
        hiltAndroidRule.inject()

        activityScenarioRule.scenario.onActivity { activity ->
            decorView = activity?.window?.decorView
        }
    }

    @Test
    fun displayArticlesList() {

        launchArticlesListFragment {
            it.bindViewState(HomeUiState.Loading(false))
            it.bindViewState(HomeUiState.Success(listOf()))
        }

        Thread.sleep(1500)

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(Visibility.GONE)))

    }

    @Test
    fun displayErrorToast() {
        launchArticlesListFragment {
            it.bindViewState(HomeUiState.Loading(false))
            it.bindViewState(HomeUiState.Error("Something went wrong"))
        }

        sleep(Toast.LENGTH_SHORT / 2L)

        onView(withText("Something went wrong")).inRoot(
                withDecorView(
                    CoreMatchers.not(
                        CoreMatchers.`is`(decorView)
                    )
                )
            ).check(matches(isDisplayed()))
    }

    @Test
    fun givenArticleIsClickedThenNavigateToDetailsScreen() {
        //Launch fragment
        launchArticlesListFragment {
            it.bindViewState(HomeUiState.Loading(false))
            it.bindViewState(
                HomeUiState.Success(listOf(Article("title", "By Faheem", "12-2-2022")))
            )
        }

        Thread.sleep(1500)

        //Click on first article
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )
        //Check that it navigates to Detail screen
        Assert.assertTrue(navController.currentDestination?.id == R.id.articleDetailsFragment)
    }

    @Test
    fun assert_progress_bar_is_visible_when_ui_state_is_loading() {
        //Launch fragment
        launchArticlesListFragment {
            it.bindViewState(HomeUiState.Loading(true))
        }

        onView(withId(R.id.progressBar)).check(
            matches(withEffectiveVisibility(Visibility.VISIBLE))
        )
    }

    @Test
    fun assert_progress_bar_is_not_visible_when_ui_state_is_loading_false() {
        //Launch fragment
        launchArticlesListFragment {
            it.bindViewState(HomeUiState.Loading(false))
        }

        onView(withId(R.id.progressBar)).check(
            matches(withEffectiveVisibility(Visibility.GONE))
        )
    }

    private fun launchArticlesListFragment(fragment: (HomeMasterFragment) -> Unit) {
        launchFragmentInHiltContainer<HomeMasterFragment> {
            navController.setGraph(R.navigation.home_nav_graph)
            navController.setCurrentDestination(R.id.homeMasterFragment)
            this.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    // The fragment’s view has just been created
                    Navigation.setViewNavController(this.requireView(), navController)
                    fragment.invoke((this as HomeMasterFragment))
                }
            }
        }
    }

//    @Test
//    fun assert_progress_bar_is_visible_when_ui_state_is_loading() {
//        scenario.onFragment {
//            it.bindViewState(HomeUiState.Loading(true))
//        }
//        onView(withId(R.id.progressBar)).check(
//            matches(
//                ViewMatchers.withEffectiveVisibility(
//                    ViewMatchers.Visibility.VISIBLE
//                )
//            )
//        )
//    }
}