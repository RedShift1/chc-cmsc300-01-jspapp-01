<%@ include file="commons/header.jsp"%>

<nav class="navbar navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Admonitus</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li class="active"><a href="#">Reminders</a></li>
			</ul>
			
			<form class="navbar-form navbar-right loggedOut" id="loginForm">
				<div class="form-group">
					<input type="text" placeholder="Email" class="form-control" id="loginEmail">
				</div>
				<div class="form-group">
					<input type="password" placeholder="Password" class="form-control" id="loginPassword">
				</div>
				<button type="submit" class="btn btn-success" id="loginButton">Sign in</button>
				
			</form>
			<div class="navbar-form navbar-right loggedIn">
                <div class="form-group">
                Logged in as <b><span id="emailAddress"></span></b>
                <button type="submit" class="btn btn-primary loggedIn" id="logoutButton">Sign out</button>
                </div>
			</div>
		</div>
	</div>
</nav>

<div class="container">

    <h1>Reminders</h1>
    
    <!-- Someday, maybe <ul class="nav nav-pills" role="tablist">
        <li role="presentation" class="active"><a href="#">For me</a></li>
        <li role="presentation"><a href="#">For my friends</a></li>
    </ul> -->
    
    <div class="viewReminders loggedIn">
        <h2>View</h2>
        <div class="row">
            <div class="col-md-12">
                <form id="reminderForm">
                <table class="table table-hover" id="remindersList">
                    <thead>
                        <tr>
                            <th>Starting at</th>
                            <th>Frequency</th>
                            <th>Text</th>
                            <th>Friends</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr id="new">
                            <td><input type="date" name="startingat" class="form-control"></td>
                            <td>
                                <select name="frequency" class="form-control">
                                    <option value="0">One-time</option>
                                    <option value="1">Daily</option>
                                    <option value="2" selected="selected">Weekly</option>
                                    <option value="3">Monthly</option>
                                </select>
                            </td>
                            <td>
                                <input type="text" class="form-control" name="text" placeholder="Take out the trash" size="40">
                            </td>
                            <td>
                            </td>
                            <td>
                                <button class="btn btn-primary" name="addReminder">
                                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                    Add
                                </button>
                            </td>
                        </tr>
                        <tr id="template" style="display: none;">
                            <td class="reminderView"><span class="startingat"></span></td>
                            <td class="reminderView"><span class="frequency"></span></td>
                            <td class="reminderView"><span class="text"></span></td>

                            <td class="reminderEdit">
                                <input type="date" name="startingat" class="startingat form-control">
                            </td>
                            <td class="reminderEdit">
                                <select name="frequency" class="frequency form-control">
                                    <option value="0">One-time</option>
                                    <option value="1">Daily</option>
                                    <option value="2">Weekly</option>
                                    <option value="3">Monthly</option>
                                </select>
                            </td>
                            <td class="reminderEdit"><input type="text" class="form-control text" name="text" size="40"></td>


                            <td>
                                <button class="btn btn-sm" name="friendButton" data-toggle="modal" data-target="#friendModal"><span class="glyphicon glyphicon-user"></span> Friends</button>
                            </td>
                            <td>
                                <button type="button" name="deleteButton" class="btn btn-danger" data-toggle="modal" data-target="#deleteModal">
                                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                    Delete
                                </button>
                                <button type="button" name="editButton" class="btn btn-warning reminderView" style="width: 5em;">
                                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                    Edit
                                </button>
                                <button type="button" name="saveButton" class="btn btn-success reminderEdit" style="width: 5em;">
                                    <span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
                                    Save
                                </button>
                            </td>

                        </tr>
                    </tbody>
                </table>
                </form>
            </div>
        </div>
    </div>
    
    <div class="loggedOut" id="registerForm">
        <h2>Register</h2>
        <form role="form">
            <div class="form-group">
                <label for="email">Email address:</label>
                <input type="email" class="form-control" id="registerEmail">
            </div>
            <div class="form-group">
                <label for="pwd">Password:</label>
                <input type="password" class="form-control" id="registerPassword1">
            </div>
            <div class="form-group">
                <label for="pwd">Repeat password:</label>
                <input type="password" class="form-control" id="registerPassword2">
            </div>
            <button type="submit" class="btn btn-default" id="registerButton">Register!</button>
        </form>

    </div>
	<div class="alert alert-success" role="alert" id="registerCompleted" style="display: none;">
	    <strong>Awesomeness</strong> You're now registered!
	</div>
</div>







<%@ include file="commons/modals.jsp" %>

<%@ include file="commons/footer.jsp"%>
