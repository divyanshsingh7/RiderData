import adapters.out.InMemoryRideRepository
import config.TestConfig
import core.Service
import core.model.Ride

import java.time.{LocalDate, LocalDateTime, LocalTime}

class RecommendTest extends munit.FunSuite {

  def ride(rideId: Int, isOpen: Boolean, waitTime: Int, date: LocalDate = LocalDate.of(2026, 11, 6), time: LocalTime = LocalTime.of(5,0)): Ride = {
    Ride(rideId, s"Ride_$rideId", isOpen, waitTime, date, time)
  }

  test("Closed rides are not recommended") {
    // 1. The TEST creates the data
    val fakeRides = List(
      ride(1, isOpen = true, waitTime = 10),
      ride(2, isOpen = false, waitTime = 0)
    )
//    // 2. The data goes IN through the constructor
//    val repo = InMemoryRideRepository(fakeRides)
//    // 3. The service gets the repository
//    val service = Service(repo)

    val service = TestConfig.service(fakeRides)
    // 4. recommend() calls repo.findAll() inside, and the data comes OUT to the service
    assertEquals(service.recommend().map((ride: Ride) => ride.rideId), List(1))
  }

  test("Returns atmost three rides") {
    val fakeRides = List(
      ride(rideId = 3, isOpen = true, waitTime = 10),
      ride(rideId = 10, isOpen = true, waitTime = 15),
      ride(rideId = 8, isOpen = true, waitTime = 15),
      ride(rideId = 5, isOpen = true, waitTime = 30)
    )

    val service = TestConfig.service(fakeRides)
    assertEquals(service.recommend().map((ride: Ride) => ride.rideId), List(3,8,10))
  }

  test("Returns less than three rides") {
    val fakeRides = List(
      ride(rideId = 3, isOpen = true, waitTime = 10),
      ride(rideId = 10, isOpen = true, waitTime = 15),
    )

    val service = TestConfig.service(fakeRides)
    assertEquals(service.recommend().map((ride: Ride) => ride.rideId), List(3, 10))
  }
  test("Unknown wait time is not returned") {
    val fakeRides = List(
      ride(rideId = 3, isOpen = true, waitTime = 10),
      ride(rideId = 10, isOpen = true, waitTime = -15),
    )

    val service = TestConfig.service(fakeRides)
    assertEquals(service.recommend().map((ride: Ride) => ride.rideId), List(3))
  }
  test("Zero waiting time is recommended") {
    val fakeRides = List(
      ride(rideId = 3, isOpen = true, waitTime = 10),
      ride(rideId = 10, isOpen = true, waitTime = 0),
    )

    val service = TestConfig.service(fakeRides)
    assertEquals(service.recommend().map((ride: Ride) => ride.rideId), List(10, 3))
  }

  test("Return empty ride when no one qualifies") {
    val fakeRides = List(
      ride(rideId = 3, isOpen = true, waitTime = -10),
      ride(rideId = 10, isOpen = false, waitTime = 0),
      //ride(10, isOpen = true, waitTime = 0),
    )

    val service = TestConfig.service(fakeRides)
    assertEquals(service.recommend().map((ride: Ride) => ride.rideId), List.empty)
  }

  // Can show the time and date as well, need to figure out how
  test("Returns latest record for each ride") {
    val fakeRides = List(
      ride(rideId = 3, isOpen = false, waitTime = 5, date = LocalDate.of(2026, 11, 6) , time = LocalTime.of(4,0)),
      ride(rideId = 3, isOpen = true, waitTime = 5, date = LocalDate.of(2026, 11, 5) , time = LocalTime.of(4,0)),
      ride(rideId = 4, isOpen = true, waitTime = 5, date = LocalDate.of(2026, 11, 6) , time = LocalTime.of(5,0)),
    )

    val service = TestConfig.service(fakeRides)
    assertEquals(service.recommend().map((ride: Ride) => ride.rideId), List(4))
  }

}
