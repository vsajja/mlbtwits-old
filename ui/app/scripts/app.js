'use strict';

/**
 * @ngdoc overview
 * @name mlbTwitsApp
 * @description
 * # mlbTwitsApp
 *
 * Main module of the application.
 */
var mlbTwitsApp = angular
  .module('mlbTwitsApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.bootstrap',
    'smart-table',
    'restangular',
    'xeditable',
    'angularMoment',
    'mentio'
  ])
  .config(function ($routeProvider, $compileProvider, $locationProvider, RestangularProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/player', {
        templateUrl: 'views/player.html',
        controller: 'PlayerCtrl',
        controllerAs: 'player'
      })
      .when('/players', {
        templateUrl: 'views/players.html',
        controller: 'PlayersCtrl',
        controllerAs: 'players'
      })
      .when('/players/:playerId', {
        templateUrl: 'views/player.html',
        controller: 'PlayerCtrl',
        controllerAs: 'player'
      })
      .when('/players', {
        templateUrl: 'views/players.html',
        controller: 'PlayersCtrl',
        controllerAs: 'players'
      })
      .when('/teams', {
        templateUrl: 'views/teams.html',
        controller: 'TeamsCtrl',
        controllerAs: 'teams'
      })
      .when('/teams/:teamId', {
        templateUrl: 'views/team.html',
        controller: 'TeamCtrl',
        controllerAs: 'team'
      })
      .when('/closers', {
        templateUrl: 'views/closers.html',
        controller: 'ClosersCtrl',
        controllerAs: 'closers'
      })
      .otherwise({
        redirectTo: '/'
      });

    // release
    // $compileProvider.debugInfoEnabled(false);
    // RestangularProvider.setBaseUrl('/api/v1');

    // dev
    RestangularProvider.setBaseUrl('http://localhost:5050/api/v1');
  });

mlbTwitsApp.run(function (editableOptions) {
  editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
});
