package hr.ferit.karlostefanac.chipcity.ProductDetails

import android.icu.util.ULocale.Category
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.ferit.karlostefanac.chipcity.CategoryRepository
import hr.ferit.karlostefanac.chipcity.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class ProductState{
    object Loading : ProductState()
    data class Success(val state : Product) : ProductState()
}
class ProductViewModel(
    private val repository : CategoryRepository = CategoryRepository()
) : ViewModel(){
    val state = MutableStateFlow<ProductState>(ProductState.Loading)

    fun getProduct(categoryId :String, productId : String){
        viewModelScope.launch {
            val result = repository.getProductByID(categoryId, productId)
            state.value = ProductState.Success(result)
        }
    }
}