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
package org.teklib.hibernate;

import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.teklib.dao.TeklibDao;
import org.teklib.model.Book;
import org.teklib.model.BookFile;
import org.teklib.model.CoverImage;
import org.teklib.model.Publisher;
import org.teklib.model.Shelf;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/org/teklib/hibernate/DatabaseTestContext.xml" })
@TransactionConfiguration(transactionManager="transactionManager1", defaultRollback=false)
@Transactional
@SuppressWarnings("deprecation")
public class TeklibDaoBookTest {

	private String TEST_DATA_FILE = "/org/teklib/hibernate/dataset.xml";
	
	@Autowired
	private TeklibDao teklibDao;

	@Autowired
	private DataSource dataSource;
	
	@BeforeTransaction
	public void setupDatabse() throws Exception {
		Connection conn = dataSource.getConnection();
		IDatabaseConnection connection = new DatabaseConnection(conn);
		DatabaseOperation.INSERT.execute(connection, new FlatXmlDataSet(
				TeklibDaoPublisherTest.class.getResourceAsStream(TEST_DATA_FILE)));
		conn.close();
	}
	@AfterTransaction
	public void tearDownDatabse() throws Exception {
		Connection conn = dataSource.getConnection();
		IDatabaseConnection connection = new DatabaseConnection(conn);
		DatabaseOperation.DELETE_ALL.execute(connection, new FlatXmlDataSet(
				TeklibDaoPublisherTest.class.getResourceAsStream(TEST_DATA_FILE)));
		conn.close();
	}
	@After
    public void tearDownWithinTransaction() {
        // execute "tear down" logic within the transaction
    }
	@Test
	public void testGetBook() {
		Book book1 = teklibDao.getBookById(1001);
		assert (book1.getName().equals("name 1")) : book1.getName();
		
		Book book2 = teklibDao.getBookByIsbn("200000");
		assert (book2.getName().equals("name 2")) : book2.getName();
	}
	
	@Test
	public void testCreateDeleteBook() {
		Publisher publisher = teklibDao.getPublisherByName("publisher 1");
		assert(publisher != null);
		Shelf shelf = teklibDao.getShelfByName("shelf 1");
		assert(shelf != null);
		
		CoverImage coverImage = new CoverImage();
		coverImage.setHeight(200);
		coverImage.setImage(new byte[]{0x00, 0x00, 0x002});
		coverImage.setMimeType("image/jpeg");
		coverImage.setType(2);
		coverImage.setWidth(150);

		BookFile file = new BookFile();
		file.setFilename("filename 3");
		file.setFormat("CHM");
		Collection<BookFile> files = new HashSet<BookFile>();
		files.add(file);
		
		Book book = new Book();
		book.setAuthor("author 3");
		book.setCoverImage(coverImage);
		book.setDescription("description 3");
		book.setFiles(files);
		book.setInsertDate(new Date(System.currentTimeMillis()));
		book.setIsbn("30000");
		book.setName("name 3");
		book.setPublisher(publisher);
		book.setReleaseDate("2010-01-01");
		book.setShelf(shelf);
		file.setBook(book);
		
		teklibDao.storeBook(book);
		
		int bookCount = teklibDao.getBookCount();
		Book retreivedBook = teklibDao.getBookByIsbn("30000");
		assert(bookCount == 3) : bookCount;
		assert(retreivedBook.getName().equals("name 3")) : retreivedBook;

		int countImage = teklibDao.getImageCount();
		assert(countImage == 3) : countImage;

		int countFiles = teklibDao.getBookFileCount();
		assert(countFiles == 3) : countFiles;
		
		
		teklibDao.deleteBook(book);

		int bookCount2 = teklibDao.getBookCount();
		assert(bookCount2 == 2) : bookCount2;

		int publisherCount = teklibDao.getPublisherList().size();
		assert(publisherCount == 2) : publisherCount;
		int shelfCount = teklibDao.getShelfList().size();
		assert(shelfCount == 2) : shelfCount;

		int countImage2 = teklibDao.getImageCount();
		assert(countImage2 == 2) : countImage2;

		int countFiles2 = teklibDao.getBookFileCount();
		assert(countFiles2 == 2) : countFiles2;
	}
}

