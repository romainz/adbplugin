package com.canal.android.adb.setting.model

//Need to keep var instead of val because for XML deserializer we need empty constructor
data class Device(var name: String? = null, var ip: String? = null)