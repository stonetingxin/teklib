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
package org.teklib.dao;

import java.util.List;
import java.util.Map;

import org.teklib.model.Book;
import org.teklib.model.BookFile;
import org.teklib.model.CoverImage;
import org.teklib.model.Publisher;
import org.teklib.model.Shelf;

/**
 * The persistence facade.
 */
public interface TeklibDao {

	/**
	 * Get the <code>Publisher</code> list.
	 * @return
	 */
	public List<Publisher> getPublisherList();
	
	/**
	 * Get the <code>Publisher</code> by name.
	 * @param name
	 * @return
	 */
	public Publisher getPublisherByName(String name);
	
	/**
	 * Get the <code>Publisher</code> by id.
	 * @param id
	 * @return
	 */
	public Publisher getPublisherById(int id);

	/**
	 * Get the book count by <code>Publisher</code>.
	 * @param id
	 * @return
	 */
	public int getBookCountByPublisher(int id);
	
	/**
	 * Get the <code>Shelf</code> list.
	 * @return
	 */
	public List<Shelf> getShelfList();

	/**
	 * Get the <code>Shelf</code> by name.
	 * @param name
	 * @return
	 */
	public Shelf getShelfByName(String name);

	/**
	 * Get the <code>Shelf</code> by id.
	 * @param id
	 * @return
	 */
	public Shelf getShelfById(int id);

	/**
	 * Get the book count by <code>Shelf</code>.
	 * @param id
	 * @return
	 */
	public int getBookCountByShelf(int id);
	
	/**
	 * Get the <code>BookFile</code> by filename.
	 * @param name
	 * @return
	 */
	public BookFile getBookFileByName(String filename);

	/**
	 * Get the <code>BookFile</code> count.
	 * @return
	 */
	public int getBookFileCount();

	/**
	 * Get the <code>CoverImage</code> by id.
	 * @param id
	 * @return
	 */
	public abstract CoverImage getImageById(int id);

	/**
	 * Get the <code>CoverImage</code> count.
	 * @return
	 */
	public abstract int getImageCount();

	/**
	 * Get the <code>Book</code> by id.
	 * @param id
	 * @return
	 */
	public abstract Book getBookById(int id);
	
	/**
	 * Get the <code>Book</code> by ISBN.
	 * @param isbn
	 * @return
	 */
	public abstract Book getBookByIsbn(String isbn);
	
	/**
	 * Get a List of all <code>Book</code> Objects. <p>
	 */
	public abstract List<Book> getBooks();
	
	/**
	 * Get a List of all <code>Book</code> Objects. <p>
	 * Possible filters are: <br/>
	 * publisher - The Publisher <br/>
	 * shelf - The Shelf <br/>
	 * search - A search term <br/>
	 */
	public abstract List<Book> getBooks(final Map<String, String> filter, final int start, final int count);
	
	/**
	 * Get selected <code>Book</code> count.
	 */
	public abstract int getSelectedBooksCount(final Map<String, String> filter);
	
	/**
	 * Get the Number of all Books.
	 */
	public abstract int getBookCount();

	/**
	 * Store the <code>Book</code> 
	 * @param book
	 */
	public abstract void storeBook(Book book);
	
	/**
	 * Delete the <code>Book</code> from the database.
	 * @param book
	 */
	public abstract void deleteBook(Book book);

	/**
	 * Store the <code>Publisher</code> 
	 * @param book
	 */
	public abstract void storePublisher(Publisher publisher);
	
	/**
	 * Delete the <code>Publisher</code> from the database.
	 * @param book
	 */
	public abstract void deletePublisher(Publisher publisher);

	/**
	 * Store the <code>Shelf</code> 
	 * @param book
	 */
	public abstract void storeShelf(Shelf shelf);
	
	/**
	 * Delete the <code>Shelf</code> from the database.
	 * @param book
	 */
	public abstract void deleteShelf(Shelf shelf);
}
