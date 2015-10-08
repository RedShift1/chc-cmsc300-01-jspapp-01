<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
    	<title>${param.title}</title>
        <link rel="stylesheet" href="/Admonitus/bower_components/bootstrap/dist/css/bootstrap.css">
        <link rel="stylesheet" href="/Admonitus/bower_components/jquery-ui/themes/base/jquery-ui.css">
        <script src="/Admonitus/bower_components/jquery/dist/jquery.js"></script>
        <script src="/Admonitus/bower_components/jquery-ui/jquery-ui.js"></script>
        <script src="/Admonitus/bower_components/bootstrap/dist/js/bootstrap.js"></script>
        <script src="/Admonitus/bower_components/fileuploader/fileuploader.js"></script> 
    	<script src="/Admonitus/js/date.format.js"></script>
    	<script src="/Admonitus/js/reminderTable.js"></script>
    	<script src="/Admonitus/js/friendsTable.js"></script>
    	<script src="/Admonitus/js/admonitus.js"></script>
    	<script src="/Admonitus/js/mainObj.js"></script>
    	<style>
    		body
            {
                padding-top: 60px;
            }
            .table > tbody > tr > td
            {
                vertical-align: middle;
            }
            .loggedIn
            {
                /* Start not logged in by default */
                display: none;
            }
            .logoutOut
            {
            }
            .reminderEdit
            {
                display: none;
            }
            img.userPicture
            {
                width: 50px;
                height: 50px;
                
            }
            div.userPicture
            {
                width: 52px;
                height: 52px;
                border: 1px solid gray;
                display: inline-block;
            }
            .popover
            {
                max-width: 100%;
            }
    	</style>
    </head>
    <body>
