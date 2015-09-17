        
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
            		newrow.show();
            		$("#remindersList").append(newrow);
        		}
            );
        	
        }
    
    });
}

$("document").ready(mainObj);
