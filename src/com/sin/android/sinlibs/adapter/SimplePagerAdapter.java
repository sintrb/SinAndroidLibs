package com.sin.android.sinlibs.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class SimplePagerAdapter extends PagerAdapter {
	private List<PagerItem> items = null;

	public SimplePagerAdapter(List<PagerItem> items) {
		this.items = items;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(items.get(position).getView());
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(items.get(position).getView());
		return items.get(position).getView();
	}
}
