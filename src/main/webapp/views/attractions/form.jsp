<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="mb-3">
	<label for="name" class="col-form-label">Nombre:</label> <input
		type="text" class="form-control" id="name" name="name"
		required value="${attraction.getNombre()}">
</div>
<div class="mb-3">
	<label for="description" class="col-form-label">Descripcion:</label> <input
		type="text" class="form-control" id="description" name="description"
		required value="${attraction.getDescripcion()}">
</div>
<div class="mb-3">
	<label for="type" class="col-form-label">Tipo Atraccion:</label> <input
		type="text" class="form-control" id="type" name="type"
		required value="${attraction.getTipoAtraccionStr()}">
</div>
<div class="mb-3">
	<label for="cost"
		class='col-form-label ${attraction.errors.get("cost") != null ? "is-invalid" : "" }'>Costo:</label>
	<input class="form-control" type="number" id="cost" name="cost"
		required value="${attraction.getValor()}"></input>
	<div class="invalid-feedback">
		<c:out value='${attraction.errors.get("cost")}'></c:out>
	</div>
</div>
<div class="mb-3">
	<label for="duration"
		class='col-form-label ${attraction.errors.get("duration") != null ? "is-invalid" : "" }'>Duracion:</label>
	<input class="form-control" type="number" id="duration" name="duration"
		required value="${attraction.getTiempo()}"></input>
	<div class="invalid-feedback">
		<c:out value='${attraction.errors.get("duration")}'></c:out>
	</div>
</div>
<div class="mb-3">
	<label for="capacity"
		class='col-form-label ${attraction.errors.get("capacity") != null ? "is-invalid" : "" }'>Capacidad:</label>
	<input class="form-control" type="number" id="capacity" name="capacity"
		required value="${attraction.getCupo()}"></input>
	<div class="invalid-feedback">
		<c:out value='${attraction.errors.get("capacity")}'></c:out>
	</div>
</div>

<div>
	<button type="submit" class="btn btn-primary">Guardar</button>
	<a onclick="window.history.back();" class="btn btn-secondary"
		role="button">Cancelar</a>
</div>