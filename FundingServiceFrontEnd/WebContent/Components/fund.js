$(document).ready(function () {
    if ($("#alertSuccess").text().trim() == "") {
        $("#alertSuccess").hide();
    }
    $("#alertError").hide();
    $("#btncancel").addClass('invisible');
    showFunds();
});


function showFunds() {
	$.ajax(
        {
            url: "FundAPI",
            type: "GET",
            dataType: "text",
            complete: function (response, status) {
                onGetComplete(response.responseText, status);
            }
        });
}


function onGetComplete(response, status) {
	if (status == "success") {
        var res = JSON.parse(response);
        $("#fundingGrid").html(res.data);
    } else if (status == "error") {
        $("#alertError").text("error...");
        $("#alertError").show();
    } else {
        $("#alertError").text("error...");
        $("#alertError").show();
    }
}


$(document).on("click", "#btnSubmit", function (event) {
    // Clear alerts
    $("#alertSuccess").text("");
    $("#alertSuccess").hide();
    $("#alertError").text("");
    $("#alertError").hide();
    // Form validation
    var status = validateForm();
    if (status != true) {
        $("#alertError").text(status);
        $("#alertError").show();
        return;
    }
    // If valid
    //check if put or post
    var type = ($("#hiddenFundId").val() == "") ? "POST" : "PUT";
    $.ajax(
        {
            url: "FundAPI",
            type: type,
            data: $("#fundsForm").serialize(),
            dataType: "text",
            complete: function (response, status) {
                onSaveComplete(response.responseText, status);
            }
        });
});


function onSaveComplete(response, status) {
    if (status == "success") {
        var res = JSON.parse(response);
        if (res.status.trim() == "success") {
            $("#alertSuccess").text("succeeded...");
            $("#alertSuccess").show();
            $("#fundingGrid").html(res.data);
        } else if (res.status.trim() == "error") {
            $("#alertError").text(res.data);
            $("#alertError").show();
        }
    } else if (status == "error") {
        $("#alertError").text("error...");
        $("#alertError").show();
    } else {
        $("#alertError").text("error...");
        $("#alertError").show();
    }
    $("#hiddenFundId").val("");
    $("#btnSubmit").text("Add Fund");
    $("#fundsForm")[0].reset();
}


$(document).on("click", "#btnUpdate", function (event) {
    $("#hiddenFundId").val($(this).data("fundid"));
    $("#btnSubmit").text("Update Fund");
    $("#funderId").val($(this).closest("tr").find('td:eq(1)').text());
    $("#researchId").val($(this).closest("tr").find('td:eq(2)').text());
    $("#fundedAmount").val($(this).closest("tr").find('td:eq(3)').text());
    $("#fundDate").val($(this).closest("tr").find('td:eq(4)').text());
    $("#service_charge").val($(this).closest("tr").find('td:eq(5)').text());
    $("#creditCardNo").val($(this).closest("tr").find('td:eq(6)').text());
});


$(document).on("click", "#btnDelete", function (event) {
    $.ajax(
        {
            url: "FundAPI",
            type: "DELETE",
            data: "fundId=" + $(this).data("fundid"),
            dataType: "text",
            complete: function (response, status) {
                onDeleteComplete(response.responseText, status);
            }
        });
});


function onDeleteComplete(response, status) {
    if (status == "success") {
        var res = JSON.parse(response);
        if (res.status.trim() == "success") {
            $("#alertSuccess").text("succeeded...");
            $("#alertSuccess").show();
            $("#fundingGrid").html(res.data);
        } else if (res.status.trim() == "error") {
            $("#alertError").text(res.data);
            $("#alertError").show();
        }
    } else if (status == "error") {
        $("#alertError").text("error ...");
        $("#alertError").show();
    } else {
        $("#alertError").text("error ...");
        $("#alertError").show();
    }
}


//Validate Form
function validateForm() {
    //validations using form input ids
    if($("#funderId").val().trim() == "") {
    	return "Funder ID cannot be empty."
    }
    
    if($("#researchId").val().trim() == "") {
    	return "Researcher ID cannot be empty."
    }
    
    if($("#fundedAmount").val().trim() == "") {
    	return "Funded Amount cannot be empty."
    }
    
    if($("#fundDate").val().trim() != "") {
    	return "Funded date cannot be Filled or Updated."
    }
    
    if($("#service_charge").val().trim() != "") {
    	return "service charge rate cannot be Filled or Updated."
    }
    
    var creditcrdNo = $("#creditCardNo").val().trim();
    if(creditcrdNo == "") {
    	return "Credit Card Number cannot be empty."
    }
    
    if(! $.isNumeric(creditcrdNo)) {
    	return "Invalid number for Credit card Number."
    }
    
    
    
    return true;
}