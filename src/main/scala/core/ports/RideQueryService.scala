package core.ports

import core.model.Ride

// Input port: The CLI and batch executor adapters can ask
trait RideQueryService:
  def list(): List[Ride]
  def find(rideId: Int): Option[Ride]
  def recommend(): List[Ride]
