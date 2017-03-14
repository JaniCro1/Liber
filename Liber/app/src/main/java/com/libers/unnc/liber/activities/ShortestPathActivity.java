package com.libers.unnc.liber.activities;

import android.os.Bundle;
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
import com.libers.unnc.liber.adapters.MapListAdapter;
import com.libers.unnc.liber.model.bean.Book;
import com.libers.unnc.liber.algorithms.Astar;



public class ShortestPathActivity extends BaseActivity{
    
    private Context context;
    private GridView gridView;
    private ListView listView;
    private MapListAdapter mapListAdapter;
    static private int path[] = new int[255];
    static int count = 0;
    
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortest_path);
        
        context = this;
        gridView = (GridView) findViewById(R.id.mapgridview);
        listView = (ListView) findViewById(R.id.maplistview);
        
        
        /**
         * need to read data from local database, litepal??
         */
        mapListAdapter = new MapListAdapter(context);
        mapListAdapter.setData(DataSupport.order("id desc").find(Book.class));  //get data from localdatabase
        mapListAdapter.notifyDataSetChanged();
        listView.setAdapter(mapListAdapter);
        
        List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
        
        int numberOfNode = 3;
        Astar.Node Door = new Astar.Node(13,8);
        Astar.Node NodeA = new Astar.Node(9,3);
        Astar.Node NodeB = new Astar.Node(1,8);
        Astar.Node NodeC = new Astar.Node(5,13);
        
        ArrayList<Astar.Node> openList = new ArrayList<Astar.Node>();
        ArrayList<Astar.Node> closeList = new ArrayList<Astar.Node>();
        
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
        
        
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, data,R.layout.item_grid,
                                                              new String[]{"imageView"}, new int[]{R.id.grid_item_image});
        
        gridView.setAdapter(simpleAdapter);
        
        /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         simpleAdapter.setSeclection(position);
         simpleAdapter.notifyDataSetChanged();
         }
         });*/
        
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
}
