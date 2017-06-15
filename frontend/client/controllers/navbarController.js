(function () {
  const injectParams = ['$scope', '$location', 'config', 'authService'];

  const NavbarController = function ($scope, $location, config, authService) {
    const appTitle = 'Discount Management';

    $scope.isCollapsed = false;
    $scope.appTitle = appTitle;

    $scope.highlight = function (path) {
      return $location.path().substr(0, path.length) === path;
    };

    $scope.loginOrOut = function () {
      setLoginLogoutText();
      const isAuthenticated = authService.user.isAuthenticated;
      if (isAuthenticated) { // logout
        authService.logout().then(function () {
          $location.path('/');
        });
      }
      redirectToLogin();
    };

    function redirectToLogin() {
      const path = `/login${$location.$$path}`;
      $location.replace();
      $location.path(path);
    }

    $scope.$on('loginStatusChanged', function (loggedIn) {
      setLoginLogoutText(loggedIn);
    });

    $scope.$on('redirectToLogin', function () {
      redirectToLogin();
    });

    function setLoginLogoutText() {
      $scope.loginLogoutText = (authService.user.isAuthenticated) ? 'Logout' : 'Login';
    }

    setLoginLogoutText();
  };

  NavbarController.$inject = injectParams;

  angular.module('rabattApp').controller('NavbarController', NavbarController);
}());
