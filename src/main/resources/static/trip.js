    //create a geocoder object to use the geocode
    const geocoder = new google.maps.Geocoder();

$('.regular').hide(); $('.oneoff').hide();
    
    // hide/show input depending on whether the trip is a regular or one-off
    var myradio = $('input[name="typeOfTrip"]');
    myradio.click(function(){
        if ($(this).is(':checked'))
        {
            if($(this).val() == "R"){
                $('.oneoff').hide(); $('.regular').show();
            }else{
                $('.regular').hide(); $('.oneoff').show();
            }
        }
    });

        // Edit Trip form: hide All date-time-checkbox inputs
        $('.regular2').hide(); $('.oneoff2').hide();
    
    // hide/show input depending on whether the trip is a regular or one-off
    var myradio2 = $('input[name="typeOfTrip2"]');
    myradio2.click(function(){
        if ($(this).is(':checked'))
        {
            if($(this).val() == "R"){
                $('.oneoff2').hide(); $('.regular2').show();
            }else{
                $('.regular2').hide(); $('.oneoff2').show();
            }
        }
    });

    //Calendar Input
    $('input[name="date2"], input[name="Date"]').datepicker({
        showAnim: "fadeIn",
        numberOfMonths: 1,
        dateFormat: "D d M, yy",
        minDate: +1,
        maxDate: "+12M",
        showWeek: true
    });

        // Fix Map    
        $('#addtripModal').on('shown.bs.modal', function () {
            google.maps.event.trigger(map, "resize");
            });

    // Click on Create Trip Button
    $('#addtripform').submit(function(event){
        event.preventDefault();
        $("#result").hide();
        $("#spinner").css("display", "block");

        const Time = document.getElementById('time').value;
        const destination = document.getElementById('destination').value;
        const departure = document.getElementById('departure').value;
        const price = document.getElementById('price').value;
        const availableSeats = document.getElementById('seatsavailable').value;
        const date = document.getElementById('date').value;

        const typeOfTrip = $('input[name="typeOfTrip"]:checked').val();
        const daysArray = [];
        $('input:checkbox[name="days"]:checked').each(function(){
            daysArray.push($(this).val());
        });

        const days = daysArray.toString();

        if(availableSeats===""||price===""||typeOfTrip===undefined){
            $('#result').html("<div class='alert alert-danger'>fill all the necessary input fields</div>");
            $("#spinner").css("display", "none");
            $("#result").slideDown();
            $('#addtripform')[0].reset();
            return;
        }

        const dep = checkPlaceCoordinates(departure,"departure");
        const des = checkPlaceCoordinates(destination,"destination");

        if(dep==false||des==false){
            $("#spinner").css("display", "none");
            $("#result").slideDown();
        }

        const datatopost = JSON.stringify({Time,destination,departure,price,availableSeats,typeOfTrip,date,days});
        submitAddTripRequest(datatopost);
    });

    function submitAddTripRequest(datatopost){
        $.ajax({
            url: "/my-trips/create-trip",
            data: datatopost,
            type: "POST",
            cache: false,
            contentType: "application/json",
            success: function(responseData){
                if(responseData==null){
                    location.href="/my-trips";
                }
                else{
                    $('#result').html("<div class='alert alert-danger'>"+responseData+"</div>");
                    $("#spinner").css("display", "none");
                    $("#result").slideDown();
                }
        },
            error: function(){
                $("#result").html("<div class='alert alert-danger'>There was an error with the Ajax Call. Please try again later.</div>");
                $("#spinner").css("display", "none");
                $("#result").fadeIn();

    }
        }); 

    }

    //check longitude and latitude
    function checkPlaceCoordinates(place, typeOfPlace){
        geocoder.geocode(
            {
                'address' : place
            },
            function(results, status){
                if(status == google.maps.GeocoderStatus.OK){
                    const longitude = results[0].geometry.location.lng();
                    const latitude = results[0].geometry.location.lat();

                    console.log(typeOfPlace +" longitude = "+longitude);
                    console.log(typeOfPlace +" latitude = "+latitude);

                    return true;
                }
                $('#result').html("<div class='alert alert-danger'>invalid " + typeOfPlace + "</div>");
                $("#spinner").css("display", "none");
                $("#result").slideDown();
                $('#addtripform')[0].reset();
                return false;
            }
        );
    }