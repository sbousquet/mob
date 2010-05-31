package com.netappsid.mob.ejb3.test;

import static junit.framework.Assert.*;

import org.dom4j.DocumentException;
import org.junit.Test;

import com.netappsid.mob.ejb3.xml.PersistenceUnitInfoXml;


public class PersistenceUnitInfoXmlTest
{

	@Test
	public void parsingPersistenceXMl() throws DocumentException
	{
		PersistenceUnitInfoXml persistenceUnitInfoXml = new PersistenceUnitInfoXml();
		persistenceUnitInfoXml.fromInputStream(PersistenceUnitInfoXmlTest.class.getResourceAsStream("persistence.xml"));
		
		assertEquals("victor",persistenceUnitInfoXml.getPersistenceUnitName());
		assertEquals("org.hibernate.ejb.HibernatePersistence",persistenceUnitInfoXml.getPersistenceProviderClassName());
	}
	
}
