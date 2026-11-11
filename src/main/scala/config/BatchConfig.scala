package config

import adapters.in.*
import adapters.out.CSVRideRepository
import core.Service

@main def BatchConfig(inputPath: String, outputPath: String): Unit =
  val repository = CSVRideRepository("queuetimes.csv")
  val service = Service(repository)
  val commandHandler = CommandHandler(service)
  BatchAdapter(commandHandler).run("batchCommands.txt", "batchResult.txt")
  