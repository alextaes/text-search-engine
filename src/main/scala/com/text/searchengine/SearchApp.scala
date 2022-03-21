package com.text.searchengine

import com.text.searchengine.utils.ScalaUtils._
import com.text.searchengine.utils.SparkUtils.createSparkContext
import org.apache.log4j.{Level, Logger}

object SearchApp {

  def main(args: Array[String]): Unit = {

    args match {
      case a if a.length != 1 => throw new IllegalArgumentException("Missing a valid directory path.")
      case _ => runJob(args.head)
    }
  }

  def runJob(path: String): Unit = {

    implicit val sparkContext = createSparkContext("text-search-engine")
    Logger.getLogger("org").setLevel(Level.OFF)

    val files = getFilesFromDirectory(path)
    println(s"${files.size} files read in directory: $path")

    val textFiles = getFilesFromDirectory(path)
    val fileContent = getFileContent(textFiles)

    chooseWords(fileContent)

  }

}
