package core.ports

import core.model.Ride

// Output Port: 
trait RideRepository:
  def findAll(): List[Ride]
