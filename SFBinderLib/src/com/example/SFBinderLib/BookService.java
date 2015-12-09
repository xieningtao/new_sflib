package com.example.SFBinderLib;

import android.app.Fragment;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xieningtao on 15-12-5.
 */
public class BookService extends Service {

    private List<Book> mBooks = new ArrayList<>();

     Fragment mFragment;
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    private IBookManager.Stub binder = new IBookManager.Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            return mBooks;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBooks.add(book);
        }
    };
}
