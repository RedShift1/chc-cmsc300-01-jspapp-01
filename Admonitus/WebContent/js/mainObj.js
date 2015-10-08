
var mainObj = function() {
    var self = this;    
    
    $(document).on("click", "#filterAll", function()
        {
            reminderTable.switchView("all");
        }
    );
    
    $(document).on("click", "#filterFriends", function()
        {
            reminderTable.switchView("friends");
        }
    );
    
    $(document).on("click", "#filterMe", function()
        {
            reminderTable.switchView("me");
        }
    );
    
    this.refreshRemindersList = function()
    {
        $.ajax({
            type : "GET",
            dataType : "json",
            url : "/Admonitus/ctl/Reminder/get",
            success : function(response) {
                
                $("#remindersList > tbody").find("tr:gt(1)").remove();
                
                $.each(response.data, function(index, entry) {
                    reminderTable.addRow(index, entry[0], entry[1]);
                });
                
                reminderTable.showAll();
            }

        });
    }
    
    this.switchToLoggedIn = function(response)
    {
        $("#userPicture").attr("src", "/Admonitus/ctl/User/getPicture?timestamp=" + new Date().getTime());
        $("#emailAddress").text(response.data.email);
        $(".loggedOut").hide();
        $(".loggedIn").show();
        self.refreshRemindersList();
        $('#reminderForm input[name=text]').focus();
    }
    
    this.switchToLoggedOut = function()
    {
        $(".loggedOut").show();
        $(".loggedIn").hide();
    }
    
    this.refreshSingleReminderRow = function(id)
    {
        $.ajax({
            type: "GET",
            dataType: "json",
            url: "/Admonitus/ctl/Reminder/getSingle/" + id,
            context: self,
            success: function(response)
            {
                if(response.success)
                {
                    reminderTable.updateRow($("#" + id), id, response.data[0], response.data[1]);
                }
                else
                {
                    $("#jsonErrorText").text(response.error);
                    $('#jsonErrorModal').modal('show');
                }
            }
        });
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
            
            var request = {
                "frequency": tr.find("select[name=frequency] option:selected").val(),
                "text": tr.find("input[name=text]").val(),
                "startingat": tr.find("input[name=startingat]").val()
            }
            
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "/Admonitus/ctl/Reminder/edit/" + id,
                data: request,
                context: self,
                success: function(response)
                {
                    if(response.success)
                    {
                        reminderTable.updateRow($("#" + id), id, response.data)
                        tr.find(".reminderView").show();
                        tr.find(".reminderEdit").hide();
                        tr.effect("highlight", { color: "#A8D9A8"}, 1000);
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
    
    
    
    $("#friendModal").on('show.bs.modal',
        function(e)
        {
            var id = $(e.relatedTarget).parent().parent().attr('id');
            $("#friendModal button[id=addFriend]").attr('data-id', id);
            
            $("#friendsTable > tbody").find("tr:gt(1)").remove();
            
            $.ajax(
                {
                    type: "GET",
                    dataType: "json",
                    url: "/Admonitus/ctl/Reminder/getFriends/" + id,
                    success: function(response)
                    {
                        if(response.success)
                        {
                            $.each(response.data, function(index, entry) {
                                friendsTable.addRow(entry, false, id);
                            });
                        }
                    }
                }
            );
        }
    );
    
    $('#friendModal').on('shown.bs.modal',
        function ()
        {
            $('#friendEmail').focus();
        }
    );
    
    $("#friendModal").on('hide.bs.modal',
        function()
        {
            reminderTable.refreshView();
        }
    );
    
    $(document).on('click', "#friendModal button[id=addFriend]",
        function(e)
        {
            e.preventDefault();
            
            var id = $(this).attr("data-id");
            
            var request = 
            {
                email: $("#friendModal input[id=friendEmail]").val()
            }
            
            $.ajax(
                {
                    type: "POST",
                    dataType: "json",
                    data: request,
                    url: "/Admonitus/ctl/Reminder/addFriend/" + id,
                    context: self,
                    success: function(response)
                    {
                        if(response.success)
                        {
                            $("#friendModal form")[0].reset();
                            friendsTable.addRow(response.data, true, id);
                            this.refreshSingleReminderRow(id);
                        }
                        else
                        {
                            $("#jsonErrorText").text(response.error);
                            $('#jsonErrorModal').modal('show');
                        }
                    }
                }
            )
        }
    );
    
    
    
    $(document).on('click', "button[name=deleteConfirm]",
        function(e)
        {
            var id = $(this).attr("data-id");

            $.ajax({
                type: "GET",
                dataType: "json",
                url: "/Admonitus/ctl/Reminder/delete/" + id,
                success:
                    function(response)
                    {
                        if(response.success)
                        {
                            $("#remindersList").find("tr[id=" + id + "]").remove();
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
    
    $(document).on('click', "button[name='friendButton']",
        function (e)
        {
            e.preventDefault();
        }
    );
    
    
    $(document).on('click', "button[name=deleteFriendButton]",
        function(e)
        {
            var id = $(this).attr("data-id");
            var reminderId = $(this).attr("data-reminder-id");
            
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "/Admonitus/ctl/Reminder/deleteFriend/" + id,
                context: self,
                success: function(response)
                {
                    if(response.success)
                    {
                        $("#friendsTable tr[data-id=" + id + "]").remove();
                        this.refreshSingleReminderRow(reminderId);
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
    
    
    
    $(document).on('click', "button[name='addReminder']", function(e) {
        e.preventDefault();

        var request = {
            "frequency": $("#new select[name=frequency] option:selected").val(),
            "text": $("#new input[name=text]").val(),
            "startingat": $("#new input[name=startingat]").val()
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
                    reminderTable.addRow(0, response.data, 0, true)
                    $("#new input[name=text]").val("");
                    $("#new select[name=frequency]").val(2);
                    $("#new input[name=startingat]").val((new Date()).toDateInputValue());
                }
                else
                {
                    $("#jsonErrorText").text(response.error);
                    $('#jsonErrorModal').modal('show');
                }
            }
        });
    });

    
    
    
    /* Initialization */
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

    $('#loginEmail').focus();

    $("[data-toggle=popover]").popover({
        html: true,
        container: 'body',
        content: function()
            {
                var html = $('#picturePopoverContent').clone();
                var jq = html
                jq.find("input[type=file]").addClass("user-file");
                return jq.html();
            }
    });

    
    $(document).on("click", "#uploadPictureButton",
        function(e)
        {
            e.preventDefault();
            var upl = fileuploader({
                selector: '.user-file',
                file_input_name: 'file',
                url: '/Admonitus/ctl/User/setPicture',
                headers: {
                    'myCsrfToken': '!334234#2344$234234@@234'
                },
                data: {
                    'filename': 'myfile',
                    'type': 'other'
                },
                onsubmit: function(xhr){
                    console.log("Starting upload for: " + xhr.fu.file.name);
                    console.log("Upload uuid: " + xhr.fu.uuid);
                },
                oncomplete: function(data, status, xhr){
                    console.log('complete');
                    console.log(data);
                    $("#userPicture").attr("src", "/Admonitus/ctl/User/getPicture?timestamp=" + new Date().getTime());                  
                },
                onerror: function(data, status, xhr){
                    console.log(data);
                }
            });
        }
    );
    
    
    
}

$("document").ready(mainObj);
