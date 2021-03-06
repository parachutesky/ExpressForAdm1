package com.jnwat.expressforadm.tools;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.Window;
import android.view.WindowManager;

import com.jnwat.expressforadm.systembartint.SystemBarTintManager;


public class ModifySysTitle {
	static Activity mcontext;

	public static void ModifySysTitleStyle(int colorID,Activity context){
		mcontext = context;
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);

		}

		SystemBarTintManager tintManager = new SystemBarTintManager(context);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(colorID);
		
		
	}
	
	

	@TargetApi(19)
	private static void setTranslucentStatus(boolean on) {
		Window win = mcontext.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

}
