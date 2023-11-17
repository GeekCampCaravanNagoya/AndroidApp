package com.kotlincocktail.pourpal.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.kotlincocktail.pourpal.ui.theme.DarkBlue
import com.kotlincocktail.pourpal.ui.theme.DarkGray
import com.kotlincocktail.pourpal.ui.theme.DarkRed
import com.kotlincocktail.pourpal.ui.theme.Red
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ResultCardView(resultString: String) {
    val pagerState = rememberPagerState(pageCount = { 20 })
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val coroutineScope = rememberCoroutineScope()
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = Modifier
            .fillMaxSize(),
        drawerContent = {
            Box(modifier = Modifier
                .padding(start = 300.dp, top = 80.dp)
                .clip(
                    RoundedCornerShape(
                        topEnd = 16.dp,
                        bottomEnd = 16.dp,
                    )
                )
                .width(60.dp)
                .height(60.dp)
                .background(Red)
            )
            Column(
                Modifier
                    .padding(top = 80.dp)
                    .clip(
                        RoundedCornerShape(
                            bottomEnd = 16.dp,
                        )
                    )
                    .height(680.dp)
                    .width(300.dp)
                    .background(Red)
                    .padding(40.dp)
            ) {// TODO リスト表示場所
                LazyColumn(content = {
                    items(count = 5) {
                        Text(
                            fontSize = 25.sp,
                            text = "ジントニック",
                            modifier = Modifier.padding(4.dp).clickable{
                                coroutineScope.launch {
                                    drawerState.close()
                                    pagerState.animateScrollToPage(it)
                                }
                            }
                        )
                        HorizontalDivider(color = DarkRed)
                    }
                })
            }
        },
        drawerState = drawerState
    ){
        Box(modifier = Modifier
            .background(DarkBlue)){
            HorizontalPager(
                verticalAlignment = Alignment.CenterVertically,
                state = pagerState,
                pageSpacing = (20.dp),
                contentPadding = PaddingValues(horizontal = 40.dp)
            ) { page ->
                val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                CardContent(
                    resultString = resultString,
                    modifier = Modifier
                        .padding(vertical = (100 + (pageOffset * 20)).dp)
                        .fillMaxSize()
                        .graphicsLayer {
                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }
                )
            }
        }
        Box(modifier = Modifier
            .padding(top = 80.dp)
            .clip(
                RoundedCornerShape(
                    topEnd = 16.dp,
                    bottomEnd = 16.dp,
                )
            )
            .width(60.dp)
            .height(60.dp)
            .background(Red)
            .clickable {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }
        )
    }
}

@Composable
fun CardContent(resultString: String, modifier:Modifier) {//TODO　カード表示の場所
    ElevatedCard(
        colors = CardDefaults.cardColors(DarkGray),
        modifier = modifier
    ) {
        Text(text = resultString)
    }
}