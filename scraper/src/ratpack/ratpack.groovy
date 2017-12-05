import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.handling.RequestLogger
import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.jsonNode

import static org.apache.poi.ss.usermodel.CellStyle.*
import static org.apache.poi.ss.usermodel.IndexedColors.*
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import au.com.bytecode.opencsv.*

final Logger log = LoggerFactory.getLogger(this.class)

ratpack {
    handlers {
        all RequestLogger.ncsa(log)

        get('maps/places') {
            def apiKey = 'AIzaSyBIVOsx4FeYY8ZrqvyToGS6Dx07nv1OIRw'

            def outputFile = new File('C:\\Users\\vsajja\\Desktop\\google api\\results.csv')
            outputFile.delete()
            outputFile.createNewFile()

            def columnStr = ['place_id',
                             'name',
                             'icon',
                             'lat',
                             'lng',
                             'rating',
                             'types'
            ].join(',')

            outputFile.text += columnStr + '\n'
            println columnStr

            def pageToken = ''

            while(pageToken != null) {
                def url = "https://maps.googleapis.com/maps/api/place/textsearch/json" +
                        "?query=starbucks+seattle" +
                        "&sensor=false" +
                        "&key=$apiKey" +
                        "&page_token=$pageToken"

                def result = new JsonSlurper().parseText(url.toURL().getText())
                def places = result.results

                places.each { place ->
                    outputFile.text += [place.get('place_id'),
                                        place.get('name'),
                                        place.get('icon'),
                                        place.get('geometry').get('location').get('lat'),
                                        place.get('geometry').get('location').get('lng'),
                                        place.get('rating'),
                                        place.get('types').toString(),
                    ].join(',') + '\n'
                }

                if(result?.get('next_page_token')) {
                    pageToken = result?.get('next_page_token')
                }
            }
            render outputFile.text
        }

        files {
            dir 'dist'
            indexFiles 'index.html'
        }
    }
}
