package hr.ferit.karlostefanac.chipcity

import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import hr.ferit.karlostefanac.chipcity.models.Category
import hr.ferit.karlostefanac.chipcity.models.Product
import kotlinx.coroutines.tasks.await

class CategoryRepository(
    private val database: FirebaseFirestore = Firebase.firestore) {
    suspend fun getCategories() : List<Category>{
        val data = database.collection("Categories").get().await()
        val categories = parseDocumentFromDatabase(data.documents)
        return categories
    }

    suspend fun getCategoryByID(id: String): Category {
        val data = database.collection("Categories").get().await()
        return data.documents.find {it.id == id}?.let {document ->
            parseCategory(document)
        } ?: Category()
    }

    suspend fun getProductByID(categoryId : String, productId : String) : Product{
        val category = getCategoryByID(id = categoryId)
        var product : Product = Product()
        category.products.forEach { prod: Product ->
            if (categoryId == productId){
                product = prod
            }
        }
        return product
    }

    private suspend fun parseDocumentFromDatabase(documents: List<DocumentSnapshot>): List<Category> {
        val categories = mutableListOf<Category>()
        documents.forEach { document ->
            val category = parseCategory(document)
            categories.add(category)
        }
        return categories
    }
    private suspend fun parseCategory(document: DocumentSnapshot) : Category {
        var category = document.toObject(Category::class.java)
        if (category != null){
            val id = document.id
            val productsArray =
                document.get("products") as? List<Map<String, Any>>
            if (productsArray != null){
                val products = getProductsFromDocument(productsArray)
                category = category.copy(products = products)
            }
            category = category.copy(id = id)
            return category
        }else{
            return Category()
        }
    }

    private suspend fun getProductsFromDocument(productsList: List<Map<String, Any>>): List<Product> {
        val products = mutableListOf<Product>()
        productsList.forEach{ productsMap ->
            val product = Product(
                id = productsMap["id"].toString() ?: "",
                image = productsMap["image"].toString() ?: "",
                name = productsMap["name"].toString(),
                price = productsMap["price"].toString().toDouble()
            )
            products.add(product)
        }

        return products
    }
}