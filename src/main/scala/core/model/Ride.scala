package core.model

import java.time.{LocalDate, LocalTime, LocalDateTime}

case class Ride(
                 rideId: Int,
                 rideName: String,
                 isOpen: Boolean,
                 waitTime: Int, // None = Unknown, but see if only int could be use
                 date: LocalDate,
                 time: LocalTime
               ):

  def recordedTime: LocalDateTime = LocalDateTime.of(date, time)



