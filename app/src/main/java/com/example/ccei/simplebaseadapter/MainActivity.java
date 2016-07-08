package com.example.ccei.simplebaseadapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView taraList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taraList = (ListView) findViewById(R.id.tara_list);

        TARABaseAdapter adapter = new TARABaseAdapter(this); // (getApplicationContext()); or (MainAcivity.this);

        Resources res = getResources();
        adapter.insertItem(new TARAValueObject("소연", res.getDrawable(R.drawable.t_ara_icon_soyeon)));
        adapter.insertItem(new TARAValueObject("지연", res.getDrawable(R.drawable.t_ara_icon_jiyeon)));
        adapter.insertItem(new TARAValueObject("큐리", res.getDrawable(R.drawable.t_ara_icon_qri)));
        adapter.insertItem(new TARAValueObject("보람", res.getDrawable(R.drawable.t_ara_icon_boram)));
        adapter.insertItem(new TARAValueObject("효민", res.getDrawable(R.drawable.t_ara_icon_hyomin)));
        adapter.insertItem(new TARAValueObject("은정", res.getDrawable(R.drawable.t_ara_icon_eunjung)));

        taraList.setAdapter(adapter);

        taraList.setOnItemClickListener(itemListener);
    }
    private AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            /* LinearLayout itemRoot = (LinearLayout)view;
            String memberName = ((TextView)((LinearLayout) view).getChildAt(1)).getText().toString(); */ // -> 두 가지 방법

            TARAValueObject valueObject = (TARAValueObject)parent.getItemAtPosition(position);

            Toast.makeText(MainActivity.this, valueObject.memberName + "를 선택하셨습니다.", Toast.LENGTH_SHORT).show();
        }
    };

    // Adapter (보통 내부 클래스 사용)
    // 가장 기본이 되는 어댑터이고 개발자의 능력에 따라 자료구조를 다양하게 재정의하여 사용할 수 있다.
    // 유연성이 좋은 어댑터이다.
    private class TARABaseAdapter extends BaseAdapter {

        private Context currentContext;    // 만들어지는 위치
        private ArrayList<TARAValueObject> itemList = new ArrayList<TARAValueObject>();

        public TARABaseAdapter() {}
        public TARABaseAdapter(Context context){
            currentContext = context;
            itemList = new ArrayList<TARAValueObject>();
        }
        public void insertItem(TARAValueObject valueObject){
            if(itemList != null){
                itemList.add(valueObject);
            }
        }
        public void insertAllItem(ArrayList<TARAValueObject> items){
            if(itemList != null){
                itemList.addAll(items);
            } else {
                itemList = items;
            }
        }

        // 필수 메소드 4개
        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        /*
           이 메소드는 어댑터뷰에서 그려질 아이템의 수만큼 호출된다.
           또한 화면에서 보여질 때 마다 매번 호출됨을 명심해 주세요
        */

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            TARAValueObject valueObject = (TARAValueObject)getItem(position);
            // convertView인자는 그려질 아이템의 root(보통)값을 의미한다. 아이템에 그려질
            // 위젯이 하나라면 그 위젯의 객체가 될 수 있다.
            // 안드로이드 시스템에 의해 convertView 객체는 계속 재사용도리 수 있음을 명심하세요.
            // 없으면 계속 inflation함, 필수 코드
            if(convertView == null){
                convertView = LayoutInflater.from(currentContext).inflate(R.layout.tara_list_view_item, null);
            }

            // 전체 ActivityMain에서 찾는 것, nullPointException 에러가 남, 가장 많이 하는 실수
            // ImageView memberImage = (ImageView)findViewById(R.id.member_image);

            // 지금은 한 행을 그릴 때 마다 새로운 위젯들을 생성하고 있음을 명심하세요.
            ImageView memberImage = (ImageView)convertView.findViewById(R.id.member_image);
            TextView memberName = (TextView)convertView.findViewById(R.id.member_name);

            memberImage.setImageDrawable(valueObject.memberImage);
            memberName.setText(valueObject.memberName);

            // 한 행이 그려질 root 레이아웃을 리턴해야한다.
            return convertView;
        }
    }
}
