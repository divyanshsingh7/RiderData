package adapters.in

import scala.io.StdIn

class CLI(handler: CommandHandler) {

  def run(): Unit = {
    var runningFlag = true

    while (runningFlag) {
      val line = StdIn.readLine()

      if (line == "exit") {
        runningFlag = false
      } else {
        println(handler.handle(line))
      }
    }
  }
}
