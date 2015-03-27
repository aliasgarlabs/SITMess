package com.aliasgarlabs.sitmess;

import android.content.Context;

/**
 * Created by Aliasgar Murtaza on 20-Mar-15.
 */
    import java.util.List;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.TextView;

    public class MessMenuAdaptar extends ArrayAdapter<MessMenu> {
        private Context mContext;
        private List<MessMenu> mTasks;

        public MessMenuAdaptar(Context context, List<MessMenu> objects) {
            super(context, R.layout.row, objects);
            this.mContext = context;
            this.mTasks = objects;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView == null){
                LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
                convertView = mLayoutInflater.inflate(R.layout.row, null);
            }

            MessMenu messMenu = mTasks.get(position);

            TextView descriptionView = (TextView) convertView.findViewById(R.id.miqaat);





            return convertView;
        }

}
