package com.example.gutendexbooks.presentation.screens
import com.example.gutendexbooks.R
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.gutendexbooks.domain.model.Book
import com.example.gutendexbooks.domain.model.BookCategory
import com.example.gutendexbooks.presentation.viewmodel.BooksViewModel
import androidx.core.net.toUri
import com.example.gutendexbooks.ui.theme.Body
import com.example.gutendexbooks.ui.theme.BookAuthor
import com.example.gutendexbooks.ui.theme.BookName
import com.example.gutendexbooks.ui.theme.GutenbergColors.GreyDark
import com.example.gutendexbooks.ui.theme.GutenbergColors.PrimaryPurple
import com.example.gutendexbooks.ui.theme.Heading2
import com.example.gutendexbooks.ui.theme.SearchBox


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksScreen(
    category:
    BookCategory,
    onBackClick: () -> Unit,
    viewModel: BooksViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val books = viewModel.books.collectAsLazyPagingItems()

    LaunchedEffect(category) {
        viewModel.setCategory(category)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        TopAppBar(
            title = { Text(text = category.name, color = PrimaryPurple, style = Heading2) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(painter = painterResource(R.drawable.back), contentDescription = "Back")
                }
            }
        )


        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = viewModel::setSearchQuery,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            placeholder = { Text("Search books...", style = SearchBox) },
            leadingIcon = {
                Icon(painter = painterResource(R.drawable.search), contentDescription = "Search")
            },
            trailingIcon = {
                if (uiState.searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.setSearchQuery("") }) {
                        Icon(
                            painter = painterResource(R.drawable.cancel),
                            contentDescription = "Clear text"
                        )
                    }
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = PrimaryPurple,
                cursorColor = PrimaryPurple,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = GreyDark
            ),
            shape = RoundedCornerShape(4.dp),
            singleLine = true
        )


        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // im using tablet so i've put 3
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(books.itemCount) { index ->
                val book = books[index]
                book?.let {
                    BookItem(
                        book = it,
                        onClick = {
                            val url = viewModel.getViewableBookUrl(it)
                            if (url != null) {
                                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                                context.startActivity(intent)
                            } else {

                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookItem(
    book: Book,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            AsyncImage(
                model = book.coverImageUrl,
                contentDescription = "Book Cover",
                modifier = Modifier
                    .width(114.dp)
                    .height(162.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shadow(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(8.dp),
                        ambientColor = Color(0xFFD3D1EE).copy(alpha = 0.5f),
                        spotColor = Color(0xFFD3D1EE).copy(alpha = 0.5f)
                    )
            )
            Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = book.title,
                    style = BookName,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                if (book.authors.isNotEmpty()) {
                    Text(
                        text = "By ${book.authors.joinToString(", ") { it.name }}",
                        style = BookAuthor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

        }
    }

}
