package hr.ferit.karlostefanac.chipcity.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.ferit.karlostefanac.chipcity.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class CartState{
    data object Loading : CartState()
    data class Success(val state : List<Product>) : CartState()
}

class CartListModel(
    private val repository : CartRepository = CartRepository()
) : ViewModel(){
//    init {
//        viewModelScope.launch{
//            getProducts()
//        }
//    }
    val state = MutableStateFlow<CartState>(CartState.Loading)

    fun getProducts(){
        viewModelScope.launch {
            val result = repository.getCartProducts()
            state.value = CartState.Success(result)
        }
    }
}