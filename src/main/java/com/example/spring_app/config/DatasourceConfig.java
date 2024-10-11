package com.example.spring_app.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DatasourceConfig {	
	
	@Value("${spring.datasource.query-timeout}")
    private int queryTimeout;
		
	@Bean(name = "databaseProp")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

	@Bean(name = "database")
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource(@Qualifier("databaseProp") DataSourceProperties databaseProp) {
        return dataSourceProperties()
          .initializeDataSourceBuilder()
          .build();
    }

	@Bean(name = "customJdbcTemplate")
	public NamedParameterJdbcTemplate jdbcTemplate(@Qualifier("database") DataSource ds) {
		JdbcTemplate template = new JdbcTemplate(ds);
		template.setQueryTimeout(queryTimeout);
		return new NamedParameterJdbcTemplate(template);
	}

}
