app.controller('loginController', function ($scope, AuthService, $rootScope, $location, $window) {
  if ($rootScope.loggedIn) {
    $location.path('/');
    return;
  }

  $scope.data = {
    username: 'fabrice',
    password: 'fab123',
  };

  $scope.submit = function () {
    AuthService.login($scope.data).then(
      function (response) {
        AuthService.currentUser(response.token).then(
          function (response) {
            $window.location.href = '/';
            $window.location.reload();
          },
        );
      },
      function (errResponse) {
        console.error(errResponse);
      },
    );
  };
});
