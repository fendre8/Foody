package com.example.foody.ui.main

import com.example.foody.network.FoodyService
import com.example.foody.persistence.FoodyDao
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val foodyService: FoodyService,
    private val foodyDao: FoodyDao
) {
}