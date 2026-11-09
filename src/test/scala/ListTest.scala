import adapters.out.InMemoryRideRepository
import config.TestConfig
import core.Service
import core.model.Ride

import java.time.{LocalDate, LocalDateTime, LocalTime}

class ListTest extends munit.FunSuite {

  def ride(rideId: Int, isOpen: Boolean, waitTime: Int, date: LocalDate = LocalDate.of(2026, 11, 6), time: LocalTime = LocalTime.of(5, 0)): Ride = {
    Ride(rideId, s"Ride_$rideId", isOpen, waitTime, date, time)
  }

  test("Returns the list ordered by id") {
    val fakeRides = List(
      ride(5, isOpen = true, waitTime = 10),
      ride(2, isOpen = true, waitTime = 10),
      ride(1, isOpen = true, waitTime = 10)
    )
    val service = TestConfig.service(fakeRides)
    assertEquals(service.list().map((ride: Ride) => ride.rideId), List(1,2,5))
  }

  // Show the date and time as well to be extra sure
  test("Returns latest record for each ride") {
    val fakeRides = List(
      ride(rideId = 3, isOpen = false, waitTime = 5, date = LocalDate.of(2026, 11, 5), time = LocalTime.of(8, 0)),
      ride(rideId = 3, isOpen = true, waitTime = 5, date = LocalDate.of(2026, 11, 6), time = LocalTime.of(4, 0))
    )
    val service = TestConfig.service(fakeRides)
    assertEquals(service.list().map((ride: Ride) => ride.rideId), List(3))
  }

  // Show the status of open and unknown rides
  test("Returns the list even ride is closed or have negative time") {
    val fakeRides = List(
      ride(5, isOpen = false, waitTime = 10),
      ride(2, isOpen = true, waitTime = -10)
    )
    val service = TestConfig.service(fakeRides)
    assertEquals(service.list().map((ride: Ride) => ride.rideId), List(2,5))
  }
}
