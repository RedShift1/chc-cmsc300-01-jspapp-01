Date.prototype.toDateInputValue = (function() {
    var local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0,10);
});


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

				switch (entry['frequency']) {
				case 0:
					newrow.find("td.frequency").text(
							"One-time	"
									+ new Date(entry['datestart'])
											.format('d:M:Y'));
					break;
				case 1:
					newrow.find("td.frequency").text(
							"Daily		"
									+ new Date(entry['datestart'])
											.format('d:M:Y'));
					break;
				case 2:
					newrow.find("td.frequency").text(
							"Weekly	"
									+ new Date(entry['datestart'])
											.format('d:M:Y'));
					break;
				case 3:
					newrow.find("td.frequency").text(
							"Monthly	"
									+ new Date(entry['datestart'])
											.format('d:M:Y'));
					break;
				default:
					newrow.find("td.frequency").text(
							"Invalid	"
									+ new Date(entry['datestart'])
											.format('d:M:Y'));

				}

				newrow.show();
				$("#remindersList").append(newrow);
			});

		}

	});
}

$("document").ready(mainObj);
