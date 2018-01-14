package assign.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import assign.domain.Date;

import java.util.logging.*;

public class DBLoader {
	private SessionFactory sessionFactory;
	
	Logger logger;
	
	public DBLoader() {
		// A SessionFactory is set up once for an application
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
        
        logger = Logger.getLogger("DBLoader");
	}
	
	public void loadData(Map<String, List<String>> data) {
		logger.info("Inside loadData.");
	}
	

	
	public Long addDate(Date newDate) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Long dateId = null;
		if(emptyString(newDate.getTitle()) ||emptyString(newDate.getDateDescription())) {
			return null;
		}
		try {
			tx = session.beginTransaction();
			session.save(newDate);
		    dateId = newDate.getId();
		    System.out.println("The new id is: "+ newDate.getId());
		    tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				return null; 
			}
		}
		finally {
			session.close();
		}
		return dateId;
	}

	protected boolean emptyString(String str) {
		if(str == null) {
			return true;
		}
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) != ' ') {
				return false;
			}
		}
		return true;
	}
//	public Meeting updateMeeting(Long meetingId, Meeting meeting, Long projId) throws Exception {
//		Session session = sessionFactory.openSession();
//		Transaction tx = null;
//		Meeting m = null;
//		if(emptyString(meeting.getName())||invalidYear(meeting.getYear())) {
//			return null;
//		}
//		try {
//			tx = session.beginTransaction();
//			m = getMeeting(meetingId);
//			m.setName(meeting.getName());
//			m.setYear(meeting.getYear());
//			session.update(m);
//		    tx.commit();
//		} catch (Exception e) {
//			if (tx != null) {
//				tx.rollback();
//				return null;  
//			}
//		}
//		finally {
//			session.close();
//		}
//		return m;
//		
//	}
//	
//	public Long addMeeting(Meeting meeting) throws Exception {
//		Session session = sessionFactory.openSession();
//		Transaction tx = null;
//		Long meetingId = null;
//		if(emptyString(meeting.getName())||invalidYear(meeting.getYear())) {
//			return null;
//		}
//		try {
//			tx = session.beginTransaction();
//			session.save(meeting);
//		    meetingId = meeting.getId();
//		    tx.commit();
//		} catch (Exception e) {
//			if (tx != null) {
//				tx.rollback();
//				return null;  
//			}
//		}
//		finally {
//			session.close();
//		}
//		return meetingId;
//	}
//	
//	public Long addMeetingAndProject(String title, String projectTitle) throws Exception {
//		Session session = sessionFactory.openSession();
//		Transaction tx = null;
//		Long meetingId = null;
//		try {
//			tx = session.beginTransaction();
//			Meeting newMeeting = new Meeting( );
//			Date project = new Date(projectTitle, projectTitle);
//			newMeeting.setProject(project);
//			session.save(project);
//			session.save(newMeeting);
//		    meetingId = newMeeting.getId();
//		    tx.commit();
//		} catch (Exception e) {
//			if (tx != null) {
//				tx.rollback();
//				throw e;
//			}
//		}
//		finally {
//			session.close();
//		}
//		return meetingId;
//	}
//	
//	public Long addMeetingsToProject(List<String> meetings, String projectTitle) throws Exception {
//		Session session = sessionFactory.openSession();
//		Transaction tx = null;
//		Long projectId = null;
//		try {
//			tx = session.beginTransaction();
//			Date project = new Date(projectTitle, projectTitle);
//			session.save(project);
//			projectId = project.getId();
//			for(String a : meetings) {
//				Meeting newMeeting = new Meeting( );
//				newMeeting.setProject(project);
//				session.save(newMeeting);
//			}
//		    tx.commit();
//		} catch (Exception e) {
//			if (tx != null) {
//				tx.rollback();
//				throw e;
//			}
//		}
//		finally {
//			session.close();
//		}
//		return projectId;
//	}
//	
//	public List<Meeting> getMeetingsForAProject(Long projectId) throws Exception {
//		Session session = sessionFactory.openSession();		
//		session.beginTransaction();
//		String query = "from Meeting where project=" + projectId; // BAD PRACTICE
//		List<Meeting> meetings = session.createQuery(query).list();
//		session.close();
//		return meetings;
//	}
//	
//	public List<Object[]> getMeetingsForAProject(String projectName) throws Exception {
//		Session session = sessionFactory.openSession();
//		session.beginTransaction();
//		String query = "from Meeting a join a.project c where c.projectName = :cname";		
//				
//		List<Object[]> meetings = session.createQuery(query).setParameter("cname", projectName).list();
//		session.close();
//		
//		return meetings;
//	}
//	
//	public Meeting getMeeting(Long id) throws Exception {
//		Session session = sessionFactory.openSession();
//		
//		session.beginTransaction();
//		
//		Criteria criteria = session.createCriteria(Meeting.class).
//        		add(Restrictions.eq("id", id));
//		
//		List<Meeting> meetings = criteria.list();
//		
//		
//		if (meetings.size() > 0) {
//			return meetings.get(0);			
//		} else {
//			return null;
//		}
//	}
//	
//	public Date getProject(String projectName) throws Exception {
//		Session session = sessionFactory.openSession();
//		
//		session.beginTransaction();
//		
//		Criteria criteria = session.createCriteria(Date.class).
//        		add(Restrictions.eq("projectName", projectName));
//		
//		List<Date> projects = criteria.list();
//		
//		if (projects.size() > 0) {
//			return projects.get(0);	
//		} else {
//			return null;
//		}
//	}
//	
	public List<Date> getAllDates(){    
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Date> list = new ArrayList<Date>();
		list = session.createQuery("from Date").list();
		return list;	    
	}
	
	
	public Date getDateById(Long id) throws Exception {
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Date.class).
        		add(Restrictions.eq("id", id));
		
		List<Date> dates = criteria.list();
		
		if (dates.size() > 0) {
			return dates.get(0);	
		} else {
			return null;
		}
	}
//
//	public void deleteMeeting(String title) throws Exception {
//		
//		Session session = sessionFactory.openSession();		
//		session.beginTransaction();
//		String query = "from Meeting a where a.title = :title";		
//				
//		Meeting a = (Meeting)session.createQuery(query).setParameter("title", title).list().get(0);
//		
//        session.delete(a);
//
//        session.getTransaction().commit();
//        session.close();
//	}
//	
//	public void deleteProject(Long id) throws Exception {
//		
//		Session session = sessionFactory.openSession();		
//		session.beginTransaction();
//		String query = "from Project c where c.id = :id";		
//				
//		Date c = (Date)session.createQuery(query).setParameter("id", id).list().get(0);
//		
//        session.delete(c);
//
//        session.getTransaction().commit();
//        session.close();
//	}

		public void deleteDate(Long id) throws Exception {
		
		Session session = sessionFactory.openSession();		
		session.beginTransaction();
		//create query
		String query = "from Date date where date.id = :id";		
		
		Date date = (Date)session.createQuery(query).setParameter("id", id).list().get(0);
		
       session.delete(date);

       session.getTransaction().commit();
       session.close();
	}
		
		//reference for table code
//		<table class="table table-bordered">
//		  <thead>
//		    <tr>
//		      <th scope="col">#</th>
//		      <th scope="col">First</th>
//		      <th scope="col">Last</th>
//		      <th scope="col">Handle</th>
//		    </tr>
//		  </thead>
//		  <tbody>
//		    <tr>
//		      <th scope="row">1</th>
//		      <td>Mark</td>
//		      <td>Otto</td>
//		      <td>@mdo</td>
//		    </tr>
//		    <tr>
//		      <th scope="row">2</th>
//		      <td>Jacob</td>
//		      <td>Thornton</td>
//		      <td>@fat</td>
//		    </tr>
//		    <tr>
//		      <th scope="row">3</th>
//		      <td colspan="2">Larry the Bird</td>
//		      <td>@twitter</td>
//		    </tr>
//		  </tbody>
//		</table>

		/*
		 * title
		 * description
		 * fecha
		 * album 
		 */
		private String newTableEntry(Date date, int index) {
			StringBuilder sb = new StringBuilder();
			String row = String.valueOf(index);
			sb.append("<th scope='row'>");
			sb.append(row);
			sb.append("</th>");
			sb.append("<td>");
			sb.append(date.getTitle());
			sb.append("</td>");
			sb.append("<td>");
			sb.append(date.getDateDescription());
			sb.append("</td>");
			
			//adds on the info for completed dates
			if(date.getCompleted()) {
				sb.append("<td>");
				sb.append(date.getDate());
				sb.append("</td>");
				String src = "images/" + date.getTitle() + "/" +  date.getDate();
				sb.append("<td>");
				sb.append("<a href='");
				sb.append(src);
				sb.append("'> Album</td>");
			}
			return sb.toString();
		}

}
