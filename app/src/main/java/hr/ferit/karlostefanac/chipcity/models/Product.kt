package hr.ferit.karlostefanac.chipcity.models

import androidx.annotation.DrawableRes
import hr.ferit.karlostefanac.chipcity.R

data class Product(
    val id: String = "",
    val image: String = "",
    val name: String = "",
    val price: Double = 0.0,
)
