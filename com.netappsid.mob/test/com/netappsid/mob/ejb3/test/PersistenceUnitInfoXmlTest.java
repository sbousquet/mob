package com.netappsid.mob.ejb3.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.naming.Context;

import org.dom4j.DocumentException;
import org.junit.Test;

import com.netappsid.mob.ejb3.xml.PersistenceUnitInfoXml;
import com.netappsid.mob.ejb3.xml.PersistenceUnitUtils;


public class PersistenceUnitInfoXmlTest
{

	@Test
	public void parsingPersistenceXMl() throws DocumentException
	{
		PersistenceUnitInfoXml persistenceUnitInfoXml = new PersistenceUnitInfoXml(mock(Context.class),mock(PersistenceUnitUtils.class));
		persistenceUnitInfoXml.fromInputStream(PersistenceUnitInfoXmlTest.class.getResourceAsStream("persistence.xml"));
		
		assertEquals("victor",persistenceUnitInfoXml.getPersistenceUnitName());
		assertEquals("org.hibernate.ejb.HibernatePersistence",persistenceUnitInfoXml.getPersistenceProviderClassName());
	}
	
}
