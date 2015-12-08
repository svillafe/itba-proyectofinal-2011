$(document).ready(
		function() {
			$("#mydatasets").tagit(
					{
						tagSource : function(search, showChoices) {
							$.getJSON(
									"../../api/search/dataset/" + search.term,
									function(json) {
										showChoices(json.availableTags);
									});
						},
						singleField : true,
						singleFieldNode : $('#dataset')
					});
		});