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
                
                <div class="userPicture">
                    <a href="#" data-toggle="popover" title="Change picture" data-placement="bottom" data-trigger="click">
                        <img class="userPicture" src="/Admonitus/commons/defaultpicture.png" id="userPicture">
                    </a>
                </div>
                <button type="submit" class="btn btn-primary loggedIn" id="logoutButton">Sign out</button>
                </div>
            </div>
        </div>
    </div>
</nav>