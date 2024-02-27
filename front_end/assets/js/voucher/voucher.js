main_app.controller("voucherController", function($scope, $http){
    $http.get("https://localhost:8080/voucher/get-all")
    .then(function(response) {
      console.log(response)
    });

})