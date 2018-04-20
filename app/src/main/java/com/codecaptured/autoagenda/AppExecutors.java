package com.codecaptured.autoagenda;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Global executor pools for the whole application.
 * <p>
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
public class AppExecutors {

	private final Executor mDiskIO;

	private final Executor mMainThread;

	private AppExecutors(Executor diskIO, Executor mainThread) {
		this.mDiskIO = diskIO;
		this.mMainThread = mainThread;
	}

	public AppExecutors() {
		this(Executors.newSingleThreadExecutor(), new MainThreadExecutor());
	}

	public Executor diskIO() {
		return mDiskIO;
	}

	public Executor mainThread() {
		return mMainThread;
	}

	private static class MainThreadExecutor implements Executor {
		private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

		@Override
		public void execute(@NonNull Runnable command) {
			mainThreadHandler.post(command);
		}
	}
}