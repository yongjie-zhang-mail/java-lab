package com.yj.lab.spark.sql

/**
 * @author zhangyj21
 */
object SparkHiveTest {
    
    // Spark环境配置
    //    private val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName(this.getClass.getSimpleName)
    //    private val spark: SparkSession = SparkSession.builder().enableHiveSupport().config(sparkConf).getOrCreate()
    
    def main(args: Array[String]): Unit = {
        
        val spark: SparkSession = SparkSession.builder().appName("SparkHiveTest").master("local[*]")
            .config("spark.sql.shuffle.partitions", "4")
            .config("spark.sql.warehouse.dir", "hdfs://xxxxx")
            .config("hive.metastore.uris", "thrift://xxxxx")
            .enableHiveSupport()
            .getOrCreate()
        
        val sc: SparkContext = spark.sparkContext
        sc.setLogLevel("WARN")
        
        // 使用spark sql连接外置hive
        // 拷贝hive-site.xml到resources下
        // 启用hive支持
        spark.sql("show databases").show(false)
        spark.sql("show tables").show(false)
        spark.sql("CREATE TABLE temp20211027 (id int, name string, age int) row format delimited fields terminated by ' '")
        spark.sql("show tables").show(false)
        
        spark.stop()
        
    }
    
    
}





















