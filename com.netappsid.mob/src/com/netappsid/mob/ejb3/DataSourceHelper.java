package com.netappsid.mob.ejb3;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author xjodoin
 * 
 */
public class DataSourceHelper
{
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DataSourceHelper.class);
	private final DatasourceProvider datasourceProvider;

	public DataSourceHelper(DatasourceProvider service)
	{
		this.datasourceProvider = service;
	}

	/**
	 * 
	 * * <datasources> <local-tx-datasource> <jndi-name>MSSQLDS</jndi-name> <connection-url>jdbc:jtds:sqlserver://128.0.0.247:1433/victorbom</connection-url>
	 * <driver-class>net.sourceforge.jtds.jdbc.Driver</driver-class> <user-name>sa</user-name> <password>victor</password> </local-tx-datasource> </datasources>
	 * 
	 */
	public void parseXmlDataSourceAndBindIt(Document document, Context context) throws ClassNotFoundException
	{
		final Element dataSource = document.getRootElement().element("local-tx-datasource");
		final String jdbcUrl = dataSource.elementText("connection-url");
		final String user = dataSource.elementText("user-name");
		final String password = dataSource.elementText("password");
		String uniqueRessourceName = dataSource.elementText("jndi-name");
		String driverClassName = dataSource.elementText("driver-class");

		DataSource ds = datasourceProvider.create(uniqueRessourceName, driverClassName, jdbcUrl, user, password);

		rebindInitialContextToDataSource(context, uniqueRessourceName, ds);
	}

	private void rebindInitialContextToDataSource(Context context, String jndiName, DataSource dataSource)
	{
		Context javaContext = null;
		try
		{
			try
			{
				javaContext = (Context) context.lookup("java:");
			}
			catch (NamingException e)
			{
				LOGGER.trace(e.getMessage(), e);

				javaContext = context.createSubcontext("java:");
			}

			javaContext.rebind(jndiName, dataSource);

		}
		catch (NamingException e1)
		{
			LOGGER.error(e1.getMessage(), e1);
		}

	}

}
