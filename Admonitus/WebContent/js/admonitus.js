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

var reminderTable = {};
reminderTable.addRow = function (rowNr, data, highlight)
{
	newrow = $("#template").clone();

	// Fill both text and val for input fields
	newrow.find("td.number").text(rowNr + 1);
	newrow.find(".text").text(data['text']);
	newrow.find(".text").val(data['text']);
	newrow.find("button[name=deleteButton]").attr('data-id', data['id']);
	newrow.attr("id", data['id']);
	newrow.find("td.startingat").text(new Date(data['datestart']).format('d-M-Y'))
	newrow.find(".startingat").val(new Date(data['datestart']).toDateInputValue())
	
	newrow.find("span.frequency").text(
		admonitus.transFreqNoToName(data['frequency'])
	);
	newrow.find(".frequency").val(data['frequency']);

	newrow.insertAfter("#template");
	
	newrow.show();
	
	if(highlight)
	{
		newrow.effect("highlight", { color: "AACCE8"}, 500);
		newrow.effect("highlight", { color: "AACCE8"}, 500);
		newrow.effect("highlight", { color: "AACCE8"}, 500);
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
					reminderTable.addRow(index, entry);
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
	
	
	$(document).on("click", "button[name=editButton]",
		function (e)
		{
			e.preventDefault();
			id = $(this).parent().parent().attr("id");
			$("#remindersList > tbody > tr[id=" + id + "]").find(".reminderView").hide();
			$("#remindersList > tbody > tr[id=" + id + "]").find(".reminderEdit").show();
		}
	);
	
	$(document).on("click", "button[name=saveButton]",
		function(e)
		{
			e.preventDefault();
			id = $(this).parent().parent().attr("id");
			var tr = $("#remindersList > tbody > tr[id=" + id + "]");
			tr.find(".reminderView").show();
			tr.find(".reminderEdit").hide();
			
			tr.effect("highlight", { color: "A8D9A8"}, 1000);
		}
	);
	
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
						$("#loginForm")[0].reset();
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
			"frequency": $("#reminderForm select[name=frequency] option:selected").val(),
			"text": $("#reminderForm input[name=text]").val(),
			"startingat": $("#reminderForm input[name=startingat]").val()
		}
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "/Admonitus/ctl/Reminder/add",
			data : request,
			context: self,
			success: function(response)
			{
				if(response.success)
				{
					reminderTable.addRow(0, response.data, true)
					$("#reminderForm")[0].reset();
					$("input[name=startingat]").val((new Date()).toDateInputValue());
				}
				else
				{
					$("#jsonErrorText").text(response.error);
					$('#jsonErrorModal').modal('show');
				}
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
