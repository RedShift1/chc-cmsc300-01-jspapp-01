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
	var self = this;
	
	
	this.refreshRemindersList = function()
	{
		$.ajax({
			type : "GET",
			dataType : "json",
			url : "/Admonitus/ctl/Reminder/get",
			success : function(response) {
				
				$("#remindersList > tbody").find("tr:gt(1)").remove();
				
				$.each(response.data, function(index, entry) {
					newrow = $("#template").clone();
					newrow.find("td.number").text(index + 1);
					newrow.find("td.text").text(entry['text']);
					newrow.find("button[name=deleteButton]").attr('data-id', entry['id']);
					newrow.attr("id", entry['id']);
					newrow.find("td.startingat").text(new Date(entry['datestart']).format('d-M-Y'))
					newrow.find("td.frequency").text(
						admonitus.transFreqNoToName(entry['frequency'])
					);

					newrow.show();
					$("#remindersList").append(newrow);
				});

			}

		});
	}
	
	this.switchToLoggedIn = function(response)
	{
		$("#emailAddress").text(response.data.email);
		$(".loggedOut").hide();
		$(".loggedIn").show();
		self.refreshRemindersList();
	}
	
	this.switchToLoggedOut = function()
	{
		$(".loggedOut").show();
		$(".loggedIn").hide();
	}
	
	
	console.log("It works!");
	$("input[name=startingat]").val((new Date()).toDateInputValue());

	
	$(document).on("click", "#registerButton",
		function(e)
		{
			e.preventDefault();
			var request = {
					"email": $("input[id=registerEmail]").val(),
					"password1": $("input[id=registerPassword1]").val(),
					"password2": $("input[id=registerPassword2]").val()
				}
				$.ajax({
					type: "POST",
					dataType: "json",
					url: "/Admonitus/ctl/User/register",
					data: request,
					context: self,
					success: function(response)
					{
						if(response.success)
						{
							$("#registerForm").hide();
							$("#registerCompleted").show();
						}
						else
						{
							$("#jsonErrorText").text(response.error);
							$('#jsonErrorModal').modal('show');
						}
					}
				});
		}
			
	);
	
	
	$(document).on("click", "#logoutButton",
		function(e)
		{
			e.preventDefault();
			$.ajax({
				type: "GET",
				dataType: "json",
				url: "/Admonitus/ctl/User/logout",
				context: self,
				success: function(response)
				{
					if(response.success)
					{
						self.switchToLoggedOut();
					}
					else
					{
						$("#jsonErrorText").text(response.error);
						$('#jsonErrorModal').modal('show');
					}
				}
			});
		}
	);
	
	$(document).on("click", "#loginButton",
		function(e)
		{
			e.preventDefault();
			var request = {
				"email": $("input[id=loginEmail]").val(),
				"password": $("input[id=loginPassword]").val()
			}
			$.ajax({
				type: "POST",
				dataType: "json",
				url: "/Admonitus/ctl/User/login",
				data: request,
				context: self,
				success: function(response)
				{
					if(response.success)
					{
						self.switchToLoggedIn(response);
					}
					else
					{
						$("#jsonErrorText").text(response.error);
						$('#jsonErrorModal').modal('show');
					}
				}
			});
		}
	);
	

	$("#deleteModal").on('show.bs.modal',
		function(e)
		{
	    	$("button[name=deleteConfirm]").attr("data-id", $(e.relatedTarget).data('id'));
		}
	);
	
	$("#jsonEditModal").on('show.bs.modal'),
		function(e)
		{
		
		}

	
	
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
			data : request,
			context: self,
			success: function(response)
			{
				self.refreshRemindersList();
			}
		});
	});

	
	$.ajax({
		type: "GET",
		dataType: "json",
		url: "/Admonitus/ctl/User/isLoggedIn",
		context: self,
		success: function(response)
		{
			if(response.data)
			{
				self.switchToLoggedIn(response);
			}
		}
	});
	
	

}

$("document").ready(mainObj);
