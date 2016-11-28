import com.zaxxer.hikari.HikariConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.config.ConfigData
import ratpack.config.ConfigDataBuilder
import ratpack.groovy.sql.SqlModule
import ratpack.handling.RequestLogger
import ratpack.hikari.HikariModule
import org.mlbtwits.postgres.PostgresConfig
import org.mlbtwits.postgres.PostgresModule
import static ratpack.groovy.Groovy.ratpack

final Logger log = LoggerFactory.getLogger(this.class)

ratpack {
    bindings {
        final ConfigData configData = ConfigData.of { ConfigDataBuilder builder ->
            builder.props(
                    ['postgres.user'        : 'zoqyxwbxjuntzp',
                     'postgres.password'    : 'sfXLiNPmZuRUxS_biBDNyNRhDh',
                     'postgres.portNumber'  : 5432,
                     'postgres.databaseName': 'd4ogiv1q9mi0tp',
                     'postgres.serverName'  : 'ec2-54-243-249-65.compute-1.amazonaws.com'])
            builder.build()
        }

        bindInstance PostgresConfig, configData.get('/postgres', PostgresConfig)

        module HikariModule, { HikariConfig config ->
            config.dataSource =
                    new PostgresModule().dataSource(
                            configData.get('/postgres', PostgresConfig))
        }
        module SqlModule
    }

    handlers {
        all RequestLogger.ncsa(log)

        get {
            redirect('index.html')
        }

        prefix('api/v1') {
            all {
                response.headers.add('Access-Control-Allow-Origin', '*')
                response.headers.add('Access-Control-Allow-Headers', 'origin, x-requested-with, content-type')
                response.headers.add('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS')
                next()
            }

            path('mlbtwits') {
                byMethod {
                    get {
                        render 'hello plebs'

//                        DataSource dataSource = registry.get(DataSource.class)
//                        DSLContext context = DSL.using(dataSource, SQLDialect.POSTGRES)
//
//                        def schools = context.selectCount().from(SCHOOL).asField('schools')
//                        def students = context.selectCount().from(STUDENT).asField('students')
//                        def companies = context.selectCount().from(COMPANY).asField('companies')
//                        def jobs = context.selectCount().from(JOB).asField('jobs')
//                        def mines = context.selectCount().from(JOB_MINE).asField('mines')
//
//                        def result = context.select(schools, students, companies, jobs, mines).fetchOneMap()
//                        result.put('countries', 1)
//
//                        render json(result)
                    }
                }
            }
        }

        files {
            dir 'dist'
        }
    }
}