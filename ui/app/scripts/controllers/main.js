'use strict';

/**
 * @ngdoc function
 * @name mlbTwitsApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the mlbTwitsApp
 */
angular.module('mlbTwitsApp')
  .controller('MainCtrl', ['$scope', '$http', '$q', '$timeout', 'Restangular', function ($scope, $http, $q, $timeout, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    $scope.alerts = [];

    var mlbtwits = Restangular.all('mlbtwits');

    // This will query /mlbtwits and return a promise.
    mlbtwits.customGET().then(function (mlbtwits) {
      $scope.mlbtwits = mlbtwits;
    });

    var tweets = Restangular.all('tweets');

    // This will query /tweets and return a promise.
    tweets.customGET().then(function (tweets) {
      $scope.tweets = tweets;
    });

    $scope.tweetPlayer = function () {
      tweets.post($scope.tweet).then(function (newTweet) {
        console.log(newTweet);

        // This will query /tweets and return a promise.
        tweets.customGET().then(function (tweets) {
          $scope.tweets = tweets;
        });

        $scope.alerts.push({type: 'success', msg: 'Success! Tweet Id: ' + newTweet.tweetId});
      }, function () {
        $scope.alerts.push({type: 'danger', msg: 'Error! Unable tweet at baseball player.'});
      });
    };

    $scope.topics = [];

    $scope.searchTopic = function(topicTerm) {
      console.log(topicTerm);
      console.log(topicTerm.length);

      // This will query /labels/:term and return a promise.
      Restangular.one('labels', topicTerm).customGET().then(function (labels) {
        console.log(labels);
        $scope.foundTopics = labels;
      });
      // var topicList = [];
      // angular.forEach($scope.topics, function(item) {
      //   if (item.label.toUpperCase().indexOf(topicTerm.toUpperCase()) >= 0) {
      //     topicList.push(item);
      //   }
      // });
      //
      // $scope.foundTopics = topicList;
    };

    $scope.getTopicTextRaw = function(topic) {
      return '@' + topic.label;
    };
  }]);


