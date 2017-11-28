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
    var playerLabels = Restangular.all('playerLabels');

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

    $scope.getPlayerLabels = function () {
      playerLabels.customGET().then(function (playerLabels) {
        $scope.playerLabels = playerLabels;
      });
    };

    $scope.tweetPlayer = function (userId) {
      $scope.FIXME_userTweet.userId = userId;
      player.post('tweets', $scope.FIXME_userTweet).then(function (newTweet) {
        $scope.getPlayerTweets();
        $scope.alerts.push({type: 'success', msg: 'Success! Tweet Id: ' + newTweet.tweetId});
      }, function () {
        $scope.alerts.push({type: 'danger', msg: 'Error! Unable FIXME_userTweet at baseball player.'});
      });
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
      $scope.getPlayer();
      $scope.getPlayerTweets();
      $scope.getPlayerLabels();
    }

    initController();
  }]);
