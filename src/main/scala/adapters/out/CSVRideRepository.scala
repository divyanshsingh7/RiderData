package adapters.out

import com.github.tototoshi.csv.*
import core.model.Ride
import core.ports.RideRepository

import java.io.File
import java.time.{LocalDate, LocalTime}

class CSVRideRepository(filePath: String) extends RideRepository {

  def findAll(): List[Ride] = {
    val reader = CSVReader.open(new File(filePath))
    try {
      val allRows: List[Map[String, String]] = reader.allWithHeaders()
      val resultRows: List[Ride] = allRows.map(row =>
        Ride(
          rideId = row("ride_id").toInt,
          rideName = row("ride_name"),
          isOpen = row("is_open").toBoolean,
          waitTime = row("wait_time").toInt,
          date = LocalDate.parse(row("date")),
          time = LocalTime.parse(row("timestamp"))
        )
      )
      resultRows
    }
    finally {
      reader.close()
    }
  }
}
