package net.liplum.lib.math;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MathTool {
    private static double PI_Divide_180 = Math.PI / 180;
    public static double HalfPI = Math.PI /2;

    public static double toRadian(double degrees) {
        return degrees % 360 * PI_Divide_180;
    }

    public static boolean isAngleLessThan(Vector2D v1, Vector2D v2, double radian) {
        double cosA = v1.cosAngle(v2);
        return cosA > MathHelper.cos(MathHelper.lfloor(radian));
    }

    public static Vec3d toEntityFace(Vec3d v3d){
        return new Vec3d(-v3d.x, v3d.y,v3d.z);
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
}
