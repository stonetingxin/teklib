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
package org.teklib.www.service;

import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.teklib.dao.TeklibDao;
import org.teklib.model.Book;
import org.teklib.model.BookFile;
import org.teklib.model.CoverImage;
import org.teklib.model.Publisher;
import org.teklib.model.Shelf;
import org.teklib.www.Constants;
import org.teklib.www.model.BookEditBean;
import org.teklib.www.model.BookFileJVT;
import org.teklib.www.model.BookJVT;
import org.teklib.www.model.PublisherEditBean;
import org.teklib.www.model.ShelfEditBean;

@Component("teklibServiceTarget")
public class TeklibService {

	private static Logger logger = Logger.getLogger(TeklibService.class.getName());

	@Autowired
	protected TeklibDao teklibDao;
	
	public List<BookJVT> booksHandler(Map<String, String> map, int page) {
		List<BookJVT> jvtBooks = new ArrayList<BookJVT>();
		List<Book> books = this.teklibDao.getBooks(map, page*Constants.PAGE_SIZE, Constants.PAGE_SIZE);
		if(books != null && !books.isEmpty()) {
			for(Book b : books) {
				jvtBooks.add(getBookJVT(b));
			}
		}
		return jvtBooks;
	}
	public List<BookJVT> getBookList(Map<String, String> map, int start, int count) {
        List<BookJVT> jvtBooks = new ArrayList<BookJVT>();
        List<Book> books = this.teklibDao.getBooks(map, start, count);
        if(books != null && !books.isEmpty()) {
            for(Book b : books) {
                jvtBooks.add(getBookJVT(b));
            }
        }
        return jvtBooks;
    }
	public int getSelectedBooks(Map<String, String> map) {
		return this.teklibDao.getSelectedBooksCount(map);
	}
	public int getPages(Map<String, String> map) {
		int booksCount = this.teklibDao.getSelectedBooksCount(map);
		return booksCount / Constants.PAGE_SIZE + ((booksCount % Constants.PAGE_SIZE) > 0 ? 1 : 0);
	}
	public BookJVT getBookById(int id) {
		return getBookJVT(teklibDao.getBookById(id));
	}
	
	public List<Map<String, String>> getPublisherList() {
		List<Publisher> publishers = teklibDao.getPublisherList();
		List<Map<String, String>> jvtPublishers = new ArrayList<Map<String, String>>();

		if(publishers != null && !publishers.isEmpty()) {
			for(Publisher p : publishers) {
                Map<String, String> item = new HashMap<String, String>();
                item.put("value", p.getName());
                item.put("text", p.getName());
                jvtPublishers.add(item);
			}
		} else {
            Map<String, String> item = new HashMap<String, String>();
            item.put("value", "Miscelangelous");
            item.put("text", "Miscelangelous");
            jvtPublishers.add(item);
        }
		return jvtPublishers;
	}
	public PublisherEditBean getPublisherByName(String name) {
		Publisher publisher = teklibDao.getPublisherByName(name);
		PublisherEditBean bean = new PublisherEditBean();
		bean.setId(publisher.getId());
		bean.setName(publisher.getName());
		bean.setBookCount(teklibDao.getBookCountByPublisher(publisher.getId()));
		return bean;		
	}
	public List<Map<String, String>> getShelfList() {
		List<Shelf> shelfs = teklibDao.getShelfList();
		List<Map<String, String>> jvtShelfs = new ArrayList<Map<String, String>>();

		if(shelfs != null && !shelfs.isEmpty()) {
			for(Shelf s : shelfs) {
                Map<String, String> item = new HashMap<String, String>();
                item.put("value", s.getName());
                item.put("text", s.getName());
				jvtShelfs.add(item);
			}
		} else {
            Map<String, String> item = new HashMap<String, String>();
            item.put("value", "Miscelangelous");
            item.put("text", "Miscelangelous");
            jvtShelfs.add(item);
		}
		return jvtShelfs;
	}
	public ShelfEditBean getShelfByName(String name) {
		Shelf shelf = teklibDao.getShelfByName(name);
		ShelfEditBean bean = new ShelfEditBean();
		bean.setId(shelf.getId());
		bean.setName(shelf.getName());
		bean.setBookCount(teklibDao.getBookCountByShelf(shelf.getId()));
		return bean;		
	}
	public CoverImage getImageById(int id) {
		return teklibDao.getImageById(id);
	}
	public List<String> getUncategorizedBooks(String path) {
		logger.info("list Book '" + path + "'");
		List<String> result = new ArrayList<String>();
		listBooks(new File(path), path, result, 0);
		return (result);
	}
	public void storeBook(BookEditBean bookEditBean) {
		logger.info("store book:" + bookEditBean);
		this.teklibDao.storeBook(createBook(bookEditBean));
	}
	public void deleteBook(BookEditBean bookEditBean) {
		logger.info("delete book:" + bookEditBean);
		this.teklibDao.deleteBook(teklibDao.getBookById(bookEditBean.getId()));
	}
	public void storePublisher(PublisherEditBean publisherEditBean) {
		logger.info("store publisher:" + publisherEditBean);
		Publisher p = teklibDao.getPublisherById(publisherEditBean.getId());
		p.setName(publisherEditBean.getName());
		this.teklibDao.storePublisher(p);
	}
	public void deletePublisher(PublisherEditBean publisherEditBean) {
		logger.info("delete publisher:" + publisherEditBean);
		this.teklibDao.deletePublisher(teklibDao.getPublisherById(publisherEditBean.getId()));
	}
	public void storeShelf(ShelfEditBean shelfEditBean) {
		logger.info("store shelf:" + shelfEditBean);
		Shelf s = teklibDao.getShelfById(shelfEditBean.getId());
		s.setName(shelfEditBean.getName());
		this.teklibDao.storeShelf(s);
	}
	public void deleteShelf(ShelfEditBean shelfEditBean) {
		logger.info("delete shelf:" + shelfEditBean);
		this.teklibDao.deleteShelf(teklibDao.getShelfById(shelfEditBean.getId()));
	}
	public Book getBookByIsbn(String isbn) {
		return(teklibDao.getBookByIsbn(isbn));
	}
	/**
	 * Get the Image from the URL
	 * @param image the Url
	 * @return the local Image
	 */
	public static File getImageFromUrl(URL image) {

		File tmpFile = null;
		try {
			tmpFile = File.createTempFile(Constants.IMAGE_TMP_PREFIX, Constants.IMAGE_EXTENSION);
			if (logger.isLoggable(Level.FINEST)) logger.finest( "tmp file : " + tmpFile.getPath() + "/" + tmpFile.getName());
			FileUtils.copyURLToFile(image, tmpFile);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		return tmpFile;
	}
	/**
	 * Resize the Image to the Size configured in <code>AppConfig</code>
	 * @param file the original image
	 * @return the new resized Image
	 */
	@SuppressWarnings("all")
	public static File getImageThumbnail(File file) {

		if (logger.isLoggable(Level.FINEST)) logger.finest( "create thumbnail file. (headless:" + System.getProperty( "java.awt.headless" ) + ")" );
		File tmpThumbFile = null;
		try {
			tmpThumbFile =
				File.createTempFile(Constants.IMAGE_THUMBNAIL_PREFIX, Constants.IMAGE_EXTENSION);
			if (logger.isLoggable(Level.FINEST)) logger.finest("tmp thumbnail file : " +  tmpThumbFile.getName());

			BufferedImage buffImage = ImageIO.read(new FileInputStream(file));
			BufferedImage pDestImage = new BufferedImage(
					Constants.IMAGE_THUMBNAIL_WIDTH, Constants.IMAGE_THUMBNAIL_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);

			AffineTransform transform = new AffineTransform();
			if (logger.isLoggable(Level.FINEST)) logger.finest("resize: " +  (float)Constants.IMAGE_THUMBNAIL_WIDTH/(float)buffImage.getWidth() + "/" +  
				(float)Constants.IMAGE_THUMBNAIL_HEIGHT/(float)buffImage.getHeight() );
			transform.scale((float)Constants.IMAGE_THUMBNAIL_WIDTH/(float)buffImage.getWidth(), 
				(float)Constants.IMAGE_THUMBNAIL_HEIGHT/(float)buffImage.getHeight());

			Graphics2D g = (Graphics2D)pDestImage.getGraphics();

			//set the rendering hints for a good thumbnail image
			Map m = g.getRenderingHints();
			m.put(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
			m.put(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY );
			m.put(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR );
			m.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY );
			g.setRenderingHints( m );	
						
			g.drawImage(buffImage, transform, null);
			g.dispose();

			ImageIO.write(pDestImage, Constants.IMAGE_TYPE, tmpThumbFile);

		} catch (HeadlessException e) {
			if (logger.isLoggable(Level.WARNING)) logger.warning(e.toString());
		} catch (FileNotFoundException e) {
			if (logger.isLoggable(Level.WARNING)) logger.warning(e.toString());
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		return tmpThumbFile;
	}
	
	private void listBooks(File file, String prefix, List<String> list, int counter) {
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {

			String localName = files[i].getAbsolutePath();
			if ( localName.toLowerCase().endsWith(Constants.FILE_EXTENSION_PDF) || 
				 localName.toLowerCase().endsWith(Constants.FILE_EXTENSION_CHM) ||
				 (files[i].isDirectory() && counter==1) ) {

				localName = "/"+localName.substring(prefix.length(), localName.length());
				
			if(this.teklibDao.getBookFileByName(localName) == null) {
					list.add(localName);
				}
			} else if (files[i].isDirectory() && (!localName.endsWith(Constants.FILE_EXTENSION_HTML)) ) {
				logger.info("add Directory " + localName);
				listBooks(files[i], prefix, list, counter+1);
			}
		}
	}
	private static BookJVT getBookJVT(Book book) {
		BookJVT jvt = new BookJVT();
		jvt.setAuthor(book.getAuthor());
		jvt.setDescription(book.getDescription());
		jvt.setId(book.getId());
		jvt.setInsertDate(book.getInsertDate());
		jvt.setIsbn(book.getIsbn());
		jvt.setName(book.getName());
		jvt.setReleaseDate(book.getReleaseDate());
		jvt.setCoverImage(book.getCoverImage().getId());
		jvt.setPublisher(book.getPublisher().getName());
		jvt.setShelf(book.getShelf().getName());
		
		Collection<BookFileJVT> jvtBooks = new ArrayList<BookFileJVT>();
		for(BookFile f : book.getFiles()) {
			BookFileJVT jvtBook = new BookFileJVT();
			jvtBook.setFilename(URLEncoder.encode(f.getFilename()));
			jvtBook.setFormat(f.getFormat());
			jvtBook.setId(f.getId());
			jvtBooks.add(jvtBook);
		}
		jvt.setFiles(jvtBooks);
		return jvt;
	}
	private Book createBook(BookEditBean bookEditBean) {
		Book book;
		if(bookEditBean.getId() == 0) {
			book = new Book();
		} else {
			book = teklibDao.getBookById(bookEditBean.getId());
		}
		book.setAuthor(bookEditBean.getAuthor());
		book.setDescription(bookEditBean.getDescription());
		book.setInsertDate(new Date(System.currentTimeMillis()));
		book.setIsbn(bookEditBean.getIsbn());
		book.setName(bookEditBean.getName());
		book.setReleaseDate(bookEditBean.getReleaseDate());

		Set<BookFile> bookList = new HashSet<BookFile>();
		for(String f : bookEditBean.getFiles()) {
			BookFile file = this.teklibDao.getBookFileByName(f);
			if(file == null) {
				file = new BookFile();
			}
			file.setBook(book);
			file.setFilename(f);
			file.setFormat(f.substring(f.lastIndexOf('.')+1, f.length()).toLowerCase());
			bookList.add(file);
		}
		book.setFiles(bookList);
		
		Publisher p = this.teklibDao.getPublisherByName(bookEditBean.getPublisher());
		if(p == null) {
			p = new Publisher();
			p.setName(bookEditBean.getPublisher());
		}
		book.setPublisher(p);
		Shelf s = this.teklibDao.getShelfByName(bookEditBean.getShelf());
		if(s == null) {
			s = new Shelf();
			s.setName(bookEditBean.getShelf());
		}
		book.setShelf(s);
		
		if(bookEditBean.getCoverUrl() != null && !bookEditBean.getCoverUrl().isEmpty()) {
			CoverImage image = (book.getCoverImage()!=null ? book.getCoverImage() : new CoverImage());
			image.setHeight(Constants.IMAGE_THUMBNAIL_HEIGHT);
			image.setWidth(Constants.IMAGE_THUMBNAIL_WIDTH);
			image.setMimeType("image/jpeg");
			image.setType(0);
			image.setImage(getImage(bookEditBean.getCoverUrl()));
			book.setCoverImage(image);
		}
		return book;
	}
	private static byte[] getImage(String url) {
		try {
			File thumbnail = getImageThumbnail(getImageFromUrl(new URL(url)));
			FileInputStream stream = new FileInputStream(thumbnail);
			byte[] buffer = new byte[(int)thumbnail.length()];
			stream.read(buffer);
			return buffer;
			
		} catch (Exception e) {
			throw new RuntimeException(e.toString(), e);
		}
		
	}
}
