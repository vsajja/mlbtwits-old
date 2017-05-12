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
    'mentio',
    'base64'
  ])
  .config(function ($routeProvider, $compileProvider, $locationProvider, RestangularProvider) {
    $routeProvider
      .when('/', {
        // templateUrl: 'views/login.html',
        // controller: 'LoginCtrl',
        // controllerAs: 'vm'
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'vm'
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
      .when('/login', {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl',
        controllerAs: 'vm'
      })
      .when('/register', {
        templateUrl: 'views/register.html',
        controller: 'RegisterCtrl',
        controllerAs: 'vm'
      })
      .otherwise({
        redirectTo: '/login'
      });

    $locationProvider.html5Mode(false);
    $locationProvider.hashPrefix('');

    // release
    // $compileProvider.debugInfoEnabled(false);
    // RestangularProvider.setBaseUrl('/api/v1');

    // dev
    RestangularProvider.setBaseUrl('http://localhost:5050/api/v1');
  });

// mlbTwitsApp.run(function (editableOptions, $cookieStore, $rootScope) {
mlbTwitsApp.run(function ($rootScope, $location, $cookieStore, $http, editableOptions) {
  editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'

  // keep user logged in after page refresh
  $rootScope.globals = $cookieStore.get('globals') || {};
  if ($rootScope.globals.currentUser) {
    $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
  }

  $rootScope.$on('$locationChangeStart', function () {
    // redirect to login page if not logged in and trying to access a restricted page
    var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;

    var loggedIn = $rootScope.globals.currentUser;
    if (restrictedPage && !loggedIn) {
      $location.path('/login');
    }
  });
});
