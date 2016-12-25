'use strict';

/**
 * @ngdoc function
 * @name mlbTwitsApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the mlbTwitsApp
 */
angular.module('mlbTwitsApp')
  .controller('MainCtrl', ['$scope', 'Restangular', function ($scope, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    $scope.alerts = [];

    var mlbtwits = Restangular.all('mlbtwits');
    var tweets = Restangular.all('tweets');

    $scope.getMLBTwits = function () {
      mlbtwits.customGET().then(function (mlbtwits) {
        $scope.mlbtwits = mlbtwits;
      });
    };

    $scope.getTweets = function () {
      tweets.customGET().then(function (tweets) {
        $scope.tweets = tweets;
      });
    };

    $scope.tweetPlayer = function () {
      tweets.post($scope.tweet).then(function (newTweet) {
        $scope.getTweets();
        $scope.alerts.push({type: 'success', msg: 'Success! Tweet Id: ' + newTweet.tweetId});
      }, function () {
        $scope.alerts.push({type: 'danger', msg: 'Error! Unable tweet at baseball player.'});
      });
    };

    $scope.searchPlayers = function (term) {
      var lables = Restangular.one('labels', term);
      lables.customGET().then(function (labels) {
        $scope.players = labels;
      });
    };

    $scope.getPlayerTextRaw = function (player) {
      return '@' + player.label;
    };

    $scope.closeAlert = function (index) {
      $scope.alerts.splice(index, 1);
    };

    $scope.getMLBTwits();
    $scope.getTweets();
  }]);
