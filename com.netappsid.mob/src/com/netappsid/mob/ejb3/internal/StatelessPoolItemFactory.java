package com.netappsid.mob.ejb3.internal;

public class StatelessPoolItemFactory<T> implements PoolItemFactory<T>
{

	private final Class<?> clazz;
	private final EJB3BundleUnit ejb3BundleUnit;

	public StatelessPoolItemFactory(Class<?> clazz, EJB3BundleUnit ejb3BundleUnit)
	{
		this.clazz = clazz;
		this.ejb3BundleUnit = ejb3BundleUnit;
	}

	@Override
	public T create() throws Exception
	{
		return (T) ejb3BundleUnit.create(clazz);
	}

	@Override
	public void destroy(T toDestroy) throws Exception
	{
		ejb3BundleUnit.destroy(toDestroy);
	}

}
