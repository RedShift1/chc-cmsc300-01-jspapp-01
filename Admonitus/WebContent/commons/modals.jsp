
<!-- Friend modal -->
<div id="friendModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Friends</h4>
      </div>
      <div class="modal-body">
      
			<form class="form-inline" role="form">
			  <div class="form-group">
			    <label for="email">Email address:</label>
			    <input type="email" class="form-control" id="friendEmail">
			  </div>
			  <button type="button" class="btn btn-primary" id="addFriend">
			  <span class="glyphicon glyphicon-plus"></span>
			  </button>
			</form>

        <ul id="friendList">
        </ul>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
      </div>
    </div>
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