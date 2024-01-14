package hr.ferit.karlostefanac.chipcity.Products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.ferit.karlostefanac.chipcity.models.Category
import hr.ferit.karlostefanac.chipcity.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class ProductsState{
    object Loading : ProductsState()
    data class Success(val state: Category) : ProductsState()
}

class ProductsListViewModel(
    private val repository : CategoryRepository = CategoryRepository()
) : ViewModel(){
    val state = MutableStateFlow<ProductsState>(ProductsState.Loading)

    fun getCategory(id: String){
        viewModelScope.launch {
            val result = repository.getCategoryByID(id)
            state.value = ProductsState.Success(result)
        }
    }
}