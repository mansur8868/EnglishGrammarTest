package vn.edu.uit.a15520275.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.List;

import vn.edu.uit.a15520275.englishgrammartest.R;
import vn.edu.uit.a15520275.model.Test;
import vn.edu.uit.a15520275.model.TestContent;
import vn.edu.uit.a15520275.model.Topic;

/**
 * Created by admin on 10/30/2017.
 */

public class Topic_Adapter extends BaseExpandableListAdapter  {

    private Activity context;
    private List<Topic> listGroup;
    private HashMap<Topic,List<Test>> hmTopic;

    public Topic_Adapter(Activity context, List<Topic> listGroup, HashMap<Topic, List<Test>> hmTopic) {
        this.context = context;
        this.listGroup = listGroup;
        this.hmTopic = hmTopic;
    }

    //return the number of Group
    @Override
    public int getGroupCount() {
        return listGroup.size()-1;
    }

    //return number of child tương ứng với GroupPosition
    @Override
    public int getChildrenCount(int groupPosition) {
        return hmTopic.get(listGroup.get(groupPosition)).size();
    }

    //trả về object của hear Group. có nghĩa là trả về phần tử tại groupPosition trong danh sách hear Group
    @Override
    public Object getGroup(int groupPosition) {
        return listGroup.get(groupPosition);
    }

    //trả về object child trong Group có vị trí là groupPosition và child có vị trí là childPosition
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return hmTopic.get(listGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //bản chất giống getView trong ListView. Nhưng getGroupView sẽ trả về View hiển thị Group Header
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(this.context);
            view = inflater.inflate(R.layout.item_group_lvtopic,viewGroup,false);
        }

        TextView  txtStt  = (TextView) view.findViewById(R.id.txtStt);
        TextView txtTopic = (TextView) view.findViewById(R.id.txtTopic);

        txtStt.setText(String.valueOf(groupPosition + 1));
        txtTopic.setText(listGroup.get(groupPosition).getTopic());
        return view;
    }

    //Bản chất cũng giống như getView. Nhưng ở đây getChildView trả về View để hiển thị view trong Header View of Group
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
       if(view == null){
           LayoutInflater inflater = LayoutInflater.from(this.context);
           view = inflater.inflate(R.layout.item_child_group_lvtopic,viewGroup,false);
       }
        ImageView imgIsCompleted = (ImageView) view.findViewById(R.id.imgIsCompleted);
        TextView  txtTestTopic = (TextView) view.findViewById(R.id.txtTestTopic);


        txtTestTopic.setText(((Test) getChild(groupPosition, childPosition)).getTest());
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true; //dùng cho setOnChildClickListner()
    }
}
