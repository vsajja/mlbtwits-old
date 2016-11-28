package org.jobmine

import groovy.json.JsonOutput
import jooq.generated.tables.daos.JobMineDao
import org.mlbtwits.postgres.PostgresConfig
import org.mlbtwits.postgres.PostgresModule
import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import ratpack.config.ConfigData
import ratpack.config.ConfigDataBuilder
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.http.client.RequestSpec
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

import javax.sql.DataSource

/**
 * Created by vsajja on 2016-11-02.
 */
@Ignore
public class StudentSpec extends Specification {
    @AutoCleanup
    @Shared
    GroovyRatpackMainApplicationUnderTest sut = new GroovyRatpackMainApplicationUnderTest()

    @Delegate
    TestHttpClient httpClient = sut.httpClient

    @Shared
    DSLContext context
    @Shared
    JobMineDao jobMineDao

    def setupSpec() {
        final ConfigData configData = ConfigData.of { ConfigDataBuilder builder ->
            builder.props(
                    ['postgres.user'        : 'zoqyxwbxjuntzp',
                     'postgres.password'    : 'sfXLiNPmZuRUxS_biBDNyNRhDh',
                     'postgres.portNumber'  : 5432,
                     'postgres.databaseName': 'd4ogiv1q9mi0tp',
                     'postgres.serverName'  : 'ec2-54-243-249-65.compute-1.amazonaws.com',
                     'redis.host'           : 'pub-redis-19472.us-east-1-2.5.ec2.garantiadata.com',
                     'redis.portNumber'     : 19472,
                     'redis.password'       : 'sfXLiNPmZuRUxS_biBDNyNRhDh'])
            builder.build()
        }
        DataSource dataSource = new PostgresModule().dataSource(configData.get('/postgres', PostgresConfig))
        Configuration configuration = new DefaultConfiguration().set(dataSource).set(SQLDialect.POSTGRES)

        jobMineDao = new JobMineDao(configuration)
        context = DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    def cleanupSpec() {

    }


    def "create student"() {
        setup:
        def first_name = this.class.getSimpleName()
        def last_name = this.class.getSimpleName()
        def username = this.class.getSimpleName()
        def email_address = "${first_name}@waterloo.ca"
        def employment_status = 'Unemployed'
        def karma = 1
        def total_views = 1
        def age = 19
        def gender = "Male"
        def salary = 1
        def relationship_status = "Single"
        def dreams = "cure cancer"
        def phone_number = "(519) 502-7991"
        def employment_history = "No employment history"
        def skills = "Really good at Photoshop"


        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [first_name         : first_name,
                     last_name          : last_name,
                     username           : username,
                     email_address      : email_address,
                     employment_status  : employment_status,
                     karama             : karma,
                     total_views        : total_views,
                     age                : age,
                     gender             : gender,
                     salary             : salary,
                     relationship_status: relationship_status,
                     dreams             : dreams,
                     phone_number       : phone_number,
                     employment_history : employment_history,
                     skills             : skills])
            )
        }

        when:
        post('api/v1/students')

        then:
        response.statusCode == 200
    }
}
