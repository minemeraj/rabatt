(function () {

    var injectParams = ['$scope', '$location', '$routeParams',
                        '$timeout', 'config', 'dataService', 'modalService'];

    var DiscountViewController = function ($scope, $location, $routeParams,
                                           $timeout, config, dataService, modalService) {

        var discountId = ($routeParams.discountId) ? parseInt($routeParams.discountId) : 0,
            onRouteChangeOff;

        $scope.discount = {};
        
        $scope.upVote = function(index){
            $scope.discount.voteTemp++;
            updateDiscount($scope.discount);
        };

        $scope.downVote = function(index){
            $scope.discount.voteTemp--;
            updateDiscount($scope.discount);
        };

        function init() {
            if(discountId > 0) {
                getDiscountById(discountId);
            }
        }

        function getDiscountById(discountId) {
            dataService.getDiscountById(discountId)
            .then(function (data) {
                $scope.$apply(function(){
                    $scope.discount = data;
                });
            }, function (error) {
                $window.alert('Sorry, an error occurred: ' + error.data.message);
            });
        }

        function updateDiscount(discount) {
            dataService.updateDiscount(discount).then(function() {
                // TODO:

            }, function(error) {
                $window.alert('Sorry, an error occurred: ' + error.data.message);
            });
        }

        init();

    };

    DiscountViewController.$inject = injectParams;

    angular.module('rabattApp').controller('DiscountViewController', DiscountViewController);

}());