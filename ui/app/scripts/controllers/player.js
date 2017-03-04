'use strict';

/**
 * @ngdoc function
 * @name mlbTwitsApp.controller:PlayerCtrl
 * @description
 * # PlayerCtrl
 * Controller of the mlbTwitsApp
 */
angular.module('mlbTwitsApp')
  .controller('PlayerCtrl', ['$scope', '$routeParams', 'Restangular', function ($scope, $routeParams, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    $scope.alerts = [];

    var playerId = $routeParams.playerId;
    var player = Restangular.one('players', playerId);

    // This will query /players/:playerId and return a promise.
    $scope.getPlayer = function () {
      player.customGET().then(function (player) {
        $scope.player = player;
      });
    };

    // This will query /players/:playerId/tweets and return a promise.
    $scope.getPlayerTweets = function () {
      player.getList('tweets').then(function (tweets) {
        $scope.tweets = tweets;
      });
    };

    $scope.tweetPlayer = function () {
      player.post('tweets', $scope.tweet).then(function (newTweet) {
        $scope.getPlayerTweets();
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
      // return '@' + player.label;
      // return '<player playerId="' + player.label + '"' + '>' + player.label + '</player>'
      return '[~' + player.name + ']';
    };

    $scope.closeAlert = function (index) {
      $scope.alerts.splice(index, 1);
    };

    $scope.getPlayer();
    $scope.getPlayerTweets();
  }]);
