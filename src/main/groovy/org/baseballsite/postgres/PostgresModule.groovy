package org.baseballsite.postgres

import groovy.transform.CompileStatic
import com.google.inject.Provides
import org.postgresql.ds.PGSimpleDataSource
import ratpack.guice.ConfigurableModule

import javax.sql.DataSource

@CompileStatic
class PostgresModule extends ConfigurableModule<PostgresConfig> {
    @Override
    protected void configure() {
    }

    @Provides
    DataSource dataSource(final PostgresConfig config) {
        createDataSource(config)
    }

    protected DataSource createDataSource(final PostgresConfig config) {
        new PGSimpleDataSource(
                user: config.user,
                password: config.password,
                serverName: config.serverName,
                databaseName: config.databaseName,
                portNumber: config.portNumber,
                ssl: true,
                sslMode: 'require',
                sslfactory: 'org.postgresql.ssl.NonValidatingFactory')
    }
}
