package com.example.skripsol.FunctionHelper

import android.app.Activity
import android.content.Intent

object Get {
    private val argumentsMap: MutableMap<String, String> = mutableMapOf()
    fun to(activity: Activity, targetActivitu: Class<*>, vararg arguments: Pair<String, String>) {
        val intent = Intent(activity, targetActivitu)
        arguments.forEachIndexed { index, argument ->
            intent.putExtra("arg$index", argument.second)
            argumentsMap[argument.first] = argument.second
        }
        activity.startActivity(intent)

    }

    fun back(activity: Activity) {
        activity.finish()
    }

    fun arguments(key: String): String? {
        return argumentsMap[key]
    }

    fun dialog() {

    }

    fun singleDialog() {

    }

}