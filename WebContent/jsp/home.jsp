<!DOCTYPE html>
<html>
<head>
<title>home</title>

<script src="jquery.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	  $("#submitFeedbackBtn").click(function(){
		   	var orgEmail = $("#organizerEmail").val();
		   	var custEmail = $("#customerEmail").val();
		   	var comment = $("#feedbackComment").val();
		   	var rating = $("#feedbackRating").val();
		   	alert("organizerEmail: " + orgEmail + " and customerEmail: " + custEmail);
			
		   	//POST
		   	$("#feedbackPost_success").load("/feedback", { 
		   		"revieweeEmail":orgEmail,
		   		"reviewerEmail":custEmail,
		   		"comment":comment,
		   		"rating":rating},function(data){
			   		alert("response has come back");
			   		$("#feedbackPost_success").html("WOOTTTT")
		   	});
	  });
	  
	  //GET
	  $("#listOrganizerFeedback").click(function(){
			var searchBy;
		   	var orgFeedback = $("#search_orgFeedback").val();
		   	//var cuisine = $("#search_cusine").val();
		   	alert("organizerEmail: " + orgFeedback);
		   	
		   	if(orgFeedback != undefined)
		   		searchBy = "revieweeEmail";
		   	/*else if(cuisine != undefined && zip == undefined)
		   		searchBy = "cuisine";
		   	else if(cuisine != undefined && zip != undefined)
		   		searchBy = "zip_cuisine";*/
		   	else
		   		alert("no data");
		   	
			//GET
		   	$("#feedback_list").load("/feedback?searchBy="+searchBy+"&revieweeEmail="+orgFeedback,function(data){
		   		alert("response has come back");
		   		var obj = jQuery.parseJSON(data);
		   		alert( obj.result[0] ); 
		   		
		   		$("#feedback_list").html(data);
		   	});
	  });

	  /*$("#search_dinner").click(function(){
	   	var cuisine = $("#search_cuisine").val();
	   	var zip = $("#search_zip").val();
	   	alert("cuisine: " + cuisine + " and zip: " + zip);
	   	var searchBy;
	   	var url = "/event?searchBy=";
	   	if(cuisine == undefined && zip != undefined)
	   		url= url + "zip" +"&zip="+zip;
	   	else if(cuisine != undefined && zip == undefined)
	   		url= url + "cuisine" +"&cuisine="+cuisine;
	   	else if(cuisine != undefined && zip != undefined)
	   		url= url + "zip_cuisine" +"&cuisine="+cuisine+"&zip="+zip;
	   	else
	   		alert("no data");
	   	
	   	$("#dinner_options").load(url,function(data){
	   	  	alert("response has come back with data "+ data );
	   	  
	   		var obj = jQuery.parseJSON(data);
	   		alert( obj.result[0].cuisine );
	   	  
	   	  	$("#dinner_options").html(data);
	   	  });
	  });*/
	});

</script>
</head>

<body>

	I am home page. Provide me with Host Event and Find Event options. Also list all the dinners happening today

	
	<input type="button" name="hostDinner" id="hostDinner" value="Host Dinner"/>
	<input type="button" name="visitDinner" id="visitDinner" value="Visit Dinner"/>
	<br>
	<br>
	<!-- <form action="/feedback"
      method="POST"> -->
    Post Feedback:<br>
		Organizer (reviewee): <input type="text" name="revieweeEmail" id="organizerEmail"><br>
		Customer (reviewer): <input type="text" name="reviewerEmail" id="customerEmail"><br>
		Comment: <input type="text" name="comment" id="feedbackComment"><br>
		Rating: <input type="text" name="rating" id="feedbackRating"><br>
		<!-- <input type="submit" value="Submit" id="submitBtn" onclick=ÓcheckName();Ó> -->
		<input type="submit" value="Submit" id="submitFeedbackBtn">
	<!-- </form> -->
	<div id="feedbackPost_success"></div>
	
	<br>
	List Feedback: <br>
	<input type="text" id="search_orgFeedback">
	<!-- <input type="text" id="search_zip"> -->
	<button id="listOrganizerFeedback">List Feedback</button>
	
	<div id="feedback_list"></div>
</body>

</html>
