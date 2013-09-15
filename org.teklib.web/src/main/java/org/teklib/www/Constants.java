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
package org.teklib.www;

public class Constants {

    /* start url and form action */

    public static final String REDIRECT_HOME = "redirect:/app/main.htm";

    public static final String FORM_ACTION_CANCEL = "cancel";
    public static final String FORM_ACTION_SUBMIT = "submit";
    public static final String FORM_ACTION_DELETE = "delete";

    /* end url and form action */




    public static final String IMAGE_TMP_PREFIX = "image.orig";
	public static final String IMAGE_TYPE = "JPEG";
	public static final String IMAGE_EXTENSION = ".jpg";
	public static final String IMAGE_THUMBNAIL_PREFIX = "image_thumb";
	public static final int IMAGE_THUMBNAIL_WIDTH = 220;
	public static final int IMAGE_THUMBNAIL_HEIGHT = 300;

	public static final int PROPERTY_BOOK_NAME_LENGTH = 125;
	
	public static final int PAGE_SIZE = 10;

	public static final String FILE_EXTENSION_CHM = ".chm";
	public static final String FILE_EXTENSION_PDF = ".pdf";
	public static final String FILE_EXTENSION_HTML = ".html";

	public static final String MIME_TYPE_TEXT_PLAIN = "text/plain";
	public static final String MIME_TYPE_MSHELP = "application/mshelp";
	public static final String MIME_TYPE_PDF = "application/pdf";
}
