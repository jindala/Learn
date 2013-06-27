<%
    String id = (String) request.getParameter("id");
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="style.css">
		<title>Meal Details</title>
		<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css">
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
		<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.0/jquery-ui.min.js"></script>	
    	<script src="https://checkout.stripe.com/v2/checkout.js"></script>
  		    	
		<script>
	        function getCookie(c_name)
	        {
	            var c_value = document.cookie;
	            var c_start = c_value.indexOf(" " + c_name + "=");
	            if (c_start == -1)
	           	{
	            	  c_start = c_value.indexOf(c_name + "=");
	            }
	            if (c_start == -1)
	            {
	            	  c_value = null;
	            }
	            else
	            {
	            	  c_start = c_value.indexOf("=", c_start) + 1;
	            	  var c_end = c_value.indexOf(";", c_start);
	            	  if (c_end == -1)
	           		  {
	            		    c_end = c_value.length;
	            	  }
	            	  c_value = unescape(c_value.substring(c_start,c_end));
	            }
	            return c_value;
	        }
        
			$(document).ready(function(){
				var param = window.location.search;
				var id =  "<%=id%>";//param.split("=")[1];
				var dinnerTitle;
				var baseAmount;
				var userRelationToEvent = "none";
				var currentUid = getCookie("socialfooduid");
				if(id != "" && id != undefined)
				{  	
				   	$.ajax({
				   		url:"/event.do?id="+id,
				   		type: "get",
				   		success: function(response, textStatus, jqXHR){
				   			dinnerTitle = response.result[0].title;
				   			baseAmount = response.result[0].price;
					   		$("#dinnerTitle").html("<h2>" + response.result[0].title + "</h2>");
					   		$("#date").html("<p id='date'>Date: " + response.result[0].date + "</p>");
					   		$("#cost").html("<p id='cost'>Cost: $" + response.result[0].price + "</p>");
					   		$("#firstCuisine").html("<a id='firstCuisine' href='#'>" + response.result[0].cuisine + "</a>");
					   		$("#location").html("<span id='location' class='bold'>" + response.result[0].address.city + ", " + response.result[0].address.state +"</span>");
					   		$("#time").html("<span id='time' class='bold'>" + response.result[0].time + "</span>");
					   		$("#totalseats").html("<span id='totalseats' class='bold'>" + response.result[0].seats +"</span>");
					   		$("#description").html("<p id='description'>" + response.result[0].description +"</p>");
					   		$("#dinner_img").attr("src","http://localhost:8080" +response.result[0].picture1);
					   		
					   		var attendees = response.result[0].attendees;
					   		var totalEventGuests = 0;
					   		if(attendees != null && attendees != undefined){
					   			for(var i=0;i<attendees.length;i++) {
					   				if(attendees[i].uId == currentUid) {
					   					userRelationToEvent = "guest";
					   					$("#form_submit input").attr("disabled", "disabled");
					   					$("#book_button").val(attendees[i].totalGuests + " guests Confirmed");
					   					$("#booking_seats").hide();
					   					$("#seatsLabel").hide();
					   				}
					   				totalEventGuests += parseInt(attendees[i].totalGuests);
					   			}
					   		}
				   			if(currentUid == response.result[0].host_id) {
				   				userRelationToEvent = "host";
			   					$("#form_submit input").attr("disabled", "disabled");
			   					$("#book_button").val(totalEventGuests + " guests Confirmed");
			   					$("#booking_seats").hide();
			   					$("#seatsLabel").hide();
				   			}
				   			
					   		var googleFormattedAddress = "";
					   		if(response.result[0].address.street1 != null || response.result[0].address.street1 != undefined) {
					   			googleFormattedAddress += response.result[0].address.street1.replace(/ /g, '+');
					   		}
					   		if(response.result[0].address.city != null || response.result[0].address.city != undefined) {
					   			googleFormattedAddress += ',' + response.result[0].address.city.replace(/ /g, '+');
					   		}
					   		if(response.result[0].address.state != null || response.result[0].address.state != undefined) {
					   			googleFormattedAddress += ',' + response.result[0].address.state.replace(/ /g, '+');
					   		}
					   		if(response.result[0].address.country != null || response.result[0].address.country != undefined) {
					   			googleFormattedAddress += ',' + response.result[0].address.country.replace(/ /g, '+');
					   		}
					   		
					   		$("#book_eventid").val(id);
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
				}
				
				$("#book_uid").val(currentUid);
				
				$("#book_button").click(function() {
					//$("#dialog").dialog("open");
					//$("#form_submit input").attr("disabled", "disabled");
					
					if(currentUid == null || currentUid == undefined) {
						alert("Please login in order to book for the event");
						return false;
					}
						
					
					// Code to support Stripe payment
					var token = function(res){
				        var $input = $('<input type=hidden name=stripeToken />').val(res.id);
				        $('form').append($input).submit();
				      };

				      var numCustomers = $('#booking_seats option:selected').val();
				      var totalAmount = parseInt(baseAmount)*parseInt(numCustomers)*100;
				      StripeCheckout.open({
				        key:         'pk_test_z3R9pRnlmZhzOymJfAuSc0L1',
				        address:     true,
				        amount:      totalAmount,
				        name:        dinnerTitle,
				        description: 'Secure payment through SocialFood',
				        panelLabel:  'Pay',
				        token:       token
				      });

				      return false;
				});
				
				$("#writeFeedbackBtn").click(function(){
					/*var overlay = jQuery('#overlay');
					overlay.appendTo(document.body);*/
					
					//$("#overlay").appendTo(document.body);
					/*$('#overlay').fadeIn('fast',function(){
						//$("#chef_feedback").appendTo($("#overlay"));
						//$("#chef_feedback").appendTo(document.body);
						$('#chef_feedback').animate({'top':'160px'},500);
					});*/
					
					//WORKS (NOT):
					//Show the overlay
			        //$("#overlay").show();

			        //Show the hidden div
			        $("#chef_feedback").fadeIn(600);
				});
				
				$("#submitFeedbackBtn").click(function(){
				   	/*var orgEmail = $("#organizerEmail").val();
				   	var custEmail = $("#customerEmail").val();*/
				   	var comment = $("#feedbackComment").val();
				   	var rating = $("#feedbackRating").val();
					
				   	//POST
				   	$("#feedbackPost_success").load("/feedback", { 
				   		//"revieweeEmail":orgEmail,
				   		//"reviewerEmail":custEmail,
				   		"comment":comment,
				   		"rating":rating},function(data){
					   		$("#feedbackPost_success").html(comment);
				   	});
				   	
				   	//Clear the form after submit
				   	$("#organizerEmail").val("");
				   	$("#customerEmail").val("");
				   	$("#feedbackComment").val("");
				   	$("#feedbackRating").val("");
				   	
				    //Hide the feedback form
			        $("#chef_feedback").fadeOut(600);
				    
				    //Update the last comment
				    $("#lastComment").html(comment);
			    });
			});
		</script>
	</head>
	<body>
		<!-- <div id="dialog" title="Your dinner seats are confirmed">Enjoy your meal!</div>-->
		<div id="header_color">
			<div id="header_wrapper">
				<div id="header">
					<a href="/"><img src="images/logo.png" alt="Social Food Logo"></a>
					<div id="search">	
						<button class="small_green_button" id="quickSearch">Go</button>
						<h4>Find homemade food near: <input name="where" type="text" value="Zip Code, City"></h4>
					</div>
				</div>
			</div>
		</div>
		<div id="wrapper">
			<div id="content">
				<div id="content_left">
					<p><h2 id="dinnerTitle">Title created by the host</h2></p>
					<img id="dinner_img" class="plate_image" src="images/plate1.jpeg" alt="Plate1">
					<div id="booking_form">
						<h3>Booking details</h3>
						<p id="date">Date: 12/03/2012</p>
						<p id="cost">Cost: $15</p>
						<form id="book_form" action="payment.do" method="POST">
						<div id="book_form_div">
							<label id="seatsLabel">Seats:</label>
							<select id="booking_seats" name="seats">
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
							</select>
							<div id="form_submit">
								<input id="book_button" class="green_button" type="submit" value="Book" />
							</div>
						</div>
						<input type="hidden" name="book_uid" id="book_uid" value=""/>
						<input type="hidden" name="book_eventid" id="book_eventid" value=""/>
						</form>
					</div>
					<div class="clear"></div>
					<div id="description">
						<ul class="content_tags">
							<li><a id="firstCuisine" href="#">Baguettes</a></li>
							<li><a href="#">Healthy</a></li>
							<li><a href="#">Mexican</a></li>
						</ul>
						<p id="description">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vel augue mauris, ut laoreet est. Praesent metus nisl, luctus a dapibus vitae, hendrerit non lacus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Proin commodo, lacus ac sollicitudin vehicula, nulla arcu ultrices tellus, ac mattis nisi lectus adipiscing ligula. Duis dictum viverra est, at euismod leo mattis sit amet. Etiam consequat varius turpis, viverra gravida magna ullamcorper vel. Etiam vel arcu et eros mattis dapibus.</p>
					</div>
					<div id="date_time_map">
						<div id="date_time">
							<p>Meal: <span class="bold">Dinner<span></p>
							<p>Time: <span id="time" class="bold">6 PM<span></p>
							<p>Location: <span id="location" class="bold">Mountain View, CA<span></p>
							<p>Total guest: <span id="totalseats" class="bold">10<span></p>
						</div>
						<div id="map" style="width:250px;height:250px">
							<!-- 
							<img id="locationImage" src=""/>
							-->
							<iframe width="250" height="250" frameborder="0" id="locationSmallMap" scrolling="no" marginheight="0" marginwidth="0" src="https://maps.google.com/maps?f=q&amp;source=s_q&amp;hl=en&amp;geocode=&amp;q=PayPal,+North+1st+Street,+San+Jose,+CA&amp;aq=0&amp;oq=paypa&amp;sll=36.914764,-119.311523&amp;sspn=10.569242,19.753418&amp;ie=UTF8&amp;hq=PayPal,+North+1st+Street,+San+Jose,+CA&amp;t=m&amp;ll=37.376455,-121.922616&amp;spn=0.017734,0.032015&amp;output=embed"></iframe><br /><small><a href="https://maps.google.com/maps?f=q&amp;source=embed&amp;hl=en&amp;geocode=&amp;q=PayPal,+North+1st+Street,+San+Jose,+CA&amp;aq=0&amp;oq=paypa&amp;sll=36.914764,-119.311523&amp;sspn=10.569242,19.753418&amp;ie=UTF8&amp;hq=PayPal,+North+1st+Street,+San+Jose,+CA&amp;t=m&amp;ll=37.376455,-121.922616&amp;spn=0.017734,0.032015" id="locationLink" style="color:#0000FF;text-align:left">View Larger Map</a></small>
							
						</div>
					</div>
				</div>
				<div id="content_right">
					<p><h2>Meet your chef</h2></p>
					<div id="chef_profile" class="chef_class">
						<img src="images/chef_image.jpg" alt="Chef Name" class="profile_img">
						<p>Laura Sauceda</p>
						<button href="mailto:" class="small_green_button">Contact</button>
					</div>
					<div id="chef_desc" class="chef_class">
						<h4>About the host<meta>:</h4>
						<p>	I am a psychotherapist and an homeopath. In my spare time, I love coffee shops and watching movies. I love baking, oats cookies are my speciality.</p>
					</div>
					<div id="chef_rating" class="chef_class">
						<h3>Rating:</h3> <img src="images/rating3.png" alt="Rating image" />
					</div>
					<div id="chef_reviews" class="chef_class">
						<p><h4>Reviews:</h4></p>
						<div class="reviews">
							<p id="lastComment" class="comments">Laura is a great cook! Her enchiladas are great! We had a great time.</p>
							<p class="date">8 hours ago</p>
							<p class="by_user">by Nora Jones</p>
						</div>
						<div class="reviews">
							<p id="secondLastComment" class="comments">Her tacos are the best ever! Just be carefull with the spicy salsa!</p>
							<p class="date">1 days ago</p>
							<p class="by_user">by John Wu</p>
						</div>
						<div class="reviews">
							<p id="thirdLastComment" class="comments">I love Mexican food! I enjoyed a lot eating at Laura's place! She is a great host.</p>
							<p class="date">3 days ago</p>
							<p class="by_user">by James Hy</p>
						</div>
					</div> 
					<div id="chef_reviews" class="chef_class">
						<button class="small_green_button" id="writeFeedbackBtn">Write a review</button>
					</div>
					<div id="chef_feedback" class="chef_class" style="display:none;">
						<div class="reviews" id="feedbackPost_success"></div>
							Post Feedback:<br>
							    Organizer (reviewee): <input type="text" name="revieweeEmail" id="organizerEmail"><br>
								Customer (reviewer): <input type="text" name="reviewerEmail" id="customerEmail"><br> 
								Comment: <input type="text" name="comment" id="feedbackComment"><br>
								Rating: <input type="text" name="rating" id="feedbackRating"><br>
								<input type="submit" value="Submit" id="submitFeedbackBtn">
							 <!-- <button href="mailto:" class="small_green_button" id="writeFeedbackBtn">Write a review</button> -->
					</div>
					<div class="overlay" id="overlay" style="display:none;"></div>
				</div>
			</div>
			<div id="footer">
			</div>
		</div>
	</body>
</html>
