<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="style.css">
		<title>Social Food</title>
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
		<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.0/jquery-ui.min.js"></script>	
		<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
		<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/additional-methods.min.js"></script>

		<script>
			$(document).ready(function(){
				// Handle Validations
				jQuery.validator.setDefaults({
					  success: "valid"
					});
				
				// Registration form rules
				$("#reg_form form").validate({
					  rules: {
						    name: {
						      required: true
						    },
						    email: {
						    	required: true,
						    	email: true
						    },
						    password: {
						    	required: true,
						    	minlength: 6
						    },
						    confirm_password: {
						    	required: true,
						    	minlength: 6
						    }
						  }
				});
				
				// Login form rules
				$("#login_form form").validate({
					  rules: {
						    email: {
						    	required: true,
						    	email: true
						    },
						    password: {
						    	required: true
						    }
						  }
				});
				
				// Host form rules
				$("#form_wrapper form").validate({
					  rules: {
						    food_type: {
						    	required: true
						    },
						    seats: {
						    	required: true
						    },
						    title: {
						    	required: true
						    },
						    description: {
						    	required: true
						    },
						    date: {
						    	required: true
						    },
						    time: {
						    	required: true
						    },
						    price: {
						    	required: true
						    },
						    address: {
						    	required: true
						    },
						    city: {
						    	required: true
						    },
						    state: {
						    	required: true
						    },
						    zip: {
						    	required: true,
						    	number: true,
						    	minlength: 5,
						    	maxlength: 5
						    },
						    country: {
						    	required: true
						    }
					}
				});
				
				var searchZIPcode, searchBy;
				
				if(document.cookie != "") {
					$("#btn_login").hide();
					$("#btn_register").hide();
					$("#btn_logout").show();
				}
				
		  		$("#btn_hiw").click(function(event){
		    		$("#how_it_works").slideToggle();
				});
		  		$("#btn_login").click(function(event){
		    		$("#login_form").slideToggle();
				});
		  		$("#btn_register").click(function(event){
		    		$("#reg_form").slideToggle();
				});
		  		$("#btn_logout").click(function(event){
		  			document.cookie = "";
		    		$("#btn_login").show();
		    		$("#btn_logout").hide();
				});
				$("#create_dinner").click(function(event){
					$("#form_wrapper").slideDown();
 				});
				$("#listEvents").click(function(event){
				   	searchZIPcode = $("#search_Events").val();
				   	
				   	if(searchZIPcode != undefined)
				   		searchBy = "zip";
				   	/*else if(cuisine != undefined && zip == undefined)
				   		searchBy = "cuisine";
				   	else if(cuisine != undefined && zip != undefined)
				   		searchBy = "zip_cuisine";*/
				   	else
				   		alert("no data");
					
				   	$.ajax({
				   		url:"/Learn/event?searchBy="+searchBy+"&zip="+searchZIPcode,
				   		type: "get",
				   		success: function(response, textStatus, jqXHR){
				            // log a message to the console
				            $("#content_food").empty();
				            for (var i=0; i<response.result.length; i++)
				            {
				            	$("#content_food").append('<div class="container" onclick="window.location=\'http://localhost:8080/Learn/meal.html?id='+ response.result[i].unique_id +'\'" style="float: left;" id="' + response.result[i].unique_id + '_eventSearchList"><img src="http://localhost:8080/Learn'+ response.result[i].picture1 +'" alt="Empanadas"><h3>' + response.result[i].title + '</h3><h4>'+ response.result[i].address.city +', '+ response.result[i].address.state + '</h4><h4>'+ response.result[i].date + ' ('+ response.result[i].time +')</h4><h2>$'+ response.result[i].price +'</h2></div>');				            	
				            }
					   		
				        },
				        // callback handler that will be called on error
				        error: function(jqXHR, textStatus, errorThrown){
				            // log the error to the console
				            alert(
				                "The following error occured: "+
				                textStatus, errorThrown
				            );
				        }
				   	});
				});
				$("#listItalian, #listMexican, #listFrench, #listMediterranean, #listVegetarian").click(function(event){
					var URL = "/Learn/event?searchBy=";
					
					var cuisine = $(this).html();
					if(searchZIPcode) {
						searchBy="zip_cuisine";
						URL= URL + searchBy +"&cuisine="+ cuisine +"&zip="+searchZIPcode;
					} else {
						searchBy="cuisine";
						URL= URL + searchBy +"&cuisine=" + cuisine;
					}
					$.ajax({
				   		url:URL,
				   		type: "get",
				   		success: function(response, textStatus, jqXHR){
				            // log a message to the console
				            
					   		$("#content_food").empty();
				            for (var i=0; i<response.result.length; i++)
				            {
				            	$("#content_food").append('<div class="container" onclick="window.location=\'http://localhost:8080/Learn/meal.html?id='+ response.result[i].unique_id +'\'" style="float: left;" id="' + response.result[i].unique_id + '_eventSearchList"><img src="http://localhost:8080/Learn'+ response.result[i].picture1 +'" alt="Empanadas"><h3>' + response.result[i].title + '</h3><h4>'+ response.result[i].address.city  +', '+ response.result[i].address.state + '</h4><h4>'+ response.result[i].date + ' ('+ response.result[i].time +')</h4><h2>$'+ response.result[i].price +'</h2></div>');				            	
				            }
				        },
				        // callback handler that will be called on error
				        error: function(jqXHR, textStatus, errorThrown){
				            // log the error to the console
				            alert(
				                "The following error occured: "+
				                textStatus, errorThrown
				            );
				        }
				   	});
				});

				$("#form_cancel").click(function(){
					$("#form_wrapper").slideUp();
					return false;
				});
			});
		</script>
	</head>
	<body>
		<div id="header_color">
			<div id="header_wrapper">
				<div id="header">
					<a href="index.html"><img src="images/logo.png" alt="Social Food Logo"></a>
					<button id="btn_register">Register</button>
					<button id="btn_login">Login</button>
					<button id="btn_logout">Logout</button>
					<button id="btn_hiw">How it works</button>
				</div>
				<div id="how_it_works">
					<img src="images/howitworks_socialfoodies.png" alt="How it Works">
				</div>
				<div id="login_form">
					<form action="/Learn/login" method="POST">
						<div class="question">
							<label for="email">Email</label>
							<input type="text" name="email"/>
						</div>
						<div class="question">
							<label for="password">Password</label>
							<input type="password" name="password"/>
						</div>
						<input class="small_green_button" type="submit" value="Login"/>
					</form>
				</div>
				<div id="reg_form">
					<form action="/Learn/registration" method="POST">
						<div>
							<label for="name">Name</label>
							<input type="text" name="name">
						</div>
						<div>
							<label for="email">Email</label>
							<input type="text" name="email"/>
						</div>
						<div>
							<label for="password">Password</label>
							<input type="password" name="password"/>
						</div>
						<div>
							<label for="confirm_password">Confirm Password</label>
							<input type="password" name="confirm_password"/>
						</div>
						<input class="small_green_button" type="submit" value="Register"/>
					</form>
				</div>
					
			</div>
		</div>
		<div id="wrapper">
				<div id="content_header">
					<div id="content_header_title">
						<h1>Discover homemade food around you</h1>
					</div>
					<div id="content_header_options">
						<div id="cook" class="options">
							<div class="options_text">
								<h2>I cook</h2>
								<p>List your special plates and invite others to eat at your place</p>
							</div>
								<button id="create_dinner" class="green_button">Host a dinner</button>
						</div>
						<div id="foodie" class="options">
							<div class="options_text">
								<h2>I eat</h2>
								<p>Find homemade food</p>
								<p>Near: <input name="where" type="text" id="search_Events" placeholder="Zip Code, City"></p>
							</div>
							<button class="green_button" id="listEvents">Find a dinner</button>
						</div>
					</div>
					<div class="clear"></div>
				</div>
				<div id="form_wrapper">
					<form action="/Learn/event" method="post" enctype="multipart/form-data">
						<div class="instructions_form">
							<h3>About your meal</h3>
							<img src="images/public.png" alt="This information will be appear public">
							<p>Tell potential guest about the meal you are offering. This information will appear public.</p>
						</div>
						<div class="question">
							<label for="food_type">Type of food</label>
								<select id="food_type" name="cuisine"/>
									<option value="italian">Italian</option>
									<option value="mexican">Mexican</option>
									<option value="vegetarian">Vegetarian</option>
									<option value="french">French</option>
									<option value="greek">Greek</option>
									<option value="persian">Persian</option>
									<option value="indian">Indian</option>
									<option value="pizza">Pizza</option>
									<option value="hamburgers">Hamburgers</option>
									<option value="pastry">Pastry</option>
									<option value="chinese">Chinese</option>
									<option value="thai">Thai</option>
								</select>
						</div>
						<div class="question">
							<label for="seats">No. of Seats</label>
								<select id="seats" name="seats"/>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
								</select>
						</div>
						<div class="question">
							<label for="title">Title</label>
								<input type="text" name="title"/>
						</div>
						<div class="question">
							<label for="description">Description</label>
								<textarea name="description"></textarea>
						</div>
						<div class="question">
							<label for="images">Upload pictures</label>
								<input type="file" name="picture1" accept="image/*"/>
						</div>				
						<div class="question">
							<label for="date">Date</label>
								<input type="date" name="date"/>		
						</div>
						<div class="question">
							<label for="time">Time</label>
								<input type="text" name="time"/>
						</div>
						<div class="question">
							<label for="price">Price per seat</label>
								<input type="text" name="price"/>	
						</div>
						<div class="instructions_form">
							<h3>Show only to confirmed guests</h3>
							<img src="images/lock.png" alt="This information will be private until the guest confirm for dinner">	
							<p>Give more details about the location and importante details for confirmed guests. This information will be shown ONLY to confirmed guests.</p>
						</div>
						<div class="question">
							<label for="phone">Your phone number</label>
								<select id="phone_country" name="phone_country"/>
									<option value="choose">Choose a country</option>
									<option value="Mexico">Mexico</option>
									<option value="US">US</option>
									<option value="UK">UK</option>
									<option value="China">China</option>
								</select>
								<input type="text" name="phone"/>
						</div>
						<div class="question">
							<label for="address">Address</label>
								<input type="text" id="address" name="street1"/>
						</div>
						<div class="question">		
							<label for="city">City</label>
								<input type="text" name="city"/>	
						</div>
						<div class="question">	
							<label for="state">State / Region</label>
								<input type="text" name="state"/>
						</div>
						<div class="question">
							<label for="zip">ZIP / Postal code</label>
								<input type="text" name="zip"/>
						</div>
						<div class="question">
							<label for="country">Country</label>
								<select id="country" name="country"/>
									<option value="choose">Choose a country</option>
									<option value="Mexico">Mexico</option>
									<option value="US">US</option>
									<option value="UK">UK</option>
									<option value="China">China</option>
								</select>
						</div>
						<div class="question">
							<label for="comments">Comments</label>
								<input type="text" name="comments"/>
						</div>
						<!--  input type="hidden" name ="host_id" value="images/plate06.jpg"/>-->
						<p>By clicking "Save and Continue," you confirm that you accept the <a href="">Terms of Service</a> and <a href="">Privacy Policy</a>.</p>
						<input class="small_green_button" type="submit" value="Save and continue"/>
						<a id="form_cancel" class="small_red_button" href="">Cancel</a>
					</form>
				</div>
				<div id="content_tags">
					<div id="content_tags_title">
						<h2>What are you craving for?</h2>
					</div>
					<div class="content_tags_body" id="cuisineFilter">
						<ul>
							<li><a href="#" id="listItalian">italian</a></li>
							<li><a href="#" id="listMexican">mexican</a></li>
							<li><a href="#" id="listFrench">french</a></li>
							<li><a href="#" id="listMediterranean">mediterranean</a></li>
							<li><a href="#" id="listVegetarian">vegetarian</a></li>
						</ul>
					</div>
					<div class="clear"></div>				
				</div>
				<div id="content_food">
				</div>				
			<div id="footer">
			</div>
		</div>
	</body>
</html>