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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.teklib.dao.TeklibDao;
import org.teklib.model.Book;
import org.teklib.model.BookFile;
import org.teklib.model.CoverImage;
import org.teklib.model.Publisher;
import org.teklib.model.Shelf;

public class TeklibDaoImpl extends HibernateDaoSupport implements TeklibDao {

	/** Logger for this class and subclasses */
	private static Logger logger = Logger.getLogger(TeklibDaoImpl.class.getName());
	
	@Override
	@SuppressWarnings("all")
	public List<Publisher> getPublisherList() {
		return getHibernateTemplate().findByNamedQuery("PublisherList");
	}
	@Override
	@SuppressWarnings("all")
	public Publisher getPublisherByName(String name) {
		List result = getHibernateTemplate().findByNamedQuery("PublisherByName", new Object[] { name });
		return result == null || result.size()==0 ? null : (Publisher)result.get(0);
	}
	@Override
	@SuppressWarnings("all")
	public Publisher getPublisherById(int id) {
		List result = getHibernateTemplate().findByNamedQuery("PublisherById", new Object[] { id });
		return result == null || result.size()==0 ? null : (Publisher)result.get(0);
	}
	@Override
	public int getBookCountByPublisher(int id) {
		return (int)(((Long) getHibernateTemplate().findByNamedQuery("BookCountByPublisher", new Object[] { id }).iterator().next()).longValue());
	}
	@Override
	@SuppressWarnings("all")
	public List<Shelf> getShelfList() {
		return getHibernateTemplate().findByNamedQuery("ShelfList");
	}
	@Override
	@SuppressWarnings("all")
	public Shelf getShelfByName(String name) {
		List result = getHibernateTemplate().findByNamedQuery("ShelfByName", new Object[] { name });
		return result == null || result.size()==0 ? null : (Shelf)result.get(0);
	}
	@Override
	@SuppressWarnings("all")
	public Shelf getShelfById(int id) {
		List result = getHibernateTemplate().findByNamedQuery("ShelfById", new Object[] { id });
		return result == null || result.size()==0 ? null : (Shelf)result.get(0);
	}
	@Override
 	public int getBookCountByShelf(int id) {
		return (int)(((Long) getHibernateTemplate().findByNamedQuery("BookCountByShelf", new Object[] { id }).iterator().next()).longValue());
	}
	@Override
	@SuppressWarnings("all")
	public BookFile getBookFileByName(String name) {
		List result = getHibernateTemplate().findByNamedQuery("BookFileByName", new Object[] { name });
		return result == null || result.size()==0 ? null : (BookFile)result.get(0);
	}
	@Override
	public int getBookFileCount() {
		return (int)(((Long) getHibernateTemplate().findByNamedQuery("BookFileCount").iterator().next()).longValue());
	}
	@Override
	@SuppressWarnings("all")
	public CoverImage getImageById(int id) {
		List result = getHibernateTemplate().findByNamedQuery("ImageById", new Object[] { id });
		return result == null || result.size()==0 ? null : (CoverImage)result.get(0);
	}
	@Override
	public int getImageCount() {
		return (int)(((Long) getHibernateTemplate().findByNamedQuery("CoverImageCount").iterator().next()).longValue());
	}	
	@Override
	@SuppressWarnings("rawtypes")
	public Book getBookById(int id) {
		List result = getHibernateTemplate().findByNamedQuery("BookById", new Object[] { id });
		if(result == null || result.size()==0) {
			return null;
		} else {
			Book book = (Book)result.get(0);
			return book;
		}
	}
	@Override
	@SuppressWarnings("rawtypes")
	public Book getBookByIsbn(String isbn) {
		List result = getHibernateTemplate().findByNamedQuery("BookByIsbn", new Object[] { isbn });
		return result == null || result.size()==0 ? null : (Book)result.get(0);
	}
	@Override
	@SuppressWarnings("all")
	public List<Book> getBooks() {
		return getHibernateTemplate().findByNamedQuery("BookList");
	}
	@Override
	@SuppressWarnings("all")
	public List getBooks(final Map<String, String> filter, final int start, final int count) {
		if(logger.isLoggable(Level.INFO)) logger.info( "get Book List  " + start + "/" + count );
        return (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
    			Criteria crit = session.createCriteria(Book.class);
    			if(filter != null) {
    				for(Iterator iFilter = filter.keySet().iterator(); iFilter.hasNext() ;) {
    					String key = (String)iFilter.next();
    					if(key.equals("search")) {
        					crit.add( Expression.ilike("name", new StringBuffer("%").append(filter.get("search")).append("%").toString()));
    					} else if(key.equals("publisher")) {
           					crit.createCriteria("publisher").add(Expression.eq("name", filter.get("publisher")));
    					} else if(key.equals("shelf")) {
           					crit.createCriteria("shelf").add(Expression.eq("name", filter.get("shelf")));
      					} else {
        					throw new IllegalArgumentException("Unsupported filter Key : "+key);
    					}
    				}
    			}
    			crit.addOrder(Order.desc("insertDate"));
    			/* crit.setMaxResults(count);
    			crit.setFirstResult(start); TODO remove index?*/

    			List list = crit.list();
    			return list;
            }
        });
	}
	@Override
	@SuppressWarnings("all")
	public int getSelectedBooksCount(final Map<String, String> filter) {
		if(logger.isLoggable(Level.INFO)) logger.info( "get Book List Count: " + filter);
        return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
    			Criteria crit = session.createCriteria(Book.class);
    			if(filter != null) {
    				for(Iterator iFilter = filter.keySet().iterator(); iFilter.hasNext() ;) {
    					String key = (String)iFilter.next();
    					if(key.equals("search")) {
        					crit.add( Expression.ilike("name", new StringBuffer("%").append(filter.get("search")).append("%").toString()));
    					} else if(key.equals("publisher")) {
           					crit.createCriteria("publisher").add(Expression.eq("name", filter.get("publisher")));
    					} else if(key.equals("shelf")) {
           					crit.createCriteria("shelf").add(Expression.eq("name", filter.get("shelf")));
      					} else {
        					throw new IllegalArgumentException("Unsupported filter Key : "+key);
    					}
    				}
    			}
    			List list = crit.list();
    			return list.size();
            }
        });
	}
	@Override
	public int getBookCount() {
		return (int)(((Long) getHibernateTemplate().findByNamedQuery("BookCount").iterator().next()).longValue());
	}
	@Override
	public void storeBook(Book book) {
		if(logger.isLoggable(Level.INFO)) logger.info("SaveBook:"+book.toString());
		getHibernateTemplate().saveOrUpdate(book);
	}
	@Override
	public void deleteBook(Book book) {
		if(logger.isLoggable(Level.INFO)) logger.info("DeleteBook:"+book.toString());
		getHibernateTemplate().delete(book);
	}
	@Override
	public void storePublisher(Publisher publisher) {
		if(logger.isLoggable(Level.INFO)) logger.info("SavePublisher:"+publisher.toString());
		getHibernateTemplate().update(publisher);
	}
	@Override
	public void deletePublisher(Publisher publisher) {
		if(logger.isLoggable(Level.INFO)) logger.info("DeletePublisher:"+publisher.toString());
		getHibernateTemplate().delete(publisher);
	}
	@Override
	public void storeShelf(Shelf shelf) {
		if(logger.isLoggable(Level.INFO)) logger.info("SaveShelf:"+shelf.toString());
		getHibernateTemplate().update(shelf);
	}
	@Override
	public void deleteShelf(Shelf shelf) {
		if(logger.isLoggable(Level.INFO)) logger.info("DeleteShelf:"+shelf.toString());
		getHibernateTemplate().delete(shelf);
	}
}
