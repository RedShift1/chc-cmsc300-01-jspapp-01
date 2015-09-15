<%@ include file="commons/header.jsp"%>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed"
                data-toggle="collapse" data-target="#navbar" aria-expanded="false"
                aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Admonitus</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Reminders</a></li>
            </ul>
        </div>
        <!--/.nav-collapse -->
    </div>
</nav>

<div class="container">

    <h1>Reminders</h1>

    <div class="viewReminders">

        <div class="row">
            <div class="col-md-12">
                <table class="table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Frequency</th>
                            <th>Text</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr id="new">
                            <td></td>
                            <td><label class="radio-inline"> <input type="radio"
                                    name="optradio">One-time
                            </label> <label class="radio-inline"><input type="radio"
                                    name="optradio">Daily</label> <label class="radio-inline"><input
                                    type="radio" name="optradio">Weekly</label> <label
                                class="radio-inline"><input type="radio" name="optradio">Monthly</label>
                                <br /> <i>Starting at <input type="date" name="startingat"></i></td>
                            <td><input type="text" class="form-control" id="text"
                                placeholder="Take out the trash"></td>
                            <td>
                                <button type="submit" class="btn btn-default">Create
                                    new</button>
                            </td>
                        </tr>
                        <tr id="template">
                            <td>1</td>
                            <td>Weekly<br /> <i>Start from 10/09/2015</i>
                            </td>
                            <td>Take out the trash</td>
                            <td>
                                <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModal">Delete</button>
                                <button type="button" class="btn btn-primary">Edit</button>
                            </td>
                        </tr>
                    </tbody>
                </table>

            </div>
        </div>
        
    <div class="alert alert-success" role="alert">
        <strong>Awesomeness</strong> Reminder created successfully!
    </div>

        
        
        
        <div id="registerForm">
        <h1>Register</h1>
		<form class="form-horizontal" role="form">
		  <div class="form-group">
		    <label class="control-label col-sm-2" for="email">Email:</label>
		    <div class="col-sm-10">
		      <input type="email" class="form-control" id="email" placeholder="Enter email">
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="control-label col-sm-2" for="pwd">Password:</label>
		    <div class="col-sm-10"> 
		      <input type="password" class="form-control" id="pwd" placeholder="Enter password">
		    </div>
		  </div>
		  <div class="form-group"> 
		    <div class="col-sm-offset-2 col-sm-10">
		      <button type="submit" class="btn btn-default">Submit</button>
		    </div>
		  </div>
		</form>
        </div>

    </div>


</div>

<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
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
        <button type="button" class="btn btn-danger" data-dismiss="modal">Yes</button>
        <button type="button" class="btn btn-success" data-dismiss="modal">No</button>
      </div>
    </div>

  </div>
</div>




<%@ include file="commons/footer.jsp"%>
