package com.example.foody.ui.details

import com.example.foody.persistence.FoodyDao
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val foodyDao: FoodyDao
) {
}