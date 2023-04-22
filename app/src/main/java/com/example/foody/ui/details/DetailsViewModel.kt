package com.example.foody.ui.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
  private val detailsRepository: DetailsRepository
) : ViewModel() {
}