package com.giby78king.merch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giby78king.merch.DataSource.FirebaseService_Activity
import com.giby78king.merch.Domain.ActivityEn
import com.giby78king.merch.Model.Activity
import com.giby78king.merch.Model.Activity.Companion.dbActivityList
import kotlinx.coroutines.launch

class VmActivitylViewModel : ViewModel() {

    private val _activityDatas = MutableLiveData<MutableList<Activity>>()
    private val _upsertActivityDatas = MutableLiveData<MutableList<Activity>>()
    val activityDatas: LiveData<MutableList<Activity>> = _activityDatas
    val upsertActivityDatas: LiveData<MutableList<Activity>> = _upsertActivityDatas

    fun getDatas(selection: String) {
        viewModelScope.launch {
            FirebaseService_Activity.getDatas()
            when (selection) {
                "editActivityId" -> {
                    _activityDatas.value = dbActivityList
                }
                "UpsertActivity" -> {
                    _upsertActivityDatas.value = dbActivityList
                }
                else ->
                {
                    _activityDatas.value = dbActivityList
                }
            }
        }
    }

    fun insertOne(id: String, data: Map<String, Any>) {
        viewModelScope.launch {
            FirebaseService_Activity.insertData(id, data)
        }
    }

    fun upsertOne(data: ActivityEn) {
        viewModelScope.launch {
            data.save()
        }
    }

    fun deleteOne(data: ActivityEn) {
        viewModelScope.launch {
            data.delete()
        }
    }
}