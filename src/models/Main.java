package models;

public class Main {

    public static void main(String[] args) {
        // TODO 自動生成されたメソッド・スタブ
        try {
            TinderDao dao = new TinderDao();
            for(int i = 1; i < 500; i++) {
            	if(dao.getProfile(i).getSex()=='女') {
            		dao.judge(i, 2, true, '女');
            	}
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
