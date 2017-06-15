(function () {
  const injectParams = ['$scope', '$location', '$routeParams', 'authService'];

  const LoginController = function ($scope, $location, $routeParams, authService) {
    let path = '/';

    $scope.email = null;
    $scope.password = null;
    $scope.errorMessage = null;

    $scope.login = function () {
      authService.login($scope.email, $scope.password).then(function (status) {
                // $routeParams.redirect will have the route
                // they were trying to go to initially
        if (!status) {
          $scope.errorMessage = 'Unable to login';
          return;
        }

        if (status && $routeParams && $routeParams.redirect) {
          path += $routeParams.redirect;
        }

        $location.path(path);
      });
    };
  };

  LoginController.$inject = injectParams;

  angular.module('rabattApp')
        .controller('LoginController', LoginController);
}());
