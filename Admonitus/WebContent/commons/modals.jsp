
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

        Want to help a friend be reminded of something? Add his or her email
        address here!

        <form class="form-inline" role="form">
        <table id="friendsTable" class="table">
            <thead>
                <tr>
	                <th>Email address</th>
	                <th>Actions</th>
                </tr>
            </thead>
            
            
            <tbody>
                <tr id="newFriend">
                    <td>
		                <input type="email" class="form-control" id="friendEmail">
                    </td>
                    <td>
                        <button type="button" class="btn btn-primary" id="addFriend">
                            <span class="glyphicon glyphicon-plus"></span>
                        </button>
                    </td>
                 </tr>
                 <tr id="friendTemplate" style="display: none;">
                    <td><span class="email"></span></td>
                    <td>
                         <button type="button" class="btn btn-danger" name="deleteFriendButton">
                            <span class="glyphicon glyphicon-trash"></span>
                        </button>
                    </td>
                 </tr>
            </tbody>
        </table>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn" data-dismiss="modal">Close</button>
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