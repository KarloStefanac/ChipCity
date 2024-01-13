package hr.ferit.karlostefanac.chipcity.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hr.ferit.karlostefanac.chipcity.R
import hr.ferit.karlostefanac.chipcity.Routes
import hr.ferit.karlostefanac.chipcity.models.Category
import hr.ferit.karlostefanac.chipcity.models.categoryA
import hr.ferit.karlostefanac.chipcity.models.categoryO
import hr.ferit.karlostefanac.chipcity.models.categoryR

//@PreviewParameter(showBackground = true)
@Composable
fun HomePage(navController: NavController) {
    val colorStops = arrayOf(
        0.1f to Black,
        0.4f to Color(110,25,25)
    )
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
//            .background(color = Black)
            .background(brush = Brush.verticalGradient(colorStops = colorStops)),
    ) {
        Header()
        CategoryCard(navController, categoryA)
        CategoryCard(navController, categoryR)
        CategoryCard(navController, categoryO)

    }
}

@Composable
fun Header() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(100.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Chip ciy logo",
            modifier = Modifier
                //.width(10.dp)
                .fillMaxHeight()
                .padding(0.dp)
                .clickable {  }
        )
        Box(modifier = Modifier
            .fillMaxHeight()
        ){
            Image(
                painter = painterResource(id = R.drawable.cart),
                contentDescription = "Chip ciy logo",
                alignment = Alignment.CenterEnd,
                modifier = Modifier
                    .fillMaxHeight()
                    .size(30.dp)
                    .clickable {  }
            )
        }
    }
}

@Composable
fun CategoryCard(
    navController: NavController,
    category : Category
){
    Card(
        modifier = Modifier
            .clickable { navController.navigate(Routes.getCategoryDetailsPath(category.id)) }
            .height(150.dp)
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row {
            Image(painter = painterResource(category.image), contentDescription = "Arduino giga")
            Text(
                text = category.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                ),
                modifier = Modifier.padding(horizontal = 20.dp).fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically),
            )
        }
    }
}