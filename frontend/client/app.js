const BACKEND_API = 'http://kildin.informatik.rwth-aachen.de/rabatt/api/v1';
const COOKIES_KEY = 'current_user';

(function () {

    var app = angular.module('rabattApp', ['ngRoute', 'ui.bootstrap', 'ngCookies']);

    app.config(['$routeProvider', function ($routeProvider) {
        var viewBase = '/views/';

        $routeProvider
            .when('/', {
                controller: 'DiscountsController',
                templateUrl: viewBase + 'discounts/discounts.html'
            })
            .when('/discount/new', {
                controller: 'DiscountEditController',
                templateUrl: viewBase + 'discounts/discountEdit.html',
                secure: true //This route requires an authenticated user
            })
            .when('/discount/:discountId', {
                controller: 'DiscountsController',
                templateUrl: viewBase + 'discounts/discountView.html'
            })
            .when('/discount/:discountId/edit', {
                controller: 'DiscountEditController',
                templateUrl: viewBase + 'discounts/discountEdit.html',
                secure: true //This route requires an authenticated user
            })
            .when('/login/:redirect*?', {
                controller: 'LoginController',
                templateUrl: viewBase + 'login.html'
            })
            .otherwise({ redirectTo: '/' });

    }]);

    app.run(['$rootScope', '$location', 'authService',

        function ($rootScope, $location, authService) {
          $rootScope.currentUser = authService.user;
          $rootScope.$on("$routeChangeStart", function (event, next, current) {
              if (next && next.$$route && next.$$route.secure) {
                  if (!authService.user.isAuthenticated) {
                      $rootScope.$evalAsync(function () {
                          authService.redirectToLogin();
                      });
                  }
              }
          });

    }]);

}());

