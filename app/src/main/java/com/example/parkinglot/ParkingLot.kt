package com.example.parkinglot

import com.example.parkinglot.model.*

class ParkingLot(
    private val numMotorbikeSpotsAvailable: Int,
    private val numCarSpotsAvailable: Int,
    private val numVanSpotsAvailable: Int
) {
    private val spots = arrayListOf<Spot>()
    private var numMotorbikeSpotsUsed = 0
    private var numCarSpotsUsed = 0
    private var numVanSpotsUsed = 0

    fun park(vehicle: Vehicle) {
        when (vehicle) {
            is Motorbike -> {
                if (!isMotorcycleSpotsFull()) {
                    spots.add(Spot(Motorbike()))
                    numMotorbikeSpotsUsed++
                } else if (!isCarSpotsFull()) {
                    spots.add(Spot(Motorbike()))
                    numCarSpotsUsed++
                } else if (!isVanSpotsFull()) {
                    spots.add(Spot(Motorbike()))
                    numVanSpotsUsed++
                }
            }
            is Car -> {
                if (!isCarSpotsFull()) {
                    spots.add(Spot(Car()))
                    numCarSpotsUsed++
                } else if (!isVanSpotsFull()) {
                    spots.add(Spot(Car()))
                    numVanSpotsUsed++
                }
            }
            is Van -> {
                if (!isVanSpotsFull()) {
                    spots.add(Spot(Van()))
                    numVanSpotsUsed++
                } else if (isCarSpotsForVan()) {
                    spots.add(Spot(Van()))
                    numCarSpotsUsed += 3
                }
            }
        }
    }

    fun isMotorcycleSpotsFull(): Boolean {
        return numMotorbikeSpotsAvailable == numMotorbikeSpotsUsed
    }

    fun isCarSpotsFull(): Boolean {
        return numCarSpotsAvailable == numCarSpotsUsed
    }

    fun isVanSpotsFull(): Boolean {
        return numVanSpotsAvailable == numVanSpotsUsed
    }

    fun isCarSpotsForVan(): Boolean {
        return numCarSpotsAvailable.minus(
            spots.count {
                it.vehicle is Car
            }
        ) >= 3
    }

    fun getTotalSpots(): Int {
        return numMotorbikeSpotsAvailable + numCarSpotsAvailable + numVanSpotsAvailable
    }

    fun getTotalVehiclesParked(): Int = spots.size

    fun getSpotsUsed(): Int = numMotorbikeSpotsUsed + numCarSpotsUsed + numVanSpotsUsed

    fun getSpotsLeft(): Int {
        return getTotalSpots().minus(getSpotsUsed())
    }

    fun isParkingLotFull(): Boolean {
        return getTotalSpots() == getSpotsUsed()
    }

    fun isParkingLotEmpty(): Boolean = getTotalVehiclesParked() == 0

    fun getSpotsUsedByVans(): Int {
        spots.count {
            it.vehicle is Car
        }.let { carCount ->
            return (numCarSpotsUsed.minus(carCount)).plus(numVanSpotsUsed)
        }
    }
}

fun main() {
    val parkingLot = ParkingLot(10, 10, 2)
    arrayListOf<Vehicle>().apply {
        for (i in 0 until 10) {
            add(Motorbike())
        }
        for (i in 0 until 7) {
            add(Car())
        }
        for (i in 0 until 3) {
            add(Van())
        }
        forEach {
            parkingLot.park(it)
        }
    }
    parkingLot.apply {
        println("Vagas Restantes: ${parkingLot.getSpotsLeft()}")
        println("Vagas Totais: ${parkingLot.getTotalSpots()}")
        println("Vagas Ocupadas por vans: ${parkingLot.getSpotsUsedByVans()}")
    }
}