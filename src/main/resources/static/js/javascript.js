//Ajax Call for the sign up form 
//Once the form is submitted
$("#signupform").submit(function(event){
    //hide message
    $("#signupmessage").hide();
    //show spinner
    $("#spinner").css("display", "block");
    //prevent default form processing
    event.preventDefault();
    //collect user inputs
    const firstname = $('input[id="firstname"]').val();
    const lastname = $('input[id="lastname"]').val();
    const password = $('input[id="password"]').val();
    const phoneNumber = $('input[id="phonenumber"]').val();
    const gender = $('input[name="gender"]:checked').val();
    const username = $('input[id="username"]').val();
    const confirmPassword = $('input[id="confirm_password"]').val();
    const email = $('input[id="email"]').val();

    const datatopost = JSON.stringify({firstname,lastname,password,phoneNumber,gender,username,confirmPassword,email});

    $.ajax({
        url: "/signup",
        type: "POST",
        data: datatopost,
        cache: false,
        contentType: "application/json",
        success: function(responseData){
            if(responseData){
                $("#signupmessage").html("<div class='alert alert-danger'>"+responseData+"</div>");
                $("#spinner").css("display", "none");
                $("#signupmessage").slideDown();
            }else{
                $("#signupmessage").html("<div class='alert alert-success'>Verification email has been sent to "+email+"</div>");
                $("#spinner").css("display", "none");
                $("#signupmessage").slideDown();
                $('#signupform')[0].reset();
            }
        },
        error: function(){
            $("#signupmessage").html("<div class='alert alert-danger'>There was an error with the Ajax Call. Please try again later.</div>");
            $("#spinner").css("display", "none");
            $("#signupmessage").slideDown();
            $('#signupform')[0].reset();
        }
    
    });

});

//Ajax Call for the login form
//Once the form is submitted
$("#loginform").submit(function(event){ 
    //hide message
    $("#loginmessage").hide();
    //show spinner
    $("#spinner").css("display", "block");
    //prevent default form processing
    event.preventDefault();
    //collect user inputs
    const email = $('input[id="loginemail"]').val();
    const password = $('input[id="loginpassword"]').val();
    const datatopost = JSON.stringify({email,password});
    //send them to loginController using AJAX
    $.ajax({
        url: "/login",
        type: "POST",
        data: datatopost,
        cache: false,
        contentType: "application/json",
        success: function(responseData){
            if(responseData){
                $('#loginmessage').html("<div class='alert alert-danger'>"+responseData+"</div>");   
                $("#spinner").css("display", "none");
                $("#loginmessage").slideDown();
            }else{
                location.href = "/my-trips";
            }
        },
        error: function(){
            $("#loginmessage").html("<div class='alert alert-danger'>There was an error with the Ajax Call. Please try again later.</div>");
            $("#spinner").css("display", "none");
            $("#loginmessage").slideDown();
            
        }
    
    });

});


//Ajax Call for the forgot password form
//Once the form is submitted
$("#forgotpasswordform").submit(function(event){ 
    //hide message
    $("#forgotpasswordmessage").hide();
    //show spinner
    $("#spinner").css("display", "block");
    //prevent default php processing
    event.preventDefault();
    //collect user inputs
    var datatopost = $(this).serializeArray();
//    console.log(datatopost);
    //send them to signup.php using AJAX
    $.ajax({
        url: "forgot-password.php",
        type: "POST",
        data: datatopost,
        success: function(data){
            $('#forgotpasswordmessage').html(data);
            //hide spinner
            $("#spinner").css("display", "none");
            //show message
            $("#forgotpasswordmessage").slideDown();
        },
        error: function(){
            $("#forgotpasswordmessage").html("<div class='alert alert-danger'>There was an error with the Ajax Call. Please try again later.</div>");
            //hide spinner
            $("#spinner").css("display", "none");
            //show message
            $("#forgotpasswordmessage").slideDown();
        }
    
    });

});

//Ajax Call for the search form 
$("#searchform").submit(function(event){
    $("#results").fadeOut();
    $("#spinner").css("display", "block");
    event.preventDefault();
    data = $(this).serializeArray();
    console.log(data);
    
    
    getSearchTripDepartureCoordinates();
    
});
                        
    // //define functions
    // function getSearchTripDepartureCoordinates(){
    //     geocoder.geocode(
    //         {
    //             'address' : document.getElementById("departure").value
    //         },
    //         function(results, status){
    //             if(status == google.maps.GeocoderStatus.OK){
    //                 departureLongitude = results[0].geometry.location.lng();
    //                 departureLatitude = results[0].geometry.location.lat();
    //                 data.push({name:'departureLongitude', value: departureLongitude});
    //                 data.push({name:'departureLatitude', value: departureLatitude});
    //                 getSearchTripDestinationCoordinates();
    //             }else{
    //                 getSearchTripDestinationCoordinates();
    //             }

    //         }
    //     );
    // }

    // function getSearchTripDestinationCoordinates(){
    //     geocoder.geocode(
    //         {
    //             'address' : document.getElementById("destination").value
    //         },
    //         function(results, status){
    //             if(status == google.maps.GeocoderStatus.OK){
    //                 destinationLongitude = results[0].geometry.location.lng();
    //                 destinationLatitude = results[0].geometry.location.lat();
    //                 data.push({name:'destinationLongitude', value: destinationLongitude});
    //                 data.push({name:'destinationLatitude', value: destinationLatitude});
    //                 submitSearchTripRequest();
    //             }else{
    //                 submitSearchTripRequest();
    //             }

    //         }
    //     );

    // }

    function submitSearchTripRequest(){
        console.log(data);
        $.ajax({
            url: "search.php",
            data: data,
            type: "POST",
            success: function(data2){
                console.log(data);
                if(data2){
                    $('#results').html(data2);
                    //accordion
                    $("#message").accordion({
                        icons: false,
                        active:false,
                        collapsible: true,
                        heightStyle: "content"   
                    });
                }
                $("#spinner").css("display", "none");
                $("#results").fadeIn();
        },
            error: function(){
                $("#results").html("<div class='alert alert-danger'>There was an error with the Ajax Call. Please try again later.</div>");
                $("#spinner").css("display", "none");
                $("#results").fadeIn();

    }
        }); 

    }