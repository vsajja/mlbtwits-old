'use strict';

/**
 * @ngdoc function
 * @name mlbTwitsApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the mlbTwitsApp
 */
angular.module('mlbTwitsApp')
  .controller('MainCtrl', ['$scope', 'Restangular', 'UserService', '$rootScope', function ($scope, Restangular, UserService, $rootScope) {
    // this.awesomeThings = [
    //   'HTML5 Boilerplate',
    //   'AngularJS',
    //   'Karma'
    // ];

    $scope.alerts = [];

    var mlbtwits = Restangular.all('mlbtwits');
    var tweets = Restangular.all('tweets');
    var playerLabels = Restangular.all('playerLabels');

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

    function loadCurrentUser() {
      UserService.GetByUsername($rootScope.globals.currentUser.username)
        .then(function (user) {
          vm.user = user;
        });
    }

    function loadAllUsers() {
      UserService.GetAll()
        .then(function (users) {
          vm.allUsers = users;
        });
    }

    function deleteUser(id) {
      UserService.Delete(id)
        .then(function () {
          loadAllUsers();
        });
    }

    var vm = this;

    vm.user = null;
    vm.allUsers = [];
    vm.deleteUser = deleteUser;

    function initController() {
      loadCurrentUser();
      loadAllUsers();

      $scope.getMLBTwits();
      $scope.getTweets();
      $scope.getPlayerLabels();
    }

    initController();
  }]);
