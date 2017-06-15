app.config(['$routeProvider',
  function ($routeProvider) {
    $routeProvider.when('/', {
      templateUrl: 'app/components/home/homeView.html',
      controller: 'homeController',
    });
    $routeProvider.when('/login', {
      templateUrl: 'app/components/login/loginView.html',
      controller: 'loginController',
    });
    $routeProvider.when('/register', {
      templateUrl: 'app/components/register/registerView.html',
      controller: 'registerController',
    });
    $routeProvider.when('/discount', {
      templateUrl: 'app/components/discount/discountView.html',
      controller: 'discountController',
    });
  },
]);
