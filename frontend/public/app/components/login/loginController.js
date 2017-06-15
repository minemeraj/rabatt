app.controller('loginController', function ($scope, AuthService, $rootScope, $location, $cookieStore) {
  if ($cookieStore.get(COOKIES_KEY)) {
    $location.path('/');
    return;
  }

  $scope.data = {
    username: 'fabrice',
    password: 'fab123',
  };

  $scope.submit = function () {
    AuthService.login($scope.data)
      .then(
        function (response) {
          AuthService.currentUser(response.token)
          .then(
            $location.path('/'),
          );
        },
        function (errResponse) {
          console.error(errResponse);
        },
    );
  };
});
