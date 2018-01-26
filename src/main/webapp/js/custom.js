	function setData(modalId,id) {
		var hidden = document.getElementById(modalId);
		hidden.value=id;
	}
	$("#utilModal").on("show.bs.modal", function(e) {
	    var link = $(e.relatedTarget);
	    $(this).find(".modal-content").load(link.attr("href"));
	});