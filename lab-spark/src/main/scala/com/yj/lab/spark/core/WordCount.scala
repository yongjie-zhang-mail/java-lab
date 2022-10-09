package com.yj.lab.spark.core

/**
 * @author zhangyj21
 */
object WordCount {
    
    def main(args: Array[String]): Unit = {
        
        // 建立和Spark框架的连接
        val sparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
        val sc = new SparkContext(sparkConf)
        
        // 执行业务操作
        //    val lines = sc.textFile("data")
        //    val words = lines.flatMap(_.split(" "))
        //    val wordGroup = words.groupBy(word => word)
        //    val wordToCount = wordGroup.map {
        //      case (word, list) => {
        //        (word, list.size)
        //      }
        //    }
        //    val array = wordToCount.collect()
        //    array.foreach(println)
        
        val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
        val words = rdd.flatMap(_.split(" "))
        val wordOne = words.map((_, 1))
        val wordCount: RDD[(String, Int)] = wordOne.reduceByKey(_ + _)
        wordCount.foreach(println)
        
        // 关闭连接
        sc.stop()
        
    }
    
}
