package org.jobmine.redis

import groovy.transform.CompileStatic

/**
 * @author vsajja
 */
@CompileStatic
class RedisConfig {
    String host = 'localhost'
    String databaseName
    Integer portNumber = 19472
}