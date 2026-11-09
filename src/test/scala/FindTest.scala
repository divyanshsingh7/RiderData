import adapters.out.InMemoryRideRepository
import config.TestConfig
import core.Service
import core.model.Ride

import java.time.{LocalDate, LocalDateTime, LocalTime}

class FindTest extends munit.FunSuite {

    def ride(rideId: Int, isOpen: Boolean, waitTime: Int, date: LocalDate = LocalDate.of(2026, 11, 6), time: LocalTime = LocalTime.of(5, 0)): Ride = {
      Ride(rideId, s"Ride_$rideId", isOpen, waitTime, date, time)
    }

    test("Finds the requested ride") {
      val fakeRides = List(
        ride(3, isOpen = true, waitTime = 5),
        ride(10, isOpen = true, waitTime = 10)
      )
      val service = TestConfig.service(fakeRides)
      assertEquals(service.find(10).map((ride: Ride) => ride.rideId), Some(10))
    }

  test("Returns None if requested ride is nothing present") {
    val fakeRides = List(
      ride(3, isOpen = true, waitTime = 5),
      ride(10, isOpen = true, waitTime = 10)
    )
    val service = TestConfig.service(fakeRides)
    assertEquals(service.find(1000).map((ride: Ride) => ride.rideId), None)
  }

}
