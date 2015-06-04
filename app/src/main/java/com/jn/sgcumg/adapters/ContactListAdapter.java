package com.jn.sgcumg.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jn.sgcumg.R;
import com.jn.sgcumg.utils.ClienteItemInterface;
import com.jn.sgcumg.utils.ContactItemComparator;
import com.jn.sgcumg.utils.ContactsSectionIndexer;

import java.util.Collections;
import java.util.List;

public class ContactListAdapter extends ArrayAdapter<ClienteItemInterface>{

    private int resource; // store the resource layout id for 1 row
    private boolean inSearchMode = false;

    private ContactsSectionIndexer indexer = null;

    public ContactListAdapter(Context _context, int _resource, List<ClienteItemInterface> _items) {
        super(_context, _resource, _items);
        resource = _resource;

        // need to sort the items array first, then pass it to the indexer
        Collections.sort(_items, new ContactItemComparator());


        setIndexer(new ContactsSectionIndexer(_items));

    }

    // get the section textview from row view
    // the section view will only be shown for the first item
    public TextView getSectionTextView(View rowView){
        TextView sectionTextView = (TextView)rowView.findViewById(R.id.sectionTextView);
        return sectionTextView;
    }

    public void showSectionViewIfFirstItem(View rowView, ClienteItemInterface item, int position){
        TextView sectionTextView = getSectionTextView(rowView);

        // if in search mode then dun show the section header
        if(inSearchMode){
            sectionTextView.setVisibility(View.GONE);
        }
        else
        {
            // if first item then show the header

            if(indexer.isFirstItemInSection(position)){

                String sectionTitle = indexer.getSectionTitle(item.getItemForIndex());
                sectionTextView.setText(sectionTitle);
                sectionTextView.setVisibility(View.VISIBLE);

            }
            else
                sectionTextView.setVisibility(View.GONE);
        }
    }

    // do all the data population for the row here
    // subclass overwrite this to draw more items
    public void populateDataForRow(View parentView, ClienteItemInterface item , int position){
        // default just draw the item only
        View infoView = parentView.findViewById(R.id.infoRowContainer);
        TextView nameView = (TextView)infoView.findViewById(R.id.nickNameView);
        nameView.setText(item.getItemForIndex());
    }

    // this should be override by subclass if necessary
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup parentView;

        ClienteItemInterface item = getItem(position);

        //Log.i("ContactListAdapter", "item: " + item.getItemForIndex());

        if (convertView == null) {
            parentView = new LinearLayout(getContext()); // Assumption: the resource parent id is a linear layout
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, parentView, true);
        } else {
            parentView = (LinearLayout) convertView;
        }

        // for the very first section item, we will draw a section on top
        showSectionViewIfFirstItem(parentView, item, position);

        // set row items here
        populateDataForRow(parentView, item, position);

        return parentView;

    }

    public boolean isInSearchMode() {
        return inSearchMode;
    }

    public void setInSearchMode(boolean inSearchMode) {
        this.inSearchMode = inSearchMode;
    }

    public ContactsSectionIndexer getIndexer() {
        return indexer;
    }

    public void setIndexer(ContactsSectionIndexer indexer) {
        this.indexer = indexer;
    }



}
