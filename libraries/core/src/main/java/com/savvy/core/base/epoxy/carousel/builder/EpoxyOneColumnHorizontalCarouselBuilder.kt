package com.savvy.core.base.epoxy.carousel.builder

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.ModelCollector
import com.savvy.core.base.epoxy.carousel.OneColumnHorizontalCarouselModelBuilder
import com.savvy.core.base.epoxy.carousel.OneColumnHorizontalCarouselModel_

fun ModelCollector.oneColumnHorizontalCarouselModelBuilder(
    builder: EpoxyOneColumnHorizontalCarouselBuilder.() -> Unit
): OneColumnHorizontalCarouselModel_ {
    val carouselBuilder = EpoxyOneColumnHorizontalCarouselBuilder()
        .apply { builder() }
    add(carouselBuilder.oneColumnHorizontalCarouselModel)
    return carouselBuilder.oneColumnHorizontalCarouselModel
}

class EpoxyOneColumnHorizontalCarouselBuilder(
    internal val oneColumnHorizontalCarouselModel: OneColumnHorizontalCarouselModel_ = OneColumnHorizontalCarouselModel_()
) : ModelCollector, OneColumnHorizontalCarouselModelBuilder by oneColumnHorizontalCarouselModel {
    private val models = mutableListOf<EpoxyModel<*>>()

    override fun add(model: EpoxyModel<*>) {
        models.add(model)
        oneColumnHorizontalCarouselModel.models(models)
    }
}
