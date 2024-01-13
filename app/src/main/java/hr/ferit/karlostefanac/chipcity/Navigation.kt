package hr.ferit.karlostefanac.chipcity

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hr.ferit.karlostefanac.chipcity.models.Category
import hr.ferit.karlostefanac.chipcity.ui.HomePage
import hr.ferit.karlostefanac.chipcity.ui.ProductsPage

object Routes{
    const val SCREEN_ALL_CATEGORIES = "HomePage"
    const val SCREEN_CATEGORY_DETAILS = "ProductsPage/{categoryId}"

    fun getCategoryDetailsPath(categoryId: String) : String {
//        if (categoryId != null && categoryId != -1){
//            return "ProductsPage"
//        }
        return "ProductsPage/$categoryId"
    }
}

@Composable
fun NavigationController() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.SCREEN_ALL_CATEGORIES){
        composable(Routes.SCREEN_ALL_CATEGORIES){
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
    }
}