package config

import adapters.out.InMemoryRideRepository
import core.Service
import core.model.Ride
import core.ports.RideQueryService

object TestConfig {
  def service(rides: List[Ride]): RideQueryService = {
    Service(InMemoryRideRepository(rides))
  }
}
