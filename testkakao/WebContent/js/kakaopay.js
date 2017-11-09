	var windowReference;
    var tid;
	$(document).ready(function() {
        if(! /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
            $('#mobile').attr('disabled', 'disabled');
            $('#mobile').removeClass('btn_pay_hover');
            $('#mobile').css('cursor','no-drop');
        }
    });

    function payment(agent){
        
        var access_token = $('.atoken').attr('data-atoken');
    	var token_type = $('.tokenType').attr('data-tokenType');
    	//var authorization = token_type + " " + access_token;
    	var authorization = access_token;

    	var item_name = '와우';
    	
    	var data = {"authorization": authorization, "item_name":item_name};
    	
        var request = $.ajax({
            url: root + "/kakao/initpay.html",
            type: 'POST',
            dataType: 'json',
            data: data
        });

        request.done(function( data ) {
            tid = data.tid;
        	if(agent === 'web'){
                windowReference = window.open(data.next_redirect_pc_url, 'payment_popup', 'width=426,height=510,toolbar=no,location=no');
                windowReeference.$('#tid').val(tid);
            } else {
                //windowReference.location = data.next_redirect_mobile_url;
                //location.href = data.next_redirect_mobile_url;
            }
        });

        request.fail(function( jqXHR, textStatus ) {
            console.log( "Request failed: " + textStatus );
        });
    }
  //]]>