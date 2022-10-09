package com.yj.lab.spark.sql

/**
 * @author zhangyj21
 */
object SparkHiveOnYarnTest {
    
    def main(args: Array[String]): Unit = {
        
        // 启动环境
        val spark: SparkSession = SparkSession.builder().appName("SparkHiveOnYarnTest").enableHiveSupport()
            //            .master("local[*]")
            //            .config("spark.sql.shuffle.partitions", "4")
            //            .config("spark.sql.warehouse.dir", "hdfs://xxxxx")
            //            .config("hive.metastore.uris", "thrift://xxxxx")
            .getOrCreate()
        
        val sc: SparkContext = spark.sparkContext
        // 设置日志级别
        sc.setLogLevel("WARN")
        
        // 代码逻辑
        spark.sql("use proj_gucp_dw")
        spark.sql("drop table if exists proj_gucp_dw.df_test")
        spark.sql(
            """
              |create table if not exists proj_gucp_dw.df_test
              |(
              |    mid string comment 'member id',
              |    id string comment 'user id',
              |    id_source string comment 'user id source'
              |) comment 'user base info table'
              |    partitioned by (l_date string comment 'partition by date')
              |    row format delimited
              |        fields terminated by ','
              |    stored as textfile;
              |""".stripMargin)
        
        spark.sql("alter table proj_gucp_dw.df_test drop if exists partition (l_date='2021-11-08')")
        spark.sql(
            """
              | insert into proj_gucp_dw.df_test partition (l_date='2021-11-08')
              | select mid, id, id_source from proj_gucp_dw.member_graph where l_date = '2021-11-08' limit 100
              |""".stripMargin)
        
        
        val df = spark.sql("select * from member_graph limit 100")
        df.show()
        
        spark.sql("create table df_20211104 (`mid` string, `mid_idtype` string, `mid_id` string, `mid_src` string, `mid_source` string, `mid_source_date` string, `id` string, `id_src` string, `id_source` string, `id_source_date` string) row format delimited fields terminated by '\u0005' ")
        df.write.mode(SaveMode.Overwrite).saveAsTable("df_20211104")
        
        val df2 = spark.sql("select * from df_20211104")
        df2.show()
        
        // 关闭环境
        spark.stop()
        
    }
    
    
}





//        val df: DataFrame = spark.sql(
//            """
//              | select * from member_graph limit 100
//              |""".stripMargin)
//
//        df.show()
//        df.write.mode(SaveMode.Overwrite).saveAsTable("df_20211104")
//
//        val df2: DataFrame = spark.sql(
//            """
//              | select * from df_20211104
//              |""".stripMargin)
//        df2.show()















