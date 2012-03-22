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
 * @param ajaxUrl ajaxUrl location of JSON
 * @param request Ajax request
 * @param response Ajax response
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