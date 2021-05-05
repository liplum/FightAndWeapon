package net.liplum.lib.math;

import com.sun.javafx.geom.Path2D;
import com.sun.javafx.geom.Shape;
import com.sun.javafx.geom.Vec3d;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;


public class MathTool {
    private static double PI_Divide_180 = Math.PI / 180;
    public static double HalfPI = Math.PI /2;

    public static double toRadian(double degrees) {
        return degrees % 360 * PI_Divide_180;
    }

    public static boolean isAngleLessThan(Vector2D v1, Vector2D v2, double radian) {
        double cosA = v1.cosAngle(v2);
        return cosA > MathHelper.cos((float)radian);
    }

    public static Vector3d toEntityFace(Vector3d v3d){
        return new Vector3d(-v3d.x, v3d.y,v3d.z);
    }

    public static boolean belongToOO(double left,double right,double value){
        if((value==left)&&(value==right)){
            return true;
        }
        double min = Math.min(left,right),max=Math.max(left, right);
        if(value>min && value < max){
            return true;
        }
        return false;
    }
    public static boolean belongToOC(double left,double right,double value){
        if((value==left)&&(value==right)){
            return true;
        }
        double min = Math.min(left,right),max=Math.max(left, right);
        if(value>min && value <= max){
            return true;
        }
        return false;
    }
    public static boolean belongToCO(double left,double right,double value){
        if((value==left)&&(value==right)){
            return true;
        }
        double min = Math.min(left,right),max=Math.max(left, right);
        if(value>=min && value < max){
            return true;
        }
        return false;
    }
    public static boolean belongToCC(double left,double right,double value){
        if((value==left)&&(value==right)){
            return true;
        }
        double min = Math.min(left,right),max=Math.max(left, right);
        if(value>=min && value <= max){
            return true;
        }
        return false;
    }

    public static Vector2D toV2D(Vector3d v3d){
        return new Vector2D(v3d.x,v3d.z);
    }

    /**
     *
     * @param direction Starts with (0,0).No matter how positive and negative.
     * @param width the width of rectangle
     * @return
     */
    public static Shape genRectangleFrom(Vector2D direction,double width){
        double halfw=width/2;
        Vector2D n = direction.perpendicular(),n_n = direction.reverse();
        Point Pd = direction.toPoint(), Pn = n.truncate(halfw).toPoint(),Pn_n= n_n.truncate(halfw).toPoint();
        Point Pn_2=Pn.add(Pd),Pn_n_2=Pn_n.add(Pd);
        Path2D rect = new Path2D();
        rect.moveTo((float)Pn.x,(float)Pn.y);//Starts with Pn
        rect.lineTo((float)Pn_2.x,(float)Pn_2.y);//Pn_2
        rect.lineTo((float)Pn_n_2.x,(float)Pn_n_2.y);//Pn_n_2
        rect.lineTo((float)Pn_n.x,(float)Pn_n.y);//Pn_n
        rect.lineTo((float)Pn.x,(float)Pn.y);//Ends with Pn
        rect.closePath();
        return rect;
    }

    public static boolean isInside(Shape figure,Point p){
        return figure.contains((float) p.x,(float)p.y);
    }
}
