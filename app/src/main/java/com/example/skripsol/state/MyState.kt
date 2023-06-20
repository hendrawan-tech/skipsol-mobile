package com.example.skripsol.state

object MyState {
    private var dataUser: Map<String, Any>? = null
    private var dataMonitoring : Map<String, Any>? = null

    fun setDataUser(data: Map<String, Any>) {
        this.dataUser = data
    }

    fun getDataUser(): Map<String, Any>? {
        return dataUser
    }
    fun setMonitoring(statsMonitoring : Map<String, Any>?){
        this.dataMonitoring = statsMonitoring
    }

}
