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
    lateinit var imagesDead: List<Bitmap>
    lateinit var imagesMove: List<Bitmap>
    lateinit var currentMoveImage: Bitmap
    var isDecomposing: Boolean = false
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
        val resourceId5 = context.resources.getIdentifier(insectName + "_5", "drawable", packageName)
        val resourceId6 = context.resources.getIdentifier(insectName + "_6", "drawable", packageName)
        imagesMove = listOf(
            BitmapFactory.decodeResource(context.resources, resourceId1),
            BitmapFactory.decodeResource(context.resources, resourceId2),
            BitmapFactory.decodeResource(context.resources, resourceId3))
        imagesDead = listOf(
            BitmapFactory.decodeResource(context.resources, resourceId4),
            BitmapFactory.decodeResource(context.resources, resourceId5),
            BitmapFactory.decodeResource(context.resources, resourceId6)
        )
        currentMoveImage = imagesMove[imageIndex]
    }

    fun moveInsect() {
        while(isLiving) {
            val radianDirectionAngle = Math.toRadians(directionAngle.toDouble())

            var temp = (-5 .. 5).random()
            if (directionAngle + temp < 0 || directionAngle + temp > 360)
            else directionAngle = directionAngle + temp

            var tempX = coordX + distancePerStep * cos(radianDirectionAngle)
            var tempY = coordY + distancePerStep * sin(radianDirectionAngle)

            var tempCoordCenterX = coordCenterX + distancePerStep * cos(radianDirectionAngle)
            var tempCoordCenterY = coordCenterY + distancePerStep * sin(radianDirectionAngle)

            if (tempCoordCenterX < 0 || tempCoordCenterX > screenWidth || tempCoordCenterY < 0 || tempCoordCenterY > screenHeight) {
                directionAngle = (0..360).random().toFloat()
                continue
            } else {
                coordX = tempX
                coordY = tempY
                coordCenterX = tempCoordCenterX
                coordCenterY = tempCoordCenterY
                imageIndex = (imageIndex + 1) % imagesMove.size
                currentMoveImage = imagesMove[imageIndex]
                Thread.sleep(201 - speed.toLong())
            }
        }
    }

    fun isKilled(){
        isLiving = false

        for (image in imagesDead) {
            currentMoveImage = image
            Thread.sleep(3000)
        }
        isDecomposing = true
    }
}