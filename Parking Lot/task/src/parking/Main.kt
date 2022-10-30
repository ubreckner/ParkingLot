package parking

class ParkingSpot(val registration: String, val color: String)

class ParkingSystem {
    private var parkingLotSize = 0
    private var spots: MutableList<ParkingSpot?>? = null

    fun create(arguments: List<String>) {
        parkingLotSize = arguments[1].toInt()
        spots = MutableList(parkingLotSize) { null }
        println("Created a parking lot with $parkingLotSize spots.")
    }

    fun park(arguments: List<String>) {
        if (arguments.size == 3) {
            if (spots != null) {
                val idx = findFirstEmptyIndex(spots!!)
                if (idx != -1) {
                    spots!![idx] = ParkingSpot(arguments[1], arguments[2])
                    println("${spots!![idx]?.color} car parked in spot ${idx + 1}.")
                } else {
                    println("Sorry, the parking lot is full.")
                }
            } else {
                println("Sorry, a parking lot has not been created.")
            }
        }
    }

    fun leave(arguments: List<String>) {
        if (arguments.size == 2) {
            if (spots != null) {
                val idx = arguments[1].toInt() - 1
                val removedSpot = spots!![idx]
                spots!![idx] = null
                if (removedSpot == null) {
                    println("There is no car in spot ${arguments[1]}.")
                } else {
                    println("Spot ${arguments[1]} is free.")
                }
            } else {
                println("Sorry, a parking lot has not been created.")
            }
        }
    }

    private fun findFirstEmptyIndex(spots: List<ParkingSpot?>): Int {
        var res = -1

        if (spots.filterNotNull().isEmpty()) {
            res = 0
        } else {
            for (i in 0 until parkingLotSize) {
                if (i >= spots.size) {
                    res = i
                    break
                } else {
                    if (spots[i] == null) {
                        res = i
                        break
                    }
                }
            }
        }
        return res
    }

    fun status() {
        if (spots != null) {
            if (spots!!.filterNotNull().isEmpty()) {
                println("Parking lot is empty.")
            } else {
                for (i in spots!!.indices) {
                    if (spots!![i] != null) {
                        println("${i + 1} ${spots!![i]?.registration} ${spots!![i]?.color}")
                    }
                }
            }
        } else {
            println("Sorry, a parking lot has not been created.")
        }
    }

    fun regByColor(arguments: List<String>) {
        val list = mutableListOf<String>()
        if (spots != null) {
            for (i in spots!!.indices) {
                if (spots!![i] != null && spots!![i]?.color?.lowercase() == arguments[1].lowercase()) {
                    list.add(spots!![i]?.registration!!)
                }
            }
            println(
                if (list.isEmpty()) {
                    "No cars with color ${arguments[1]} were found."
                } else {
                    list.joinToString(", ")
                }
            )
        } else {
            println("Sorry, a parking lot has not been created.")
        }
    }

    fun spotByColor(arguments: List<String>) {
        if (spots != null) {
            val list = mutableListOf<Int>()
            for (i in spots!!.indices) {
                if (spots!![i] != null && spots!![i]?.color?.lowercase() == arguments[1].lowercase()) {
                    list.add(i + 1)
                }
            }
            println(
                if (list.isEmpty()) {
                    "No cars with color ${arguments[1]} were found."
                } else {
                    list.joinToString(", ")
                }
            )
        } else {
            println("Sorry, a parking lot has not been created.")
        }
    }

    fun spotByReg(arguments: List<String>) {
        if (spots != null) {
            var res = -1
            for (i in spots!!.indices) {
                if (spots!![i] != null && spots!![i]?.registration?.lowercase() == arguments[1].lowercase()) {
                    res = i + 1
                    break
                }
            }
            println(
                if (res == -1) {
                    "No cars with registration number ${arguments[1]} were found."
                } else {
                    res
                }
            )
        } else {
            println("Sorry, a parking lot has not been created.")
        }
    }
}

fun main() {
    val parkingSystem = ParkingSystem()

    var input = readln()
    var arguments = input.split(" ")
    while (true) {
        when (arguments[0]) {
            "park" -> {
                parkingSystem.park(arguments)
            }

            "leave" -> {
                parkingSystem.leave(arguments)
            }

            "create" -> {
                parkingSystem.create(arguments)
            }

            "status" -> {
                parkingSystem.status()
            }

            "reg_by_color" -> {
                parkingSystem.regByColor(arguments)
            }

            "spot_by_color" -> {
                parkingSystem.spotByColor(arguments)
            }

            "spot_by_reg" -> {
                parkingSystem.spotByReg(arguments)
            }

            "exit" -> {
                break
            }

            else -> println("Wrong task: $input")
        }
        input = readln()
        arguments = input.split(" ")
    }
}
