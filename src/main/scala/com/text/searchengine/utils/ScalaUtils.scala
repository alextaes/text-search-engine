package com.text.searchengine.utils

import org.apache.spark.SparkContext

import java.io.File
import scala.collection.immutable.ListMap
import scala.io.StdIn.readLine

object ScalaUtils {

  /**
   * Gets the files inside the given directory.
   *
   * @param dir  path of the directory
   */
  def getFilesFromDirectory(dir: String): Map[String, String] = {
    val file = new File(dir)
    file.listFiles.filter(_.getName.endsWith(".txt")).map(file => file.getName -> file.getPath ).toMap
  }

  /**
   * Gets the content of the files and upload them in memory.
   *
   * @param files  Map with file name and path
   */
  def getFileContent(files: Map[String, String])(implicit spark: SparkContext): Map[String, Array[String]] = {
    files.map { file =>
      file._1 -> spark.textFile(file._2)
        .flatMap(_.split("\\W+"))
        .filter(_.nonEmpty)
        .map(_.toLowerCase)
        .collect
    }
  }

  /**
   * Count how many words are present in each file.
   *
   * @param textFiles file or files to do the search
   * @param words list of words to find in files
   */
  def countWordOcurrences(textFiles: Map[String, Array[String]], words: List[String]): Unit = {
    val fileOcurrences = textFiles map { file =>
      file._1 -> words.foldLeft(0)( (sum,word) => if (file._2 contains word.toLowerCase ) sum + 1 else sum )
    }
    val orderedFiles = orderTextFiles(fileOcurrences)

    printResults(orderedFiles, words.size)
  }

  /**
   * Open the terminal to pass words to find in the given files.
   *
   * @param textFiles file or files to do the search
   */
  def chooseWords(textFiles: Map[String, Array[String]]): String = {
    val line = readLine("search> ")
    line match {
      case "quit" => println("$"); line
      case "" => chooseWords(textFiles)
      case _ => countWordOcurrences(textFiles, line.split(" ").toList); chooseWords(textFiles)
    }
  }
  /**
   * Sort map value in descending order and get first ten elements.
   *
   * @param textFiles file or files to do the search
   */
  def orderTextFiles(textFiles: Map[String, Int]): ListMap[String, Int] =
    ListMap(textFiles.toSeq.sortWith(_._2 > _._2):_*).take(10)

  /**
   * Print the percentage of occurences that happens on each file.
   *
   * @param textFiles file or files to do the search
   * @param words list of words to find in files
   */
  def printResults(textFiles: ListMap[String, Int], words: Int): Unit = {
    textFiles map ( file => {
      val ocur = file._2 * 100 / words
      println(s"${file._1} : $ocur%")

    })

  }

}
