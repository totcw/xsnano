package com.betterda.xsnano.shouye.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.shouye.presenter.IShouyeThreePresenter;
import com.betterda.xsnano.util.UtilMethod;

public class MoreAdapter extends BaseAdapter {
	private IShouyeThreePresenter iShouyeThreePresenter;
	private List<String> lists;

	public MoreAdapter(IShouyeThreePresenter iShouyeThreePresenter, List<String> lists) {
		this.iShouyeThreePresenter = iShouyeThreePresenter;
		this.lists = lists;
	}

	public int getCount() {
		if (null != lists) {
			return lists.size();
		} else {
			return  0;
		}
	}

	public Object getItem(int position) {

		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView( int position, View convertview, ViewGroup viewGroup) {
		View view = iShouyeThreePresenter.getMoreView(position, convertview,lists);
		if (null != view) {
			return view;
		} else {

			return null;
		}
	}



}
