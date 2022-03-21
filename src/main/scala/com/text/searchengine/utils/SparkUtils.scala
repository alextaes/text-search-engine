package com.text.searchengine.utils

import org.apache.spark.{SparkConf, SparkContext}

object SparkUtils {

  def createSparkContext(appName: String): SparkContext = {
    val conf = new SparkConf().setAppName(appName).setMaster("local[1]")
    new SparkContext(conf)
  }

}
