package com.jn.sgcumg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jn.sgcumg.R;
import com.jn.sgcumg.models.DrawerItem;

import java.util.List;


public class DrawerAdapter extends BaseAdapter {
	
	private List<DrawerItem> mDrawerItems;
	private LayoutInflater mInflater;
	private boolean mIsFirstType; //Choose between two types of list items
	private int typeList;
	
	public DrawerAdapter(Context context, List<DrawerItem> items, int TypeList) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDrawerItems = items;
		typeList = TypeList;
	}

	@Override
	public int getCount() {
		return mDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mDrawerItems.get(position).getTag();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			if (typeList==1) {
				convertView = mInflater.inflate(R.layout.list_view_item_navigation_drawer_1, parent, false);
			}else if(typeList==2){
				convertView = mInflater.inflate(R.layout.list_view_item_navigation_drawer_2, parent, false);
			}else {
				convertView = mInflater.inflate(R.layout.list_view_item_navigation_drawer_3, parent, false);
			}
			holder = new ViewHolder();
			if(typeList==1){
				holder.icon = (TextView) convertView.findViewById(R.id.icon); // holder.icon object is null if mIsFirstType is set to false
				holder.title = (TextView) convertView.findViewById(R.id.title);
			}else if(typeList==2){				
				holder.title = (TextView) convertView.findViewById(R.id.title);
			}else{
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.imageView_icon = (ImageView)convertView.findViewById(R.id.imageView_icon);
			}
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		DrawerItem item = mDrawerItems.get(position);
		
		/*if (mIsFirstType) {	//We chose to set icon that exists in list_view_item_navigation_drawer_1.xml
			holder.icon.setText(item.getIcon());
		}*/
		holder.title.setText(item.getTitle());
		if(typeList==3){
			holder.imageView_icon.setImageResource(item.getIcon());
		}
		
		return convertView;
	}
	
	private static class ViewHolder {
		public TextView icon;
		public /*Roboto*/TextView title;
		public ImageView imageView_icon;
	}
}
