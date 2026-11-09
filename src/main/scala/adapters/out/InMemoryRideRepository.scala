package adapters.out

import core.model.Ride
import core.ports.RideRepository

class InMemoryRideRepository(rides: List[Ride]) extends RideRepository {

  override def findAll(): List[Ride] = rides
}
