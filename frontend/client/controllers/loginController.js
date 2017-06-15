(function () {
  const injectParams = ['$scope', '$routeParams', 'authService', '$location'];

  const LoginController = function ($scope, $routeParams, authService, $location) {
    if (authService.user.isAuthenticated) {
      $location.path('/');
      return;
    }

    $scope.username = 'fabrice';
    $scope.password = 'fab123';
    $scope.errorMessage = null;

    $scope.login = function () {
      authService.login($scope.username, $scope.password).then(
        function (results) {
          if (results.status !== 200) {
            $scope.errorMessage = results.data.message;
          } else {
            $location.path('/');
          }
        });
    };
  };

  LoginController.$inject = injectParams;

  angular.module('rabattApp').controller('LoginController', LoginController);
}());
