package com.netappsid.mob.ejb3.osgi;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.netappsid.mob.ejb3.internal.EJB3ThreadWorker;

public class EJB3ExecutorService implements ExecutorService
{
	private ExecutorService executorService;

	public EJB3ExecutorService()
	{
		executorService = Executors.newCachedThreadPool(new ThreadFactory()
			{

				@Override
				public Thread newThread(Runnable r)
				{
					return new EJB3ThreadWorker(r);
				}
			});
	}

	public void execute(Runnable command)
	{
		executorService.execute(command);
	}

	public void shutdown()
	{
		executorService.shutdown();
	}

	public List<Runnable> shutdownNow()
	{
		return executorService.shutdownNow();
	}

	public boolean isShutdown()
	{
		return executorService.isShutdown();
	}

	public boolean isTerminated()
	{
		return executorService.isTerminated();
	}

	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException
	{
		return executorService.awaitTermination(timeout, unit);
	}

	public <T> Future<T> submit(Callable<T> task)
	{
		return executorService.submit(task);
	}

	public <T> Future<T> submit(Runnable task, T result)
	{
		return executorService.submit(task, result);
	}

	public Future<?> submit(Runnable task)
	{
		return executorService.submit(task);
	}

	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException
	{
		return executorService.invokeAll(tasks);
	}

	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException
	{
		return executorService.invokeAll(tasks, timeout, unit);
	}

	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException
	{
		return executorService.invokeAny(tasks);
	}

	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException,
			TimeoutException
	{
		return executorService.invokeAny(tasks, timeout, unit);
	}
}
