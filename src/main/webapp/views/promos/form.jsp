<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="mb-3">
	<label for="name" class="col-form-label">Nombre:</label> <input
		type="text" class="form-control" id="name" name="name"
		required value="${promotion.getNombre()}">
</div>
<div class="mb-3">
	<label for="description" class="col-form-label">Descripcion:</label> <input
		type="text" class="form-control" id="description" name="description"
		required value="${promotion.getDescripcion()}">
</div>
<div class="mb-3">
	<label for="promo_type" class="col-form-label">Tipo Promocion:</label> <input
		type="text" class="form-control" id="promo_type" name="promo_type"
		required value="${promotion.getTipoPromo()}">
</div>
<div class="mb-3">
	<label for="attraction_type" class="col-form-label">Tipo Atraccion:</label> <input
		type="text" class="form-control" id="attraction_type" name="attraction_type"
		required value="${promotion.getTipoAtraccionStr()}">
</div>
<div class="mb-3">
	<label for="discount"
		class='col-form-label ${attraction.errors.get("cost") != null ? "is-invalid" : "" }'>Descuento (porcentaje/valor/id atraccion):</label>
	<input class="form-control" type="number" id="discount" name="discount"
		required value="${promotion.getValor()}"></input>
	<div class="invalid-feedback">
		<c:out value='${attraction.errors.get("cost")}'></c:out>
	</div>
</div>

<<div class="mb-3">
	<label for="id_attractions" class="col-form-label">ID Atracciones (separados por guion "-"):</label> <input
		type="text" class="form-control" id="id_attractions" name="id_attractions"
		required value="${promotion.getId_atraccionesStr()}">
</div>

<div>
	<button type="submit" class="btn btn-primary">Guardar</button>
	<a onclick="window.history.back();" class="btn btn-secondary"
		role="button">Cancelar</a>
</div>