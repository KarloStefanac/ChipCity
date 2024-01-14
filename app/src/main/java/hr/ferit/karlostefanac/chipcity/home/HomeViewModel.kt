package hr.ferit.karlostefanac.chipcity.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.ferit.karlostefanac.chipcity.models.Category
import hr.ferit.karlostefanac.chipcity.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class HomeState(){
    object Loading : HomeState()
    data class Success(val state : List<Category>) : HomeState()
}

class HomeViewModel(
    private val repository: CategoryRepository = CategoryRepository()
) : ViewModel(){
    init {
        viewModelScope.launch{
            getCategories()
        }
    }

    val state = MutableStateFlow<HomeState>(HomeState.Loading)

    private suspend fun getCategories() {
        val result = repository.getCategories()
        state.value = HomeState.Success(result)
    }
}

