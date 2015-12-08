$(document).ready(
		function() {
			var cache = {}, lastXhr;
			$("#publisher").autocomplete(
					{
						minLength : 1,
						source : function(request, response) {
							var term = request.term;
							if (term in cache) {
								response(cache[term]);
								return;
							}

							lastXhr = $.getJSON("../../api/search/publisher/"
									+ request.term,
									function(data, status, xhr) {

										cache[term] = data.source;
										if (xhr === lastXhr) {
											response(data.source);
										}
									});
						}
					});

			$("#location-div").hide();

			$("#downloadInfo").hide();

			$("#typeDistribution").change(function() {
				if ($("#typeDistribution").val() == "Download") {
					$("#downloadInfo").show();
				} else {
					$("#downloadInfo").hide();
				}

			});

			$("#spatial").change(
					function() {

						if ($("#spatial").val() == "provincial"
								|| $("#spatial").val() == "municipal"
								|| $("#spatial").val() == "comunal") {
							$("#location-div").show();
						} else {
							$('#location option').eq(0).attr('selected',
									'selected');
							$("#location-div").hide();
						}

					});

		});
