package com.example.skripsol.config
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Utils {
    class ToJson {
        fun convert(value: String) {
            val gson = Gson()
            val mapType = object : TypeToken<Map<String, String>>() {}.type
            return gson.fromJson(value, mapType)
        }
    }
}