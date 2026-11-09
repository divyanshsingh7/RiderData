package adapters.in

import java.io.PrintWriter
import scala.io.Source

class BatchAdapter(handler: CommandHandler) {
  def run(sourcePath: String, destinationPath: String): Unit = {

    val sourceFile = Source.fromFile(sourcePath)
    val lines = sourceFile.getLines().toList
    sourceFile.close()

    val writer = new PrintWriter(destinationPath)

    try {
      for (line <- lines) {
        if (line.trim.nonEmpty) {
          val result = handler.handle(line)
          writer.write(result + "\n")
        }
      }
    }
    finally {
      writer.close()
    }
  }
}
