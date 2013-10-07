package com.sin.android.sinlibs.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SimpleListAdapter extends BaseAdapter {
	private Context context;
	private List<?> list;
	private SimpleViewInitor initor;

	public SimpleListAdapter(Context context, List<?> list, SimpleViewInitor initor) {
		super();
		this.context = context;
		this.list = list;
		this.initor = initor;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return position < getCount() ? list.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return initor.initView(context, position, convertView, parent, getItem(position));
	}
}
