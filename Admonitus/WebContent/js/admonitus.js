Date.prototype.toDateInputValue = (function() {
    var local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0,10);
});


var admonitus = {};
admonitus.transFreqNoToName = function(num)
{
	switch (num)
	{
	case 0:
		return "One-time";
	case 1:
		return "Daily";
	case 2:
		return "Weekly";
	case 3:
		return "Monthly";
	default:
		return "Invalid";
	}
}


var mainObj = function() {
	console.log("It works!");
	$("input[name=startingat]").val((new Date()).toDateInputValue());

	
	$("#deleteModal").on('show.bs.modal',
		function(e)
		{
	    	$("button[name=deleteConfirm]").attr("data-id", $(e.relatedTarget).data('id'));
		}
	);
	
	$(document).on('click', "button[name=deleteConfirm]",
		function(e)
		{
			var id = $(this).attr("data-id");
			console.log("Delete id " + id);

			$.ajax({
				type: "GET",
				dataType: "json",
				url: "/Admonitus/ctl/Reminder/delete/" + id
			});
			
			$("#remindersList").find("tr[id=" + id + "]").remove();
		}
	);
	
	
	$("#reminderForm").submit(function(e) {
		e.preventDefault();

		var request = {
			"frequency": $("input[name=frequency]:checked").val(),
			"text": $("input[name=text]").val(),
			"startingat": $("input[name=startingat]").val()
		}
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "/Admonitus/ctl/Reminder/add",
			data : request
		});
	});

	$.ajax({
		type : "GET",
		dataType : "json",
		url : "/Admonitus/ctl/Reminder/get",
		success : function(data) {

			$.each(data, function(index, entry) {
				newrow = $("#template").clone();
				newrow.find("td.number").text(index + 1);
				newrow.find("td.text").text(entry['text']);
				newrow.find("button[name=deleteButton]").attr('data-id', entry['id']);
				newrow.attr("id", entry['id']);

				newrow.find("td.frequency").text(
					admonitus.transFreqNoToName(entry['frequency'])
					+ new Date(entry['datestart']).format('d-M-Y')
				);

				newrow.show();
				$("#remindersList").append(newrow);
			});

		}

	});
}

$("document").ready(mainObj);
