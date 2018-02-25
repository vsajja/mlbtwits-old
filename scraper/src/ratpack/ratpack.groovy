import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.handling.RequestLogger

import java.text.Normalizer

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.jsonNode

import static org.apache.poi.ss.usermodel.CellStyle.*
import static org.apache.poi.ss.usermodel.IndexedColors.*
import java.io.*
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*
import au.com.bytecode.opencsv.CSVReader

final Logger log = LoggerFactory.getLogger(this.class)

ratpack {
    handlers {
        all RequestLogger.ncsa(log)

        get('maps/places') {
            def apiKey = 'AIzaSyBIVOsx4FeYY8ZrqvyToGS6Dx07nv1OIRw'
//            def apiKey = 'AIzaSyAmftkf9z9JpMJjpBUjT0K8wGZU0hRdIYo'

            def outputFile = new File('C:\\Users\\vsajja\\Desktop\\google api\\results.csv')
            outputFile.delete()
            outputFile.createNewFile()

            def columnStr = ['Name',
//                             'Formatted Address',
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

            def spottedPlaces = ''
            def franchises = []

            new File("C:\\Users\\vsajja\\Desktop\\google api\\franchise in canda.txt").text.eachLine { franchise ->
                franchises.add(franchise)
            }

            int count = 0

            franchises.each { String franchise->
                franchise = Normalizer.normalize(franchise.replaceAll("[^\\x00-\\x7f]", ""), Normalizer.Form.NFD)
                def pageToken = ''
                while (pageToken != null) {
                def url = "https://maps.googleapis.com/maps/api/place/textsearch/json" +
                        "?query=${franchise.replaceAll(' ', '+').toLowerCase()}+in+canada" +
                        "&sensor=false" +
                        "&key=$apiKey" +
                        "&pagetoken=$pageToken"

//                    def url = "https://maps.googleapis.com/maps/api/place/search/json" +
//                            "?location=47.6049984,-122.3295292" +
//                            "&radius=10000" +
//                            "&types=store" +
//                            "&hasNextPage=true" +
//                            "&nextPage()=tru" +
//                            "e&sensor=false" +
//                            "&key=AIzaSyBIVOsx4FeYY8ZrqvyToGS6Dx07nv1OIRw" +
//                            "&pagetoken=$pageToken"

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

                        if (addressDetails.size() == 5) {
                            address = addressDetails[0] + ' ' + addressDetails[1]?.trim()
                            city = addressDetails[2]?.trim()
                            stateOrProvince = addressDetails[3]?.trim().split(' ')[0]?.trim()
                            zipOrPostalCode = addressDetails[3]?.trim().split(' ')[1]?.trim()
                            country = addressDetails[4]?.trim()
                        }
                        else if(addressDetails.size() == 4) {
                            address = addressDetails[0]?.trim()
                            city = addressDetails[1]?.trim()
                            stateOrProvince = addressDetails[2]?.trim().split(' ')[0]?.trim()
                            zipOrPostalCode = addressDetails[2]?.trim().split(' ')[1]?.trim()
                            country = addressDetails[3]?.trim()
                        }
                        else {
                            return
                        }

                        def phoneNumber = detailsResult.result.formatted_phone_number
                        def website = detailsResult.result.website
                        def priceLevel = detailsResult.result.price_level

                        spottedPlaces += [
                                name,
                                address,
                                city,
                                stateOrProvince,
                                zipOrPostalCode,
                                country,
                                phoneNumber,
                                website,
                                priceLevel,
                                place.get('rating'),
                                place.get('types').join(' ').toString(),
                                place.get('geometry').get('location').get('lat'),
                                place.get('geometry').get('location').get('lng')
                        ].join(',') + '\n'
                        count++
                        println "$count: \t$franchise"
                    }

//                    if (result?.get('next_page_token')) {
//                        println "pageToken found: " + result?.get('next_page_token')
//                    }
                    pageToken = result?.get('next_page_token')
                }
                outputFile.text += spottedPlaces + '\n'
            }

            Workbook wb = new HSSFWorkbook()
            CreationHelper helper = wb.getCreationHelper()
            Sheet sheet = wb.createSheet("StarbucksInCalgary")

            CSVReader reader = new CSVReader(new FileReader("C:\\Users\\vsajja\\Desktop\\google api\\results.csv"))

            String[] line
            int r = 0

            while ((line = reader.readNext()) != null) {
                Row row = sheet.createRow((short) r++)

                for (int i = 0; i < line.length; i++)
                    row.createCell(i)
                            .setCellValue(helper.createRichTextString(line[i]))
            }

            autoSizeColumns(wb)

            // Write the output to a file
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\vsajja\\Desktop\\google api\\results.xls")
            wb.write(fileOut)
            fileOut.close()

            render outputFile.text
        }

        files {
            dir 'dist'
            indexFiles 'index.html'
        }
    }
}

def void autoSizeColumns(Workbook workbook) {
    int numberOfSheets = workbook.getNumberOfSheets();
    for (int i = 0; i < numberOfSheets; i++) {
        Sheet sheet = workbook.getSheetAt(i);
        if (sheet.getPhysicalNumberOfRows() > 0) {
            Row row = sheet.getRow(0);
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                int columnIndex = cell.getColumnIndex();
                sheet.autoSizeColumn(columnIndex);
            }
        }
    }
}
