package com.example.gutendexbooks.presentation.screens
import com.example.gutendexbooks.R
import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.ImagePainter
import com.example.gutendexbooks.domain.model.BookCategory
import com.example.gutendexbooks.presentation.viewmodel.CategoriesViewModel
import com.example.gutendexbooks.ui.theme.GenreCard
import com.example.gutendexbooks.ui.theme.Heading1
import com.example.gutendexbooks.ui.theme.Heading2
import java.io.IOException

val categoryDrawableMap  = mapOf(
    1 to R.drawable.fiction,
    2 to R.drawable.history,
    3 to R.drawable.history,
    4 to R.drawable.philosophy,
    5 to R.drawable.philosophy,
    6 to R.drawable.politics,
    7 to R.drawable.drama,
    8 to R.drawable.adventure,
    9 to R.drawable.drama,
    10 to R.drawable.fiction,

)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    onCategoryClick: (BookCategory) -> Unit,
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Gutenberg\n" +
                    "Project",

            style = Heading1,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp, top = 32.dp, start = 16.dp)
        )
        Text(
            text = "A social cataloging website that allows you to \n"+
                    "freely search its database of books,\n" +
            "annotations, and reviews.",

            style = Heading2,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, top = 10.dp, start = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            itemsIndexed (viewModel.categories) {index, category ->
                val drawableId = categoryDrawableMap[index+1] ?: R.drawable.politics
                CategoryCard(
                    category = category,
                    onClick = { onCategoryClick(category) },
                    imagePainter = painterResource(id =drawableId )
                )
            }
        }

    }
}

@Composable
fun CategoryCard(
    category: BookCategory,
    onClick: () -> Unit,
    imagePainter: Painter
) {
    val arrowPainter = painterResource(id = R.drawable.next)

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 10.dp, end = 10.dp)
            .shadow(
                elevation = 0.dp,
                shape = RoundedCornerShape(4.dp),
                ambientColor = Color(0x80D3D1EE),
                spotColor = Color(0x80D3D1EE),
                clip = false
            ),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = imagePainter,
                    contentDescription = "Category image",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = category.name,
                    style = GenreCard,
                )
            }

            Image(
                painter = arrowPainter,
                contentDescription = "Next arrow",
                modifier = Modifier.size(24.dp)
            )
        }
    }


}


@Composable
fun loadImageFromAssets(assetName: String, context: Context): ImageBitmap? {
    return try {
        val inputStream = context.assets.open(assetName)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        bitmap.asImageBitmap()
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}
