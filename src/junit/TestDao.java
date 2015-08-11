package junit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import cn.itcast.elec.container.ServiceProvider;
import cn.itcast.elec.dao.IElecTextDao;
import cn.itcast.elec.domain.ElecText;

public class TestDao {

	//保存
	@Test
	public void saveElecText(){
		IElecTextDao elecTextDao = (IElecTextDao) ServiceProvider.getService(IElecTextDao.SERVICE_NAME);
		ElecText elecText = new ElecText();
		elecText.setTextDate(new Date());
		elecText.setTextName("hello world");
		elecText.setTextRemark("hahahahaha");
		
		elecTextDao.save(elecText);
		
	}

	//修改
	@Test
	public void update(){
		IElecTextDao elecTextDao = (IElecTextDao) ServiceProvider.getService(IElecTextDao.SERVICE_NAME);
		ElecText elecText = new ElecText();
		elecText.setTextID("2c9081ea4eedf0e3014eedf0e6090001");
		elecText.setTextDate(new Date());
		elecText.setTextName("he555555d");
		elecText.setTextRemark("hahahahaha");
		
		elecTextDao.update(elecText);
		
	}
	
	//通过ID查找对象
	@Test
	public void findObjectByID(){
		IElecTextDao elecTextDao = (IElecTextDao) ServiceProvider.getService(IElecTextDao.SERVICE_NAME);
		Serializable id = "2c9081ea4ee942e8014ee942eaf10001";
		ElecText elecText = elecTextDao.findObjectById(id);
		System.out.println(elecText.getTextName()+"..."+elecText.getTextRemark());
	}
	
	//通过ID删除对象
	@Test
	public void deleteObjectByIDs(){
		IElecTextDao elecTextDao = (IElecTextDao) ServiceProvider.getService(IElecTextDao.SERVICE_NAME);
		Serializable[] ids = {"2c9081ea4ee9561b014ee9561e250001","2c9081ea4ee94fc5014ee95378250001"};
		elecTextDao.deleteObjectByIds(ids);
	}
	
	//通过集合对象进行删除对象
	@Test
	public void deleteObjectByCollection(){
		IElecTextDao elecTextDao = (IElecTextDao) ServiceProvider.getService(IElecTextDao.SERVICE_NAME);
		Collection<ElecText> list = new ArrayList<ElecText>();
		ElecText elecText1 = new ElecText();
		elecText1= elecTextDao.findObjectById("2c9081ea4eedb838014eedb83bbe0001");
		ElecText elecText2 = new ElecText();
		elecText2= elecTextDao.findObjectById("2c9081ea4ee94be8014ee94beb880001");
		list.add(elecText1);
		list.add(elecText2);
		elecTextDao.deleteObjectByCollection(list);
	}
	/**
	 * org.springframework.dao.DataIntegrityViolationException: not-null property references a null or transient value: cn.itcast.elec.domain.ElecText.textName; nested exception is org.hibernate.PropertyValueException: not-null property references a null or transient value: cn.itcast.elec.domain.ElecText.textName
	at org.springframework.orm.hibernate3.SessionFactoryUtils.convertHibernateAccessException(SessionFactoryUtils.java:630)
	at org.springframework.orm.hibernate3.HibernateAccessor.convertHibernateAccessException(HibernateAccessor.java:412)
	at org.springframework.orm.hibernate3.HibernateTemplate.doExecute(HibernateTemplate.java:424)
	at org.springframework.orm.hibernate3.HibernateTemplate.executeWithNativeSession(HibernateTemplate.java:374)
	at org.springframework.orm.hibernate3.HibernateTemplate.deleteAll(HibernateTemplate.java:874)
	at cn.itcast.elec.dao.impl.CommonDaoImpl.deleteObjectByCollection(CommonDaoImpl.java:114)
	at junit.TestDao.deleteObjectByCollection(TestDao.java:72)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:606)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:44)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:15)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:41)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:20)
	at org.junit.runners.BlockJUnit4ClassRunner.runNotIgnored(BlockJUnit4ClassRunner.java:79)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:71)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:49)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:193)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:52)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:191)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:42)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:184)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:236)
	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:50)
	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:459)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:675)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:382)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:192)
Caused by: org.hibernate.PropertyValueException: not-null property references a null or transient value: cn.itcast.elec.domain.ElecText.textName
	at org.hibernate.engine.Nullability.checkNullability(Nullability.java:101)
	at org.hibernate.event.def.DefaultDeleteEventListener.deleteEntity(DefaultDeleteEventListener.java:272)
	at org.hibernate.event.def.DefaultDeleteEventListener.onDelete(DefaultDeleteEventListener.java:163)
	at org.hibernate.event.def.DefaultDeleteEventListener.onDelete(DefaultDeleteEventListener.java:74)
	at org.hibernate.impl.SessionImpl.fireDelete(SessionImpl.java:948)
	at org.hibernate.impl.SessionImpl.delete(SessionImpl.java:926)
	at org.springframework.orm.hibernate3.HibernateTemplate$27.doInHibernate(HibernateTemplate.java:878)
	at org.springframework.orm.hibernate3.HibernateTemplate.doExecute(HibernateTemplate.java:419)
	... 27 more

org.springframework.dao.DataIntegrityViolationException: not-null property references a null or transient value: cn.itcast.elec.domain.ElecText.textName; nested exception is org.hibernate.PropertyValueException: not-null property references a null or transient value: cn.itcast.elec.domain.ElecText.textName
	at org.springframework.orm.hibernate3.SessionFactoryUtils.convertHibernateAccessException(SessionFactoryUtils.java:630)
	at org.springframework.orm.hibernate3.HibernateAccessor.convertHibernateAccessException(HibernateAccessor.java:412)
	at org.springframework.orm.hibernate3.HibernateTemplate.doExecute(HibernateTemplate.java:424)
	at org.springframework.orm.hibernate3.HibernateTemplate.executeWithNativeSession(HibernateTemplate.java:374)
	at org.springframework.orm.hibernate3.HibernateTemplate.deleteAll(HibernateTemplate.java:874)
	at cn.itcast.elec.dao.impl.CommonDaoImpl.deleteObjectByCollection(CommonDaoImpl.java:114)
	at junit.TestDao.deleteObjectByCollection(TestDao.java:72)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:606)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:44)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:15)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:41)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:20)
	at org.junit.runners.BlockJUnit4ClassRunner.runNotIgnored(BlockJUnit4ClassRunner.java:79)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:71)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:49)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:193)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:52)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:191)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:42)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:184)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:236)
	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:50)
	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:459)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:675)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:382)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:192)
Caused by: org.hibernate.PropertyValueException: not-null property references a null or transient value: cn.itcast.elec.domain.ElecText.textName
	at org.hibernate.engine.Nullability.checkNullability(Nullability.java:101)
	at org.hibernate.event.def.DefaultDeleteEventListener.deleteEntity(DefaultDeleteEventListener.java:272)
	at org.hibernate.event.def.DefaultDeleteEventListener.onDelete(DefaultDeleteEventListener.java:163)
	at org.hibernate.event.def.DefaultDeleteEventListener.onDelete(DefaultDeleteEventListener.java:74)
	at org.hibernate.impl.SessionImpl.fireDelete(SessionImpl.java:948)
	at org.hibernate.impl.SessionImpl.delete(SessionImpl.java:926)
	at org.springframework.orm.hibernate3.HibernateTemplate$27.doInHibernate(HibernateTemplate.java:878)
	at org.springframework.orm.hibernate3.HibernateTemplate.doExecute(HibernateTemplate.java:419)
	... 27 more



	 */
}
