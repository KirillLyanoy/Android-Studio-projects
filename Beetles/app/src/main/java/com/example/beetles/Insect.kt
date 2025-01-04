package com.example.beetles

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlin.math.cos
import kotlin.math.sin

abstract class Insect(
    var isLiving: Boolean,
    var coordX: Double,
    var coordY: Double,
    var directionAngle: Float,
    var distancePerStep: Int,
    var screenWidth: Int,
    var screenHeight: Int,
    var speed: Int) {
    abstract var insectName:String
    lateinit var imageDead: Bitmap
    lateinit var imagesMove: List<Bitmap>
    lateinit var currentMoveImage: Bitmap
    var coordCenterX: Double = coordX + 25
    var coordCenterY: Double = coordY + 12.5

    var imageIndex: Int = 0

    @SuppressLint("DiscouragedApi")
    fun loadImages(context: Context) {
        val packageName = context.packageName
        val resourceId1 = context.resources.getIdentifier(insectName + "_1", "drawable", packageName)
        val resourceId2 = context.resources.getIdentifier(insectName + "_2", "drawable", packageName)
        val resourceId3 = context.resources.getIdentifier(insectName + "_3", "drawable", packageName)
        val resourceId4 = context.resources.getIdentifier(insectName + "_4", "drawable", packageName)
        imagesMove = listOf(
            BitmapFactory.decodeResource(context.resources, resourceId1),
            BitmapFactory.decodeResource(context.resources, resourceId2),
            BitmapFactory.decodeResource(context.resources, resourceId3))
        imageDead = BitmapFactory.decodeResource(context.resources, resourceId4)
        currentMoveImage = imagesMove[imageIndex]
    }

    fun moveInsect() {
        while(true) {
            if (isLiving) {
                var X = coordX + distancePerStep * cos(directionAngle * 0.0174533)
                var Y = coordY + distancePerStep * sin(directionAngle * 0.0174533)

                if (X + 25 < 0 || X + 25 > screenWidth || Y + 12.5 < 0 || Y + 12.5 > screenHeight) {
                    directionAngle = (0..360).random().toFloat()
                    continue
                } else {
                    coordX = X
                    coordY = Y
                    coordCenterX = coordX + 25
                    coordCenterY = coordY + 12.5
                    if (imageIndex + 1 >= 3) imageIndex = 0
                    else imageIndex++
                    currentMoveImage = imagesMove[imageIndex]

                    var temp = (-5 .. 5).random()
                    if (directionAngle + temp < 0 || directionAngle + temp > 360)
                    else directionAngle = directionAngle + temp

                    Thread.sleep(speed.toLong())
                }
            } else break
        }
    }
}