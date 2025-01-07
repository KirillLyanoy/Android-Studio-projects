package com.example.beetles

import java.io.Serializable

class Settings (var playerName: String = "Игрок", var musicVolume: Boolean = true, var soundVolume: Boolean = true,
                var beetlesMoveSpeed: Int = 100, var beetlesRespawnSpeed: Int = 1000, var beetlesMax: Int = 20): Serializable {
}