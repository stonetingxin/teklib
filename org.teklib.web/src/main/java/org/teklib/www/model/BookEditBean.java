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
package org.teklib.www.model;

import java.util.List;

public class BookEditBean {

	private static final long serialVersionUID = 1L;
	
	private List<String> publisherList;
	private List<String> shelfList;
	
	private String publisher;
	private String shelf;
	
	private int id;
	private String isbn;
	private String name;
	private String author;
	private List<String> files;
	private String description;
	private String releaseDate;
	private String coverUrl;
	private int coverId;
	
	public List<String> getPublisherList() {
		return publisherList;
	}
	public void setPublisherList(List<String> publisherList) {
		this.publisherList = publisherList;
	}
	public List<String> getShelfList() {
		return shelfList;
	}
	public void setShelfList(List<String> shelfList) {
		this.shelfList = shelfList;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getShelf() {
		return shelf;
	}
	public void setShelf(String shelf) {
		this.shelf = shelf;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public List<String> getFiles() {
		return files;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	public int getCoverId() {
		return coverId;
	}
	public void setCoverId(int coverId) {
		this.coverId = coverId;
	}
	
	@Override
	public String toString() {
		return "BookEditBean [publisherList=" + publisherList + ", shelfList="
				+ shelfList + ", publisher=" + publisher + ", shelf=" + shelf
				+ ", id=" + id + ", isbn=" + isbn + ", name=" + name
				+ ", author=" + author + ", files=" + files + ", description="
				+ description + ", releaseDate=" + releaseDate + ", coverUrl="
				+ coverUrl + ", coverId=" + coverId + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + coverId;
		result = prime * result
				+ ((coverUrl == null) ? 0 : coverUrl.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((files == null) ? 0 : files.hashCode());
		result = prime * result + id;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((publisher == null) ? 0 : publisher.hashCode());
		result = prime * result
				+ ((publisherList == null) ? 0 : publisherList.hashCode());
		result = prime * result
				+ ((releaseDate == null) ? 0 : releaseDate.hashCode());
		result = prime * result + ((shelf == null) ? 0 : shelf.hashCode());
		result = prime * result
				+ ((shelfList == null) ? 0 : shelfList.hashCode());
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
		BookEditBean other = (BookEditBean) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (coverId != other.coverId)
			return false;
		if (coverUrl == null) {
			if (other.coverUrl != null)
				return false;
		} else if (!coverUrl.equals(other.coverUrl))
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
		if (publisherList == null) {
			if (other.publisherList != null)
				return false;
		} else if (!publisherList.equals(other.publisherList))
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
		if (shelfList == null) {
			if (other.shelfList != null)
				return false;
		} else if (!shelfList.equals(other.shelfList))
			return false;
		return true;
	}
}
