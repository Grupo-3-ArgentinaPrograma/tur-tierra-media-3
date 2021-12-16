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
			<h1>Itinerarios</h1>
		</div>
		<c:choose>
			<c:when test="${!user.comprasIsEmpty()}">
				<table class="table table-stripped table-hover">
					<thead>
						<tr>
							<th>Atraccion/Promo</th>
							<th>Descripcion</th>
							<th>Costo</th>
							<th>Tiempo</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${user.getCompras()}" var="compra">
							<tr>
								<td><strong><c:out value="${compra.getNombre()}"></c:out></strong></td>
								<td><c:out
										value="${compra.getDescripcion()}. Una oferta de ${compra.getTipoAtraccionStr()}"></c:out></td>
								<td><c:out value="${compra.getValor()}"></c:out></td>
								<td><c:out value="${compra.getTiempo()}"></c:out></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<div class="alert alert-danger">
					<p>
						<b>No tienes atracciones ni promos compradas!</b>
					</p>
				</div>
			</c:otherwise>
		</c:choose>
		<c:if test="${user.isAdmin()}">
			<table class="table table-stripped table-hover">
				<thead>
					<tr>
						<th>Usuario</th>
						<th>Atraccion/Promo</th>
						<th>Descripcion</th>
						<th>Costo</th>
						<th>Tiempo</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${users}" var="temp_user">
						<c:if test="${temp_user!=user}">
							<c:forEach items="${temp_user.getCompras()}"
								var="temp_user_compra">
								<tr>
									<td><strong><c:out
												value="${tmp_user.getNombre()}"></c:out></strong></td>
									<td><c:out value="${temp_user_compra.getNombre()}"></c:out></td>
									<td><c:out
											value="${temp_user_compra.getDescripcion()}. Una oferta de ${compra.getTipoAtraccionStr()}"></c:out></td>
									<td><c:out value="${temp_user_compra.getValor()}"></c:out></td>
									<td><c:out value="${temp_user_compra.getTiempo()}"></c:out></td>

								</tr>
							</c:forEach>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</c:if>

	</main>

</body>
</html>
ml>
