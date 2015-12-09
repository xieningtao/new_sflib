package com.example.SFBinderLib;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by xieningtao on 15-12-5.
 */
public class MainActivity extends Activity {

    private IBookManager mIBookManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("Binder");
        setContentView(button);

        Intent intent = new Intent(this, BookService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIBookManager!=null){
                    try {
                        mIBookManager.addBook(new Book("hello",1));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    ArrayList<Book> books= null;
                    try {
                        books = (ArrayList<Book>) mIBookManager.getBooks();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    Log.i("MainActivity","books: "+books);
                }
            }
        });
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIBookManager = IBookManager.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIBookManager = null;
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}