'use strict';

describe('Controller: ClosersCtrl', function () {

  // load the controller's module
  beforeEach(module('mlbTwitsApp'));

  var ClosersCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ClosersCtrl = $controller('ClosersCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ClosersCtrl.awesomeThings.length).toBe(3);
  });
});
