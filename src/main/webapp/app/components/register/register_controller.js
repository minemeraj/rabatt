'use strict';

angular.module('rabattApp').controller('RegisterController', [ '$scope', 'UserService', 'FlashService', '$location','$window' , function($scope, UserService, FlashService, $location, $window) {
	var self = this;
	self.user = {
			id : null,
			username : '',
			address : '',
			email : '',
			password: ''
		};

	self.submit = submit;

	function submit() {
		UserService.createUser(self.user)
			.then(
				FlashService.Success('Registration successful', true),
				$window.location.href = './index.html',
				function(errResponse) {
					console.error(errResponse);
				}
		);
	}

} ]);