package com.example.mindvalleytest

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mindvalleytest.Util.launchFragmentInHiltContainer
import com.example.mindvalleytest.core.di.AppModule
import com.example.mindvalleytest.core.di.BindingModule
import com.example.mindvalleytest.core.network.MindValleyService
import com.example.mindvalleytest.core.repository.IMindValleyRepository
import com.example.mindvalleytest.core.repository.MindValleyRepository
import com.example.mindvalleytest.core.room.MindValleyDb
import com.example.mindvalleytest.core.util.DispatcherProvider
import com.example.mindvalleytest.core.util.mappers.CategoryMapper
import com.example.mindvalleytest.core.util.mappers.ChannelMapper
import com.example.mindvalleytest.core.util.mappers.NewEpisodeMapper
import com.example.mindvalleytest.ui.main.MainFragment
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@UninstallModules(AppModule::class, BindingModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainFragmentTest {


    @BindValue
    @JvmField
    val service: MindValleyService = FakeMVService()

    @BindValue
    @JvmField
    val db: MindValleyDb = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        MindValleyDb::class.java
    )
        .setTransactionExecutor(Executors.newSingleThreadExecutor())
        .build()

    @BindValue
    @JvmField
    val dispatcherProvider: DispatcherProvider = object : DispatcherProvider {
        override fun default(): CoroutineDispatcher = Dispatchers.Main
        override fun io(): CoroutineDispatcher = Dispatchers.Main
        override fun main(): CoroutineDispatcher = Dispatchers.Main
        override fun unconfined(): CoroutineDispatcher = Dispatchers.Main
    }

    @BindValue
    @JvmField
    val repository: IMindValleyRepository = MindValleyRepository(
        db, service, ChannelMapper(), NewEpisodeMapper(), CategoryMapper(), dispatcherProvider
    )

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        (service as FakeMVService).shutDown()
        db.close()
    }

    @Test
    fun fragmentIsLaunched() {
        launchFragmentInHiltContainer<MainFragment>()
        onView(withId(R.id.main_rv))
            .check(matches(isDisplayed()))

//        onView(allOf(isDescendantOfA(withRecyclerView(R.id.main_rv).atPosition(0)),
//            isDescendantOfA(withRecyclerView(R.id.new_episodes_rv).atPosition(0)),
//            withId(R.id.episode_title)))
//            .check(matches(withText("New Episodes")));
    }

}