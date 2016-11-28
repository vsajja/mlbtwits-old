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
////                    ['postgres.user'        : 'zoqyxwbxjuntzp',
////                     'postgres.password'    : 'sfXLiNPmZuRUxS_biBDNyNRhDh',
////                     'postgres.portNumber'  : 5432,
////                     'postgres.databaseName': 'd4ogiv1q9mi0tp',
////                     'postgres.serverName'  : 'ec2-54-243-249-65.compute-1.amazonaws.com',
////                     'redis.host'           : 'pub-redis-19472.us-east-1-2.5.ec2.garantiadata.com',
////                     'redis.portNumber'     : 19472,
////                     'redis.password'       : 'sfXLiNPmZuRUxS_biBDNyNRhDh']
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