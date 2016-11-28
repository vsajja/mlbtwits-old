'use strict';

describe('Controller: SchoolProfileCtrl', function () {

  // load the controller's module
  beforeEach(module('jobmineApp'));

  var SchoolProfileCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    SchoolProfileCtrl = $controller('SchoolProfileCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(SchoolProfileCtrl.awesomeThings.length).toBe(3);
  });
});
