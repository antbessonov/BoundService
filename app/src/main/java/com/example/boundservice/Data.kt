package com.example.boundservice

object Data {

    fun getDataList(): List<Int> {
        val dataList = mutableListOf<Int>()
        for (i in 0..100 step 5) {
            dataList.add(i)
        }
        return dataList
    }
}