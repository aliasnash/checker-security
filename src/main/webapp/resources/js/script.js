$(window).on('load', function() {

	$('select').selectpicker();
	$('#date').datepicker({ format : "yyyy-mm-dd", language : "ru", weekStart : 1, autoclose : true });

	$("#template-upload").fileinput({
	    language: "ru",
	    showPreview: false,
	    elErrorContainer: "#errorBlock",
	    allowedFileExtensions: ["xls", "xlsx"]
	});
	
	$('#modal-edit-category, #modal-edit-goods, #modal-edit-promo, #modal-edit-region, #modal-edit-market')
			.on('show.bs.modal', function(e) {
				// get data-id attribute of the clicked element
				var elementId = $(e.relatedTarget).data('element-id');
				var elementName = $(e.relatedTarget).data('element-name');
				var owner = $(e.relatedTarget).data('element-owner');
				// populate the textbox
				$(e.currentTarget).find('input[name="id"]').val(elementId);
				$(e.currentTarget).find('input[name="name"]').val(elementName);

				if (owner) {
					$(e.currentTarget).find('input[name="owner"]').prop('checked', true);
				} else {
					$(e.currentTarget).find('input[name="owner"]').prop('checked', false);
				}
			});

	// triggered when modal is about to be shown
	$('#modal-edit-articul').on('show.bs.modal', function(e) {
		// get data-id attribute of the clicked element
		var elementId = $(e.relatedTarget).data('element-id');
		var elementName = $(e.relatedTarget).data('element-name');
		var elementCode = $(e.relatedTarget).data('element-code');
		// var elementSubCategoryId =
		// $(e.relatedTarget).data('element-subcategory-id');
		var topProduct = $(e.relatedTarget).data('element-top-product');
		// populate the textbox
		$(e.currentTarget).find('input[name="id"]').val(elementId);
		$(e.currentTarget).find('input[name="name"]').val(elementName);
		$(e.currentTarget).find('input[name="code"]').val(elementCode);

		if (topProduct) {
			$(e.currentTarget).find('input[name="top_product"]').prop('checked', true);
		} else {
			$(e.currentTarget).find('input[name="top_product"]').prop('checked', false);
		}
	});

	// triggered when modal is about to be shown
	$('#modal-edit-template').on('show.bs.modal', function(e) {
		// get data-id attribute of the clicked element
		var elementId = $(e.relatedTarget).data('element-id');
		var elementName = $(e.relatedTarget).data('element-name');
		var elementDate = $(e.relatedTarget).data('element-date');
		var elementFile = $(e.relatedTarget).data('element-file');

		// populate the textbox
		$(e.currentTarget).find('input[name="id"]').val(elementId);
		$(e.currentTarget).find('input[name="name"]').val(elementName);

		$(e.currentTarget).find('input[name="date"]').val(elementDate);
		$(e.currentTarget).find('input[name="file"]').val(elementFile);

		$(e.currentTarget).find('input[name="date"]').prop('disabled', true);
		$(e.currentTarget).find('input[name="file"]').prop('disabled', true);
	});

	$('#modal-edit-market-point').on('show.bs.modal', function(e) {
		// get data-id attribute of the clicked element
		var elementId = $(e.relatedTarget).data('element-id');
		var elementName = $(e.relatedTarget).data('element-name');
		var elementCity = $(e.relatedTarget).data('element-id-city');
		var citySelector = $('select[name="city_id"]');

		// populate the textbox
		$('input[name="id"]').val(elementId);
		$('input[name="name"]').val(elementName);
		$('input[name="idcity"]').val(elementCity);

		if (elementId) {
			citySelector.prop('disabled', true);
			citySelector.val(elementCity);
		} else {
			citySelector.prop('disabled', false);
			citySelector.val("-1");
		}

		citySelector.selectpicker('render');
		citySelector.selectpicker('refresh');
	});

	$('#modal-edit-city').on('show.bs.modal', function(e) {
		// get data-id attribute of the clicked element
		var elementId = $(e.relatedTarget).data('element-id');
		var elementName = $(e.relatedTarget).data('element-name');
		var elementRegion = $(e.relatedTarget).data('element-region');
		var regionSelector = $('select[name="region_id"]');

		// populate the textbox
		$('input[name="id"]').val(elementId);
		$('input[name="name"]').val(elementName);
		$('input[name="idregion"]').val(elementRegion);

		if (elementId) {
			regionSelector.prop('disabled', true);
			regionSelector.val(elementRegion);
		} else {
			regionSelector.prop('disabled', false);
			regionSelector.val("-1");
		}

		regionSelector.selectpicker('render');
		regionSelector.selectpicker('refresh');
	});

	$('#save-market-point').on('click', function(e) {
		var cityValue = $('select[name="city_id"]').val();

		if (cityValue) {
			$('input[name="idcity"]').val(cityValue);
			return true;
		} else {
			return false;
		}
	});

	$('#save-city').on('click', function(e) {
		var regionValue = $('select[name="region_id"]').val();

		if (regionValue) {
			$('input[name="idregion"]').val(regionValue);
			return true;
		} else {
			return false;
		}
	});

	$('#top-product').change(function(e) {
		if ($(this).prop('checked')) {
			$('#articul-listing tr.articul:not(.top-product)').hide();
		} else {
			$('#articul-listing tr.articul:not(.top-product)').show();
		}
	});
});
