app.controller('registerController', function ($scope, $cookieStore) {
  if ($cookieStore.get(COOKIES_KEY)) {
    $location.path('/');
  }
});
