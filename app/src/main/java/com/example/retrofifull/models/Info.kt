package com.example.retrofifull.models

import java.io.Serializable

data class Info(
    val bajarildi: Boolean,
    val batafsil: String,
    val id: Int,
    val oxirgi_muddat: String,
    val sana: String,
    val sarlavha: String,
    val zarurlik: String
):Serializable