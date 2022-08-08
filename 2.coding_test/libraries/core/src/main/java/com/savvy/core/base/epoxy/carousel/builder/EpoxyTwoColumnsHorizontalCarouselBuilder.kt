package com.savvy.core.base.epoxy.carousel.builder

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.ModelCollector
import com.savvy.core.base.epoxy.carousel.TwoColumnsHorizontalCarouselModelBuilder
import com.savvy.core.base.epoxy.carousel.TwoColumnsHorizontalCarouselModel_

fun ModelCollector.twoColumnsHorizontalCarouselModelBuilder(
    builder: EpoxyTwoColumnsHorizontalCarouselBuilder.() -> Unit
): TwoColumnsHorizontalCarouselModel_ {
    val carouselBuilder = EpoxyTwoColumnsHorizontalCarouselBuilder()
        .apply { builder() }
    add(carouselBuilder.twoColumnsHorizontalCarouselModel)
    return carouselBuilder.twoColumnsHorizontalCarouselModel
}

class EpoxyTwoColumnsHorizontalCarouselBuilder(
    internal val twoColumnsHorizontalCarouselModel: TwoColumnsHorizontalCarouselModel_ = TwoColumnsHorizontalCarouselModel_()
) : ModelCollector, TwoColumnsHorizontalCarouselModelBuilder by twoColumnsHorizontalCarouselModel {
    private val models = mutableListOf<EpoxyModel<*>>()

    override fun add(model: EpoxyModel<*>) {
        models.add(model)
        twoColumnsHorizontalCarouselModel.models(models)
    }
}
