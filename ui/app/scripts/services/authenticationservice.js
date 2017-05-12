'use strict';

/**
 * @ngdoc service
 * @name mlbTwitsApp.AuthenticationService
 * @description
 * # AuthenticationService
 * Factory in the mlbTwitsApp.
 */
angular.module('mlbTwitsApp')
  .factory('AuthenticationService', function ($http, $cookies, $base64, $rootScope, $timeout, UserService) {
    // Service logic
    // ...

    function Login(username, password, callback) {

      /* Dummy authentication for testing, uses $timeout to simulate api call
       ----------------------------------------------*/
      $timeout(function () {
        var response;
        UserService.GetByUsername(username)
          .then(function (user) {
            if (user !== null && user.password === password) {
              response = { success: true };
            } else {
              response = { success: false, message: 'Username or password is incorrect' };
            }
            callback(response);
          });
      }, 1000);

      /* Use this for real authentication
       ----------------------------------------------*/
      //$http.post('/api/authenticate', { username: username, password: password })
      //    .success(function (response) {
      //        callback(response);
      //    });

    }

    function SetCredentials(username, password) {
      var authdata = $base64.encode(username + ':' + password);

      $rootScope.globals = {
        currentUser: {
          username: username,
          authdata: authdata
        }
      };

      // set default auth header for http requests
      $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line

      // store user details in globals cookie that keeps user logged in for 1 week (or until they logout)
      var cookieExp = new Date();
      cookieExp.setDate(cookieExp.getDate() + 7);
      $cookies.putObject('globals', $rootScope.globals, { expires: cookieExp });
    }

    function ClearCredentials() {
      $rootScope.globals = {};
      $cookies.remove('globals');
      $http.defaults.headers.common.Authorization = 'Basic';
    }

    var service = {};

    service.Login = Login;
    service.SetCredentials = SetCredentials;
    service.ClearCredentials = ClearCredentials;

    return service;

    // var meaningOfLife = 42;
    //
    // // Public API here
    // return {
    //   someMethod: function () {
    //     return meaningOfLife;
    //   }
    // };
  });
