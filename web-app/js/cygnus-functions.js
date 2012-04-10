//temporarily reserved for dropdown search
function enableDropDownOnTextBox(textBoxId, ajaxUrl, minlength) {
	$(textBoxId).autocomplete({
		source : function(request, response) {
			$.ajax({
				url : ajaxUrl,
				data : request,
				success : function(data) {
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
function ajaxRequest(ajaxUrl, request, response) {

	$.ajax({
		url : ajaxUrl,
		data : request,
		success : function(data) {
			response(data);
		}

	});

}

/**
 * Various settings on document.ready
 */
$(document)
		.ready(
				function() {
				

					
					
					// disable escape button
					$(document).keydown(function(e) {
						if (e.keyCode == 27) {
							alert('sorry esc is disabled');
							return false;
						}

					});

					$("#spinner").dialog('close');

					$(' .nav ul li a, th a, td a, a.edit, a.create,a.list,a.delete,div.pagination *')
							.click(function() {
								var objectUrl = $(this).attr('href');
								openUrl(objectUrl);
								return false;
							});

				});

function handleError(XMLHttpRequest, textStatus, errorThrown) {
	$("#ajaxError").empty;
	$('#ajaxRequestError').dialog(
			{
				autoOpen : false,
				modal : true,
				show : "slide",
				hide : "fade",
				height : 500 ,
				width :500
			
			});
	$("#ajaxError").prepend(XMLHttpRequest.responseText);
	$('#ajaxRequestError .cygnusAjaxErrorRespondText').empty();
	$('#ajaxRequestError .cygnusAjaxErrorRespondText').append("</br><h1>Ajax Response Text : "+textStatus+"</br> Ajax Error Thrown : "+errorThrown+"</h1>")
	
	//Force destroy spinner
	$('#spinner').dialog('destroy');
	
	$('#ajaxRequestError').dialog('open');

}


$('#openError').click(function (){
	$("#ajaxError").show();
	
});


function loadSpinner() {
	$('#spinner').dialog('destroy');
	$('#spinner').dialog(
			{
				closeOnEscape : false,
				autoOpen : false,
				modal : true,
				show : "fade",
				hide : "fade",
				open : function(event, ui) {
					$(this).parent().children().children(
							'.ui-dialog-titlebar-close').hide();
				}

			});

	$('#spinner').dialog('open');

}

function closeSpinner() {
	$('#spinner').dialog('close');

}

/**
 * Overriding links for inner div's
 * 
 * @param objectUrl
 * @returns
 */
function openUrl(objectUrl) {

	$("#pageContent").empty();
	loadSpinner();

	$("#pageContent").load(objectUrl);

}

jQuery.fn.center = function() {
	this.css("position", "absolute");
	this.css("top", ($(window).height() - this.height()) / 2
			+ $(window).scrollTop() + "px");
	this.css("left", ($(window).width() - this.width()) / 2
			+ $(window).scrollLeft() + "px");
	return this;
}
