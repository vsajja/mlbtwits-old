'use strict';

describe('Controller: DevelopersCtrl', function () {

  // load the controller's module
  beforeEach(module('jobmineApp'));

  var DevelopersCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    DevelopersCtrl = $controller('DevelopersCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(DevelopersCtrl.awesomeThings.length).toBe(3);
  });
});
