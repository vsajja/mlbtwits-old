'use strict';

describe('Controller: StudentProfileCtrl', function () {

  // load the controller's module
  beforeEach(module('jobmineApp'));

  var StudentProfileCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    StudentProfileCtrl = $controller('StudentProfileCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(StudentProfileCtrl.awesomeThings.length).toBe(3);
  });
});
