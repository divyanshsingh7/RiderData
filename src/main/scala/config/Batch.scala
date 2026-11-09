package config

import adapters.in.*
import adapters.out.CSVRideRepository
import core.Service

@main def Batch(inputPath: String, outputPath: String): Unit =
  val repository = CSVRideRepository("queuetimes.csv")
  val service    = Service(repository)
  val handler    = CommandHandler(service)
  BatchAdapter(handler).run("batchCommands.txt", "batchResult.txt")
  