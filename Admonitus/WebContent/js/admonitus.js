        

var mainObj = function()
{
	console.log("It works!");
	
    $.ajax({
        type: "GET",
        dataType: "json",
        url: "/Admonitus/ctl/Reminder/get",
        success: function(data) {

            $.each(data,
        		function(index, entry)
        		{
            		newrow = $("#template").clone();
            		newrow.find("td.number").text(index + 1);
            		newrow.find("td.text").text(entry['text']);
            		newrow.attr("id", entry['id']);
            		
            		switch(entry['frequency']) {
            		case 0:
            			newrow.find("td.frequency").text("One-time	" + new Date(entry['datestart']).format('d:M:Y'));
            			break;
            		case 1:
            			newrow.find("td.frequency").text("Daily		" + new Date(entry['datestart']).format('d:M:Y'));
            			break;
            		case 2:
            			newrow.find("td.frequency").text("Weekly	" + new Date(entry['datestart']).format('d:M:Y'));
            			break;
            		case 3:
            			newrow.find("td.frequency").text("Monthly	" + new Date(entry['datestart']).format('d:M:Y'));
            			break;
            		default:
            			newrow.find("td.frequency").text("Invalid	" + new Date(entry['datestart']).format('d:M:Y'));
            		
            		}
            		
            		
            		newrow.show();
            		$("#remindersList").append(newrow);
        		}
            );
        	
        }
    
    });
}

$("document").ready(mainObj);
