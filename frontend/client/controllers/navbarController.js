(function () {
  const injectParams = ['$scope', '$location', 'config', 'authService', '$window'];

  const NavbarController = function ($scope, $location, config, authService, $window) {
    const appTitle = 'Discount Management';

    $scope.isCollapsed = false;
    $scope.appTitle = appTitle;

    $scope.highlight = function (path) {
      return $location.path().substr(0, path.length) === path;
    };

    $scope.logout = function () {
      authService.logout();
      $window.location.href = '/';
    };
  };

  NavbarController.$inject = injectParams;

  angular.module('rabattApp').controller('NavbarController', NavbarController);
}());
