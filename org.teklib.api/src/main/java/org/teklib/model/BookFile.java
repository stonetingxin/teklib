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
package org.teklib.model;

import java.io.Serializable;

/**
 * The BookFile Entity.
 */
public class BookFile implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String filename;
	private String format;
	private Book book;

	public BookFile() {}

	public int getId() {
		return id;
	}
	public String getFormat() {
		return format;
	}
	public String getFilename() {
		return filename;
	}
	public Book getBook() {
		return book;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setFormat(String f) {
		format = f;
	}
	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "BookFile [id=" + id + ", filename=" + filename + ", format=" + format + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookFile other = (BookFile) obj;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (format == null) {
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
