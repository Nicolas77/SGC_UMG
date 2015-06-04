package com.jn.sgcumg.models;

public class DrawerItem {
	/* Commented tags are expected in future updates.
	 */
	public static final int DRAWER_ITEM_TAG_CLIENTES = 1;
	public static final int DRAWER_ITEM_TAG_CATALOGOS = 2;
	public static final int DRAWER_ITEM_TAG_RECOLECCCION_DATOS = 3;
	//public static final int DRAWER_ITEM_TAG_GIVES = 4;
	//public static final int DRAWER_ITEM_TAG_PREPAID_CARD = 5;
	//public static final int DRAWER_ITEM_TAG_NEW = 6;
	//public static final int DRAWER_ITEM_USUALLY_ORDER = 7;
	//public static final int DRAWER_ITEM_EVENTS = 8;
	
	
	
	public DrawerItem(int icon, int title, int tag) {
		this.icon = icon;
		this.title = title;
		this.tag = tag;
	}

	private int icon;
	private int title;
	private int tag;
	

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getTitle() {
		return title;
	}

	public void setTitle(int title) {
		this.title = title;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}
}