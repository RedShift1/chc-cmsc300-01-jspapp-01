var reminderTable = {};
reminderTable.view = "all";
reminderTable.addRow = function (rowNr, data1, data2, highlight)
{
	newrow = $("#template").clone();

	this.updateRow(newrow, rowNr, data1, data2);
	
	newrow.insertAfter("#template");
	
	newrow.show();
	
	if(highlight)
	{
		newrow.effect("highlight", { color: "#AACCE8"}, 1500);
	}
}

reminderTable.updateRow = function (tr, id, data1, data2)
{
	tr.find("td.number").text(id + 1);
	tr.find("span.text").text(data1['text']);
	tr.find("input.text").val(data1['text']);
	tr.find("button[name=deleteButton]").attr('data-id', data1['id']);
	tr.attr("id", data1['id']);
	tr.find("span.startingat").text(new Date(data1['datestart']).format('d-M-Y'))
	tr.find(".startingat").val(new Date(data1['datestart']).toDateInputValue())
	
	tr.find("span.frequency").text(
		admonitus.transFreqNoToName(data1['frequency'])
	);
	tr.find(".frequency").val(data1['frequency']);
	tr.find(".numFriends").text(data2);
}

reminderTable.showFriendsOnly = function()
{
	$.each($("#remindersList > tbody").find("tr:gt(1)"),
		function(index, entry)
		{
			if($(entry).find(".numFriends").text() > 0)
			{
				$(entry).show();
				
			}
			else
			{
				$(entry).hide();
			}
		}
	);
}

reminderTable.showNoFriendsOnly = function()
{
	$.each($("#remindersList > tbody").find("tr:gt(1)"),
		function(index, entry)
		{
			if($(entry).find(".numFriends").text() == 0)
			{
				$(entry).show();
			}
			else
			{
				$(entry).hide();
			}
		}
	);
}

reminderTable.showAll = function()
{
	$("#remindersList > tbody").find("tr:gt(1)").show();
}

reminderTable.switchView = function(view)
{
	$("[id^=filter]").removeClass("btn-primary");
	switch(view)
	{
		case "all":
			this.view = "all";
			this.showAll();
			$("#filterAll").addClass("btn-primary");
			break;
		case "me":
			this.view = "me";
			this.showNoFriendsOnly();
			$("#filterMe").addClass("btn-primary");
			break;
		case "friends":
			this.view = "friends";
			this.showFriendsOnly();
			$("#filterFriends").addClass("btn-primary");
			break;
	}
}

reminderTable.refreshView = function()
{
	this.switchView(this.view);
}