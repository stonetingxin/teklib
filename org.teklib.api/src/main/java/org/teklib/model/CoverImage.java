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
import java.util.Arrays;

/**
 * The Cover Image Entity.
 */
public class CoverImage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id; //IMAGES_KEY INTEGER NOT NULL,
	private int type; //IMAGE_TYPE INTEGER,
	private int height; //HEIGHT,
	private int width; //WIDTH,
	private String mimeType; //MIME_TYPE,
	private byte[] image; //IMAGE_DATA BYTEA,

	public CoverImage() {}

	public int getId() {
		return id;
	}
	public byte[] getImage() {
		return image;
	}
	public int getType() {
		return type;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public String getMimeType() {
		return mimeType;
	}

	public void setId(int i) {
		id = i;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public void setType(int i) {
		type = i;
	}
	public void setWidth( int w) {
		width = w;
	}
	public void setHeight( int h) {
		height = h;
	}
	public void setMimeType( String m ) {
		mimeType = m;
	}
	
	@Override
	public String toString() {
		return "CoverImage [id=" + id + ", type=" + type + ", height=" + height
				+ ", width=" + width + ", mimeType=" + mimeType + ", image="
				+ Arrays.toString(image) + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + id;
		result = prime * result + Arrays.hashCode(image);
		result = prime * result
				+ ((mimeType == null) ? 0 : mimeType.hashCode());
		result = prime * result + type;
		result = prime * result + width;
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
		CoverImage other = (CoverImage) obj;
		if (height != other.height)
			return false;
		if (id != other.id)
			return false;
		if (!Arrays.equals(image, other.image))
			return false;
		if (mimeType == null) {
			if (other.mimeType != null)
				return false;
		} else if (!mimeType.equals(other.mimeType))
			return false;
		if (type != other.type)
			return false;
		if (width != other.width)
			return false;
		return true;
	}
}
