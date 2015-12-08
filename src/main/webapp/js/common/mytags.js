$(document).ready(function() {
	$("#mytags").tagit({
		tagSource : function(search, showChoices) {
			$.getJSON("../../api/search/tag/" + search.term, function(json) {
				showChoices(json.availableTags);
			});
		},
		singleField : true,
		singleFieldNode : $('#keyword')
	});

});