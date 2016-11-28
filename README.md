# jobmine #

jobmine, originally founded by the University of Waterloo, is a website that helps students find jobs by creating companies and jobs. jobmine requires courage as it's aiming to eliminate resumes and interviews from the job market. jobmine introduces the concept of a mine and is also the only company allowed to have a mine. All other mines will be created by schools. Students and alumni will be able to join the site from all universities, colleges and high-schools worldwide. 

It will be free for everyone and there will be no ads. 

The ultimate goal of jobmine is to help students and companies automate the startup process.

## startup process ##

<img src="http://i.imgur.com/LuSwdzz.png" alt="the startup process" width="144px" height="192px">

# jobmine report #

## ui ##

start 206ms

change < 10ms

## server ##

start 11.044 seconds

change 8.562 seconds

## lines of code ##
lines: 20175 total

## stats ##
16 schools, 16 students and 14 companies. search 10 jobs from 2 mines

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
www.jobmine.ca/index.html

# heroku #
https://jm-app.herokuapp.com/index.html

## deploy ##
[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy)
<<<<<<< HEAD
TODO

# Report #
UI start time: 206ms
Server startup time: 11.044 seconds

UI change: < 10ms
Server change: 8.562 seconds

Total Lines of Code: 20175 total
=======
>>>>>>> bae7ca471392fa3f0ce8c5cb2817c013b7cf3e76
