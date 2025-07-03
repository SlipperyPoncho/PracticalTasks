package com.artem.android.newsfeature

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.artem.android.core.data.getDrawableFromName
import com.artem.android.core.data.setDateText
import com.artem.android.core.domain.models.EventModel
import com.artem.android.newsfeature.viewmodel.NewsFragmentViewModel
import java.util.UUID

@Composable
fun NewsScreen(
    newsViewModel: NewsFragmentViewModel,
    context: Context,
    onNewsClick: (UUID) -> Unit,
    onFilterClick: () -> Unit
) {
    val primaryColor = Color(android.graphics.Color.parseColor("#66a636"))
    val unreadNewsCount = newsViewModel.unreadNewsCounter.collectAsState()
    val newsCount = newsViewModel.newsEvents.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Toolbar
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = com.artem.android.core.R.string.news_toolbar_text),
                    fontFamily = FontFamily(Font(com.artem.android.core.R.font.officina_sans_extra_bold)),
                    color = Color.White,
                    fontSize = 21.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            navigationIcon = {
                if (unreadNewsCount.value > 0) {
                    Box(
                        modifier = Modifier
                            .padding(start = 20.dp, top = 8.dp)
                            .size(24.dp)
                            .background(Color.Red, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = unreadNewsCount.value.toString(),
                            color = Color.White,
                            fontFamily = FontFamily(Font(com.artem.android.core.R.font.officina_sans_extra_bold)),
                            fontSize = 12.sp
                        )
                    }
                }
            },
            backgroundColor = primaryColor,
            actions = {
                IconButton(onClick = { onFilterClick() }) {
                    Icon(
                        painter = painterResource(id = com.artem.android.core.R.drawable.filter),
                        contentDescription = stringResource(id = com.artem.android.core.R.string.app_name),
                        tint = Color.White
                    )
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(newsCount.value ?: emptyList()) {
                ListItemNews(event = it, context = context, onNewsClick = onNewsClick)
            }
        }
    }
}

@Composable
fun ListItemNews(
    event: EventModel,
    context: Context,
    onNewsClick: (UUID) -> Unit
) {
    val primaryColor = Color(android.graphics.Color.parseColor("#66a636"))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
            .clickable { onNewsClick(event.id) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            getDrawableFromName(context, event.images?.get(0) ?: "")?.toBitmap()?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = stringResource(id = com.artem.android.core.R.string.app_name),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentScale = ContentScale.Crop
                )
            }
            Image(
                painter = painterResource(id = com.artem.android.core.R.drawable.fade),
                contentDescription = stringResource(id = com.artem.android.core.R.string.app_name),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = event.title,
            fontFamily = FontFamily(Font(com.artem.android.core.R.font.officina_sans_extra_bold)),
            color = Color(0xFF687B8F),
            textAlign = TextAlign.Center,
            fontSize = 21.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = com.artem.android.core.R.drawable.decor),
            contentDescription = stringResource(id = com.artem.android.core.R.string.app_name),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = event.eventDetails,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(primaryColor)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = com.artem.android.core.R.drawable.icon_calendar),
                    contentDescription = stringResource(id = com.artem.android.core.R.string.app_name),
                    modifier = Modifier.size(16.dp)
                )
                setDateText(event)?.let {
                    Text(
                        text = it,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}