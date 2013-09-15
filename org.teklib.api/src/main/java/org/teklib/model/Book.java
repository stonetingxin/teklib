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
import java.util.Collection;
import java.util.Date;

/**
 * The Book Entity.
 */
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private Publisher publisher;
	private Shelf shelf;
	private String isbn;
	private String name;
	private String description;
	private String author;
	private String releaseDate;
	private Date insertDate;
	private Collection<BookFile> files;
	private CoverImage coverImage;

	public Book() {}

	public Collection<BookFile> getFiles() {
		return files;
	}
	public CoverImage getCoverImage() {
		return coverImage;
	}
	public Publisher getPublisher() {
		return publisher;
	}
	public Shelf getShelf() {
		return shelf;
	}
	public int getId() {
		return id;
	}
	public String getAuthor() {
		return author;
	}
	public String getDescription() {
		return description;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public String getIsbn() {
		return isbn;
	}
	public String getName() {
		return name;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	
	public void setAuthor(String string) {
		author = string;
	}
	public void setDescription(String string) {
		description = string;
	}
	public void setFiles(Collection<BookFile> f) {
		files = f;
	}
	public void setCoverImage(CoverImage coverImage) {
		this.coverImage = coverImage;
	}
	public void setId(int i) {
		id = i;
	}
	public void setInsertDate(Date date) {
		insertDate = date;
	}
	public void setIsbn(String string) {
		isbn = string;
	}
	public void setName(String string) {
		name = string;
	}
	public void setPublisher(Publisher i) {
		publisher = i;
	}
	public void setReleaseDate(String string) {
		releaseDate = string;
	}
	public void setShelf(Shelf i) {
		shelf = i;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", publisher=" + publisher + ", shelf="
				+ shelf + ", isbn=" + isbn + ", name="+ name + ", description=" 
				+ description + ", author=" + author+ ", releaseDate=" 
				+ releaseDate + ", insertDate=" + insertDate+ ", files=" 
				+ files + ", coverImages=" + coverImage + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result
				+ ((coverImage == null) ? 0 : coverImage.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((files == null) ? 0 : files.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((insertDate == null) ? 0 : insertDate.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((publisher == null) ? 0 : publisher.hashCode());
		result = prime * result
				+ ((releaseDate == null) ? 0 : releaseDate.hashCode());
		result = prime * result + ((shelf == null) ? 0 : shelf.hashCode());
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
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (coverImage == null) {
			if (other.coverImage != null)
				return false;
		} else if (!coverImage.equals(other.coverImage))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (files == null) {
			if (other.files != null)
				return false;
		} else if (!files.equals(other.files))
			return false;
		if (id != other.id)
			return false;
		if (insertDate == null) {
			if (other.insertDate != null)
				return false;
		} else if (!insertDate.equals(other.insertDate))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (publisher == null) {
			if (other.publisher != null)
				return false;
		} else if (!publisher.equals(other.publisher))
			return false;
		if (releaseDate == null) {
			if (other.releaseDate != null)
				return false;
		} else if (!releaseDate.equals(other.releaseDate))
			return false;
		if (shelf == null) {
			if (other.shelf != null)
				return false;
		} else if (!shelf.equals(other.shelf))
			return false;
		return true;
	}
}
