package com.yj.lab.spring.config.rdb;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author Zhang Yongjie
 */
@Configuration
@MapperScan(basePackages = "com.yj.lab.spring.model.rdb.mapper.medical", sqlSessionTemplateRef = "medicalSst")
public class MedicalRdbConfig {

    @Bean(name = "medicalDs")
    @ConfigurationProperties(prefix = "spring.datasource.medical")
    public DataSource medicalDs() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "medicalSsf")
    public SqlSessionFactory medicalSsf(@Qualifier("medicalDs") DataSource ds,
                                        @Qualifier("mpInterceptor") MybatisPlusInterceptor mpi) throws Exception {
        MybatisSqlSessionFactoryBean ssf = new MybatisSqlSessionFactoryBean();
        // 设置数据源
        ssf.setDataSource(ds);
        // 设置分页插件
        ssf.setPlugins(mpi);
        // 设置xml目录
        ssf.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:model/rdb/mapper/medical/*.xml"));
        return ssf.getObject();
    }

    @Bean(name = "medicalTm")
    public DataSourceTransactionManager medicalTm(@Qualifier("medicalDs") DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Bean(name = "medicalSst")
    public SqlSessionTemplate medicalSst(@Qualifier("medicalSsf") SqlSessionFactory ssf) throws Exception {
        return new SqlSessionTemplate(ssf);
    }

    /**
     * 拦截器，分页插件
     *
     * @return MybatisPlusInterceptor
     */
    @Bean(name = "mpInterceptor")
    public MybatisPlusInterceptor mpInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}













