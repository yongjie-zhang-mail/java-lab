package com.yj.lab.spark.sql


/**
 * @author zhangyj21
 */
object SparkMySqlTest {
    
    // Spark环境配置
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkMySqlTest")
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    
    // MySQL配置信息
    private val url = "jdbc:mysql://10.122.61.79:3306/member"
    private val driver = "com.mysql.cj.jdbc.Driver"
    private val user = "a_appconnect"
    private val password = "StcH7C:C"
    private val dbtable = "task_copy1"
    
    def main(args: Array[String]): Unit = {
        
        readMySql1()
        //        readMySql2()
        //        readMySql3()
        
        //        val ds: Dataset[Task] = createWriteDs
        //        writeMySql1(ds)
        //        writeMySql2(ds)
        
        //        spark.stop()
        spark.close()
    }
    
    // 样例类，User对象
    //    case class User(name: String, age: Int)
    
    private def createWriteDs: Dataset[Task] = {
        val taskList = List(
            Task(name = "task1", description = "description1", category = 2, `type` = 1, state = 1),
            Task(name = "task2", description = "description2", category = 2, `type` = 3, state = 1),
            Task(name = "task3", description = "description3", category = 2, `type` = 6, state = 1))
        val rdd = spark.sparkContext.makeRDD(taskList)
        val ds = rdd.toDS()
        ds
    }
    
    private def writeMySql2(ds: Dataset[Task]): Unit = {
        val props: Properties = mySqlProps
        ds.write.mode(SaveMode.Append).jdbc(url, dbtable, props)
    }
    
    private def mySqlProps: Properties = {
        val props = new Properties()
        props.setProperty("user", user)
        props.setProperty("password", password)
        props
    }
    
    private def writeMySql1(ds: Dataset[Task]): Unit = {
        ds.write.format("jdbc")
            .options(mySqlSettings)
            .mode(SaveMode.Append)
            .save()
    }
    
    // 样例类，Task对象
    case class Task(name: String, description: String, category: Int, `type`: Int, state: Int)
    
    private def readMySql3(): Unit = {
        val props: Properties = mySqlProps
        val df = spark.read.jdbc(url, dbtable, props)
        df.show()
    }
    
    private def readMySql2(): Unit = {
        spark.read.format("jdbc")
            .options(mySqlSettings)
            .load().show()
    }
    
    private def mySqlSettings: Map[String, String] = {
        Map("url" -> url,
            "driver" -> driver,
            "user" -> user,
            "password" -> password,
            "dbtable" -> dbtable)
    }
    
    private def readMySql1(): Unit = {
        spark.read.format("jdbc")
            .option("url", url)
            .option("driver", driver)
            .option("user", user)
            .option("password", password)
            .option("dbtable", dbtable)
            .load().show()
    }
}
