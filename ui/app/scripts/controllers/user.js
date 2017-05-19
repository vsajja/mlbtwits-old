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

    var username = $routeParams.username;
    var user = Restangular.one('users', username);

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

    function initController() {
      $scope.getUser();
      $scope.getUserTweets();
    }

    initController();
  }]);
