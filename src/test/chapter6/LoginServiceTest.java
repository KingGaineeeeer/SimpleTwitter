package test.chapter6;

import static chapter6.utils.DBUtil.commit;
import static chapter6.utils.DBUtil.getConnection;

import java.io.File;
import java.sql.Connection;

import chapter6.beans.User;
import chapter6.dao.UserDao;
import chapter6.utils.CipherUtil;

public class LoginServiceTest {
    // TODO: Junitのライブラリを追加する必要がある
    // TOOD： DBUnit用の期待値データを集めたExcelファイルが必要

    @Test
    public void testlogin() {
        /**
         * ライブラリと実行環境がないので、一旦実装方針をjavadocとして記載しておきます。
         * 
         * そもそも、プログラムのテストは、メソッドレベルのテストから、機能単位のテストまでいろいろとありますが、
         * 一番重要なのがテストデータの準備です。これは事前に処理前に用意しておくデータと、処理後に変化するデータ(つまり期待値データ)の２種類が必要です。
         * ばななさんが使おうとしているDBUnitは有名なjavaの単体テスト用ライブラリですが、これはExcelファイル(csv)に事前/期待値データを作成しておくことで、DBUnitがこれを読み込んでくれて、
         * 処理結果を期待値と比較して突合してくれるものです。 準備が必要なものは下記。 1. 事前データの準備(User検索ならもともと入れておくUserのデータ)
         * 2. 期待値データの準備(Excelで用意。)更新であれば更新後の結果を用意しておきます。 最悪これだけあれば事足ります。
         * 
         * 下にサンプル書いておきます。
         */

        Connection connection = getConnection();
        ;

        // TODO: file指定
        IDataSet dataset = new XlsDataSet(new File("XXXXXXX.xlsx"));
        // 事前データ投入
        databaseTester.setDataSet(dataSet);

        UserDao userDao = new UserDao();
        String accountOrEmail = "XX"; // TODO: 正しいaddress入れる
        String password = "XX"; // TODO: 正しいpw入れる
        String encPassword = CipherUtil.encrypt(password);

        // テスト対象のメソッド
        User user = userDao.getUser(connection, accountOrEmail, encPassword);

        // 予測結果
        ReplacementDataSet replacementDataSet = getReplacementDataSet("expectdata.xml");// TODO: file指定

        // 期待値(期待値エクセルに複数テーブルの期待値をセットしない場合はこの処理不要かも。)
        ITable predictionDataTable = replacementDataSet.getTable("TBL名を指定");

        // 実行結果
        ITable resultDataTable = getConnection().createDataSet().getTable("TBL名を指定");

        // テスト結果
        Assertion.assertEquals(predictionDataTable, resultDataTable);

        commit(connection);

    }

}
