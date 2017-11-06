package vn.edu.uit.a15520275.adapter;


import android.app.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


import vn.edu.uit.a15520275.englishgrammartest.R;
import vn.edu.uit.a15520275.model.Test;


/**
 * Created by admin on 10/31/2017.
 */

public class Test_Adapter extends ArrayAdapter<Test> {
    Activity context;
    int resource;
    List<Test> objects;

    public Test_Adapter(Activity context, int resource, List<Test> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.resource = resource;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(this.resource,null);

        TextView txtTest = (TextView) view.findViewById(R.id.txtTest);

        txtTest.setText(String.valueOf(position + 1));
        if(this.objects.get(position).isCompleted() == 1) {
            txtTest.setBackgroundResource(R.drawable.star_1);
        }

        return view ;
    }
}
