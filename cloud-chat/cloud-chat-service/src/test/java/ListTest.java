import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * @author join
 * @Description
 * @date 2023/3/26 13:45
 */
public class ListTest {
    public static void main(String[] args) throws Exception{
        String s="\uD83E\uDD32 \uD83D\uDC50 \uD83D\uDE4C \uD83D\uDC4F \uD83E\uDD1D \uD83D\uDC4D \uD83D\uDC4E \uD83D\uDC4A ✊ \uD83E\uDD1B \uD83E\uDD1C \uD83E\uDD1E ✌️ \uD83E\uDD1F \uD83E\uDD18 \uD83D\uDC4C \uD83D\uDC48 \uD83D\uDC49 \uD83D\uDC46 \uD83D\uDC47 ☝️ ✋ \uD83E\uDD1A \uD83D\uDD90 \uD83D\uDD96 * \uD83E\uDD19 \uD83D\uDCAA\uD83D\uDD95 ✍️ \uD83D\uDE4F";
        String[] s1 = s.split(" ");
        String username="root";
        String password="123456";
        String url="jdbc:mysql:// 192.168.133.248/matchChat?characterEncoding=UTF-8&serverTimezone=UTC";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);

        for (String ss:s1) {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into sys_emoji ( emoji, create_time) values (?,?)");
//            preparedStatement.setLong(1,id);
            preparedStatement.setString(1,ss);
            preparedStatement.setLong(2,System.currentTimeMillis());
            boolean execute = preparedStatement.execute();
            if (execute){
                System.out.println("插入一个表情成功！");
            }
        }

        System.out.println("插入表情完毕");



    }
}
