package adapters.in

import core.model.Ride
import core.ports.RideQueryService

class CommandHandler(service: RideQueryService) {

  def handle(line: String): String = {
    val commands = line.trim.split(" ").toList
    val firstCommand = commands.head

    firstCommand match {
      case "list" =>
        val listRides = service.list()
        if listRides.isEmpty then
          "There are no rides"
        else
          listRides.map(formatRide).mkString("\n")

      case "find" =>
        if (commands.length < 2) {
          "Please enter a ride ID"
        }
        else {
          val rideId = commands(1).toIntOption
          if (rideId.isEmpty) {
            "Invalid ride ID"
          }
          else {
            val requestedRide = service.find(rideId.get)
            if requestedRide.isEmpty then
              "Ride not found"
            else
              formatRide(requestedRide.get)
          }
        }

      case "recommend" =>
        val recommendRides = service.recommend()
        if (recommendRides.isEmpty) {
          "There are no rides for recommendation"
        }
        else {
          recommendRides.map(formatRide).mkString("\n")
        }

      case _ =>
        "Unknown command"
    }
  }

  private def formatRide(ride: Ride): String = {
    var openString: String = ""
    var waitTime: String = ""

    if ride.isOpen then
      openString = "Open"
    else
      openString = "Closed"

    if ride.waitTime < 0 then
      waitTime = "Unknown"
    else
      waitTime = ride.waitTime.toString

    val resultString = s"${ride.rideId} -- ${ride.rideName} -- $openString -- $waitTime -- ${ride.date} -- ${ride.time}"
    resultString
  }
}
