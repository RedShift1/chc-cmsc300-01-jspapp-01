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
			
			<form class="navbar-form navbar-right loggedOut">
				<div class="form-group">
					<input type="text" placeholder="Email" class="form-control" id="loginEmail">
				</div>
				<div class="form-group">
					<input type="password" placeholder="Password" class="form-control" id="loginPassword">
				</div>
				<button type="submit" class="btn btn-success" id="loginButton">Sign in</button>
				<button type="submit" class="btn btn-danger loggedIn" id="logoutButton">Sign out</button>
			</form>
			<div class="navbar-right loggedIn">
                Logged in as <span id="emailAddress"></span>
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
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr id="new">
                            <td></td>
                            <td>
                                <label class="radio-inline"><input type="radio" name="frequency" value="0">One-time</label>
                                <label class="radio-inline"><input type="radio" name="frequency" value="1">Daily</label>
                                <label class="radio-inline"><input type="radio" name="frequency" value="2" checked="checked">Weekly</label>
                                <label class="radio-inline"><input type="radio" name="frequency" value="3">Monthly</label>
                                <br />
                                <i>Starting at <input type="date" name="startingat"></i></td>
                                <td>
                            <td>
                                <input type="text" class="form-control" name="text" placeholder="Take out the trash">
                            </td>
                            <td>
                                <button type="submit" class="btn btn-default">Create new</button>
                            </td>
                        </tr>
                        <tr id="template" style="display: none;" id="">
                            <td class="number"></td>
                            <td class="startingat"></td>
                            <td class="frequency"></td>
                            <td class="text"></td>
                            <td>
                                <button type="button" name="deleteButton" class="btn btn-danger" data-toggle="modal" data-target="#deleteModal">Delete</button>
                                <button type="button" name="editButton" class="btn btn-primary" data-toggle="modal" data-target="#jsonEditModal">Edit</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
                </form>
            </div>
        </div>
    </div>
    
    <div class="loggedOut">
        <h2>Register</h2>
        <form role="form">
            <div class="form-group">
                <label for="email">Email address:</label> <input type="email"
                    class="form-control" id="email">
            </div>
            <div class="form-group">
                <label for="pwd">Password:</label> <input type="password"
                    class="form-control" id="pwd">
            </div>
            <div class="form-group">
                <label for="pwd">Repeat password:</label> <input type="password"
                    class="form-control" id="pwd">
            </div>
            <button type="submit" class="btn btn-default">Register!</button>
        </form>
    </div>

</div>



        <!-- 
    <div class="alert alert-success" role="alert">
        <strong>Awesomeness</strong> Reminder created successfully!
    </div>
-->



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
