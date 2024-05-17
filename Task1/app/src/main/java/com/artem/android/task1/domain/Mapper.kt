package com.artem.android.task1.domain

import com.artem.android.task1.data.Category
import com.artem.android.task1.data.Event
import javax.inject.Inject

class Mapper @Inject constructor() {
    fun mapCategories(categories: List<Category>): List<CategoryModel> {
        return categories.map {
            CategoryModel(
                title = it.title,
                image = it.image,
                categoryType = it.categoryType
            )
        }
    }

    fun mapEvents(events: List<Event>): List<EventModel> {
        return events.map {
            EventModel(
                id = it.id,
                title = it.title,
                images = it.images,
                eventDetails = it.eventDetails,
                eventDateStart = it.eventDateStart,
                eventDateFinish = it.eventDateFinish,
                sponsor = it.sponsor,
                address = it.address,
                phoneNumbers = it.phoneNumbers,
                detailsText1 = it.detailsText1,
                detailsText2 = it.detailsText2,
                categories = it.categories
            )
        }
    }
}