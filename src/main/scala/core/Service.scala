package core

import core.model.Ride
import core.ports.*

import scala.collection.mutable.Map

class Service(rideRepository: RideRepository) extends RideQueryService:

  // Helper Method to get the latest state of each ride
  private def currentStates(): List[Ride] = {
    val allRecords = rideRepository.findAll()
    val newest = Map[Int, Ride]()

    for record <- allRecords do
      newest.get(record.rideId) match
        case None =>
          newest(record.rideId) = record

        case Some(existing) =>
          if record.recordedTime.isAfter(existing.recordedTime) then
            newest(record.rideId) = record
    
    newest.values.toList.sortBy((ride: Ride) => ride.rideId)
  }

  override def list(): List[Ride] = {
    currentStates()
  }

  override def find(rideId: Int): Option[Ride] = {
    val listRecords: List[Ride] = currentStates()
    var findRide: Option[Ride] = None

    for ride <- listRecords do
      if ride.rideId == rideId then
        findRide = Some(ride)

    findRide
  }

  override def recommend(): List[Ride] = {
    val listRecords: List[Ride] = currentStates()
    var recommendRide: List[Ride] = List.empty

    for ride <- listRecords do
      if (ride.isOpen && ride.waitTime >= 0)
        recommendRide = recommendRide :+ ride

    recommendRide = recommendRide.sortBy((ride: Ride) => (ride.waitTime, ride.rideId))
    recommendRide = recommendRide.take(3)
    recommendRide
  }
