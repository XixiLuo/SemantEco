var SemantAquaUI = {
	"configureMap": function() {
		var mapOptions = {
				center: new google.maps.LatLng(37.4419, -122.1419),
				zoom: 8,
				mapTypeId: google.maps.MapTypeId.ROADMAP,
				zoomControl: true,
				panControl: true
			};
		SemantAquaUI.map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
		SemantAquaUI.geocoder = new google.maps.Geocoder();
	},
	"doGeocode": function(zip) {
		console.trace();
		if(SemantAquaUI.geocoder) {
			SemantAquaUI.geocoder.geocode({"address":zip, "region":"US"},
				function(pt) {
					console.log(pt);
					if(!pt) {
						alert(zip + " not found");
					}
					else {
						SemantAquaUI.map.setCenter(pt[0].geometry.location);
						SemantAquaUI.map.setZoom(10);
					}
				});
		}
		else {
			console.log("SemantAqua.geocoder is null");
		}
	},
	"getState": function() {
		return $.extend({}, $.bbq.getState(), SemantAquaUI.getFacetParams());
	},
	"getFacetParams": function() {
		var params = {};
		$("div#facets .facet").each(function() {
			var that = $(this);
			$("input", that).each(function() {
				var type = this.getAttribute("type").toLowerCase();
				var name = this.getAttribute("name");
				if(type == "checkbox") {
					if(typeof params[name] == "undefined") {
						params[name] = [];
					}
					if(this.checked) {
						params[name].push(this.value);
					}
				}
				else if(type == "radio") {
					if(this.checked) {
						params[name] = this.value;
					}
				}
				else if(type == "text") {
					params[name] = this.value;
				}
				else {
					console.warn("Facet "+that.getAttribute("id")+
							" uses input type "+type+
							" which is not supported.");
				}
			});
			$("select", that).each(function() {
				var name = this.getAttribute("name");
				params[name] = this.value;
			});
		});
		return params;
	},
	"populateFacets": function() {
		var params = $.bbq.getState();
		for(var param in params) {
			var inputs = $("*[name="+param+"]");
			if(inputs.length > 0) {
				if(inputs[0].tagName == "INPUT") {
					var type = inputs[0].type.toLowerCase();
					if(type == "checkbox") {
						for(var i=0;i<inputs.length;i++) {
							if($.inArray($(inputs[i]).val(), params[param])>=0) {
								console.debug("Setting "+$(inputs[i]).val()+" to true")
								inputs[i].checked=true;
							}
							else {
								console.debug("Setting "+$(inputs[i]).val()+" to false")
								inputs[i].checked=false;
							}
						}
					}
					else if(type == "radio") {
						
					}
					else if(type == "text") {
						console.debug("Setting "+$(inputs[i]).attr("name")+" to "+params[param]);
						inputs[0].value = params[param];
					}
				}
				else {
					console.debug("Setting "+$(inputs[0]).attr("name")+" to "+params[param]);
					inputs[0].value = params[param];
				}
			}
		}
	},
	"createMarker": function(uri, lat, lng, icon, visible) {
		var opts = {"clickable": true,
				"icon": icon,
				"position": new google.maps.LatLng(lat, lng),
				"visible": visible
				};
		google.maps.event.addListener(marker, "click",
				function() {
			SemantAqua.action = SemantAquaUI.handleClickedMarker;
			$.bbq.pushState({"uri": uri});
		});
		return new google.maps.Marker(opts);
	},
	"handleClickedMarker": function() {
		$(window).trigger('show-marker-info');
	},
	"showSpinner": function() {
		$("#spinner").css("display", "block");
	},
	"hideSpinner": function() {
		$("#spinner").css("display", "none");
		$.bbq.removeState("action");
	}
};