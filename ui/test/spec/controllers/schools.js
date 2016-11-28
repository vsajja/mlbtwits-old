'use strict';

describe('Controller: SchoolsCtrl', function () {

  // load the controller's module
  beforeEach(module('jobmineApp'));

  var SchoolsCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    SchoolsCtrl = $controller('SchoolsCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(SchoolsCtrl.awesomeThings.length).toBe(3);
  });
});
