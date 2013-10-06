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
<title>Download apk file</title>
<div class="span8" align="center">
	<div class="hero-unit" class="container">
		<h1>Hello!</h1>
		<p>Here you can download the compiled project</p>
		<p>
			Click here to <a href='<c:url value='/downloadfile' />'
				class="btn btn-large btn-primary disabled">download file</a>
		</p>
	</div>
</div>
</head>
<body>

</body>
</html>