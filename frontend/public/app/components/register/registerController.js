app.controller('registerController', function ($scope, $rootScope) {
  if ($rootScope.loggedIn) {
    $location.path('/');
  }
});
