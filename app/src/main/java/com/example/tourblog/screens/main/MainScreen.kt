package com.example.tourblog.screens.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tourblog.R
import com.example.tourblog.utils.ButtonType
import com.example.tourblog.utils.Constants.CONTENT_DESCRIPTION
import com.example.tourblog.utils.Constants.DETAILS_ROUTE
import com.example.tourblog.utils.PriceType
import com.example.tourblog.utils.Templates
import com.example.tourblog.utils.components.ShimmeredBox
import com.example.tourblog.utils.isLoad

@Composable
fun MainScreen(
    navController: NavController,
) {
    val viewModel: MainViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            )
            .fillMaxSize()
    ) {
        Grid(
            uiState,
            onCardClick = { id ->
                navController.navigate("${DETAILS_ROUTE}/$id")
            },
//            showMoreClick = { name ->
//                viewModel.updateShowed(name)
//            }
        )
    }
}

@Composable
fun Grid(
    uiState: State<MainUIState>,
    onCardClick: (id: String) -> Unit,
//    showMoreClick: (category: String) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (isLoad(uiState.value.pageState)) {
            item(
                span = { GridItemSpan(maxLineSpan) }, content = {
                    LazyRow(
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(3) {
                            ShimmeredBox(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .sizeIn(minHeight = 40.dp, minWidth = 100.dp)
                            )
                        }
                    }
                }
            )
            item(
                span = { GridItemSpan(maxLineSpan) },
                content = {
                    ShimmeredBox(
                        modifier = Modifier
                            .clip(shape = MaterialTheme.shapes.small)
                            .fillMaxWidth()
                            .sizeIn(minHeight = 40.dp)
                    )
                }
            )

            items(6) {
                ShimmeredBox(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            bottom = 4.dp
                        )
                        .clip(shape = MaterialTheme.shapes.small)
                        .size(160.dp)
                )
            }
        }
        else {
            item(
                span = { GridItemSpan(maxLineSpan) }, content = {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.value.buttons) {
                            ButtonItem(it)
                        }
                    }
                }
            )
            uiState.value.categories.forEachIndexed { index, category ->
                item(
                    span = { GridItemSpan(maxLineSpan) },
                    content = {
                        CategoryItem(category.name)
                    }
                )
                items(
                    items = category.showedList
                ) { item ->
                    CategoryListItem(
                        item = item,
                        onCardClick = {
                            if (category.template == Templates.BLOG.value)
                                onCardClick(item.id)
                        }
                    )
                }
                if (category.blogList.size > 6) {
                    item(
                        span = { GridItemSpan(maxLineSpan) },
                        content = {
                            ShowMoreBtn(
                                category.blogList.size,
//                                showMore = {
//                                    showMoreClick(category.name)
//                                }
                            )
                        }
                    )
                }
                if (index != uiState.value.categories.size - 1) {
                    item(
                        span = { GridItemSpan(maxLineSpan) },
                        content = {
                            Divider(
                                modifier = Modifier
                                    .padding(top = 24.dp),
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonItem(item: Button) {
    val button = when (item.type) {
        ButtonType.MAP_MARKER.value -> ButtonType.MAP_MARKER
        ButtonType.HELP.value -> ButtonType.HELP
        ButtonType.WEATHER.value -> ButtonType.WEATHER
        else -> ButtonType.THREE_D_VIEW
    }
    Card(
        modifier = Modifier
            .height(40.dp),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = item.let {
                Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = button.colors.map {
                                colorResource(id = it)
                            }
                        )
                    )
                    .padding(8.dp)
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            item.let {
                Icon(
                    painter = painterResource(id = button.icon),
                    tint = Color.White,
                    contentDescription = CONTENT_DESCRIPTION
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = item.title,
                    color = Color.White,
                    lineHeight = 20.sp,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                )
            }
        }
    }
}

@Composable
fun CategoryItem(value: String) {
    Row(
        Modifier
            .padding(top = 24.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = value,
            lineHeight = 28.8.sp,
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun CategoryListItem(
    item: Blog,
    onCardClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                top = 16.dp,
                bottom = 4.dp
            )
            .clickable(onClick = (onCardClick))
    ) {
        item.image?.let {
            ItemImage(it)
        }
        ItemTitle(item.title)
        ItemSubtitle(item)
        item.price?.let {
            ItemPrice(item)
        }
    }
}

@Composable
fun ItemImage(image: ImageBitmap) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.small
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            bitmap = image,
            contentDescription = CONTENT_DESCRIPTION,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ItemTitle(title: String) {
    Text(
        modifier = Modifier
            .padding(
                top = 4.dp,
                bottom = 2.dp
            ),
        text = title,
        lineHeight = 16.sp,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun ItemSubtitle(item: Blog) {
    Text(
        modifier = Modifier
            .padding(
                bottom = 4.dp,
            ),
        text = item.subtitle ?: item.location ?: stringResource(
            id = R.string.guest_count,
            item.countTourist.toString()
        ),
        lineHeight = 16.sp,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun ItemPrice(item: Blog) {
    var price = stringResource(id = R.string.rub_price, item.price?.price!!)
    if (item.price?.typePrice == PriceType.NIGHT.value) price += stringResource(id = R.string.night_lable)
    Text(
        text = price,
        lineHeight = 20.sp,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        maxLines = 1,
    )
}

@Composable
fun ShowMoreBtn(
    size: Int,
//    showMore: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(top = 4.dp)
            .fillMaxWidth(),
//            .clickable(
//                onClick = (showMore)
//            ),
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.secondary),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxSize(),
            text = stringResource(id = R.string.show_all_items, size.toString()),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}