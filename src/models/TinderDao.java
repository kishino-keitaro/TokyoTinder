package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TinderDao {
	private Connection connection;

	public TinderDao() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tinder_db" + "?user=root&password=admin"
				+ "&useUnicode=true&characterEncoding=MS932");
	}

	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void signUp(String name, String birth, String sex, String comment, String image, String login, String pass)
			throws SQLException {
		// 会員情報登録

		PreparedStatement pstatement = null;

		try {

			// テーブルロック（他者からの読み書き禁止）
			pstatement = connection.prepareStatement("LOCK TABLES profile_tbl WRITE");
			pstatement.executeUpdate();

			// 入力された情報をDBに登録
			pstatement = connection
					.prepareStatement("insert into profile_tbl(name,birth,sex,comment,img,login_id,pass) "
							+ "values(?,str_to_date(" + birth + ", '%Y-%M-%d'),?, ?, ?, ?,?)");
			pstatement.setString(1, name);
			pstatement.setString(2, sex);
			pstatement.setString(3, comment);
			pstatement.setString(4, image);
			pstatement.setString(5, login);
			pstatement.setString(6, pass);
			pstatement.executeUpdate();

		} finally {
			// テーブルアンロック
			pstatement = connection.prepareStatement("UNLOCK TABLES");
			pstatement.executeUpdate();

			pstatement.close();
		}
		return;
	}

	public int login(String loginId, String pass) throws ClassNotFoundException {
		int id = 0;
		PreparedStatement pstatement = null;
		ResultSet rs = null;

		try {
			// SQL文を生成
			pstatement = connection.prepareStatement("select * from profile_tbl where login_id = ? and pass = ?");

			// 生成したSQL文の「？」の部分にIDとパスワードをセット
			pstatement.setString(1, loginId);
			pstatement.setString(2, pass);

			rs = pstatement.executeQuery();

			if (rs.next()) {
				id = rs.getInt("user_id");
			} else {
				id = -1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public ArrayList<MatchUserEntity> getMatchUser(int userId) throws SQLException {

		PreparedStatement pstatement = null;
		ResultSet rs = null;
		ArrayList<MatchUserEntity> entList;

		timeOutMatchUser();

		try {
			pstatement = connection.prepareStatement("select * from match_tbl where s_id = ? or b_id = ? order by latest DESC");
			pstatement.setInt(1, userId);
			pstatement.setInt(2, userId);
			rs = pstatement.executeQuery();

			entList = new ArrayList<MatchUserEntity>();
			while (rs.next()) {
				MatchUserEntity ent = new MatchUserEntity();
				int id = rs.getInt("s_id");
				id = userId == id ? rs.getInt("b_id") : id;
				ent.setUserId(id);
				ent.setEnt(getProfile(id));
				ent.setLatest(rs.getString("l_message"));
				ent.setMessageId(rs.getInt("message_id"));
				entList.add(ent);
			}
			rs.close();
		} finally {
			pstatement.close();
		}
		return entList;
	}

	public void deleteUser(int userId) throws SQLException {

		PreparedStatement pstatement = null;
		try {
			pstatement = connection.prepareStatement("delete from message_tbl where message_tbl.message_id in (select message_id from match_tbl where s_id = ? or b_id = ?)");
			pstatement.setInt(1, userId);
			pstatement.setInt(2, userId);
			pstatement.executeUpdate();

			pstatement = connection.prepareStatement("delete from profile_tbl where user_id");
			pstatement.setInt(1, userId);
			pstatement.executeUpdate();

			pstatement = connection.prepareStatement("delete from match_tbl where s_id = ? or b_id = ?");
			pstatement.setInt(1, userId);
			pstatement.setInt(2, userId);
			pstatement.executeUpdate();

			pstatement = connection.prepareStatement("delete from female_tbl where female_id = ? or male_id = ?");
			pstatement.setInt(1, userId);
			pstatement.setInt(2, userId);
			pstatement.executeUpdate();

			pstatement = connection.prepareStatement("delete from male_tbl where female_id = ? or male_id = ?");
			pstatement.setInt(1, userId);
			pstatement.setInt(2, userId);
			pstatement.executeUpdate();
		} finally {
			pstatement.close();
		}
		return;
	}

	public ProfileEntity getProfile(int userId) throws SQLException {
		ProfileEntity ent = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;

		try {

			pstatement = connection.prepareStatement("select * from profile_tbl where user_id = ?");

			pstatement.setInt(1, userId);
			rs = pstatement.executeQuery();

			if (rs.next()) {
				ent = new ProfileEntity();
				ent.setUserId(rs.getInt("user_id"));
				ent.setName(rs.getString("name"));
				ent.setComment(rs.getString("comment"));
				ent.setAge(rs.getString("birth"));
				ent.setImage(rs.getString("img"));
				ent.setSex(rs.getString("sex").charAt(0));
				ent.setLoginId(rs.getString("login_id"));
				ent.setPass(rs.getString("pass"));
			}
			rs.close();
		} finally {
			pstatement.close();
		}

		return ent;
	}

	public void setProfile(int userId, String name, String birth, String comment, String image, String login,
			String pass) throws SQLException {
		// 会員情報登録修正

		PreparedStatement pstatement = null;

		try {

			// テーブルロック（他者からの読み書き禁止）
			pstatement = connection.prepareStatement("LOCK TABLES profile_tbl WRITE");
			pstatement.executeUpdate();

			// 入力された情報をDBに登録
			pstatement = connection.prepareStatement(
					"update profile_tbl set name=?,birth=?,comment=?,img=?,login_id=?,pass=? where user_id = ?");
			pstatement.setString(1, name);
			pstatement.setString(2, birth);
			pstatement.setString(3, comment);
			pstatement.setString(4, image);
			pstatement.setString(5, login);
			pstatement.setString(6, pass);
			pstatement.setInt(7, userId);
			pstatement.executeUpdate();

		} finally {
			// テーブルアンロック
			pstatement = connection.prepareStatement("UNLOCK TABLES");
			pstatement.executeUpdate();

			pstatement.close();
		}

		return;
	}

	public ProfileEntity getRandomId(int userId, char sex) throws SQLException {
		ProfileEntity ent = null;
		PreparedStatement pstatement = null;
		ResultSet rs = null;

		try {

			if (sex == '男') {
				pstatement = connection.prepareStatement(
						"select * from profile_tbl left join (select female_id as a_id from male_tbl where male_id = ?) as a on profile_tbl.user_id = a.a_id left join (select female_id as b_id from female_tbl where male_id = ? and good = 0) as b on profile_tbl.user_id = b.b_id where sex = ? and a_id is null and b_id is null order by rand() limit 1");

				pstatement.setString(3, "女");
			} else {
				pstatement = connection.prepareStatement(
						"select * from profile_tbl left join (select male_id as a_id from female_tbl where female_id = ?) as a on profile_tbl.user_id = a.a_id left join (select male_id as b_id from male_tbl where female_id = ? and good = 0) as b on profile_tbl.user_id = b.b_id where sex = ? and a_id is null and b_id is null order by rand() limit 1");

				pstatement.setString(3, "男");
			}

			pstatement.setInt(2, userId);
			pstatement.setInt(1, userId);
			rs = pstatement.executeQuery();

			if (rs.next()) {
				ent = new ProfileEntity();
				ent.setUserId(rs.getInt("user_id"));
				ent.setName(rs.getString("name"));
				ent.setComment(rs.getString("comment"));
				ent.setAge(rs.getString("birth"));
				ent.setImage(rs.getString("img"));
				ent.setSex(rs.getString("sex").charAt(0));
			}
			rs.close();
		} finally {
			pstatement.close();
		}

		return ent;
	}

	public boolean setMatchUser(int a, int b) throws SQLException {
		// IDをふたつ渡してマッチテーブルに追加
		PreparedStatement pstatement = null;

		int _a = a < b ? a : b;
		int _b = a > b ? a : b;

		if (getMessageId(_a, _b) != -1)
			return false;


		try {
			// テーブルロック（他者からの読み書き禁止）
			pstatement = connection.prepareStatement("LOCK TABLES match_tbl WRITE");
			pstatement.executeUpdate();

			// 入力された情報をDBに登録
			pstatement = connection.prepareStatement("insert into match_tbl (s_id,b_id) value(?,?)");
			pstatement.setInt(1, _a);
			pstatement.setInt(2, _b);
			pstatement.executeUpdate();

		} finally {
			// テーブルアンロック
			pstatement = connection.prepareStatement("UNLOCK TABLES");
			pstatement.executeUpdate();

			sendMessage("新しいマッチです！", getMessageId(_a, _b),0);

			pstatement.close();
		}

		return true;
	}

	public void deleteMatchUser(int messageId) throws SQLException {
		// マッチを解除（同時にメッセージ履歴を削除）

		PreparedStatement pstatement = null;

		try {

			// テーブルロック（他者からの読み書き禁止）
			pstatement = connection.prepareStatement("LOCK TABLES match_tbl WRITE");
			pstatement.executeUpdate();

			// マッチテーブルから削除
			pstatement = connection.prepareStatement("delete from match_tbl where message_id = " + messageId);
			pstatement.executeUpdate();

			// テーブルロック（他者からの読み書き禁止）
			pstatement = connection.prepareStatement("LOCK TABLES message_tbl WRITE");
			pstatement.executeUpdate();

			// メッセージテーブルから削除
			pstatement = connection.prepareStatement("delete from message_tbl where message_id = " + messageId);
			pstatement.executeUpdate();

		} finally {
			// テーブルアンロック
			pstatement = connection.prepareStatement("UNLOCK TABLES");
			pstatement.executeUpdate();

			pstatement.close();
		}

		return;
	}

	public boolean judge(int selfId, int targetId, boolean good, char sex) throws SQLException {
		// イイネかヨクナイネの評価をテーブルに追加し、マッチしていればSetMatchUser()を召喚
		boolean match = false;
		PreparedStatement pstatement = null;
		ResultSet rs = null;
		String sql_a;
		String sql_b;

		if (sex == '男') {
			sql_a = "insert into male_tbl(male_id,female_id,good) " + "values(?, ?, ?)";
			sql_b = "select * from female_tbl where female_id = ? and male_id = ? and good =1";
		} else {
			sql_a = "insert into female_tbl(female_id,male_id,good) " + "values(?, ?, ?)";
			sql_b = "select * from male_tbl where male_id = ? and female_id = ? and good =1";
		}
		try {

			// テーブルロック（他者からの読み書き禁止）
			// pstatement = connection.prepareStatement("LOCK TABLES female_tbl WRITE");
			// pstatement.executeUpdate();
			// pstatement = connection.prepareStatement("LOCK TABLES male_tbl WRITE");
			// pstatement.executeUpdate();

			// 入力された情報をDBに登録
			pstatement = connection.prepareStatement(sql_a);
			pstatement.setInt(1, selfId);
			pstatement.setInt(2, targetId);
			pstatement.setBoolean(3, good);
			pstatement.executeUpdate();

			pstatement = connection.prepareStatement(sql_b);
			pstatement.setInt(1, targetId);
			pstatement.setInt(2, selfId);
			rs = pstatement.executeQuery();

			if (rs.next() && good) {
				match = setMatchUser(selfId, targetId);
			}

		} finally {
			// テーブルアンロック
			// pstatement = connection.prepareStatement("UNLOCK TABLES");
			// pstatement.executeUpdate();
			pstatement.close();
		}

		return match;
	}

	public int getMessageId(int a, int b) throws SQLException {

		int id;
		Statement statement = null;
		ResultSet rs = null;

		int _a = a < b ? a : b;
		int _b = a > b ? a : b;

		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("select * from match_tbl where s_id = " + _a + " and b_id = " + _b);
			if (rs.next()) {
				id = rs.getInt("message_id");
			} else {
				id = -1;
			}
			rs.close();

		} finally {
			statement.close();
		}
		return id;
	}

	public String getMatchTime(int message_id) throws SQLException {

		String time = "";
		Statement statement = null;
		ResultSet rs = null;

		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("select * from message_tbl where message_id = " + message_id + " and speaker = 0");
			if (rs.next()) {
				Date date = rs.getDate("time");
				time = date.toString().replaceAll("-", "/");
			}
			rs.close();

		} finally {
			statement.close();
		}
		return time;
	}

	public ArrayList<MessageEntity> getMessageData(int messageId) throws SQLException {

		PreparedStatement pstatement = null;
		MessageEntity ent = null;
		ResultSet rs = null;
		ArrayList<MessageEntity> entList;
		try {
			pstatement = connection.prepareStatement("select * from message_tbl where message_id = ?");
			pstatement.setInt(1, messageId);

			rs = pstatement.executeQuery();

			entList = new ArrayList<MessageEntity>();
			while (rs.next()) {
				ent = new MessageEntity();
				ent.setMessageId(rs.getInt("message_id"));
				ent.setSpeaker(rs.getInt("speaker"));
				ent.setMessage(rs.getString("message"));
				ent.setTime(rs.getString("time"));
				entList.add(ent);
			}
			rs.close();
		} finally {
			pstatement.close();
		}
		return entList;
	}

	public void sendMessage(String message, int messageId, int userId) throws SQLException {

		PreparedStatement pstatement = null;

		try {

			// テーブルロック（他者からの読み書き禁止）
			pstatement = connection.prepareStatement("LOCK TABLES message_tbl WRITE");
			pstatement.executeUpdate();


			// 入力された情報をDBに登録
			pstatement = connection.prepareStatement(
					"insert into message_tbl(message_id,speaker,message,time) " + "values(?, ?, ?,now())");
			pstatement.setInt(1, messageId);
			pstatement.setInt(2, userId);
			pstatement.setString(3, message);
			pstatement.executeUpdate();

			pstatement = connection.prepareStatement("LOCK TABLES match_tbl WRITE");
			pstatement.executeUpdate();

			pstatement = connection.prepareStatement(
					"update match_tbl set latest = now(), l_message = ? where message_id = ?");
			pstatement.setString(1, message);
			pstatement.setInt(2, messageId);
			pstatement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			// テーブルアンロック
			pstatement = connection.prepareStatement("UNLOCK TABLES");
			pstatement.executeUpdate();
			pstatement.close();
		}

		return;
	}



	public void timeOutMatchUser() throws SQLException {
		// マッチを解除（同時にメッセージ履歴を削除）

		PreparedStatement pstatement = null;
		ResultSet rs = null;
		try {
			// テーブルから削除
			pstatement = connection.prepareStatement("select message_id from match_tbl where not latest BETWEEN (CURDATE() - INTERVAL 14 DAY) AND (CURDATE() + INTERVAL 1 DAY)");
			rs = pstatement.executeQuery();

			while(rs.next()) {
				deleteMatchUser(rs.getInt("message_id"));
			}
			rs.close();

		} finally {
			pstatement.close();
		}

		return;
	}

}