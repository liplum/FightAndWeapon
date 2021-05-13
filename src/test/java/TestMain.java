import net.liplum.lib.math.MathUtil;
import net.liplum.lib.math.Point;
import net.liplum.lib.math.Vector2D;

public class TestMain {
    public static void main(String[] args) {
        Point player = new Point(3, 2),
                enemy = new Point(3, 2);
        Vector2D look = new Vector2D(1,2).normalized();
        System.out.println(MathUtil.isInside(look,player,enemy,1,4));

    }
}
