/* This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.teklib.www.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.teklib.model.Book;
import org.teklib.www.Constants;
import org.teklib.www.model.BookEditBean;
import org.teklib.www.model.PublisherEditBean;
import org.teklib.www.model.ShelfEditBean;
import org.teklib.www.service.TeklibService;

@Component
public class BookValidator {
	
	@Autowired
	private TeklibService teklibService;
	
	public void validate(BookEditBean book, Errors errors) {
		String name = book.getName();
		if (!StringUtils.hasLength(name)) {
			errors.rejectValue("name", "required", "required");
		} else if(name.length() > Constants.PROPERTY_BOOK_NAME_LENGTH) {
			errors.rejectValue("name", "to long", "to long");
		}
		String isbn = book.getIsbn();
		if (!StringUtils.hasLength(isbn)) {
			errors.rejectValue("isbn", "required", "required");
		}
		Book existingBook = teklibService.getBookByIsbn(isbn);
		if(existingBook != null && existingBook.getId() != book.getId()) {
			errors.rejectValue("isbn", "duplicate book", "duplicate book");
		}
		String author = book.getAuthor();
		if (!StringUtils.hasLength(author)) {
			errors.rejectValue("author", "required", "required");
		}
		String coverUrl = book.getCoverUrl();
		if (!StringUtils.hasLength(coverUrl) && book.getCoverId()==0) {
			errors.rejectValue("coverUrl", "required", "required");
		}
		String publisher = book.getPublisher();
		if (!StringUtils.hasLength(publisher)) {
			errors.rejectValue("publisher", "required", "required");
		}
		String shelf = book.getShelf();
		if (!StringUtils.hasLength(shelf)) {
			errors.rejectValue("shelf", "required", "required");
		}
		String releaseDate = book.getReleaseDate();
		if (!StringUtils.hasLength(releaseDate)) {
			errors.rejectValue("releaseDate", "required", "required");
		}
	}
	public void validate(ShelfEditBean shelf, Errors errors) {
		String name = shelf.getName();
		if (!StringUtils.hasLength(name)) {
			errors.rejectValue("name", "required", "required");
		}
	}
	public void validate(PublisherEditBean publisher, Errors errors) {
		String name = publisher.getName();
		if (!StringUtils.hasLength(name)) {
			errors.rejectValue("name", "required", "required");
		}
	}
}
