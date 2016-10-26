package com.betterda.xsnano.shouye.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.betterda.xsnano.R;
import com.betterda.xsnano.shouye.model.Bean;
import com.betterda.xsnano.shouye.model.Category;
import com.betterda.xsnano.shouye.presenter.IShouyeThreePresenter;

import java.util.List;

public class MainAdapter extends BaseAdapter  {
	private IShouyeThreePresenter iShouyeThreePresenter;
	private List<String> list;

	public MainAdapter(IShouyeThreePresenter iShouyeThreePresenter) {
		this.iShouyeThreePresenter = iShouyeThreePresenter;
		if (iShouyeThreePresenter != null) {
			list = iShouyeThreePresenter.getCategoryList();
		}
	}

	public int getCount() {
		if (null != list) {
			return list.size();
		} else {
			return 0;
		}
	}

	public Object getItem(int position) {

		if (null != list) {
			return list.get(position);
		} else {
			return 0;
		}
	}

	public long getItemId(int position) {

		return position;
	}

	public View getView( int position, View view, ViewGroup viewGroup) {
		View view1 = iShouyeThreePresenter.getMainView(position,view);
		if (null != view1) {
			return view1;
		} else {
			return null;
		}


	}


}
