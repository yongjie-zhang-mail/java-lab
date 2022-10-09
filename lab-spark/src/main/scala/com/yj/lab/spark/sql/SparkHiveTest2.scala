package com.yj.lab.spark.sql

/**
 * @author zhangyj21
 */
object SparkHiveTest2 {
    
    // Spark环境配置
    //    private val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName(this.getClass.getSimpleName)
    //    private val spark: SparkSession = SparkSession.builder().enableHiveSupport().config(sparkConf).getOrCreate()
    
    def main(args: Array[String]): Unit = {
        
        val spark: SparkSession = SparkSession.builder().appName("SparkHiveTest2").master("local[*]")
            .config("spark.sql.shuffle.partitions", "4")
            .config("spark.sql.warehouse.dir", "hdfs://xxxxx")
            .config("hive.metastore.uris", "thrift://xxxxx")
            .enableHiveSupport()
            .getOrCreate()
        
        val sc: SparkContext = spark.sparkContext
        sc.setLogLevel("WARN")
        
        spark.sql("use proj_gucp_dw")
        spark.sql(
            s"""
               | create table temp20211028 (id int, name string, age int) row format delimited fields terminated by ','
            """.stripMargin)
        
        val df: DataFrame = spark.sql(
            """
              | select * from temp20211028
              |""".stripMargin)
        
        df.show(100)
        df.write.mode(SaveMode.Overwrite).saveAsTable("result")
        
        
        spark.sql("show databases").show(false)
        spark.sql("show tables").show(false)
        spark.sql("CREATE TABLE temp20211027 (id int, name string, age int) row format delimited fields terminated by ' '")
        spark.sql("show tables").show(false)
        
        spark.stop()
        
    }
    
    
}





















