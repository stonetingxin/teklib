/* =========================================================================================================
   ===                                                                                                   ===
   ===                                     teklib javascript code                                        ===
   ===                                                                                                   ===
   ========================================================================================================= */


/* ---------------------------------------------------------------------------------------------------------
   ---                                                                                                   ---
   ---                                        Handle the books                                           ---
   ---                                                                                                   ---
   --------------------------------------------------------------------------------------------------------- */
/* get the number of selected books */
function booksCount() {
    return $.ajax({
          url: '/teklib/ajax/books/count?shelf='+shelf()+
               '&publisher='+publisher()+'&search='+search(),
          async: false
    }).responseText;
}
function refreshBookList() {
    $('#slideshow-holder').empty();
    page = 0;
    loadBooks(false);
}
function loadBooks(scrollRight) {
	console.log("test load pages:"+page+' < '+totalPages());
	if(page < totalPages()) {
		console.log("load pages:"+(page*itemsPerPage)+'/'+itemsPerPage);
		$.getJSON('/teklib/ajax/books?shelf='+shelf()+
		    '&publisher='+publisher()+'&search='+search()+'&index='+(page*itemsPerPage)+'&count='+itemsPerPage, function(data) {

		page++;
		var bookList = data.books;
		for(i=0; i<bookList.length; i++) {
			var el = $('<img>', {
				src: '/teklib/app/image/'+(bookList[i].coverId)
			 }).addClass('slideshow-content');
			addBookEvent(el, bookList[i].id);
			$('#slideshow-holder').append(el);
		}
		updateScrollArea();
	    updateButtons();
		if(scrollRight) { showNextSlide(); }
		});
	} else {
		updateButtons();
	}
	return false;
}
function addBookEvent(el, id) {
	el.mouseover(function() {
		hoverBook(id);
	});
	el.click(function() {
		clickBook(id);
	});
}

/* ---------------------------------------------------------------------------------------------------------
   ---                                                                                                   ---
   ---                                         The Search Form                                           ---
   ---                                                                                                   ---
   --------------------------------------------------------------------------------------------------------- */
function loadSearchForm() {
    loadSearchPublisher();
    loadSearchShelf();
}
function resetSearchForm() {
    $("#publisher").prop("selectedIndex", 0);
    $("#shelf").prop("selectedIndex", 0);
    $("#search").val('');
    refreshBookList();
}

/* load the group */
function loadGroup(select, name, first, selected) {
    $.getJSON('/teklib/ajax/'+name, function(data) {
            select.empty();
            select.append($('<option></option>').val('').text(first));
        for (i=0; i<data.length; i++) {
		var actName = data[i];
		if(actName == selected) {
		        select.append(
		            $('<option selected="true"></option>').val(actName).text(actName)
		        );
		} else {
		        select.append(
		            $('<option></option>').val(data[i]).text(actName)
		        );
		}
            }
    });
}



/* load the book count */
/* search form values */
function shelf() {
    if($("#shelf").prop("selectedIndex") > 0) {
        return $("select[name='shelf'] option:selected").val();
    } else { return ''; }
}
function publisher() {
    if($("#publisher").prop("selectedIndex") > 0) {
        return $("select[name='publisher'] option:selected").val();
    } else { return ''; }
}
function search() {
    var selectedSearch = $("#search").val();
    if(selectedSearch == undefined) {
        return '';
    } else {
        return selectedSearch;
    }
}

/* ---------------------------------------------------------------------------------------------------------
   ---                                                                                                   ---
   ---                                          The Book Form                                            ---
   ---                                                                                                   ---
   --------------------------------------------------------------------------------------------------------- */
function loadBookForm(id) {

}
