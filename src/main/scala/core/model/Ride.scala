package core.model

import java.time.{LocalDate, LocalDateTime, LocalTime}

case class Ride(
                 rideId: Int,
                 rideName: String,
                 isOpen: Boolean,
                 waitTime: Int,
                 date: LocalDate,
                 time: LocalTime
               ):

  def recordedTime: LocalDateTime = LocalDateTime.of(date, time)
