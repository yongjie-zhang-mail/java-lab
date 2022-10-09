package com.yj.lab.spark.core

/**
 * @author zhangyj21
 */
object WordCount2 {
    
    def main(args: Array[String]): Unit = {
    
        val conf: SparkConf = new SparkConf().setAppName("WordCount2").setMaster("local[*]")
        val sc = new SparkContext(conf)
        sc.setLogLevel("WARN")
        //        System.setProperty("HADOOP_USER_NAME", "root")
    
        val lines: RDD[String] = sc.textFile("lab-spark/data/input/words.txt")
        val words: RDD[String] = lines.flatMap(_.split(" "))
        val word2one: RDD[(String, Int)] = words.map((_, 1))
        val wordCount: RDD[(String, Int)] = word2one.reduceByKey(_ + _)
    
        wordCount.foreach(println)
        println(wordCount.collect().toBuffer)
    
        wordCount.repartition(1).saveAsTextFile("lab-spark/data/output/result")
    
        sc.stop()
    
    }
    
}
