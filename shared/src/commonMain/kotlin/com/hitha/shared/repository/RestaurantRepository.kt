package com.hitha.shared.repository

import com.hitha.shared.model.MenuItem

class RestaurantRepository {

    private val menu = listOf(
        MenuItem(1, "Butter Chicken", "Creamy tomato-based curry with tender tandoori chicken", 450.0, "Main Course", "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?w=300"),
        MenuItem(2, "Biryani", "Fragrant basmati rice layered with spiced meat and saffron", 380.0, "Main Course", "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=300"),
        MenuItem(3, "Paneer Tikka", "Char-grilled cottage cheese marinated in spiced yogurt", 320.0, "Starters", "https://images.unsplash.com/photo-1567188040759-fb8a883db5ba?w=300"),
        MenuItem(4, "Masala Dosa", "Crispy rice crepe filled with spiced potato, served with chutneys", 220.0, "Breakfast", "https://images.unsplash.com/photo-1630383249896-424e482df921?w=300"),
        MenuItem(5, "Gulab Jamun", "Deep-fried milk dumplings soaked in rose-scented syrup", 180.0, "Desserts", "https://images.unsplash.com/photo-1666196361608-e29ba7e5b5ae?w=300"),
        MenuItem(6, "Palak Paneer", "Fresh spinach curry with soft cottage cheese cubes", 340.0, "Main Course", "https://images.unsplash.com/photo-1618449840665-9ed506d73a34?w=300"),
        MenuItem(7, "Tandoori Naan", "Leavened bread baked in a traditional clay oven", 80.0, "Breads", "https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=300"),
        MenuItem(8, "Mango Lassi", "Creamy yogurt drink blended with fresh alphonso mango", 150.0, "Beverages", "https://images.unsplash.com/photo-1588616194254-f38a77b94d6e?w=300"),
        MenuItem(9, "Samosa (2 pcs)", "Crispy pastry filled with spiced potatoes and peas", 160.0, "Starters", "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=300"),
        MenuItem(10, "Chicken Tikka", "Tender chicken pieces marinated and grilled to perfection", 350.0, "Starters", "https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?w=300"),
        MenuItem(11, "Dal Makhani", "Slow-cooked black lentils in rich creamy gravy", 290.0, "Main Course", "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=300"),
        MenuItem(12, "Kulfi", "Traditional Indian ice cream with pistachio and cardamom", 200.0, "Desserts", "https://images.unsplash.com/photo-1624353365286-3f8d62daad51?w=300")
    )

    fun getMenu(): Result<List<MenuItem>> = Result.success(menu)

    fun getMenuByCategory(category: String): Result<List<MenuItem>> {
        return Result.success(menu.filter { it.category == category })
    }

    fun getCategories(): List<String> = menu.map { it.category }.distinct()
}
