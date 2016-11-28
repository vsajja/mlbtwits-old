# mlbtwits #

mlbtwits is the goto place for everything fantasy baseball.

# mlbtwits report #

## ui ##

start 206ms

change < 10ms

## server ##

start 11.044 seconds

change 8.562 seconds

## lines of code ##
lines: 20175 total

## stats ##
0 players, 0 users, 0 tweeets

# the *simple* stack#

UI:
Angular JS 1.x,
Restangular,
angular-xeditable,
ui.bootstrap,
Bootstrap,
HTML,
CSS,
Javascript

UI build:
Grunt,
Bower

Server:
Java,
Groovy,
Ratpack,
Gradle,
JOOQ

Database:
Postgres

Test:
Spock

Collaboration:
Slack

Deployment:
Heroku

# ui #

## dev setup ##

## starting UI ##
grunt serve

## full Build ##
grunt

# server #

## dev setup ##

## starting Server ##
./gradlew clean run -it

## kill server (on windows) ##
netstat -o -n -a | findstr 0.0:5050
taskkill /F /PID <pid>

# website #
www.mlbtwits.com

# heroku #
https://mlbtwits-app.herokuapp.com/index.html

## deploy ##
[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy)

# Report #
UI start time: 206ms
Server startup time: 11.044 seconds

UI change: < 10ms
Server change: 8.562 seconds