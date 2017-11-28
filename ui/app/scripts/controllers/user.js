'use strict';

/**
 * @ngdoc function
 * @name mlbTwitsApp.controller:UserCtrl
 * @description
 * # UserCtrl
 * Controller of the mlbTwitsApp
 */
angular.module('mlbTwitsApp')
  .controller('UserCtrl', ['$scope', '$routeParams', 'Restangular', function ($scope, $routeParams, Restangular) {

    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    $scope.alerts = [];

    var username = $routeParams.username;
    var user = Restangular.one('users', username);
    var playerLabels = Restangular.all('playerLabels');

    $scope.getPlayerLabels = function () {
      playerLabels.customGET().then(function (playerLabels) {
        $scope.playerLabels = playerLabels;
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

    // This will query /users/:username and return a promise.
    $scope.getUser = function () {
      user.customGET().then(function (user) {
        $scope.user = user;
      });
    };

    // This will query /users/:userId/tweets and return a promise.
    $scope.getUserTweets = function () {
      user.getList('tweets').then(function (tweets) {
        $scope.tweets = tweets;
      });
    };

    $scope.tweetUser = function (userId) {
      $scope.FIXME_userTweet.userId = userId;
      user.post('tweets', $scope.FIXME_userTweet).then(function (newTweet) {
        $scope.getUserTweets();
        $scope.alerts.push({type: 'success', msg: 'Success! Tweet Id: ' + newTweet.tweetId});
      }, function () {
        $scope.alerts.push({type: 'danger', msg: 'Error! Unable FIXME_userTweet at baseball player.'});
      });
    };

    function initController() {
      $scope.getUser();
      $scope.getUserTweets();
      $scope.getPlayerLabels();
    }

    initController();
  }]);
