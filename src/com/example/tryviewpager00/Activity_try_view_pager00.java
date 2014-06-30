package com.example.tryviewpager00;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class Activity_try_view_pager00 extends Activity {

    private ViewPager mViewPager;   // ビューページャー


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // タイトルバーのロード中のくるくる回るやつを表示する
        this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_try_view_pager00);

        // ページャービュー(この中がスライドして変わっていく)
        this.mViewPager = (ViewPager)this.findViewById(R.id.viewpager1);
        this.mViewPager.setAdapter(new FunkyPagerAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_try_view_pager00, menu);
        return true;
    }


}

/**
 * ページャーアダプタ
 * @author a5
 *
 */
class FunkyPagerAdapter extends PagerAdapter {

    private static final int TOTAL_PAGE = 5;
    private LayoutInflater mInflter;    // レイアウトを作るやつ
    private Activity mParentActivity;   // アクティビティ

    /**
     * コンストラクタ
     * @param context コンテキスト
     */
    public FunkyPagerAdapter(final Activity activity) {
        this.mInflter = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mParentActivity = activity;
    }

    /**
     * ページを作るときの呼ばれる
     * @param container この中にビューを作る(レイアウトからひっぱてきたり)
     * @param position インスタンスを作る位置
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LinearLayout layout = (LinearLayout)this.mInflter.inflate(R.layout.page0, null);

        Random random = new Random();
        layout.setBackgroundColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        container.addView(layout);



        WebView wv = (WebView)layout.findViewById(R.id.webView1);
        wv.setWebViewClient(new WebViewClient(){
            /**
             * 別のウィンドウでページを開かない様にする
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mParentActivity.setProgressBarIndeterminateVisibility(true);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mParentActivity.setProgressBarIndeterminateVisibility(false);
                super.onPageFinished(view, url);
            }
        });
        String[] urlList = {
            new String("http://www.google.ne.jp/"),
            new String("http://www.yahoo.co.jp/"),
            new String("http://jp.msn.com/"),
            new String("http://www.4gamer.net/"),
            new String("http://game.watch.impress.co.jp/"),
        };
        wv.loadUrl(urlList[position % urlList.length]);
        Log.i(">>>>instantiateItem", "POS: " + position);


        return layout;
    }

    /**
     * 削除されるタイミングで呼ばれる。
     * 常にメモリにレイアウトをキープせずにそのつど作り出す設計らしい。
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager)container;
        viewPager.removeView((View)object);
        Log.i("<<<<destroyItem", "POS: " + position);
    }


    /*
     * この二つは使わない方がいいみたい？
     * super.destroyItem,super.instantiateItemのとこらへんで「使うな！」ってでるけど。
     * 使わない方がいいみたい？廃止予定？
    @Override
    public void destroyItem(View container, int position, Object object) {
        // TODO 自動生成されたメソッド・スタブ
        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(View container, int position) {
        // TODO 自動生成されたメソッド・スタブ
        return super.instantiateItem(container, position);
    }
    */

    /**
     * ページの枚数を返す
     */
    @Override
    public int getCount() {
        // TODO 自動生成されたメソッド・スタブ
        return TOTAL_PAGE;
    }

    /**
     * ビュー同士の比較
     * 多分内部で呼び出されるんじゃないかな？
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO 自動生成されたメソッド・スタブ
        return view.equals(object);
    }

    /*

    @Override
    public void onPageScrolled(int position, float arg1, int arg2) {
        if (position >= NUM_PAGE-1) {
            mViewPager.setCurrentItem(0, true);
        }
    }
    */
}
