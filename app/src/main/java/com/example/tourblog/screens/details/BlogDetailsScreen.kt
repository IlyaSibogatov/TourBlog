package com.example.tourblog.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.example.tourblog.R
import com.example.tourblog.utils.Constants.CONTENT_DESCRIPTION
import com.example.tourblog.utils.PageState
import com.example.tourblog.utils.components.ShimmeredBox
import com.example.tourblog.utils.isLoad
import com.example.tourblog.utils.parseDate
import com.example.tourblog.utils.shimmerEffect
import dev.jeziellago.compose.markdowntext.MarkdownText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogDetailsScreen(
    navController: NavController,
    id: String?,
) {
    val viewModel: BlogDetailsViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_left),
                                contentDescription = CONTENT_DESCRIPTION,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Text(
                            text = when (uiState.value.pageState) {
                                PageState.LOAD -> stringResource(id = R.string.load_label)
                                else -> uiState.value.title ?: ""
                            },
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 24.sp,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())
        ) {
            if (isLoad(uiState.value.pageState)) {
                ShimmeredBox(
                    modifier = Modifier
                        .shimmerEffect()
                        .fillMaxWidth()
                        .heightIn(min = 300.dp)
                )

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .fillMaxWidth()
                ) {
                    ShimmeredBox(
                        modifier = Modifier
                            .sizeIn(minHeight = 30.dp, minWidth = 80.dp)
                    )
                    ShimmeredBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .sizeIn(minHeight = 80.dp)
                    )
                    DetailsDivider()
                    ShimmeredBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .sizeIn(minHeight = 150.dp)
                    )
                }
            } else {
                BlogImage(uiState.value.image)

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .fillMaxWidth()
                ) {
                    BlogDate(uiState.value.date)
                    BlogTitle(title = uiState.value.subtitle)
                    DetailsDivider()
                    BlogContent(content = uiState.value.content)
                }
            }

        }
    }

    val lifeCycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifeCycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE)
                id?.let {
                    viewModel.getInfo(it)
                }
        }
        lifeCycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun DetailsDivider() {
    Divider(
        modifier = Modifier
            .padding(vertical = 16.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
fun BlogImage(image: ImageBitmap?) {
    image?.let {
        Image(
            modifier = Modifier.fillMaxWidth(),
            bitmap = it,
            contentDescription = CONTENT_DESCRIPTION,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun BlogDate(date: String?) {
    date?.let {
        Text(
            text = it.parseDate(),
            lineHeight = 16.sp,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun BlogTitle(title: String?) {
    title?.let {
        Text(
            text = title,
            lineHeight = 28.8.sp,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun BlogContent(content: String?) {
    content?.let {
        MarkdownText(
            markdown = content,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}