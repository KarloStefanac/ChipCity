package hr.ferit.karlostefanac.chipcity.models

import androidx.annotation.DrawableRes
import hr.ferit.karlostefanac.chipcity.R

data class Product(
    val id: String = "",
    val image: String = "",
    val name: String = "",
    val category: String = "",
    val price: Double = 0.0,
)

//val arduinos : List<Product> = listOf(
//    Product(
//        id = "1",
//        image = R.drawable.arduino,
//        name = "Arduino GIGA",
//        category = "1",
//        price = 36.40
//    ),Product(
//        id = "2",
//        image = R.drawable.arduino,
//        name = "Arduino GIGA",
//        category = "1",
//        price = 36.40
//    ),Product(
//        id = "3",
//        image = R.drawable.arduino,
//        name = "Arduino GIGA",
//        category = "1",
//        price = 36.40
//    ),
//)
//val raspberyyies : List<Product> = listOf(
//    Product(
//        id = "1",
//        image = R.drawable.raspberry_pi,
//        name = "Raspberry pi 4",
//        category = "2",
//        price = 136.40
//    ),
//    Product(
//        id = "2",
//        image = R.drawable.raspberry_pi,
//        name = "Raspberry pi 4",
//        category = "2",
//        price = 136.40
//    ),
//    Product(
//        id = "3",
//        image = R.drawable.raspberry_pi,
//        name = "Raspberry pi 4",
//        category = "2",
//        price = 136.40
//    )
//)