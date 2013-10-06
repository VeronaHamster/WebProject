<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF8"%>
<%@ taglib uri="/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF8">
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<script src="//code.jquery.com/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>
<title>Welcome</title>

</head>
<body>

	<div class="container" align="center">
		<h2>Android application</h2>
		<span class="label label-success">Enter text into this area</span>
		<FORM ACTION="upload" ENCTYPE="MULTIPART/FORM-DATA" METHOD=post>
			<textarea class="span9" rows="10" name="text"></textarea>
			<br> <INPUT TYPE="FILE" Name="Filename"> <BR> <INPUT
				TYPE=SUBMIT class="btn btn-primary btn-lg"
				name="build the application .apk">
		</FORM>

	</div>

</body>
</html>