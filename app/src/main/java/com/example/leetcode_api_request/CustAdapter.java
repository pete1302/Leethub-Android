package com.example.leetcode_api_request;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustAdapter extends ArrayAdapter {

    ArrayList<CustPair<String, Integer>> users = new ArrayList<>();

    public CustAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CustPair<String, Integer>> objects) {
        super(context, resource, objects);
        users = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.cust_list_view_2, null);

        TextView tvName = v.findViewById(R.id.tvName);
        TextView tvTime = v.findViewById(R.id.tvTime);
        tvName.setText(users.get(position).getL().toString());
//        tvTime.setText(users.get(position).getR().toString());

        return v;
    }
}
