package com.libers.unnc.liber.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import android.view.*;

import android.widget.AdapterView;
import org.litepal.crud.DataSupport;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.litepal.crud.DataSupport;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

import com.libers.unnc.liber.R;
import com.libers.unnc.liber.adapters.BookRecyclerAdapter;
import com.libers.unnc.liber.adapters.GuideListAdapter;
import com.libers.unnc.liber.adapters.MapListAdapter;
import com.libers.unnc.liber.app.MyApplication;
import com.libers.unnc.liber.model.bean.Book;
import com.libers.unnc.liber.algorithms.Astar;

import static com.libers.unnc.liber.app.MyApplication.getContext;


public class ShortestPathActivity extends BaseActivity implements GuideListAdapter.OnClickInAdapter{

    private Context context;
    private static GridView gridView;

   public  static String infor = "";

    static List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();

    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private GuideListAdapter adapter;

    public static SimpleAdapter simpleAdapter;

    static private int path[] = new int[255];
    static int count = 0;

    List <Book> guideBooks = new ArrayList<Book>();
    private int res[] = new int[] {R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor, R.drawable.floor,
    R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor, R.drawable.floor,
    R.drawable.floor,R.drawable.floor,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.floor,R.drawable.floor,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.bookshelf, R.drawable.floor,R.drawable.floor, R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.floor,R.drawable.floor,
    R.drawable.floor,R.drawable.floor,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.floor,R.drawable.floor,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.bookshelf, R.drawable.floor,R.drawable.floor, R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.floor,R.drawable.floor,
    R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor, R.drawable.floor,
    R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor, R.drawable.floor,
    R.drawable.floor,R.drawable.floor,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.floor,R.drawable.floor,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.bookshelf, R.drawable.floor,R.drawable.floor, R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.floor,R.drawable.floor,
    R.drawable.floor,R.drawable.floor,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.floor,R.drawable.floor,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.bookshelf, R.drawable.floor,R.drawable.floor, R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.floor,R.drawable.floor,
    R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor, R.drawable.floor,
    R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor, R.drawable.floor,
    R.drawable.floor,R.drawable.floor,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.floor,R.drawable.floor,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.bookshelf, R.drawable.floor,R.drawable.floor, R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.floor,R.drawable.floor,
    R.drawable.floor,R.drawable.floor,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.floor,R.drawable.floor,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.bookshelf, R.drawable.floor,R.drawable.floor, R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.bookshelf,R.drawable.floor,R.drawable.floor,
    R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor, R.drawable.floor,
    R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor,R.drawable.floor, R.drawable.floor, R.drawable.floor,};

    public static void getPath(Astar.Node stNode, Astar.Node edNode) {
        final int[][] NODES = {
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0 },
            { 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0 },
            { 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0 },
            { 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        };
        Astar.Node startNode = stNode;
        Astar.Node endNode = edNode;
        Astar.Node parent = new Astar().findPath(startNode, endNode);
        ArrayList<Astar.Node> arrayList = new ArrayList<Astar.Node>();

        while (parent != null) {
            arrayList.add(new Astar.Node(parent.x, parent.y));
            parent = parent.parent;
        }
        System.out.println("\n");

        for (int i = 0; i < NODES.length; i++) {
            for (int j = 0; j < NODES[0].length; j++) {
                if (new Astar().exists(arrayList, i, j)) {
                    path[count] = 17*i+j;
                    System.out.println(path[count]);
                    count = count+1;
                } else {
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        Bundle extras = getIntent().getExtras();
//        if(extras == null){
//            Toast.makeText(ShortestPathActivity.this, "Empty", Toast.LENGTH_SHORT).show();
//        }else{
//            String isbn1 = extras.getString("id");
//            Toast.makeText(ShortestPathActivity.this, isbn1, Toast.LENGTH_SHORT).show();
//        }



        ArrayList<Astar.Node> openList = new ArrayList<Astar.Node>();
        ArrayList<Astar.Node> closeList = new ArrayList<Astar.Node>();
        guideBooks = DataSupport.findAll(Book.class);
        int size = guideBooks.size();
        for(int i = 0; i < size; i++){
            String isbn = guideBooks.get(i).getIsbn13();
            String bookshlf = isbn.substring(11,12);
            String level = isbn.substring(12, 13);
            int bs = Integer.parseInt(bookshlf);
            int front_end = Integer.parseInt(level) % 2;
            int quotitent = bs/3 - 1;
            int remainder = Math.round(bs%4);
            int y, x = 0;
            //if the lev % 2 is even, it located front;
                if (remainder == 1) {
                    y = 3;
                    if(quotitent == 0){
                        if(front_end == 0){
                            x = 12;
                        }else{
                            x = 9;
                        }
                    }
                } else if (remainder == 2) {
                    y = 8;
                    if(quotitent == 1){
                         if(front_end == 0){
                             x = 8;
                         } else{
                             x = 5;
                         }
                    }
                } else {
                    y = 13;
                    if(quotitent == 2){
                        if(front_end == 0){
                            x = 4;
                        } else{
                            x = 1;
                        }
                    }
                }

            Astar.Node node = new Astar.Node(x, y);
            openList.add(i, node);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortest_path);

        context = this;
        gridView = (GridView) findViewById(R.id.mapgridview);



        recyclerView = (RecyclerView) findViewById(R.id.guidelist_recycler);
        manager = new LinearLayoutManager(this);
        adapter = new GuideListAdapter(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        List<Book> books = DataSupport.findAll(Book.class);

        adapter.setData(books);
        adapter.notifyDataSetChanged();

        int numberOfNode = 3;
        Astar.Node Door = new Astar.Node(13,8);
        Astar.Node NodeA = new Astar.Node(9,3);
        Astar.Node NodeB = new Astar.Node(1,8);
        Astar.Node NodeC = new Astar.Node(5,13);




        openList.add(0, NodeA);
        openList.add(1, NodeB);
        openList.add(2, NodeC);

        closeList.add(0, Door);
        closeList.add(1, NodeA);
        closeList.add(2, NodeB);
        closeList.add(3, NodeC);
        closeList.add(numberOfNode+1, Door);

        int[] distanceList = new int[numberOfNode+1];
        for(int c = 0; c<numberOfNode+1; c++)
        {
            for (int i = 1; i < openList.size(); i++)
            {
                for (int j = i + 1; j < openList.size(); j++)
                {
                    if (Astar.calD(closeList.get(i),closeList.get(i+1)) > Astar.calD(closeList.get(j),closeList.get(j+1)))
                    {
                        Astar.Node tempNode = new Astar.Node(0,0);
                        tempNode = closeList.get(j+1);
                        closeList.add(j+1, closeList.get(i+1));
                        closeList.add(i+1, tempNode);
                    }
                }
            }
            closeList.add(numberOfNode+1, Door);
            System.out.println(c);
            getPath(closeList.get(c),closeList.get(c+1));
        }


        for(int i=0;i<res.length;i++){
            int a = 0;
            int b = 0;
            Map<String,Object> map = new HashMap<String, Object>();
            for(int j=0; j<count; j++){
                if (i == path[j])
                {
                    a++;
                }
                else if ((i == (17*NodeA.x+ NodeA.y)) || (i == (17*NodeB.x+ NodeB.y))|| (i == (17*NodeC.x+ NodeC.y)))
                {
                    b++;
                }
            }
            if (a==0 && b==0)
            {
                map.put("imageView",res[i]);
                data.add(map);
            }
            else if (a != 0 && b == 0)
            {
                map.put("imageView", R.drawable.footprint);
                data.add(map);
            }
            else
            {
                map.put("imageView", R.drawable.book);
                data.add(map);
            }
        }


        simpleAdapter = new SimpleAdapter(this, data,R.layout.item_grid,
                new String[]{"imageView"}, new int[]{R.id.grid_item_image});

        gridView.setAdapter(simpleAdapter);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setTitle("Shortest path");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onClickInAdapter(String content) {
        // you can fill the editText here
        Toast.makeText(ShortestPathActivity.this, "FROM SHORTEST", Toast.LENGTH_SHORT).show();
    }

    public static void mapChanged(){
       String bookShelf= infor.substring(12, 13);

        int bs = Integer.parseInt(bookShelf);


        Map<String,Object> test = new HashMap<String, Object>();
        test.put("imageView", R.drawable.door);
        data.set(8, test);

        simpleAdapter.notifyDataSetChanged();

        //Toast.makeText(getContext(), infor, Toast.LENGTH_SHORT).show();


    }


}
