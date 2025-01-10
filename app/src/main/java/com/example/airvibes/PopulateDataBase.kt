package com.example.airvibes

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun populateDatabase(context: Context, viewModel: CategoryViewModel,webCategoryViewModel: WebCategoryViewModel) {

    withContext(Dispatchers.IO) {
        if (viewModel.isDatabasePopulated()) {
            return@withContext
        }
    }

    val categories = listOf(
        Category(
            cname = "Number",
            imageUrl = "https://img.freepik.com/premium-vector/online-booking-plane-tickets-travel-mobile-e-boarding-pass-isometric-couple-tourists-plane-suitcase-vector-illustration_259594-298.jpg?w=740"
        ),
        Category(
            cname = "Airlines",
            imageUrl = "https://img.freepik.com/premium-vector/world-map-is-pinned-plan-travel-by-international-airlines-with-luggage-plane-tickets_68708-2289.jpg?w=740"
        ),
        Category(
            cname = "News        ",
            imageUrl = "https://img.freepik.com/premium-vector/air-traffic-control-abstract-concept-vector-illustration_107173-25556.jpg?w=740"
        ),
        Category(
            cname = "Tickets",
            imageUrl = "https://img.freepik.com/premium-vector/woman-with-plane-tickets-concept-young-girl-with-luggage-baggage-traveller-with-mobile-application-tourist-with-bag-journey-holiday-vacations-cartoon-flat-vector-illustration_1002658-1149.jpg?w=900"
        ),
        Category(
            cname = "Nearby",
            imageUrl = "https://img.freepik.com/premium-vector/online-map-phone-search-route-map-phone-navigator_647843-79.jpg?w=740"
        ),

        )
    viewModel.insertAll(categories)
    withContext(Dispatchers.IO) {
        if (webCategoryViewModel.isDatabasePopulated()) {
            return@withContext
        }
    }




}