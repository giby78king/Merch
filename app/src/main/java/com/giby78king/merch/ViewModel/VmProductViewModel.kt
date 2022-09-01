package com.giby78king.merch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giby78king.merch.DataSource.FirebaseService_Product
import com.giby78king.merch.Domain.ProductEn
import com.giby78king.merch.Model.Product
import com.giby78king.merch.Model.Product.Companion.dbProductList
import kotlinx.coroutines.launch

class VmProductViewModel : ViewModel() {

    private val _productDatas = MutableLiveData<MutableList<Product>>()
    private val _upsertProductDatas = MutableLiveData<MutableList<Product>>()
    val productDatas: LiveData<MutableList<Product>> = _productDatas
    val upsertProductDatas: LiveData<MutableList<Product>> = _upsertProductDatas

    fun getDatas(selection: String) {
        viewModelScope.launch {
            FirebaseService_Product.getDatas()
            when (selection) {
                "editProductId" -> {
                    _productDatas.value = dbProductList
                }
                "UpsertProduct" -> {
                    _upsertProductDatas.value = dbProductList
                }
                else -> {
                    _productDatas.value = dbProductList
                }
            }
        }
    }

    fun insertOne(id: String, data: Map<String, Any>) {
        viewModelScope.launch {
            FirebaseService_Product.insertData(id, data)
        }
    }

    fun upsertOne(data: ProductEn) {
        viewModelScope.launch {
            data.save()
        }
    }

    fun deleteOne(data: ProductEn) {
        viewModelScope.launch {
            data.delete()
        }
    }
}