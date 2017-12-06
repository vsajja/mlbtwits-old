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
            // vsajja
//            def apiKey = 'AIzaSyBIVOsx4FeYY8ZrqvyToGS6Dx07nv1OIRw'
            // pistamayne
            def apiKey = 'AIzaSyAmftkf9z9JpMJjpBUjT0K8wGZU0hRdIYo'

            def outputFile = new File('C:\\Users\\vsajja\\Desktop\\google api\\results.csv')
            outputFile.delete()
            outputFile.createNewFile()

            def columnStr = ['place_id',
                             'name',
                             'Formatted Address',
                    'Address',
                    'City',
                    'State/Province',
                    'ZIP/Postal Code',
                    'Country',
                    'Phone Number',
//                    'Email Address',
                    'Website',
                    'Price Level',
                    'Rating',
                    'Types',
                    'Latitude',
                    'Longitude'
            ].join(',')

            outputFile.text += columnStr + '\n'
            println columnStr

//            def pageToken = ''

//            while(pageToken != null) {


            def url = "https://maps.googleapis.com/maps/api/place/textsearch/json" +
                    "?query=starbucks+seattle" +
                    "&sensor=false" +
                    "&key=$apiKey"
//                    "&page_token=$pageToken"

            def result = new JsonSlurper().parseText(url.toURL().getText())
            def places = result.results

            places.each { place ->

                // get place details
                def placeId = place.place_id

                def detailsUrl = "https://maps.googleapis.com/maps/api/place/details/json" +
                        "?placeid=$placeId" +
                        "&key=$apiKey"

                def detailsResult = new JsonSlurper().parseText(detailsUrl.toURL().getText())

                def name = place.get('name')
                def formattedAddress = detailsResult.result.formatted_address

                def addressDetails = formattedAddress.split(',')

                def address = null
                def city = null
                def stateOrProvince = null
                def zipOrPostalCode = null
                def country = null

                if(addressDetails.size() == 5) {
                    address = addressDetails[1]?.trim()
                    city = addressDetails[2]?.trim()
                    stateOrProvince = addressDetails[3]?.trim().split(' ')[0]?.trim()
                    zipOrPostalCode = addressDetails[3]?.trim().split(' ')[1]?.trim()
                    country = addressDetails[4]?.trim()
                }
                else {
                    address = addressDetails[0]?.trim()
                    city = addressDetails[1]?.trim()
                    stateOrProvince = addressDetails[2]?.trim().split(' ')[0]?.trim()
                    zipOrPostalCode = addressDetails[2]?.trim().split(' ')[1]?.trim()
                    country = addressDetails[3]?.trim()
                }

                def phoneNumber = detailsResult.result.formatted_phone_number
                def website = detailsResult.result.website
                def priceLevel = detailsResult.result.price_level

                outputFile.text += [placeId,
                                    name,
                                    formattedAddress,
                                    address,
                                    city,
                                    stateOrProvince,
                                    zipOrPostalCode,
                                    country,
                                    phoneNumber,
                                    website,
                                    priceLevel,
                                    place.get('rating'),
                                    place.get('types').toString(),
                                    place.get('geometry').get('location').get('lat'),
                                    place.get('geometry').get('location').get('lng')
                ].join(',') + '\n'
            }

            if(result?.get('next_page_token')) {
                println "pageToken found: " + result?.get('next_page_token')
            }
//            }
            render outputFile.text
        }

        files {
            dir 'dist'
            indexFiles 'index.html'
        }
    }
}
