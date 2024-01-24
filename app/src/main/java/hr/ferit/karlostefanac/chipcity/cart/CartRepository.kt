package hr.ferit.karlostefanac.chipcity.cart

import android.annotation.SuppressLint
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import hr.ferit.karlostefanac.chipcity.models.Product
import kotlinx.coroutines.tasks.await

class CartRepository {
    @SuppressLint("StaticFieldLeak")
    private val database: FirebaseFirestore = Firebase.firestore
        fun addToCart(product: Product) {
            val cartRef = database.collection("Cart")
            val data = hashMapOf(
                "id" to product.id,
                "image" to product.image,
                "name" to product.name,
                "price" to product.price
            )
            cartRef.add(data)
        }
        suspend fun getCartProducts() : List<Product>{
            val cart = database.collection("Cart").get().await()
            val products = parseDocumentFromDatabase(cart.documents)
            return products
        }

    private fun parseDocumentFromDatabase(documents: List<DocumentSnapshot>): List<Product> {
        val products = mutableListOf<Product>()
        documents.forEach { document ->
            if (document.exists()){
                var product = document.toObject(Product::class.java)
                if (product!=null){
                    val id = document.id
                    val image = product.image
                    val name = product.name
                    val price = product.price
                    product = product.copy(id = id,image = image,name = name,price = price)
                    products.add(product)
                }else{
                    products.add(Product())
                }
            }
        }
        return products
    }
    fun deleteProduct(productId : String){
        val cartRef = database.collection("Cart").document(productId)
        cartRef.delete()
    }

    fun clearCart(products: List<Product>) {
        products.forEach {product ->
            val cartRef = database.collection("Cart").document(product.id)
            cartRef.delete()
        }
    }
}