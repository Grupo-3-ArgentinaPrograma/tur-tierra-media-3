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
			<h1>Estas son las promociones de la Tierra Media</h1>
		</div>

		<c:if test="${user.isAdmin()}">
			<div class="mb-3">
				<a href="/turismo/promos/create.do" class="btn btn-primary"
					role="button"> <i class="bi bi-plus-lg"></i> Nueva Promocion
				</a>
			</div>
		</c:if>
		<table class="table table-stripped table-hover">
			<thead>
				<tr>
					<th>Promocion</th>
					<th>Descripcion</th>
					<th>Costo</th>
					<th>Duracion</th>
					<th>Cupo</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${promotions}" var="promotion">
					<tr>
						<td><strong><c:out value="${promotion.getNombre()}"></c:out></strong>
						<td><c:out value="${promotion.getDescripcion()}. Una promcion de ${promotion.getTipoAtraccionStr()}."></c:out></td>
						<td><c:out value="${promotion.getValor()}"></c:out></td>
						<td><c:out value="${promotion.getTiempo()}"></c:out></td>
						<td><c:out value="${promotion.getCupo(attractions)}"></c:out></td>

						<td><c:if test="${user.isAdmin()}">
								<a href="/turismo/promos/edit.do?id=${promotion.getId()}"
									class="btn btn-primary rounded" role="button"><i
									class="bi bi-pencil-fill"></i>Editar</a>
								<a href="/turismo/promos/delete.do?id=${promotion.getId()}"
									class="btn btn-danger rounded" role="button"><i
									class="bi bi-x-circle-fill"></i>Borrar</a>
							</c:if> 
							
							<c:choose>
								<c:when
									test="${user.puedeComprar(promotion) && promotion.hayCupo(attractions)}">
									<a href="/turismo/promos/buy.do?id=${promotion.getId()}"
										class="btn btn-success rounded" role="button">Comprar</a>
								</c:when>
								<c:otherwise>
									<a href="#" class="btn btn-secondary rounded disabled"
										role="button">No se puede comprar</a>
								</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</main>

</body>
</html>