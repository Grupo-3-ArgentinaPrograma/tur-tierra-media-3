<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav class="navbar navbar-expand-md navbar-dark bg-dark mb-4">
	<div class="container">
		<a class="navbar-brand" href="/turismo/index.jsp">Turismo en la
			Tierra Media</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarCollapse" aria-controls="navbarCollapse"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarCollapse">
			<ul class="navbar-nav me-auto mb-2 mb-md-0">
				<li class="nav-item"><a class="nav-link active"
					aria-current="page" href="/turismo/attractions/index.do">Atracciones</a></li>
					<li class="nav-item"><a class="nav-link active"
					aria-current="page" href="/turismo/promos/index.do">Promociones</a></li>
					<li class="nav-item"><a class="nav-link active"
					aria-current="page" href="/turismo/itinerarios/index.do">Itinerarios</a></li>
				<c:if test="${user.isAdmin()}">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="/turismo/users/index.do">Usuarios</a></li>
				</c:if>
			</ul>
			<ul class="navbar-nav">
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-expanded="false">
						<c:out value="${user.getNombre()}"></c:out>
					</a>
					<div class="dropdown-menu"
						aria-labelledby="navbarDropdown">
						<a class="dropdown-item" style="color: black;">
							<i title="monedas" style="color: gold;" class="bi bi-coin"></i> <c:out value="${user.getMonedas()}"></c:out>
						</a>
						<a class="dropdown-item" style="color: black;">
							<i title="tiempo" style="color: blue;" class="bi bi-clock-fill"></i> <c:out value="${user.getTiempoDisponible()}hs"></c:out>
						</a>
						<hr class="dropdown-divider">
						<a href="/turismo/logout" class="dropdown-item">Salir</a>
					</div>
				</li>
			</ul>
		</div>
	</div>
</nav>

<c:if test="${success != null}">
	<div class="alert alert-success">
		<p>
			<c:out value="${success}" />
		</p>
	</div>
</c:if>