'use strict';

angular.module('rabattApp').controller('LogoutController', [ '$scope', function($scope) {
		Session.clear();
} ]);