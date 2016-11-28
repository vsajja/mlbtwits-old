'use strict';

describe('Controller: CompanyProfileCtrl', function () {

  // load the controller's module
  beforeEach(module('jobmineApp'));

  var CompanyProfileCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    CompanyProfileCtrl = $controller('CompanyProfileCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(CompanyProfileCtrl.awesomeThings.length).toBe(3);
  });
});
