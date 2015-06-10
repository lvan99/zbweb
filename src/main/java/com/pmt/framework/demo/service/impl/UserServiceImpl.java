//package com.pmt.framework.demo.service.impl;
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.pmt.framework.dao.BaseDaoI;
//import com.pmt.framework.demo.service.UserServiceI;
//import com.pmt.framework.form.DynamicForm;
//
//@Service
//public class UserServiceImpl implements UserServiceI {
//	@Autowired
//	private BaseDaoI dao;
//
//	@Override
//	public Map<String, Object> query(DynamicForm dynamicForm)
//	{
//		int currPage = dynamicForm.getPage();
//		int pageSize = dynamicForm.getLimit();
//		return dao.query("select * from users", currPage, pageSize);
//	}
//	
//	@Override
//	public void initData()
//	{
//		// 初始化表
//				String create_table_sql = "CREATE CACHED TABLE USERS  (ID NUMERIC  NOT NULL,  USERNAME VARCHAR(30) NOT NULL,"
//						+ "  AGE NUMERIC,  PRIMARY KEY(ID));";
//				dao.execute(create_table_sql);
//				for (int i = 1; i < 100; i++) { // 插入6万条数据用了43秒，生成262M的文件
//					dao.execute("insert into USERS values(" + i + ",'zhaoyang" + i
//							+ "',30)");
//				}
//	}
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.pmt.framework.demo.service.UserServiceI#add(com.pmt.framework.form
//	 * .DynamicForm)
//	 */
//	@Override
//	public boolean add(DynamicForm dynamicForm) {
//		// 初始化表
//		String create_table_sql = "CREATE CACHED TABLE USERS  (ID NUMERIC  NOT NULL,  USERNAME VARCHAR(30) NOT NULL,"
//				+ "  AGE NUMERIC,  PRIMARY KEY(ID));";
//		dao.execute(create_table_sql);
//		for (int i = 1; i < 60000; i++) { // 插入6万条数据用了43秒，生成262M的文件
//			dao.execute("insert into USERS values(" + i + ",'zhaoyang" + i
//					+ "',30)");
//		}
//		List<Map<String, Object>> dataList = dao
//				.queryForList("select * from users");
//		System.out.println("size:" + dataList);
//		return false;
//	}
//
//	public static void main(String[] args) {
//	}
//}
