package com.journaldev.spring.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.journaldev.spring.model.Person;
import com.journaldev.spring.model.UserInfo;

@Repository
public class UserDAOImpl implements UserDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}


	@Override
	public List<UserInfo> LoginUser(String username, String password) {
		List<UserInfo> login_user=new ArrayList<UserInfo>();
		login_user.remove(login_user);
		Session session = this.sessionFactory.getCurrentSession();
		Criteria cr=session.createCriteria(UserInfo.class);
		cr.add(Restrictions.eq("userId", username));
		cr.add(Restrictions.eq("passwordEnc", password));
		login_user=cr.list();
		System.out.println("List Size : "+login_user.size());
		return login_user;
	}

}
