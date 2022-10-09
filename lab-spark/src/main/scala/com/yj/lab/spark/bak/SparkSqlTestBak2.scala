package com.yj.lab.spark.bak

/**
 * @author zhangyj21
 */
object SparkSqlTestBak2 {
    
    private val url = "jdbc:mysql://10.122.61.79:3306/member"
    private val driver = "com.mysql.cj.jdbc.Driver"
    private val user = "a_appconnect"
    private val password = "StcH7C:C"
    private val dbtable = "task"
    
    def main(args: Array[String]): Unit = {
        
        val conf = new SparkConf().setMaster("local[*]").setAppName("SparkSqlTest")
        val spark = SparkSession.builder().config(conf).getOrCreate()
        
        /*
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbc-url: jdbc:mysql://10.122.61.79:3306/member
        username: a_appconnect
        password: StcH7C:C
         */
        
        spark.read.format("jdbc")
            .option("url", url)
            .option("driver", driver)
            .option("user", user)
            .option("password", password)
            .option("dbtable", dbtable)
            .load().show
        
        spark.read.format("jdbc")
            .options(Map("url" -> url,
                "driver" -> driver,
                "user" -> user,
                "password" -> password,
                "dbtable" -> dbtable))
            .load().show()
        
        val props = new Properties()
        props.setProperty("user", user)
        props.setProperty("password", password)
        val df = spark.read.jdbc(url, dbtable, props)
        df.show()
        
        
        spark.stop()
        
    }
}
