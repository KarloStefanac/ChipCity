package hr.ferit.karlostefanac.chipcity.ProductDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import hr.ferit.karlostefanac.chipcity.Products.PageTitle
import hr.ferit.karlostefanac.chipcity.home.Header
import hr.ferit.karlostefanac.chipcity.models.Product

@Composable
fun ProductDetails(
    navController: NavController,
    categoryId : String,
    productId : String
) {
    val viewModel : ProductViewModel = viewModel()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(
        key1 ="",
        block ={
            viewModel.getProduct(categoryId, productId)
        })

    when(state.value){
        is ProductState.Loading -> ProductLoading()
        is ProductState.Success -> ProductDetailsShow(
            navController = navController,
            product = (state.value as ProductState.Success).state)
    }
}

@Composable
fun ProductLoading() {

}

@Composable
fun ProductDetailsShow(
    navController: NavController,
    product: Product
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
            .background(brush = Brush.verticalGradient(colorStops = colorStops)),
    ){
        Header(navController)
        Column(modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)) {
            PageTitle(text = "Opis/info")
            ProductImage(product)
            Details(product)
        }
    }
}

@Composable
fun ProductImage(
//    navController: NavController,
    product: Product
) {
    AsyncImage(
        model = product.image,
        contentDescription = product.name,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .fillMaxWidth()
            .height(270.dp)
            .padding(vertical = 10.dp))
}

@Composable
fun Details(
    product: Product
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .border(1.dp, Color(255, 255, 255, 100))
            .fillMaxWidth()
            .background(Color.Transparent)){
        Text(
            modifier = Modifier.padding(vertical = 5.dp),
            text = product.name,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace
            ),)
        Text(
            text = product.price.toString() + "€",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace
            ),)
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .background(
                    color = Color(165, 45, 45, 128)
                )
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .clickable { }
        ) {
            Text(
                text = "Stavi u košaricu",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}