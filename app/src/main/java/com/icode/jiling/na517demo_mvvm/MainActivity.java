package com.icode.jiling.na517demo_mvvm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.testproto.bean.News;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<News.New> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 20; i++) {
            News.New aNew = createNew("I am "+i);
            newsList.add(aNew);
        }

        for (News.New news: newsList) {
            if(news.getName().contains("7")) {
                Toast.makeText(this, ""+news.getName(), Toast.LENGTH_SHORT).show();
                // 转换为Byte
                byte[] products = news.toByteArray();
                // 反解
                try
                {
                    News.New productParse = News.New.parseFrom(products);
                    String s = productParse.getTitleBytes().toStringUtf8();
                    String name = productParse.getNameBytes().toStringUtf8();


                   /* ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    ObjectOutputStream outputStream1 = new ObjectOutputStream(outputStream);
                    outputStream1.writeObject(createNew("I am kloo"));*/
                    Toast.makeText(this, name+""+s, Toast.LENGTH_SHORT).show();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

    }
    static News.New createNew(String s) {
        News.New.Builder builder = News.New.newBuilder();
        int id = 31415926;
        builder.setId(id);
        builder.setName(s);
        builder.setTitle("不好吃扩大出口v");
        builder.addImgUrls("test1");
        builder.addImgUrls("test2");
        builder.addImgUrls("test3");
        builder.addImgUrls("test4");
        builder.addImgUrls("test5");

        return builder.build();
    }

}
