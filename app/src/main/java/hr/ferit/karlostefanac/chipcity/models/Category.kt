package hr.ferit.karlostefanac.chipcity.models

import androidx.annotation.DrawableRes
import hr.ferit.karlostefanac.chipcity.R

data class Category (
    val id: String = "",
    val title : String  = "",
    @DrawableRes val image : Int,
    val products: List<Product> = emptyList()
)

val categoryA : Category = Category("1", "Arduino", R.drawable.arduino, arduinos)
val categoryR : Category = Category("2", "Raspberry", R.drawable.raspberry_pi ,raspberyyies)
val categoryO : Category = Category("3", "Osnovno", R.drawable.essentials ,raspberyyies)

val categories : List<Category> = listOf(categoryA, categoryR, categoryO)