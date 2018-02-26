var app = angular.module('expmgr', ['ui.bootstrap', 'ngMessages', 'zingchart-angularjs']);

app.service('transactionService', function($http) {
	this.getTransaction =  function(id) {
       return $http.get('http://localhost:8081/exp-account-service/api/acct-service/1/transaction/' + id)
       .then(function successCallback(response) {
           return response;
       },
       function errorCallback(response) {
        return response;
       });
    };
	this.getListOfTransactions = function() {
       return  $http.get('http://localhost:8081/exp-account-service/api/acct-service/1/transaction/')
       .then(function successCallback(response) {
           return response;
       },
       function errorCallback(response) {
           return response;
       });
    };
	this.updateTransaction =  function(transaction) {
	       return $http.put('http://localhost:8081/exp-account-service/api/acct-service/1/transaction/' + transaction.id, transaction)
	       .then(function successCallback(response) {
	            return response;
	        },
	        function errorCallback(response) {
	            return response;
	        });
	};
	this.createTransaction =  function(transaction) {
			console.log('About to create '+transaction);
	       return $http.post('http://localhost:8081/exp-account-service/api/acct-service/1/transaction/', transaction)
	       .then(function successCallback(response) {
	            return response;
	        },
	        function errorCallback(response) {
	            return response;
	        });
	};
	this.deleteTransaction =  function(transaction) {
	       return $http.delete('http://localhost:8081/exp-account-service/api/acct-service/1/transaction/' + transaction.id)
	       .then(function successCallback(response) {
	            return response;
	        },
	        function errorCallback(response) {
	            return response;
	        });
	};
});

app.controller('expmgrCtrl',  function($scope, $uibModal, transactionService){

	$scope.transactions = [];
	$scope.chartData = [];
	
	$scope.graphTransactions = {
	        globals: {
	            shadow: false,
	            fontFamily: "Verdana",
	            fontWeight: "100"
	        },
	        type: "pie",
	        backgroundColor: "black",

	        legend: {
	            layout: "x5",
	            position: "50%",
	            borderColor: "transperant",
	            marker: {
	                borderRadius: 10,
	                borderColor: "black"
	            }
	        },
	        tooltip: {
	            text: "%v CAD$"
	        },
	        plot: {
	            refAngle: "-90",
	            borderWidth: "0px",
	            valueBox: {
	                placement: "in",
	                text: "%npv %",
	                fontSize: "15px",
	                textAlpha: 20,
	            }
	        },
	        series: [
	        	{		text: 'INCOME',
	        			values: [0],  
	        			backgroundColor: "#28C2D1"
	        	},
	        	{	
		        		text: 'EXPENSE',
		        		values: [0], 
	        			backgroundColor: "#FDAA97 #FC9B87"
	        	}
	          ]
			};
	

	$scope.today = new Date();
	$scope.currentTransaction = null;

	$scope.formAction = 'Add';
	
	
	transactionService.getListOfTransactions().then(function(result){
		console.log('result is :: '+JSON.stringify(result));
		if (result) {
			if (result.status === 200 || result.status === 201){
				result.data.forEach(function(transaction){
					$scope.transactions.push(transaction);
				});
				$scope.updateValuesOnGraph($scope.transactions);
			}
			else {
				console.log("Error while getting list.Please verify backend service. Status code is "+ result.status);
			}
		}

	});
	
	$scope.updateValuesOnGraph = function(listOftransactions){
		console.log("Inside updateValues on Graph");
		var INCOME = 0;
		var EXPENSE = 0;
		for(var i = 0; i< listOftransactions.length; i++){
			if(listOftransactions[i].type === 'INCOME'){
				INCOME = INCOME +listOftransactions[i].amount;
			}else {
				EXPENSE = EXPENSE +listOftransactions[i].amount;
			}
		}
		$scope.graphTransactions.series[0].values[0] = INCOME;
		$scope.graphTransactions.series[1].values[0] = EXPENSE;
		console.log('INCOME is :: '+ INCOME);
		console.log('EXPENSE is :: '+ EXPENSE);
	}
	
	$scope.showTransactionForm = function(transaction){
		console.log('Inside the showTransactionForm method');
        $scope.message = "Show Form Button Clicked";
        if(transaction != null){
			console.log('The returned transaction is ::'+transaction);
        }
        console.log('Opening the modal form');
        var modalInstance = $uibModal.open({
            templateUrl: '/modal-form',
            controller: ModalInstanceCtrl,
            scope: $scope,
            resolve: {
            	// Setting up the data to be passed to ModalInstanceCtrl function
                transactionData: transaction,
                transactionService: transactionService,
            }
        });
        
        // Take action when modal form is opened.
        modalInstance.opened.then(function () {
        	console.log('Modal Opened');
        });
        
        // Take the action when modal form is closed.
        modalInstance.result.then(function (transaction) {
        	console.log('Modal Closed');
        }, function () {
            console.log('Modal dismissed at: ' + new Date());
        });
    };
});

var ModalInstanceCtrl = function ($scope, $uibModalInstance, transactionData, transactionService) {
	console.log('transaction data received is :: '+ transactionData);
	var action = '';
	var updateTransactionData = {};
	
	if (transactionData != null){
		$scope.formAction = 'Edit';
		$scope.showDeleteBtn = true;
		$scope.description = transactionData.description;
		$scope.type = transactionData.type;
		$scope.amount = transactionData.amount;
		$scope.date = new Date(transactionData.date);
		
		$scope.currentTransaction = transactionData;
	}
    
    $scope.submitForm = function () {
    	
        if ($scope.form.transactionDetailsForm.$valid) {
            console.log('transactionDetails form is in scope');
            /// Check if transaction exists and decide the action
            if (transactionData == null){
            	action = 'create';
            	transactionData = new Object();
               	transactionData.amount = $scope.amount;
            	transactionData.type = $scope.type;
            	transactionData.description = $scope.description;
            	transactionData.date = $scope.date;
            }
            else if (transactionData.amount === $scope.amount && transactionData.description === $scope.description && transactionData.type === $scope.type && transactionData.date === $scope.date){
            	action = 'noupdate';
            }
            else {
            	action = 'update';
            	transactionData = transactionData;
            	transactionData.amount = $scope.amount;
            	transactionData.type = $scope.type;
            	transactionData.description = $scope.description;
            	transactionData.date = $scope.date;
            }
            
            
            // work as per action as update or create
            if (action === 'update'){
            	console.log('updating transaction');
	            transactionService.updateTransaction(transactionData).then(function(result){
	        		if (result) {
	        			if (result.status === 200 || result.status === 201){
			            	console.log('returned result of update is '+JSON.stringify(result.data));
			    			console.log(JSON.stringify($scope.transactions));
			                $scope.updateValuesOnGraph($scope.transactions);
	        			}
	        		}
	    		});
            }
            else {
            	// add a transaction
            	console.log('adding a transaction');
	            transactionService.createTransaction(transactionData).then(function(result){
	        		if (result) {
	        			if (result.status === 200 || result.status === 201){
			            	console.log(JSON.stringify($scope.transactions));
			    			$scope.transactions.push(result.data);
			                console.log('no of transactions:: '+$scope.transactions.length);
			                $scope.updateValuesOnGraph($scope.transactions);
	        			}
	        			else {
	        				console.log('Error while adding a transaction'+ result.status);
	        			}
	        		}
	    		});
            }
            $uibModalInstance.close('closed');
        } else {
            console.log('transactionDetailsform is not in scope');
        }
    };
    
    $scope.deleteTransaction = function(){
    	console.log("Deleting Transaction :: "+JSON.stringify($scope.currentTransaction));
    	var transactionToDelete = $scope.currentTransaction;
    	console.log("Delete::" + transactionToDelete.id);
        transactionService.deleteTransaction(transactionData).then(function(result){
    		if (result) {
    			if (result.status === 200 || result.status === 201){
    				var indexOfElementTobeDeleted ;
    				for (var i=0; i < $scope.transactions.length;i++){
    					if ($scope.transactions[i].id === transactionToDelete.id){
    						indexOfElementTobeDeleted = i;
    					}
    				}
		        	$scope.transactions.splice(indexOfElementTobeDeleted, 1);
		        	console.log('Total transactions after deleting'+ JSON.stringify($scope.transactions));
		        	console.log('Total number of transactions are :: '+$scope.transactions.length);
		        	if($scope.transactions.length > 0){
		        		$scope.updateValuesOnGraph($scope.transactions);
		        	}
    			}
    			else {
    				console.log('Error while deleting a transaction'+ result.status);

    			}
    		}
        	$scope.close();
		});
    };
    
    $scope.close = function () {
    	console.log("Closing modal form");
    	$uibModalInstance.dismiss('cancel');
    };
};

