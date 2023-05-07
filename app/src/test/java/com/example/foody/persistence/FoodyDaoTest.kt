package com.example.foody.persistence

import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class FoodyDaoTest : LocalDatabase() {
    private lateinit var foodyDao: FoodyDao

    @Before
    fun init() {
        foodyDao = db.foodyDao()
    }

    // TODO testing
}