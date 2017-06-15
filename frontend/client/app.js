(function () {

    var app = angular.module('rabattApp',
        ['ngRoute', 'ui.bootstrap']);

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
                controller: 'DiscountViewController',
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
            
            //Client-side security. Server-side framework MUST add it's 
            //own security as well since client-based security is easily hacked
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

