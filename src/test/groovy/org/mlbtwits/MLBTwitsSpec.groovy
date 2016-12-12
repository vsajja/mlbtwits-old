package org.mlbtwits

import groovy.json.JsonSlurper
import org.jooq.DSLContext
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
public class MLBTwitsSpec extends Specification {
    @AutoCleanup
    @Shared
    GroovyRatpackMainApplicationUnderTest sut = new GroovyRatpackMainApplicationUnderTest()

    @Delegate
    TestHttpClient httpClient = sut.httpClient

    @Shared
    DSLContext context

    @Shared
    JsonSlurper jsonSlurper = new JsonSlurper()

    def setupSpec() {
//        final ConfigData configData = ConfigData.of { ConfigDataBuilder builder ->
//            builder.props(
////                    ['postgres.user'        : 'elnsxicscvthpy',
////                     'postgres.password'    : '42YdDI0OVjJhMhzBEvxA-f5rze',
////                     'postgres.portNumber'  : 5432,
////                     'postgres.databaseName': 'ddqeubt8e101m',
////                     'postgres.serverName'  : 'ec2-54-243-202-113.compute-1.amazonaws.com']
//            )
//            builder.build()
//        }
//        DataSource dataSource = new PostgresModule().dataSource(configData.get('/postgres', PostgresConfig))
//        context = DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    def cleanupSpec() {

    }

    def "ping mlbtwits"() {
        when:
        get('api/v1/mlbtwits')

        then:
        response.statusCode == 200
    }
}