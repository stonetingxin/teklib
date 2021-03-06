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
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
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
import org.teklib.model.Publisher;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/org/teklib/hibernate/DatabaseTestContext.xml" })
@TransactionConfiguration(transactionManager="transactionManager1", defaultRollback=false)
@Transactional
public class TeklibDaoPublisherTest {

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
		DatabaseOperation.DELETE.execute(connection, new FlatXmlDataSet(
				TeklibDaoPublisherTest.class.getResourceAsStream(TEST_DATA_FILE)));
		conn.close();
	}

	@Test
	public void testGetPublisherByName() {
		List<Publisher> publishers = teklibDao.getPublisherList();
		assert (publishers.size() == 2) : publishers.size();

		Publisher publisher = teklibDao.getPublisherByName("publisher 1");
		assert (publisher.getName().equals("publisher 1")) : publisher.getName();
	}
	@Test
	public void testGetPublisherById() {
		List<Publisher> publishers = teklibDao.getPublisherList();
		assert (publishers.size() == 2) : publishers.size();

		Publisher publisher = teklibDao.getPublisherById(1001);
		assert (publisher.getName().equals("publisher 1")) : publisher.getName();
	}
	@Test
	public void testGetBookCountByPublisher() {
		int count = teklibDao.getBookCountByPublisher(1001);
		assert (count == 1) : count;
	}
}
