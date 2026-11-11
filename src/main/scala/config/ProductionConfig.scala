package config

import adapters.in.*
import adapters.out.CSVRideRepository
import core.Service

@main def ProductionConfig(): Unit =
  val repository = CSVRideRepository("queuetimes.csv")
  val service = Service(repository)
  val commandHandler = CommandHandler(service)
  CLI(commandHandler).run()
