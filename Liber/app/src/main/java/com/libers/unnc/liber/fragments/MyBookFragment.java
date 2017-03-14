package com.libers.unnc.liber.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.libers.unnc.liber.R;
import com.libers.unnc.liber.activities.BookInfoActivity;
import com.libers.unnc.liber.activities.BookInfoAddActivity;
import com.libers.unnc.liber.activities.MainActivity;
import com.libers.unnc.liber.adapters.BookGridAdapter;
import com.libers.unnc.liber.adapters.MyBookAdapter;
import com.libers.unnc.liber.model.bean.Book;
import com.libers.unnc.liber.model.bean.State;
import com.libers.unnc.liber.model.bean.User;
import com.libers.unnc.liber.model.data.DataManager;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the interface
 * to handle interaction events.
 * Use the {@link MyBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyBookFragment extends Fragment implements AdapterView.OnItemClickListener{
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    public MyBookFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment MyBookFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static MyBookFragment newInstance(String param1, String param2) {
//        MyBookFragment fragment = new MyBookFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_my_book, container, false);
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
////    @Override
////    public void onAttach(Context context) {
////        super.onAttach(context);
////        if (context instanceof OnFragmentInteractionListener) {
////            mListener = (OnFragmentInteractionListener) context;
////        } else {
////            throw new RuntimeException(context.toString()
////                    + " must implement OnFragmentInteractionListener");
////        }
////    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

    private GridView gridView; // 网格列表
    private MyBookAdapter myBookAdapter; // 数据适配器
    private int gridPosition = -1; // 选中项的position
    private List<Book> Books = new  ArrayList<Book>();
    static int read_db = 0;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (this.isVisible()) {
            if (isVisibleToUser) {
                fetchData();
                myBookAdapter.notifyDataSetChanged();
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    public static MyBookFragment newInstance() {
        MyBookFragment fragment = new MyBookFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_book, container, false);

        // gridView
        gridView = (GridView) view.findViewById(R.id.gridView);

        // ItemClickListener
        gridView.setOnItemClickListener(this);

        // ContextMenu - 'Delete' Function
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                gridPosition = position;
                Log.i("HB", "onItemLongClick:gridPosition: " + gridPosition);
                return false;
            }
        });
        registerForContextMenu(gridView);

        // EmptyView
        View emptyView = view.findViewById(R.id.empty);
//        ImageView ivIcon = (ImageView) emptyView.findViewById(R.id.iv_icon);
        TextView tvText = (TextView) emptyView.findViewById(R.id.tv_text);

        tvText.setText("Please login");

        gridView.setEmptyView(emptyView);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        myBookAdapter = new MyBookAdapter(getContext());
        fetchData();
        myBookAdapter.notifyDataSetChanged();
        gridView.setAdapter(myBookAdapter);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(getActivity(), BookInfoActivity.class);
//        intent.putExtra("id", (int) myBookAdapter.getItemId(position));
//        startActivity(intent);
        Intent intent = new Intent(getActivity(), BookInfoAddActivity.class);
        intent.putExtra("ISBN",  myBookAdapter.getItemISBN(position));
        getActivity().startActivity(intent);


    }


    public void fetchData() {
        Log.i("HB",  "MyBookFragment.fetchData");
        final List<User> data;

        State check = DataSupport.findLast(State.class);
        Log.i("HB","The state :"+ check.getState()+"");

        if(check != null){
            int currentstate = check.getState();

            if(currentstate != 0) {

                String username = check.getUsername();
                if (read_db == 0) {
                    data = DataSupport.where("username = ?", username).find(User.class);
                    Log.e("HB", "DataSIZE out the loop: " + data.size());
                    int check_bookSize = Books.size();
                    Log.e("HB", "checkbookSIZE: " + check_bookSize);

                    if(check_bookSize <  data.size()){

                        for (int i = 0; i < data.size(); i++) {
                            DataManager.getBookInfoFromISBN(data.get(i).getIsbn13(), new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {


                                    Book book = DataManager.doubanBook2Book(DataManager.jsonObject2DoubanBook(response));
//                                    try {
//                                        Thread.sleep(50);
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }


//                                Toast.makeText(getActivity(), book.getIsbn13(), Toast.LENGTH_SHORT).show();
                                    Books.add(book);
                                    if(Books.size() > data.size()){
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                    }

                                    int bookSize = Books.size();

                                    Log.e("HB", "DataSIZE: " + data.size());
                                    Log.e("HB", "BookSIZE: " + Books.size());
                                    if(bookSize == data.size()) {
                                        myBookAdapter.setData(Books);
                                        Log.e("HB", "have setData ");
                                        onStop();
                                    }

                                }


                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(getActivity(), "Book dosen't exist or connect failure", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                        Log.e("HB", "jump out of loop");
                    }


                }
                read_db = 1;


            }

        }



    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("HB",  "GridFragment.onResume");


        read_db = 0;
        Books.clear();

        Log.e("HB", "BookSIZE on resume: " + Books.size());

        Log.i("HB",  "read_db ============ 0");
        fetchData();
        myBookAdapter.notifyDataSetChanged();
    }


}