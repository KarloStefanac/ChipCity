package hr.ferit.karlostefanac.chipcity.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hr.ferit.karlostefanac.chipcity.models.Category
import hr.ferit.karlostefanac.chipcity.models.categories
import hr.ferit.karlostefanac.chipcity.models.categoryA

//@Preview (showBackground = true)
@Composable
fun ProductsPage(
    navController: NavController,
    categoryID: String
) {

    var category : Category = categories[2]
    categories.forEach { cat -> if (cat.id == categoryID) {category = cat}}

    val colorStops = arrayOf(
        0.1f to Color.Black,
        0.4f to Color(110,25,25)
    )
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
//            .background(color = Black)
            .background(brush = Brush.verticalGradient(colorStops = colorStops))
    ){
        Header()
        Column(modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)) {
        Text(text = "Sve Arduino ploče",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace
        ),
        modifier = Modifier
            .align(Alignment.Start),)
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .height(1.dp))
        ProductGrid(category)
        }
//        ProductCard("Arduino uno", price = "36,40", R.drawable.arduino)
    }
}

@Composable
fun ProductCard(
    title: String, price: String, @DrawableRes image: Int
) {
    Card(
        modifier = Modifier
            .height(200.dp)
            .width(150.dp)
            .padding(5.dp),
//            .shadow(elevation = 15.dp, shape = RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(Color(191, 25, 25, 255)),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column {
            Image(painter = painterResource(id = image),
                contentDescription = title,
                modifier = Modifier.clickable { })
            Text(text = title, style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp),
                modifier = Modifier.padding(5.dp))
            Row {
                Text(text = price, style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                ),
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .weight(1f))
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0, 0, 0, 128),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                        .clickable { }
                ) {
                    Text(
                        text = "Add to cart",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal
                        ))
                }
            }
        }
    }
}

@Composable
fun ProductGrid(
    category : Category
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp)
    ) {
        category.products.forEach { product ->
            item { ProductCard(product.name, price = "" + product.price + "€", product.image) }
        }
    }
}