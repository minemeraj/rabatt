(function () {
  const injectParams = ['$scope', '$location', '$routeParams',
    '$timeout', 'config', 'dataService', 'modalService'];

  const DiscountEditController = function ($scope, $location, $routeParams,
    $timeout, config, dataService, modalService) {
    const discountId = ($routeParams.discountId) ? parseInt($routeParams.discountId, 10) : 0;
    let onRouteChangeOff;

    $scope.discount = {};
    $scope.updateStatus = false;
    $scope.errorMessage = '';

    $scope.saveDiscount = function () {
      if ($scope.editForm.$valid) {
        if (!$scope.discount.id) {
          dataService.insertDiscount($scope.discount).then(function () {
            onRouteChangeOff();
            $location.path('/discounts');
          }, function (error) {
            $scope.errorMessage = error.message;
          });
        } else {
          dataService.updateDiscount($scope.discount).then(function () {
            $scope.editForm.$dirty = false;
            $scope.updateStatus = true;
            $scope.title = 'Edit';
            $scope.buttonText = 'Update';
          }, function (error) {
            $scope.errorMessage = error.message;
          });
        }
      }
    };

    $scope.deleteDiscount = function () {
      const modalOptions = {
        closeButtonText: 'Cancel',
        actionButtonText: 'Delete Discount',
        headerText: 'Confirm Delete',
        bodyText: 'Are you sure you want to delete this discount?',
      };

      modalService.showModal({}, modalOptions).then(function (result) {
        if (result === 'ok') {
          dataService.deleteDiscount($scope.discount.id).then(function () {
            onRouteChangeOff();
            $location.path('/discounts');
          }, function (error) {
            $scope.errorMessage = error.message;
          });
        }
      });
    };

    $scope.upload = function () {
      alert('Upload success!');
    };

    function init() {
      if (discountId > 0) {
        getDiscountById(discountId);
      }

      onRouteChangeOff = $scope.$on('$locationChangeStart', function (event, newUrl, oldUrl) {
        if (!$scope.editForm || !$scope.editForm.$dirty) return;

        modalService.showModal({}, {
          closeButtonText: 'Cancel',
          actionButtonText: 'Ignore Changes',
          headerText: 'Unsaved Changes',
          bodyText: 'You have unsaved changes. Leave the page?',
        }).then(function (result) {
          if (result === 'ok') {
            onRouteChangeOff();
            $location.path($location.url(newUrl).hash());
          }
        });

        event.preventDefault();
      });
    }

    function getDiscountById(discountId) {
      dataService.getDiscountById(discountId)
            .then(function (data) {
              $scope.$apply(function () {
                $scope.discount = data;
              });
            }, function (error) {
              $window.alert(`Sorry, an error occurred: ${error.data.message}`);
            });
    }

    init();
  };

  DiscountEditController.$inject = injectParams;

  angular.module('rabattApp').controller('DiscountEditController', DiscountEditController);
}());
