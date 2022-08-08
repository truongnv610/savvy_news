package com.savvy.app.base.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import com.savvy.app.R

class CardLayout : FrameLayout {

    companion object {
        const val SEMI_CIRCLE_NONE = 0x00
        const val SEMI_CIRCLE_LEFT = 0x01
        const val SEMI_CIRCLE_TOP = 0x02
        const val SEMI_CIRCLE_RIGHT = 0x04
        const val SEMI_CIRCLE_BOTTOM = 0x08
        const val SEMI_CIRCLE_HORIZONTAL = SEMI_CIRCLE_LEFT or SEMI_CIRCLE_RIGHT
        const val SEMI_CIRCLE_VERTICAL = SEMI_CIRCLE_TOP or SEMI_CIRCLE_BOTTOM
        const val SEMI_CIRCLE_ALL = SEMI_CIRCLE_HORIZONTAL or SEMI_CIRCLE_VERTICAL
        const val CORNER_NONE = 0x00
        const val CORNER_TOP_LEFT = 0x01
        const val CORNER_TOP_RIGHT = 0x02
        const val CORNER_BOTTOM_LEFT = 0x04
        const val CORNER_BOTTOM_RIGHT = 0x08
        const val CORNER_TOP = CORNER_TOP_LEFT or CORNER_TOP_RIGHT
        const val CORNER_BOTTOM = CORNER_BOTTOM_LEFT or CORNER_BOTTOM_RIGHT
        const val CORNER_LEFT = CORNER_TOP_LEFT or CORNER_BOTTOM_LEFT
        const val CORNER_RIGHT = CORNER_TOP_RIGHT or CORNER_BOTTOM_RIGHT
        const val CORNER_ALL = CORNER_TOP_LEFT or CORNER_TOP_RIGHT or CORNER_BOTTOM_LEFT or CORNER_BOTTOM_RIGHT
        private const val FORCE_MOVE = false
    }

    private var borderWidth = 4f
    private var cornerRadius = 32f
    private var semiCircleRadius = 48f
    private var backgroundPathColor = Color.BLACK
    private var backgroundPathColorStateList: ColorStateList? = null
    private var borderPathColor = Color.parseColor("#000000")
    private var borderPathColorStateList: ColorStateList? = null
    private var cornerDirection = CORNER_ALL
    private var semiCircleDirection = SEMI_CIRCLE_HORIZONTAL

    private val borderPaint = Paint().apply { isAntiAlias = true }
    private val backgroundPaint = Paint().apply { isAntiAlias = true }
    private val path = Path()

    constructor(context: Context) : super(context) { setup(null, 0) }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { setup(attrs, 0) }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setup(attrs, defStyleAttr)
    }

    fun setCardBackgroundColor(@ColorInt colorId: Int): CardLayout {
        backgroundPathColor = colorId
        postInvalidate()
        return this
    }

    fun setCardBackgroundColor(colorStateList: ColorStateList): CardLayout {
        backgroundPathColorStateList = colorStateList
        postInvalidate()
        return this
    }

    fun setCardBorderColor(@ColorInt colorId: Int): CardLayout {
        borderPathColor = colorId
        postInvalidate()
        return this
    }

    fun setCardBorderColor(colorStateList: ColorStateList): CardLayout {
        borderPathColorStateList = colorStateList
        postInvalidate()
        return this
    }

    private fun setup(attrs: AttributeSet?, defStyleAttr: Int) {
        setWillNotDraw(false)
        val attribute = context.obtainStyledAttributes(attrs, R.styleable.CardLayout, defStyleAttr, 0)
        attribute.apply {
            borderWidth = getDimension(R.styleable.CardLayout_card_borderWidth, 4f)
            cornerRadius = getDimension(R.styleable.CardLayout_card_cornerRadius, 32f)
            semiCircleRadius = getDimension(R.styleable.CardLayout_card_semiCircleRadius, 48f)
            backgroundPathColor = getColor(R.styleable.CardLayout_card_backgroundColor, Color.WHITE)
            backgroundPathColorStateList = getColorStateList(R.styleable.CardLayout_card_backgroundColor)
            borderPathColor = getColor(R.styleable.CardLayout_card_borderColor, Color.parseColor("#00000000"))
            borderPathColorStateList = getColorStateList(R.styleable.CardLayout_card_borderColor)
            cornerDirection = getInt(R.styleable.CardLayout_card_cornerDirection, CORNER_ALL)
            semiCircleDirection = getInt(R.styleable.CardLayout_card_semiCircleDirection, SEMI_CIRCLE_NONE)
            recycle()
        }
    }

    private fun setupPaint() {
        borderPaint.color = getAvailableColor(borderPathColor, borderPathColorStateList)
        borderPaint.strokeWidth = borderWidth
        borderPaint.style = Paint.Style.STROKE

        backgroundPaint.color = getAvailableColor(backgroundPathColor, backgroundPathColorStateList)
        backgroundPaint.style = Paint.Style.FILL
    }

    @ColorInt
    private fun getAvailableColor(@ColorInt color: Int, colorStateList: ColorStateList?): Int =
        if (colorStateList != null) {
            val defaultColor = colorStateList.getColorForState(intArrayOf(), 0)
            colorStateList.getColorForState(drawableState, defaultColor)
        } else {
            color
        }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        setupPath()
        setupPaint()
        canvas?.let {
            it.drawPath(path, backgroundPaint)
            it.drawPath(path, borderPaint)
        }
    }

    private fun setupPath() {
        val isTopRightRequired = isCornerDirectionRequired(CORNER_TOP_RIGHT)
        val isBottomRightRequired = isCornerDirectionRequired(CORNER_BOTTOM_RIGHT)
        val isBottomLeftRequired = isCornerDirectionRequired(CORNER_BOTTOM_LEFT)
        val isTopLeftRequired = isCornerDirectionRequired(CORNER_TOP_LEFT)
        val isRightRequired = isSemiCircleDirectionRequired(SEMI_CIRCLE_RIGHT)
        val isBottomRequired = isSemiCircleDirectionRequired(SEMI_CIRCLE_BOTTOM)
        val isLeftRequired = isSemiCircleDirectionRequired(SEMI_CIRCLE_LEFT)
        val isTopRequired = isSemiCircleDirectionRequired(SEMI_CIRCLE_TOP)

        val areaWidth = width
        val areaHeight = height

        path.apply {
            reset()
            moveTo(
                areaWidth - (cornerRadius + (borderWidth / 2f)),
                borderWidth / 2f
            )

            topRightCorner(areaWidth, getCornerRadius(isTopRightRequired))
            if (isRightRequired) rightSemiCircle(areaWidth, areaHeight)

            bottomRightCorner(areaWidth, areaHeight, getCornerRadius(isBottomRightRequired))
            if (isBottomRequired) bottomSemiCircle(areaWidth, areaHeight)

            bottomLeftCorner(areaHeight, getCornerRadius(isBottomLeftRequired))
            if (isLeftRequired) leftSemiCircle(areaHeight)

            topLeftCorner(getCornerRadius(isTopLeftRequired))
            if (isTopRequired) topSemiCircle(areaWidth)

            close()
        }
    }

    private fun isCornerDirectionRequired(direction: Int) = cornerDirection and direction == direction
    private fun isSemiCircleDirectionRequired(direction: Int) = semiCircleDirection and direction == direction
    private fun getCornerRadius(isRequired: Boolean) = if (isRequired) cornerRadius else 0f

    private fun Path.topRightCorner(areaWidth: Int, cornerRadius: Float) {
        arcTo(
            areaWidth - ((cornerRadius * 2f) + (borderWidth / 2f)),
            borderWidth / 2f,
            areaWidth - (borderWidth / 2f),
            (cornerRadius * 2f) + (borderWidth / 2f),
            270f,
            90f,
            FORCE_MOVE
        )
    }

    private fun Path.rightSemiCircle(areaWidth: Int, areaHeight: Int) {
        arcTo(
            areaWidth - ((semiCircleRadius / 2f) + (borderWidth / 2f)),
            (areaHeight / 2f) - (semiCircleRadius / 2f),
            areaWidth + ((semiCircleRadius / 2f) - (borderWidth / 2f)),
            (areaHeight / 2f) + (semiCircleRadius / 2f),
            270f,
            -180f,
            FORCE_MOVE
        )
    }

    private fun Path.bottomRightCorner(areaWidth: Int, areaHeight: Int, cornerRadius: Float) {
        arcTo(
            areaWidth - ((cornerRadius * 2f) + (borderWidth / 2f)),
            areaHeight - ((cornerRadius * 2f) + (borderWidth / 2f)),
            areaWidth - (borderWidth / 2f),
            areaHeight - (borderWidth / 2f),
            0f,
            90f,
            FORCE_MOVE
        )
    }

    private fun Path.bottomSemiCircle(areaWidth: Int, areaHeight: Int) {
        arcTo(
            (areaWidth / 2f) - (semiCircleRadius / 2f),
            areaHeight - ((semiCircleRadius / 2f) + (borderWidth / 2f)),
            (areaWidth / 2f) + (semiCircleRadius / 2f),
            areaHeight + ((semiCircleRadius / 2f) - (borderWidth / 2f)),
            0f,
            -180f,
            FORCE_MOVE
        )
    }

    private fun Path.bottomLeftCorner(areaHeight: Int, cornerRadius: Float) {
        arcTo(
            borderWidth / 2f,
            areaHeight - ((cornerRadius * 2f) + (borderWidth / 2f)),
            (cornerRadius * 2) + (borderWidth / 2f),
            areaHeight - (borderWidth / 2f),
            90f,
            90f,
            FORCE_MOVE
        )
    }

    private fun Path.leftSemiCircle(areaHeight: Int) {
        arcTo(
            -(semiCircleRadius / 2f) + (borderWidth / 2f),
            (areaHeight / 2f) - (semiCircleRadius / 2f),
            (semiCircleRadius / 2f) + (borderWidth / 2f),
            (areaHeight / 2f) + (semiCircleRadius / 2f),
            90f,
            -180f,
            FORCE_MOVE
        )
    }

    private fun Path.topLeftCorner(cornerRadius: Float) {
        arcTo(
            borderWidth / 2f,
            borderWidth / 2f,
            (cornerRadius * 2f) + (borderWidth / 2f),
            (cornerRadius * 2f) + (borderWidth / 2f),
            180f,
            90f,
            FORCE_MOVE
        )
    }

    private fun Path.topSemiCircle(areaWidth: Int) {
        arcTo(
            (areaWidth / 2f) - (semiCircleRadius / 2f),
            -(semiCircleRadius / 2f) + (borderWidth / 2f),
            (areaWidth / 2f) + (semiCircleRadius / 2f),
            (semiCircleRadius / 2f) + (borderWidth / 2f),
            180f,
            -180f,
            FORCE_MOVE
        )
    }
}
