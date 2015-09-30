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
    
    <div class="viewReminders loggedIn">
        <h2>View</h2>
        <div class="row">
            <div class="col-md-12">
                <form id="reminderForm">
                <table class="table" id="remindersList">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Starting at</th>
                            <th>Frequency</th>
                            <th>Text</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr id="new">
                            <td></td>
                            <td><input type="date" name="startingat"></td>
                            <td>
                                <select name="frequency">
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
                                <button type="submit" class="btn btn-primary">
                                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                                    Create new
                                </button>
                            </td>
                        </tr>
                        <tr id="template" style="display: none;">
                            <td class="number reminderView"></td>
                            <td class="startingat reminderView"></td>
                            <td class="frequency reminderView"></td>
                            <td class="text reminderView"></td>


                            <td class="number reminderEdit"></td>
                            <td class="startingat reminderEdit"></td>
                            <td class="frequency reminderEdit"></td>
                            <td class="reminderEdit"><input type="text" class="form-control text" name="text" size="40"></td>

                            <td>
                                <button type="button" name="deleteButton" class="btn btn-danger" data-toggle="modal" data-target="#deleteModal">
                                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                    Delete
                                </button>
                                <button type="button" name="editButton" class="btn btn-warning reminderView" style="width: 5em;">
                                    <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                                    Edit
                                </button>
                                <button type="button" name="saveButton" class="btn btn-warning reminderEdit" style="width: 5em;">
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








<!-- Delete modal -->
<div id="deleteModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Are you sure?</h4>
      </div>
      <div class="modal-body">
        <p>Are you sure you want to delete the selected reminder?</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-dismiss="modal" name="deleteConfirm">Yes</button>
        <button type="button" class="btn btn-success" data-dismiss="modal">No</button>
      </div>
    </div>
  </div>
</div>

<!-- Error modal -->
<div id="jsonErrorModal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Something went wrong!</h4>
			</div>
			<div class="modal-body">
				<p id="jsonErrorText"></p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-info" data-dismiss="modal">Ok!</button>
			</div>
		</div>
	</div>
</div>
<!-- Edit modal -->
<div id="jsonEditModal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Edit reminder</h4>
			</div>
			<div class="modal-body">
			<h5>Frequency</h5>
				<label class="radio-inline"><input type="radio" name="frequency" value="0">One-time</label>
                <label class="radio-inline"><input type="radio" name="frequency" value="1">Daily</label>
                <label class="radio-inline"><input type="radio" name="frequency" value="2" checked="checked">Weekly</label>
                <label class="radio-inline"><input type="radio" name="frequency" value="3">Monthly</label>
                <hr>
            <h5>Starting date</h5>
            	<input type="date" name="startingat">
            	<hr>
            <h5>Text</h5>
            	<input type="text" class="form-control" name="text" placeholder="Reminder text field" width="50" >
            	
            </div>
			<div class ="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-success" data-dismiss="modal">Confirm</button>
			</div>
		</div>	
	</div>
</div>


<%@ include file="commons/footer.jsp"%>
