<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Funding Service</title>
<!-- Bootstrap -->
<link rel="stylesheet" href="Views/bootstrap.min.css">
<!-- JQuery -->
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/fund.js"></script>
</head>
<body>
	<main>
		<div class="container py-5">
			<div class="row pb-3">
				<h3 class="col-12">Funding Form</h3>
				<form id="fundsForm" class="form col-12">
					<div class="row">
						<input type="text" id="funderId" name="funderId"
							placeholder="Funder ID" class="form-control col-12 mb-2">
						<input type="text" id="researchId" name="researcId"
							placeholder="Research ID" class="form-control col-12 mb-2">
						<input type="text" id="fundedAmount" name="fundedAmount"
							placeholder="Funded Amount" class="form-control col-12 mb-2">
						<input type="text" id="fundDate" name="fundDate"
							placeholder="Funded Date" class="form-control col-12 mb-2">
						<input type="text" id="service_charge" name="service_charge"
							placeholder="Service Charge" class="form-control col-12 mb-2">
						<input type="text" id="creditCardNo" name="creditCardNo"
							placeholder="Credit card Number" class="form-control col-12 mb-2">
						<input type="hidden" id="hiddenFundId" name="hiddenFundId" value="">
						<button type="button" id="btnSubmit"
							class="form-control btn btn-primary mb-2" value="" class="col-12">Add Fund</button>
					</div>
				</form>
			</div>
			<div class="row pb-3">
				<br>
				<div id='alertSuccess'
					class='alert alert-success alertSuccess'></div>
				<div id='alertError' class='alert alert-danger alertError'></div>
				<br>
			</div>
			<div class="row pb-3">
				<h3 class="col-12">Funding Table</h3>
				<div class="" id="fundingGrid">
					<!-- auto generated table on AJAX request -->
				</div>
			</div>
		</div>
	</main>
</body>
</html>