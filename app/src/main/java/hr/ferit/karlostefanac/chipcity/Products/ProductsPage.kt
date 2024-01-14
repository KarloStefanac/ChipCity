package hr.ferit.karlostefanac.chipcity.Products

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import hr.ferit.karlostefanac.chipcity.R
import hr.ferit.karlostefanac.chipcity.Routes
import hr.ferit.karlostefanac.chipcity.home.Header
import hr.ferit.karlostefanac.chipcity.models.Category
import hr.ferit.karlostefanac.chipcity.models.Product

@Composable
fun ProductsPage(
    navController: NavController,
    categoryID: String
) {
    val viewModel : ProductsListViewModel = viewModel()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = "", block = {
        viewModel.getCategory(categoryID)
    })

    when(state.value){
        is ProductsState.Loading -> DetailsLoading()
        is ProductsState.Success -> ProductsPageShow(
            navController = navController,
            category = (state.value as ProductsState.Success).state)
    }
}

@Composable
fun DetailsLoading() {

}

@Composable
fun ProductsPageShow(
    navController: NavController,
    category: Category
) {
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
        PageTitle(text = "Sve Arduino ploče")
        ProductGrid(navController,category)
        }
    }
}

@Composable
fun PageTitle(
    text : String
) {
    Column{
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier
                .align(Alignment.Start),
        )
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .height(1.dp)
        )
    }
}

@Composable
fun ProductCard(
    navController: NavController,
    product : Product,
    category: Category
) {
    Card(
        modifier = Modifier
            .height(240.dp)
            .width(150.dp)
            .padding(5.dp),
//            .shadow(elevation = 15.dp, shape = RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(Color(191, 25, 25, 255)),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column {
            Box(modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(Routes.getProductDetailsPath(category.id,product.id)) }){
            AsyncImage(model = product.image, contentDescription = product.name)
            }
            Text(text = product.name, style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp),
                modifier = Modifier.padding(5.dp))
            Row {
                Text(text = product.price.toString() + "€", style = MaterialTheme.typography.bodyLarge.copy(
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
    navController: NavController,
    category : Category
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp)
    ) {
        category.products.forEach { product ->
            item { ProductCard(navController, product, category) }
        }
    }
}