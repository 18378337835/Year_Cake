package com.hjn.year_cake.ui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.hjn.year_cake.R;
public class SelectListDialog extends Dialog {

    private TextView mTitle;
    private ListView mList;

    private String titleStr;
    private String[] array;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private boolean cancleable;
    private OnItemClickListener listener;
    private OnCancleClick onCancleClick;

    public void setOnCancleClick(OnCancleClick onCancleClick) {
        this.onCancleClick = onCancleClick;
    }

    public SelectListDialog(Activity mContext) {
        super(mContext);
    }

    public SelectListDialog(Activity mContext, String titleStr, String[] array, boolean cancleable) {
        super(mContext, R.style.LoadingDialog);
        this.titleStr = titleStr;
        this.array = array;
        this.inflater = LayoutInflater.from(mContext);
        this.cancleable = cancleable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_select_list);

        mTitle = (TextView) findViewById(R.id.mTitle);
        mList = (ListView) findViewById(R.id.mList);
        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if(null != onCancleClick){
                    onCancleClick.cancleClick();
                }
            }
        });

        this.setCanceledOnTouchOutside(cancleable);

        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.turn_up);
        mTitle.setText(titleStr);
        mList.setAdapter(new MyAdapter());
        mList.setOnItemClickListener(listener);
    }

    public void setListItemOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return array.length;
        }

        @Override
        public Object getItem(int position) {
            return array[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.cell_group, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.txt.setText(array[position]);

            return convertView;
        }

    }

    class ViewHolder {

        public ViewHolder(View v) {
            txt = (TextView) v.findViewById(R.id.mText);
        }

        TextView txt;
    }

    public interface OnCancleClick{
        void cancleClick();
    }


}
