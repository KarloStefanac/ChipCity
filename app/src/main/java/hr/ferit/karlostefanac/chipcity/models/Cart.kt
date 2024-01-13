package hr.ferit.karlostefanac.chipcity.models

class Cart() {
    val products: List<Product> = emptyList()
    var totalPrice: Double = 0.0
    init {
        for (product in products){
            totalPrice += product.price;
        }
    }
}