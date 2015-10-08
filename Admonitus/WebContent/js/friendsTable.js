var friendsTable = {};
friendsTable.addRow = function(data, highlight, reminderId)
{
	newrow = $("#friendTemplate").clone();
	
	newrow.attr("id", "");
	newrow.attr("data-id", data['id']);
	
	newrow.find("span.email").text(data['email']);
	newrow.find("button[name=deleteFriendButton]").attr('data-id', data['id']);
	newrow.find("button[name=deleteFriendButton]").attr('data-reminder-id', reminderId);
	
	newrow.show();
	newrow.insertAfter("#friendTemplate");
	if(highlight)
	{
		newrow.effect("highlight", { color: "#AACCE8"}, 1500);
	}
}