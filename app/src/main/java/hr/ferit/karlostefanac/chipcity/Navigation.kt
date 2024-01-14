package hr.ferit.karlostefanac.chipcity

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hr.ferit.karlostefanac.chipcity.ProductDetails.ProductDetails
import hr.ferit.karlostefanac.chipcity.home.HomePage
//import hr.ferit.karlostefanac.chipcity.ui.ProductDetails
import hr.ferit.karlostefanac.chipcity.Products.ProductsPage

object Routes{
    const val SCREEN_ALL_CATEGORIES = "HomePage"
    const val SCREEN_CATEGORY_DETAILS = "ProductsPage/{categoryId}"
    const val SCREEN_PRODUCT_DETAILS = "ProductsPage/{categoryId}/{productId}"
    fun getCategoryDetailsPath(categoryId: String) : String {
//        if (categoryId != null && categoryId != -1){
//            return "ProductsPage"
//        }
        return "ProductsPage/$categoryId"
    }
    fun getProductDetailsPath(categoryId : String, productId : String) : String{
        return "ProductsPage/$categoryId/$productId"
    }
}

@Composable
fun NavigationController() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.SCREEN_ALL_CATEGORIES){
        composable(Routes.SCREEN_ALL_CATEGORIES){
//            HomePage(navController)
            HomePage(navController)
        }
        composable(
            Routes.SCREEN_CATEGORY_DETAILS,
            arguments = listOf(
                navArgument("categoryId"){
                    type = NavType.StringType
                }
            )
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("categoryId")?.let {idFromArguments ->
                ProductsPage(
                    navController = navController,
                    categoryID = idFromArguments
                )
            }
        }
        composable(
            Routes.SCREEN_PRODUCT_DETAILS,
            arguments = listOf(
                navArgument("categoryId"){
                    type = NavType.StringType
                },
                navArgument("productId"){
                    type = NavType.StringType
                }
            )
        ){ backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")?: ""
            val productId = backStackEntry.arguments?.getString("productId")?: ""
            ProductDetails(navController = navController, categoryId = categoryId, productId = productId)
        }
    }
}