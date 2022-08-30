package com.giby78king.merch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VmProductSaveViewModel : ViewModel() {

    companion object {
        var selectedSpecification: Int = 0
    }

    private val _specificationSelectInfo = MutableLiveData<Int>()
    val specificationSelectInfo: LiveData<Int> = _specificationSelectInfo

    fun setSelectSpecificationInfo(index: Int) {
        selectedSpecification = index
        _specificationSelectInfo.value = index
    }

}