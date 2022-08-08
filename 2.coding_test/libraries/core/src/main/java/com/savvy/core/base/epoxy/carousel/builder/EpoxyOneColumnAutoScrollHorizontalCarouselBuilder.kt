package com.savvy.core.base.epoxy.carousel.builder

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.ModelCollector
import com.savvy.core.base.epoxy.carousel.OneColumnAutoScrollHorizontalCarouselModelBuilder
import com.savvy.core.base.epoxy.carousel.OneColumnAutoScrollHorizontalCarouselModel_

fun ModelCollector.oneColumnAutoScrollHorizontalCarouselModelBuilder(
    builder: EpoxyOneColumnAutoScrollHorizontalCarouselBuilder.() -> Unit
): OneColumnAutoScrollHorizontalCarouselModel_ {
    val carouselBuilder = EpoxyOneColumnAutoScrollHorizontalCarouselBuilder()
        .apply { builder() }
    add(carouselBuilder.oneColumnAutoScrollHorizontalCarouselModel)
    return carouselBuilder.oneColumnAutoScrollHorizontalCarouselModel
}

class EpoxyOneColumnAutoScrollHorizontalCarouselBuilder(
    internal val oneColumnAutoScrollHorizontalCarouselModel: OneColumnAutoScrollHorizontalCarouselModel_ = OneColumnAutoScrollHorizontalCarouselModel_()
) : ModelCollector,
    OneColumnAutoScrollHorizontalCarouselModelBuilder by oneColumnAutoScrollHorizontalCarouselModel {
    private val models = mutableListOf<EpoxyModel<*>>()

    override fun add(model: EpoxyModel<*>) {
        models.add(model)
        oneColumnAutoScrollHorizontalCarouselModel.models(models)
    }
}
