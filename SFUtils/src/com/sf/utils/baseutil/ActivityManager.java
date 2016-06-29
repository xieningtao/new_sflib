package com.sf.utils.baseutil;

import java.util.Stack;

import android.app.Activity;

/**
 * 这个还没有弄好，暂时改为包权限
 * 
 * @author xieningtao
 *
 * @param <T>
 */
public class ActivityManager<T extends Activity> {

	private Stack<T> stacks = new Stack<T>();

	private static ActivityManager<Activity> manager = new ActivityManager<Activity>();

	private ActivityManager() {

	}

	public static ActivityManager<Activity> getInstance() {
		return manager;
	}

	public void popAllExceptFirstOne() {
		if (!stacks.isEmpty())
			stacks.pop();
		while (!stacks.isEmpty()) {
			Activity activity = stacks.peek();
			Class topClass = activity.getClass();
			activity.finish();
			stacks.pop();
		}
	}

	public void pop() {
		if (stacks.isEmpty())
			return;
		else {
			Activity activity = stacks.pop();
			activity.finish();
		}
	}

	public void push(T activity) {
		stacks.push(activity);
	}

	public void popAll() {
		while (!stacks.isEmpty()) {
			Activity activity = stacks.peek();
			activity.finish();
			stacks.pop();
		}
	}

	public boolean popTo(Class activityClass) {
		while (!stacks.isEmpty()) {
			Activity activity = stacks.peek();
			Class topClass = activity.getClass();
			if (topClass == activityClass) {
				return true;
			} else {
				activity.finish();
				stacks.pop();
			}
		}
		return false;
	}
}
