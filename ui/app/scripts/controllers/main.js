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
    // this.awesomeThings = [
    //   'HTML5 Boilerplate',
    //   'AngularJS',
    //   'Karma'
    // ];

    $scope.alerts = [];

    var mlbtwits = Restangular.all('mlbtwits');
    var tweets = Restangular.all('tweets');
    var playerLabels = Restangular.all('playerLabels');

    $scope.getMLBTwits = function getMLBTwits() {
      mlbtwits.customGET().then(function (mlbtwits) {
        $scope.mlbtwits = mlbtwits;
      });
    };

    $scope.getTweets = function () {
      tweets.customGET().then(function (tweets) {
        $scope.tweets = tweets;
      });
    };

    $scope.getPlayerLabels = function () {
      playerLabels.customGET().then(function (playerLabels) {
        $scope.playerLabels = playerLabels;
      });
    };

    $scope.tweetPlayer = function () {
      tweets.post($scope.tweet).then(function (newTweet) {
        $scope.getTweets();
        $scope.getMLBTwits();
        $scope.alerts.push({type: 'success', msg: 'Success! Tweet Id: ' + newTweet.tweetId});
      }, function () {
        $scope.alerts.push({type: 'danger', msg: 'Error! Unable tweet at baseball player.'});
      });
    };

    $scope.getPeopleText = function (player) {
      // return '@' + player.label;
      // return '<player playerId="' + player.label + '"' + '>' + player.label + '</player>'
      return '[~' + player.label + ']';
    };

    $scope.searchPlayers = function (term) {
      if(!term) {
        $scope.players = null;
        return;
      }

      var labels = $scope.playerLabels.filter(function (playerLabel) {
        return playerLabel.label.toLowerCase().indexOf(term.toLowerCase()) >= 0;
      });
      $scope.players = labels;
    };

    $scope.getPlayerTextRaw = function (player) {
      // return '@' + player.label;
      // return '<player playerId="' + player.label + '"' + '>' + player.label + '</player>'
      return '[~' + player.playerName + ']';
    };

    $scope.closeAlert = function (index) {
      $scope.alerts.splice(index, 1);
    };

    function initController() {
      $scope.getMLBTwits();
      $scope.getTweets();
      $scope.getPlayerLabels();
    }

    initController();
  }]);
