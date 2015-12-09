package com.example.SFBinderLib;

import java.util.List;
import com.example.SFBinderLib.Book;
/**
 * Created by xieningtao on 15-12-5.
 */
interface IBookManager {

 List<Book> getBooks();

 void addBook(in Book book);
}
