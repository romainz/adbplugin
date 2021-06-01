package com.zanon.android.adb.setting.model

//Need to keep var instead of val because for XML deserializer we need empty constructor
data class Application(var name: String = "", var id: String = "")