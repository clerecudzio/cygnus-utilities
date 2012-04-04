//temporarily reserved for dropdown search
function enableDropDownOnTextBox(textBoxId, ajaxUrl,minlength){
		  $(textBoxId).autocomplete({ 
		    source:function(request,response){
						    $.ajax({
							    url:ajaxUrl,
							    data: request,
								success: function(data) {
									response(data);
								}
								
								    
							    });
			    			},
			    minLength : parseInt(minlength)
		  }); 
	
	
}

/**
 * Standardizing Ajax Requests for JSON
 * 
 * @param ajaxUrl
 *            ajaxUrl location of JSON
 * @param request
 *            Ajax request
 * @param response
 *            Ajax response
 */
function ajaxRequest(ajaxUrl,request,response){
	
	    $.ajax({
		    url:ajaxUrl,
		    data: request,
			success: function(data) {
				response(data);
			}
			
			    
		    });
		
}

/**
 * Spinner modal box
 */
$(document).ready(function(){
	//disable escape button
	$(document).keydown(function(e) {
	    if (e.keyCode == 27) {
	    	alert('sorry esc is disabled');
	    	return false;
	    }
	});
	
	$("#spinner").dialog('close');
	
	$('#nav ul li a, .nav ul li a, th a, td a, a.edit, a.create,a.list,a.delete,div.pagination *').click(function(){
		var objectUrl = $(this).attr('href'); 
		openUrl(objectUrl);
		return false;
	});
	 

	
	
});


/**
 * Overriding links for inner div's
 * 
 * @param objectUrl
 * @returns
 */
function openUrl(objectUrl){
	
	
	$('#spinner').dialog({
			 closeOnEscape:false,
			 autoOpen: false,
	         modal:true,
	         show: "bounce",
			 hide: "fade",
			 open: function(event, ui) { $(this).parent().children().children('.ui-dialog-titlebar-close').hide();}
	         
	  });
	
	$('#spinner').dialog('open');
	
	$("#pageContent").load(objectUrl);
		
}


jQuery.fn.center = function () {
    this.css("position", "absolute");
    this.css("top", ($(window).height() - this.height())/ 2 + $(window).scrollTop() + "px");
    this.css("left", ($(window).width() - this.width()) / 2 + $(window).scrollLeft() + "px");
    return this;
}

