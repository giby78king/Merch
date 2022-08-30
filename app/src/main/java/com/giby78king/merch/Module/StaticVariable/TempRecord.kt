package com.giby78king.merch.Module.StaticVariable

import com.giby78king.merch.Model.DdlNormalModel

class TempRecord {
    companion object {
        var ddlGroupList= java.util.ArrayList<DdlNormalModel>()
    }
}

enum class TransactionDetailType(val Name: String) {
    Add("Add"),
    Edit("Edit"),
    AddCombine("AddCombine"),
    EditCombine("EditCombine"),
    ChangeClass("ChangeClass"),
    TempAddSuccess("TempAddSuccess"),
}