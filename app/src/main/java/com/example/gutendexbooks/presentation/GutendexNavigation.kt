package com.example.gutendexbooks.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gutendexbooks.domain.model.BookCategory
import com.example.gutendexbooks.presentation.screens.BooksScreen
import com.example.gutendexbooks.presentation.screens.CategoriesScreen

@Composable
fun GutendexNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "categories"
    ) {
        composable("categories") {
            CategoriesScreen(
                onCategoryClick = { category ->
                    navController.navigate("books/${category.name}/${category.topic}")
                }
            )
        }

        composable("books/{categoryName}/{categoryTopic}") { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            val categoryTopic = backStackEntry.arguments?.getString("categoryTopic") ?: ""
            val category = BookCategory(categoryName, categoryTopic)

            BooksScreen(
                category = category,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
