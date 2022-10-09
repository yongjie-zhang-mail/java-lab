package com.yj.lab.spark.sql

/**
 * @author zhangyj21
 */
object ReadHiveToDf {
    def main(args: Array[String]): Unit = {
    
        val spark: SparkSession = SparkSession.builder()
            // 本地调试 master local；提交到集群，代码中注释掉 master 属性；
            .master("local")
            .config("hive.metastore.uris", "thrift://10.122.36.40:30083")
            .appName("ReadHiveToDf")
            // 开启 hive 支持
            .enableHiveSupport()
            .getOrCreate()
    
        // 读取 hive 中的数据，加载 dataframe
        spark.sql("use proj_gucp_dw")
        val resultDf: DataFrame = spark.sql("select mid, id, id_source from member_graph limit 100")
        resultDf.show(100)
    
        //        spark.sql("drop table if exists df_test")
        //        spark.sql(
        //            """
        //              |
        //              |create table if not exists df_test
        //              |(
        //              |    mid string comment 'member id',
        //              |    id string comment 'user id',
        //              |    id_source string comment 'user id source'
        //              |) comment 'user base info table'
        //              |    partitioned by (l_date string comment 'partition by date')
        //              |    row format delimited
        //              |        fields terminated by ','
        //              |    stored as textfile;
        //              |
        //              |""".stripMargin)
        //
        //        spark.sql("alter table df_test drop if exists partition (l_date='2021-11-15')")
        //        spark.sql(
        //            """
        //              |
        //              |insert into df_test partition (l_date='2021-11-15')
        //              |select mid, id, id_source from member_graph limit 100;
        //              |
        //              |""".stripMargin)
        //
        //        val resultDf: DataFrame = spark.sql("select * from df_test")
    
    }
}
