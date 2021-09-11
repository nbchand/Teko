// Ajax call to update-username controller
$("#updateusernameform").submit(function(event){ 
//prevents loading after form submission
    event.preventDefault();
    //collect user inputs
    // var datatopost = $(this).serializeArray();

    const newUsername = document.getElementById('username').value;

    $.ajax({
        url: "/profile/update-username",
        type: "POST",
        contentType: "text/plain",
        cache: false,
        data: newUsername,
        success: function(responseData){
            if(responseData==null){
                location.href ="/profile";
            }
                $("#updateusernamemessage").html("<div class='alert alert-danger'>"+responseData+"</div>");
        },
        error: function(){
            $("#updateusernamemessage").html("<div class='alert alert-danger'>There was an error with the Ajax Call. Please try again later.</div>");
            
        }
    
    });

});


$("#updatepasswordform").submit(function(event){ 

    event.preventDefault();

    const prevPassword = document.getElementById('currentpassword').value;
    const newPassword = document.getElementById('password').value;
    const confirmNewPassword = document.getElementById('password2').value;
    const datatopost = JSON.stringify({prevPassword,newPassword,confirmNewPassword});

    $.ajax({
        url: "/profile/update-password",
        type: "POST",
        contentType: "application/json",
        cache: false,
        data: datatopost,
        success: function(responseData){
            if(responseData==null){
                location.href ="/profile";
            }
                $("#updatepasswordmessage").html("<div class='alert alert-danger'>"+responseData+"</div>");
        },
        error: function(){
            $("#updatepasswordmessage").html("<div class='alert alert-danger'>There was an error with the Ajax Call. Please try again later.</div>");
            
        }
    
    });

});



// Ajax call to update-email controller
$('#loading').hide();
$("#updateemailform").submit(function(event){ 
    event.preventDefault();
    //collect user inputs
    // var datatopost = $(this).serializeArray();

    const newEmail = document.getElementById('email').value;

    $.ajax({
        url: "/profile/update-email",
        type: "POST",
        contentType: "text/plain",
        cache: false,
        data: newEmail,
        success: function(responseData){
            if(responseData==null){
                location.href ="/profile";
            }
            if(responseData=="success"){
                $("#updateemailmessage").html("<div class='alert alert-success'>Confirmation email sent</div>");
            }else{
                $("#updateemailmessage").html("<div class='alert alert-danger'>"+responseData+"</div>");
            }
        },
        error: function(){
            $("#updateemailmessage").html("<div class='alert alert-danger'>There was an error with the Ajax Call. Please try again later.</div>");
            
        }
    
    });

});





//Update picture
var file;

$("#updatepictureform").submit(function(event) {
    //hide message
    $("#updatepicturemessage").hide();
    //show spinner
    $("#spinner").css("display", "block");
    event.preventDefault();
    if(!file){
        $("#spinner").css("display", "none");
        $("#updatepicturemessage").html('<div class="alert alert-danger">Please upload a picture!</div>');
            $("#updatepicturemessage").slideDown();
        return false;
    }
    var imagefile = file.type;
    var match= ["image/jpeg","image/png","image/jpg"];
        if($.inArray(imagefile, match) == -1){
            $("#updatepicturemessage").html('<div class="alert alert-danger">Wrong File Format</div>');
            $("#updatepicturemessage").slideDown();
            $("#spinner").css("display", "none");
            return false;
        }else{
            $.ajax({
                url: "updatepicture.php", 
                type: "POST",             
                data: new FormData(this), 
                contentType: false,       // The content type used when sending data to the server.
                cache: false,             // To unable request pages to be cached
                processData:false,        // To send DOMDocument or non processed data file it is set to false
                success: function(data){
                    if(data){
                        $("#updatepicturemessage").html(data);
                        //hide spinner
                        $("#spinner").css("display", "none");
                        //show message
                        $("#updatepicturemessage").slideDown();
                        //update picture in the settings
                    }else{
                        location.reload();
                    }

                },
                error: function(){
                    $("#updatepicturemessage").html("<div class='alert alert-danger'>There was an error with the Ajax Call. Please try again later.</div>");
                    //hide spinner
                    $("#spinner").css("display", "none");
                    //show message
                    $("#signupmessage").slideDown();

                }
            });
        }

});

// Function to preview image after validation
$(function() {
$("#picture").change(function() {
$("#updatepicturemessage").empty();
file = this.files[0];
var imagefile = file.type;
var match= ["image/jpeg","image/png","image/jpg"];
    if($.inArray(imagefile, match) == -1){
        $("#updatepicturemessage").html("<div class='alert alert-danger'>Wrong file format!</div>");
        return false;
    }
    else{
        var reader = new FileReader();
        reader.onload = imageIsLoaded;
        reader.readAsDataURL(this.files[0]);
    }
});
});
function imageIsLoaded(event) {
    $('#previewing').attr('src', event.target.result);
};