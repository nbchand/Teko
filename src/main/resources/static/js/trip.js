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

        // Click on Edit Trip Button
        $('#edittripModal').on('show.bs.modal', function (event) {
            $('#result2').html("");
            const id = event.relatedTarget.id;

            $.ajax({
                url: "/my-trips/getTrip",
                data: id,
                type: "POST",
                cache: false,
                contentType: "text/plain",
                success: function(trip){
                    if(trip){
                        formatModal(trip);
                    }
                    else{
                        $('#result2').html("<div class='alert alert-danger'>no data found</div>");
                    }
            },
                error: function(){
                    $('#result2').html("<div class='alert alert-danger'>There was an error with the Ajax Call. Please try again later.</div>");
        }
            });
        });

        function formatModal(trip){
            $('#departure2').val(trip.departure);    
            $('#destination2').val(trip.destination); 
            $('#price2').val(trip.price);    
            $('#seatsavailable2').val(trip.availableSeats); 
            if(trip.typeOfTrip == "R"){
                $('#yes2').prop('checked', true);
                $('#monday2').prop('checked', trip.days.includes("Mon")? true:false);
                $('#tuesday2').prop('checked', trip.days.includes("Tue")? true:false);
                $('#wednesday2').prop('checked', trip.days.includes("Wed")? true:false);
                $('#thursday2').prop('checked', trip.days.includes("Thu")? true:false);
                $('#friday2').prop('checked', trip.days.includes("Fri")? true:false);
                $('#saturday2').prop('checked', trip.days.includes("Sat")? true:false);
                $('#sunday2').prop('checked', trip.days.includes("Sun")? true:false);
                $('input[name="time2"]').val(trip.time);
                $('.oneoff2').hide(); $('.regular2').show();
            }else{
                $('#no2').prop('checked', true);
                let d = new Date(trip.date).toDateString().split(" ");
                let date = d[0]+" "+d[2]+" "+d[1]+", "+d[3];
                $('input[name="date2"]').val(date);
                $('input[name="time2"]').val(trip.time);
                $('.regular2').hide(); $('.oneoff2').show();
            }
            $('input[name="deletetrip"]').attr('id', 'd'+trip.tripId);
            $('input[name="updatetrip"]').attr('id', 'u'+trip.tripId);
        }

                //setup delete button for AJAX Call
                $('input[name="deletetrip"]').click(function(){
                    $("#spinner").css("display", "block");
                    const arr = this.id.split("d");
                    $.ajax({
                        url: "/my-trips/delete-trip",
                        data: arr[1],
                        type: "POST",
                        cache: false,
                        contentType: "text/plain",
                        success: function(response){
                            if(response=="success"){
                                location.href = "/my-trips"
                            }else{
                                $('#result2').html("<div class='alert alert-danger'>Trip not found</div>");
                                $('#result2').hide();
                                $('#result2').fadeIn();
                                $("#spinner").css("display", "none");
                            }
                    },
                        error: function(){
                            $('#result2').html("<div class='alert alert-danger'>There was an error with the Ajax Call. Please try again later.</div>");
                            $('#result2').hide();
                            $('#result2').fadeIn();
                            $("#spinner").css("display", "none");
                        }
                    
                });
                });

        // submit edit trip form
        $('#edittripform').submit(function(event){
            $("#result2").hide();
            $("#spinner").css("display", "block");
            event.preventDefault();
            const btnId = $("input[name='updatetrip']").attr('id');
            const id = btnId.substring(1);

            const Time = document.getElementById('time2').value;
            const destination = document.getElementById('destination2').value;
            const departure = document.getElementById('departure2').value;
            const price = document.getElementById('price2').value;
            const availableSeats = document.getElementById('seatsavailable2').value;
            const date = document.getElementById('date2').value;
    
            const typeOfTrip = $('input[name="typeOfTrip2"]:checked').val();
            const daysArray = [];
            $('input:checkbox[name="days2"]:checked').each(function(){
                daysArray.push($(this).val());
            });
    
            const days = daysArray.toString();
    
            if(availableSeats===""||price===""||typeOfTrip===undefined){
                $('#result2').html("<div class='alert alert-danger'>fill all the necessary input fields</div>");
                $("#spinner").css("display", "none");
                $("#result2").slideDown();
                $('#edittripform')[0].reset();
                return;
            }
    
            const dep = checkPlaceCoordinates(departure,"departure");
            const des = checkPlaceCoordinates(destination,"destination");
    
            if(dep==false||des==false){
                $("#spinner").css("display", "none");
                $("#result2").slideDown();
            }
    
            const datatopost = JSON.stringify({Time,destination,departure,price,availableSeats,typeOfTrip,date,days,id});
            submitEditTripRequest(datatopost);
        });

        function submitEditTripRequest(datatopost){
            $.ajax({
                url: "/my-trips/edit-trip",
                data: datatopost,
                type: "POST",
                cache: false,
                contentType: "application/json",
                success: function(responseData){
                    if(responseData==null){
                        location.href="/my-trips";
                    }
                    else{
                        $('#result2').html("<div class='alert alert-danger'>"+responseData+"</div>");
                        $("#spinner").css("display", "none");
                        $("#result2").slideDown();
                    }
            },
                error: function(){
                    $("#result2").html("<div class='alert alert-danger'>There was an error with the Ajax Call. Please try again later.</div>");
                    $("#spinner").css("display", "none");
                    $("#result2").fadeIn();
    
        }
            }); 
    
        }