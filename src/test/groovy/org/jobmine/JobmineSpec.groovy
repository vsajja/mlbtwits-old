package org.jobmine

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufAllocator
import io.netty.buffer.Unpooled
import io.netty.handler.codec.http.DefaultFullHttpRequest
import io.netty.handler.codec.http.HttpRequest
import io.netty.handler.codec.http.HttpVersion
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder
import org.mlbtwits.postgres.PostgresConfig
import org.mlbtwits.postgres.PostgresModule
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import ratpack.config.ConfigData
import ratpack.config.ConfigDataBuilder
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.http.client.RequestSpec
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Unroll

import javax.sql.DataSource

/**
 * Created by vsajja on 2016-11-02.
 */
@Stepwise
public class JobmineSpec extends Specification {
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
        context = DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    def cleanupSpec() {

    }

    def "ping jobmine"() {
        when:
        get('api/v1/jobmine')

        then:
        response.statusCode == 200
    }

    @Unroll
    def "1. create school #name"() {
        setup:
        def image_id = null
        def location_id = null

        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [name           : name,
                     type           : type,
                     totalStudents  : totalStudents,
                     establishedDate: established_date,
                     description    : description])
            )
        }

        when:
        post('api/v1/schools')

        then:
        response.statusCode == 200

        where:
        name                                         || type                      || totalStudents || establishedDate || description
        'University of Waterloo'                     || 'University'              || 35900         || '1986'          || "The University of Waterloo is a public research university with a main campus located in Waterloo, Ontario. The main campus is located on 404 hectares of land in 'Uptown' Waterloo, adjacent to Waterloo Park"
        'York University'                            || 'University'              || 53000         || '1986'          || "York University is a public research university in Toronto, Ontario, Canada. It is Canada's third-largest university. York University has approximately 53,000 students, 7,000 faculty and staff, and 295,000 alumni worldwide."
        'University of Toronto'                      || 'University'              || 84556         || '1986'          || "The University of Toronto is a public research university in Toronto, Ontario, Canada, situated on the grounds that surround Queen's Park."
        'Ryserson University'                        || 'University'              || 34831         || '1986'          || "Ryerson University is a public research university located in downtown Toronto, Ontario. Its urban campus surrounds the Yonge-Dundas Square, located at the busiest intersection in downtown Toronto."
        'Hogwarts School of Witchcraft and Wizardry' || 'Witchcraft and Wizardry' || 1             || '1905'          || "Hogwarts School of Witchcraft and Wizardry, shortened Hogwarts, is a fictional British school of magic for students aged eleven to eighteen, and is the primary setting for the first six books in J.K. Rowling's Harry Potter series."
    }

    def "2. create job_mine (UW's jobmine)"() {
        setup:
        def name = 'University of Waterloo jobmine'

        def school_id = null

        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson([name: name]))
        }

        when:
        post('api/v1/mines')

        then:
        response.statusCode == 200
    }

    @Unroll
    def "3. create student #username"() {
        setup:
        def employment_status = 'Unemployed'
        def karma = 1
        def total_views = 1
        def age = 19
        def gender = 'Male'
        def salary = 1
        def relationship_status = 'Single'
        def dreams = 'help people find jobs'
        def phone_number = '(123) 456-7891'
        def employment_history = 'No history'
        def skills = 'No skills'

        def image_id = null
        def location_id = null
        def school_id = null

        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [first_name         : first_name,
                     last_name          : last_name,
                     username           : username,
                     email_address      : email_address,
                     employment_status  : employment_status,
                     karma              : karma,
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

        where:
        first_name || last_name    || username   || email_address
        'Andrew'   || 'Tran'       || 'atran'    || 'atran@hogwarts.ca'
        'Vinod'    || 'Sajja'      || 'vsajja'   || 'vsajja@engmail.uwaterloo.ca'
        'Riaz'     || 'Hassan'     || 'rhassan'  || 'rhassan@edu.yorku.ca'
        'Rahal'    || 'Balasuriya' || 'rahalb'   || 'rahalb@edu.yorku.ca'
        'Jason'    || 'Phinney'    || 'jphinney' || 'jphinney@edu.yorku.ca'
        'Ramanan'  || 'AR'         || 'ramar'    || 'ramar@edu.yorku.ca'
        'Thong'    || 'Thai'       || 'tthai'    || 'tthai@mail.utoronto.ca'
        'Vincent'  || 'Nguyen'     || 'vnguyen'  || 'vnguyen@mail.ryerson.ca'
        'Donald'   || 'Trump'      || 'dtrump'   || 'dtrump@whitehouse.com'
    }

    def "4. create company (jobmine)"() {
        setup:
        def image_id = null
        def location_id = null

        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [name           : name,
                     description    : description,
                     website_url    : website_url,
                     total_employees: total_employees,
                     industry       : industry,
                     founded_date   : founded_date])
            )
        }

        when:
        post('api/v1/companies')

        then:
        response.statusCode == 200

        where:
        name                            || description                                         || website_url           || total_employees || industry   || founded_date
        'jobmine'                       || 'jobmine is a website that helps people find jobs.' || 'www.jobmine.ca'      || 1               || 'Software' || '2017-01-01'
        'Toronto District School Board' || 'TDSB hires teachers.'                              || 'www.tdsb.ca'         || 1               || 'School'   || '2017-01-01'
        'Brazzers'                      || 'You know what Brazzers does.'                      || 'brazzerswebsite.com' || 1               || 'Porn'     || '2017-01-01'
    }

    def "5. post job on UW's jobmine #title"() {
        setup:
        def location_id = null
        def job_mine_id = null
        def company_id = null

        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [title         : title,
                     description   : description,
                     type          : type,
                     status        : status,
                     total_openings: total_openings])
            )
        }

        when:
        post('api/v1/jobs')

        then:
        response.statusCode == 200

        where:
        title     || description                        || type        || status    || total_openings
        'Founder' || 'Become the founder of jobmine.ca' || 'Full-Time' || 'Pending' || 1
        'Teacher' || 'Teach students.'                  || 'Full-Time' || 'Pending' || 1
    }

    def "6. search for jobs on UW's jobmine"() {
        when:
        get('api/v1/jobs')

        then:
        response.statusCode == 200
    }

    def "7. create job app package (vsajja's application)"() {
        setup:
        def name = 'App Package 1'

        def student_id = null

        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [name: name])
            )
        }

        when:
        post('api/v1/students/packages')

        then:
        response.statusCode == 200
    }

    def "8. view job app packages"() {
        setup:
        def student_id = null

        when:
        get('api/v1/students/packages')

        then:
        response.statusCode == 200
    }

    def "9. upload documents to job app package (cover letter & resume)"() {
        setup:
        File resume = File.createTempFile('vsajja_resume', '.pdf')
        File coverLetter = File.createTempFile('vsajja_coverletter', '.pdf')
        resume << 'resume content'
        coverLetter << 'cover letter'

        def job_app_package_id = null

        requestSpec { RequestSpec requestSpec ->
            HttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, io.netty.handler.codec.http.HttpMethod.POST, '/')
            HttpPostRequestEncoder httpPostRequestEncoder = new HttpPostRequestEncoder(request, true)
            httpPostRequestEncoder.addBodyFileUpload('resume', resume, 'text/plain', true)
            httpPostRequestEncoder.addBodyFileUpload('coverLetter', coverLetter, 'text/plain', true)

            request = httpPostRequestEncoder.finalizeRequest()

            request.headers().each {
                requestSpec.headers.set(it.key, it.value)
            }

            def chunks = []
            while (!httpPostRequestEncoder.isEndOfInput()) {
                chunks << httpPostRequestEncoder.readChunk(null as ByteBufAllocator).content()
            }
            requestSpec.body.buffer(Unpooled.wrappedBuffer(chunks as ByteBuf[]))
        }

        when:
        post('api/v1/students/packages/documents')

        then:
        response.statusCode == 200
    }

    def "10. apply to job (Founder)"() {
        setup:
        def job_id = 79
        def job_app_package_id = 24

        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [job_id            : job_id,
                     job_app_package_id: job_app_package_id])
            )
        }

        when:
        post('api/v1/jobs/applications')

        then:
        response.statusCode == 200
    }

    def "11. view apps for job"() {
        when:
        get('api/v1/jobs/applications')

        then:
        response.statusCode == 200
    }

    def "12. company creates job interview with student"() {
        setup:
        def status = 'Created'

        def job_id = null
        def student_id = null
        def location_id = null

        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [status            : status,
                     job_app_package_id: job_id,
                     student_id        : student_id,
                     location_id       : location_id])
            )
        }

        when:
        post('api/v1/jobs/interviews')

        then:
        response.statusCode == 200
    }

    def "13. student views interviews"() {
        setup:
        def student_id = null

        when:
        get('api/v1/jobs/interviews')

        then:
        response.statusCode == 200
    }

    def "14. interview is scheduled"() {
        setup:
        def status = 'Scheduled'

        def interview_timestamp = null
        def job_id = null
        def student_id = null
        def location_id = null

        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [status            : status,
                     job_app_package_id: job_id,
                     student_id        : student_id,
                     location_id       : location_id])
            )
        }

        when:
        post('api/v1/jobs/interviews')

        then:
        response.statusCode == 200
    }

    def "15. company creates an offer for job"() {
        setup:
        def expiry_timestamp = '2017-01-01'
        def salary = 10

        def job_id = null
        def student_id = null

        requestSpec { RequestSpec request ->
            request.body.type('application/json')
            request.body.text(JsonOutput.toJson(
                    [expiry_timestamp: expiry_timestamp,
                     salary          : salary,
                     job_id          : job_id,
                     student_id      : student_id])
            )
        }

        when:
        post('api/v1/jobs/offers')

        then:
        response.statusCode == 200
    }

    def "16. student views job offers"() {
        when:
        get('api/v1/jobs/offers')

        then:
        response.statusCode == 200
    }

//    def "vsajja accepts offer and becomes jobmine's founder"() {
//        expect: false
//    }

//    def "vsajja shortlists for jobs on UW's jobmine"() {
//        expect: false
//    }
//
//    def "vsajja views job shortlist"() {
//        expect: false
//    }
}