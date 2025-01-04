package com.example.beetles

class Cockroach(
    itLiving: Boolean,
    coordX: Double,
    coordY: Double,
    directionAngle: Float,
    distancePerStep: Int,
    screenWidth: Int,
    screenHeight: Int, speed: Int
) : Insect(itLiving, coordX, coordY, directionAngle, distancePerStep, screenWidth, screenHeight,
    speed
) {
    override var insectName: String = "cockroach"
    }