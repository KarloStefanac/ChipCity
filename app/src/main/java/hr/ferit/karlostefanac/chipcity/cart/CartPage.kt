package hr.ferit.karlostefanac.chipcity.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import hr.ferit.karlostefanac.chipcity.Products.PageTitle
import hr.ferit.karlostefanac.chipcity.R
import hr.ferit.karlostefanac.chipcity.Routes
import hr.ferit.karlostefanac.chipcity.home.Header
import hr.ferit.karlostefanac.chipcity.models.Product

@Composable
fun CartPage(
    navController: NavController,
    repository: CartRepository
) {
    val viewModel : CartListModel = viewModel()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = "", block = {
        viewModel.getProducts()
    })

    when(state.value){
        is CartState.Loading -> CartLoading()
        is CartState.Success -> CartPageShow(navController = navController, products = (state.value as CartState.Success).state,repository)
    }
}

@Composable
fun CartLoading() {

}

@Composable
fun CartPageShow(
    navController : NavController,
    products : List<Product>,
    repository: CartRepository
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
            .verticalScroll(rememberScrollState(), true),
    ){
        Header(navController)
        Column(modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)) {
            PageTitle(text = "Vaša košarica")
            CartColumn(products,repository,navController)
            Box(
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxWidth()
                    .height(1.dp)
            )
            CartOrder(repository,products,navController)
        }
    }
}

@Composable
fun CartColumn(
    products: List<Product>,
    repository: CartRepository,
    navController: NavController
) {
    Column {
        products.forEach{ product ->
            CartItem(product = product,repository,navController)
        }
    }
}
@Composable
fun CartItem(
    product : Product,
    repository: CartRepository,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(Color(255,255,255,0)),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = product.name,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight())
            CartText(product)
            Image(
                painter = painterResource(id = R.drawable.remove),
                contentDescription = "Remove icon",
                alignment = Alignment.CenterEnd,
                modifier = Modifier
                    .fillMaxHeight()
                    .size(30.dp)
                    .clickable {
                        repository.deleteProduct(product.id)
                        navController.navigate(Routes.getCartPath())
                    })
        }
    }
}

@Composable
fun CartText(
    product: Product
) {
    Column {
        Text(
            text = product.name,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
            ),
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .wrapContentHeight(align = Alignment.CenterVertically),
        )
        Text(
            text = "${String.format("%.2f", product.price)}€",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
            ),
            modifier = Modifier
                .padding(horizontal = 20.dp)
        )
    }
}
@Composable
fun CartOrder(
    repository: CartRepository,
    products: List<Product>,
    navController: NavController
) {
    val totalPrice = getTotalPrice(products)
    Spacer(modifier = Modifier.height(10.dp))
    Column (
//        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .border(1.dp, Color(255, 255, 255, 100))
            .fillMaxWidth()
            .background(Color.Transparent))
    {
        Text(
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth(),
            text = "Sažetak narudžbe",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace
            ),)
        Text(
            text = "Ukupna cijena: ${String.format("%.2f",totalPrice)}€",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                fontFamily = FontFamily.Monospace
            ),)
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
            .fillMaxWidth())
        {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(
                        color = Color(165, 45, 45, 128)
                    )
                    .padding(horizontal = 25.dp, vertical = 5.dp)
                    .clickable {
                        repository.clearCart(products)
                        navController.navigate(Routes.getCartPath())
                    }
            ) {
                Text(
                    text = "Plaćanje",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
    }
}
fun getTotalPrice(products: List<Product>) : Double{
    var totalPrice = 0.0
    products.forEach { product ->
        totalPrice += product.price
    }
    return totalPrice
}