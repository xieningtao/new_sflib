package com.sf.SFSample.ui;

import android.view.View;
import android.widget.AdapterView;

import com.basesmartframe.baseadapter.BaseAdapterHelper;
import com.basesmartframe.basecircle.BaseCircleFragment;
import com.basesmartframe.basehttp.SFHttpClient;
import com.basesmartframe.basepull.PullHttpResult;
import com.basesmartframe.basethread.ThreadHelp;
import com.sf.SFSample.R;
import com.sf.httpclient.core.AjaxParams;

import java.util.ArrayList;
import java.util.List;

public class MyCircleFragment extends
        BaseCircleFragment<Student, String, String> {

    private String url[] = {
            "http://g.hiphotos.baidu.com/image/w%3D310/sign=40484034b71c8701d6b6b4e7177e9e6e/21a4462309f79052f619b9ee08f3d7ca7acbd5d8.jpg",
            "http://a.hiphotos.baidu.com/image/w%3D310/sign=b0fccc9b8518367aad8979dc1e728b68/3c6d55fbb2fb43166d8f7bc823a4462308f7d3eb.jpg",
            "http://d.hiphotos.baidu.com/image/w%3D310/sign=af0348abeff81a4c2632eac8e72b6029/caef76094b36acaf8ded6c2378d98d1000e99ce4.jpg"};

    private String comment[] = {"one", "two", "three"};

    @Override
    protected boolean onRefresh() {
        ThreadHelp.runInMain(new Runnable() {

            @Override
            public void run() {
                SFHttpClient.get("http://news.baidu.com/",
                        new PullStudentsResult(Students.class));
            }
        }, 100);
        return false;
    }

    @Override
    protected boolean onLoadMore() {
        return false;
    }

    class PullStudentsResult extends PullHttpResult<Students> {

        public PullStudentsResult(Class<Students> classType) {
            super(classType);
        }

        @Override
        protected void onPullResult(Students t,
                                    AjaxParams params) {
            List<Student> students = new ArrayList<Student>();
            for (int i = 0; i < 10; i++) {
                students.add(new Student("students" + i, 100 * i));

            }
            finishRefreshOrLoading(students, false);
        }

    }

    class Students {
        public List<Student> students;
    }

    @Override
    protected void bindMainView(BaseAdapterHelper help, int GroupPosition,
                                Student bean) {
        help.setImageResource(R.id.photo, R.drawable.ic_launcher);
        help.setText(R.id.title_tv, "title");
    }

    @Override
    protected int[] getMainViewLayoutIds() {
        return new int[]{R.layout.circle_layout};
    }

    @Override
    protected int getNineGridViewCount(int groupPos, Student student) {
        return groupPos % 6 + 1;
    }

    @Override
    protected int getCommentListViewCount(int groupPos, Student student) {
        return 3;
    }

    @Override
    protected int getNineGrideViewId() {
        return R.id.nine_gv;
    }

    @Override
    protected int getCommentListViewId() {
        return R.id.comment_lv;
    }

    @Override
    protected int[] getNineGridViewLayoutIds() {
        return new int[]{R.layout.image_test};
    }

    @Override
    protected int[] getCommentListviewLayoutIds() {
        return new int[]{android.R.layout.test_list_item};
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

    }

    @Override
    protected void bindNineGridView(BaseAdapterHelper help, int GroupPosition,
                                    int childPosition, String bean) {
        help.setImageBuilder(R.id.iv, url[GroupPosition % 3]);
    }

    @Override
    protected void bindCommentListView(BaseAdapterHelper help,
                                       int GroupPosition, int childPosition, String bean) {
        help.setText(android.R.id.text1, comment[GroupPosition % 3]);
    }

    @Override
    protected String getNineGrideItem(int childPos, Student bean) {
        return null;
    }

    @Override
    protected String getCommentListItem(int childPos, Student bean) {
        return null;
    }

}
