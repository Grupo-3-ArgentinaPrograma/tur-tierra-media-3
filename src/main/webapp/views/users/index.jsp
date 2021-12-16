<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/partials/head.jsp"></jsp:include>
</head>
<body>

	<jsp:include page="/partials/nav.jsp"></jsp:include>

	<main class="container">

		<c:if test="${flash != null}">
			<div class="alert alert-danger">
				<p>
					<c:out value="${flash}" />
					<c:if test="${errors != null}">
						<ul>
							<c:forEach items="${errors}" var="entry">
								<li><c:out value="${entry.getValue()}"></c:out></li>
							</c:forEach>
						</ul>
					</c:if>
				</p>
			</div>
		</c:if>

		<div class="bg-light p-4 mb-3 rounded">
			<h1>Usuarios</h1>
		</div>

		<c:if test="${user.isAdmin()}">
			<div class="mb-3">
				<a href="/turismo/users/create.do" class="btn btn-primary"
					role="button"> <i class="bi bi-plus-lg"></i> Nuevo Usuario
				</a>
			</div>
		</c:if>
		<table class="table table-stripped table-hover">
			<thead>
				<tr>
					<th>Nombre</th>
					<th>Monedas</th>
					<th>Tiempo</th>
					<th>Rol</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${users}" var="tmp_user">
					<tr>
						<td><strong><c:out value="${tmp_user.getNombre()}"></c:out></strong></td>
						<td><c:out value="${tmp_user.getMonedas()}"></c:out></td>
						<td><c:out value="${tmp_user.getTiempoDisponible()}"></c:out></td>
						<td>
							<c:choose>
								<c:when test="${tmp_user.isAdmin()}">
									Admin
								</c:when>
								<c:otherwise>
									Normal
								</c:otherwise>
							</c:choose>						
						</td>
						<td><c:if test="${user.isAdmin() && (!tmp_user.isAdmin() || tmp_user.getId() == user.getId())}">
								<a href="/turismo/users/edit.do?id=${tmp_user.getId()}"
									class="btn btn-primary rounded-0" role="button"><i
									class="bi bi-pencil-fill"></i>Editar</a>
								<a href="/turismo/users/delete.do?id=${tmp_user.getId()}"
									class="btn btn-danger rounded" role="button"><i
									class="bi bi-x-circle-fill"></i>Borrar</a>
							</c:if></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</main>

</body>
</html>