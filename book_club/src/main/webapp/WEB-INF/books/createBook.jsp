<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. -->
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!-- Formatting (dates) -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Create a Book</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="/css/main.css" />
    <!-- change to match your file/naming structure -->
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/app.js"></script>
    <!-- change to match your file/naming structure -->
  </head>
  <body>
    <div class="container">
    <h1>Add a Book to your shelf</h1>
    <a href="/logout">Logout</a>
    <a href="/books">Back to shelfs</a>
    <form:form action="/books/create" method="post" modelAttribute="book">
      <form:hidden path="user" value="${user.id}"/>
      <p>
        <form:label path="title">Title</form:label>
        <form:errors path="title" />
        <form:input path="title" class="form-control" />
      </p>
      <p>
        <form:label path="author">Author</form:label>
        <form:errors path="author" />
        <form:input path="author" class="form-control" />
      </p>
      <p>
        <form:label path="thoughts">My thoughts</form:label>
        <form:errors path="thoughts" />
        <form:input path="thoughts" class="form-control" />
      </p>
        <input type="submit" value="submit" class="btn btn-secondary"/>
    </form:form>
    </div>
  </body>
</html>