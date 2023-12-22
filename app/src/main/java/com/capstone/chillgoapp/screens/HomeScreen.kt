package com.capstone.chillgoapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.chillgoapp.R
import com.capstone.chillgoapp.components.CardLoading
import com.capstone.chillgoapp.components.CityItem
import com.capstone.chillgoapp.components.HomeSection
import com.capstone.chillgoapp.components.PlaceItem
import com.capstone.chillgoapp.components.buttonSearch
import com.capstone.chillgoapp.data.home.HomeViewModel
import com.capstone.chillgoapp.data.home.PlaceUiState
import com.capstone.chillgoapp.data.home.RecommendedPlaceUiState
import com.capstone.chillgoapp.data.home.TopPlaceUiState
import com.capstone.chillgoapp.data.response.PlaceResponse
import com.capstone.chillgoapp.model.FakeTravelDataSource
import com.capstone.chillgoapp.model.OrderTravel
import com.capstone.chillgoapp.ui.theme.PrimaryBody

@Composable
fun Banner(
    modifier: Modifier = Modifier,
    onNavigateToMore: () -> Unit
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.banner),
            contentDescription = "Banner Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(160.dp),
        )
        buttonSearch(
            onNavigateToMore = onNavigateToMore
        )
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (Long) -> Unit = {},
    navigateToMore: () -> Unit = {},
) {
    val placeUiState by homeViewModel.placeUiState.collectAsState(initial = PlaceUiState())
    val topPlaceUiState by homeViewModel.topPlaceUiState.collectAsState(initial = TopPlaceUiState())
    val recommendedPlaceUiState by homeViewModel.recommendedPlaceUiState.collectAsState(initial = RecommendedPlaceUiState())

    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .background(PrimaryBody)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryBody)
        ) {
            Banner(
                onNavigateToMore = navigateToMore
            )

            HomeSection(
                title = stringResource(R.string.by_city),
                cityOnly = true,
                content = {
                        HomeCity(
                            modifier = modifier,
                            navigateToMore = navigateToMore,
                        )
                }
            )
            /*Spacer(modifier = Modifier.height(10.dp))*/
            HomeSection(
                title = stringResource(R.string.top_rating),
                /*showLocation = true,*/
                content = {
                    if (topPlaceUiState.loading) CardLoading() else
                        LazyRow(
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = modifier
                                .testTag("TravelList")
                                .background(PrimaryBody)
                        ) {
                            items(topPlaceUiState.data.size) { index ->
                                val data = topPlaceUiState.data[index].Place
                                var orderCount by remember {
                                    mutableIntStateOf(topPlaceUiState.data.size)
                                }

                                PlaceItem(
                                    imageUrl = data.image_url,
                                    placeId = data.id,
                                    placeName = data.place_name,
                                    placePrice = data.price,
                                    orderCount = orderCount,
                                    modifier = Modifier.clickable {
                                        navigateToDetail(data.id)
                                    },
                                    onFavClick = {
                                        if (orderCount == 0) {
                                            orderCount += 1
                                        } else {
                                            orderCount = 0
                                        }

                                    }
                                )
                            }
                        }
                },
                onTextSelected = {
                    navigateToMore()
                }
            )
            /* Spacer(modifier = Modifier.height(10.dp))*/
            HomeSection(
                title = stringResource(R.string.top_place),
                content = {

                    if (recommendedPlaceUiState.loading) CardLoading() else
                        LazyRow(
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = modifier
                                .testTag("TravelList")
                                .background(PrimaryBody)
                        ) {
                            items(recommendedPlaceUiState.data.size) { index ->
                                val data = recommendedPlaceUiState.data[index]
                                var orderCount by remember {
                                    mutableIntStateOf(recommendedPlaceUiState.data.size)
                                }

                                PlaceItem(
                                    imageUrl = data.placeImageUrl,
                                    placeId = data.id,
                                    placeName = data.placeName,
                                    placePrice = data.placePrice,
                                    orderCount = orderCount,
                                    modifier = Modifier.clickable {
                                        navigateToDetail(data.id)
                                    },
                                    onFavClick = {
                                        if (orderCount == 0) {
                                            orderCount += 1
                                        } else {
                                            orderCount = 0
                                        }

                                    }
                                )
                            }
                        }
                },
                onTextSelected = {
                    navigateToMore()
                }
            )

            /*Spacer(modifier = Modifier.height(10.dp))*/

        }
    }
}

@Composable
fun HomeCity(
    modifier: Modifier = Modifier,
    navigateToMore: () -> Unit
) {
    val cityImages = mapOf(
        "Bandung" to R.drawable.bandung,
        "Jakarta" to R.drawable.jakarta,
        "Sukabumi" to R.drawable.sukabumi,
        "Bogor" to R.drawable.bogor,
        "Yogyakarta" to R.drawable.yogyakarta,
        "Semarang" to R.drawable.semarang,
        "Surabaya" to R.drawable.surabaya
    )
    LazyRow(
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.testTag("TravelList")
    ) {
        items(cityImages.keys.toList()) { city ->
            val imageResId = cityImages[city] ?: R.drawable.bandung

            CityItem(
                image = imageResId,
                title = city,
                modifier = Modifier.clickable {
                    // Implement logic when a city is clicked
                    navigateToMore()
                }
            )
        }
    }
}


@Composable
fun HomeContent(
    orderTravel: List<PlaceResponse>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    LazyRow(
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .testTag("TravelList")
            .background(PrimaryBody)
    ) {
        items(orderTravel.size) { index ->
            val data = orderTravel[index]
            var orderCount by remember {
                mutableIntStateOf(orderTravel.size)
            }

            PlaceItem(
                imageUrl = data.image_url,
                placeId = data.id,
                placeName = data.place_name,
                placePrice = data.price,
                orderCount = orderCount,
                modifier = Modifier.clickable {
                    navigateToDetail(data.id)
                },
                onFavClick = {
                    if (orderCount == 0) {
                        orderCount += 1
                    } else {
                        orderCount = 0
                    }

                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(
    homeViewModel: HomeViewModel = viewModel(),
    navigateToMore: () -> Unit = {}
) {
    val orders = arrayListOf<OrderTravel>()
    FakeTravelDataSource.dummyTravels.forEach {
        orders.add(OrderTravel(it, 0))
    }

    HomeContent(
        orderTravel = listOf(),
        navigateToDetail = {},
    )
}