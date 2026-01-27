package kr.co.aia.dmd.common.config.db;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@Configuration
@MapperScan(basePackages = "kr.co.aia.dmd.ipro", sqlSessionFactoryRef = "primarySqlSessionFactory", excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "kr.co.aia.dmd.ipro.secondary.mapper.*"))
public class PrimaryDatabaseConfig {

    @Primary
    @Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "primarySqlSessionFactory")
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource,
            ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("kr.co.aia.dmd.common.map");
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));

        // MyBatis Configuration (Snake Case -> Camel Case 등)
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCallSettersOnNulls(true);
        // 필요한 경우 LogImpl 설정 추가:
        // configuration.setLogImpl(org.apache.ibatis.logging.slf4j.Slf4jImpl.class);

        sqlSessionFactoryBean.setConfiguration(configuration);

        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean(name = "primarySqlSessionTemplate")
    public SqlSessionTemplate primarySqlSessionTemplate(
            @Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Primary
    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager primaryTransactionManager(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
