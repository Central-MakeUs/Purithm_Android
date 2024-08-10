package com.cmc.purithm.feature.filter.viewmodel

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class FilterValueViewModel @Inject constructor(

) : ViewModel() {

}

sealed interface FilterValueState {

}

sealed interface FilterValueSideEffects {

}