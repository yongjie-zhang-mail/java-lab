//package com.yj.lab.spring.config.rdb;
//
//import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * @author Zhang Yongjie
// */
//@Configuration
//@MapperScan(basePackages = "com.yj.lab.spring.model.rdb.mapper.second", sqlSessionTemplateRef = "secondSst")
//public class SecondRdbConfig {
//
//    @Bean(name = "secondDs")
//    @ConfigurationProperties(prefix = "spring.datasource.second")
//    public DataSource secondDs() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "secondSsf")
//    public SqlSessionFactory secondSsf(@Qualifier("secondDs") DataSource ds,
//                                       @Qualifier("mpInterceptor") MybatisPlusInterceptor mpi) throws Exception {
//        MybatisSqlSessionFactoryBean ssf = new MybatisSqlSessionFactoryBean();
//        // 设置数据源
//        ssf.setDataSource(ds);
//        // 设置分页插件
//        ssf.setPlugins(mpi);
//        // 设置xml目录
//        ssf.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:model/rdb/mapper/second/*.xml"));
//        return ssf.getObject();
//    }
//
//    @Bean(name = "secondTm")
//    public DataSourceTransactionManager secondTm(@Qualifier("secondDs") DataSource ds) {
//        return new DataSourceTransactionManager(ds);
//    }
//
//    @Bean(name = "secondSst")
//    public SqlSessionTemplate secondSst(@Qualifier("secondSsf") SqlSessionFactory ssf) throws Exception {
//        return new SqlSessionTemplate(ssf);
//    }
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
