public class QueueAccl {
    public class Position {
        double accelx, accely, accelz ;
        double velx , vely , velz ;
        double x, y ,z;
        long time;
        int flag;
    }

    public static Position[] Queue = new Position[100];
    static int i;

    public static void Add(double x, double y, double z, long t) {
        if(i>100)
        {
            i=0;
        }
        Queue[i].accelx = x;
        Queue[i].accely = y;
        Queue[i].accelz = z;
        Queue[i].time = t;
        Queue[i].flag = 1;
        i++;
    }

    public static double ReadAcclX(int j){
        if(Queue[j+1].flag==0)
            j++;
        return Queue[j+1].accelx;
    }

    public static double ReadAcclY(int j){
        if(Queue[j+1].flag==0)
            j++;
        return Queue[j+1].accely;
    }

    public static double ReadAcclZ(int j){
        if(Queue[j+1].flag==0)
            j++;
        return Queue[j+1].velx;
    }

    public static double ReadVelX(int j){
        if(Queue[j+1].flag==0 || Queue[j+1].flag==1)
            j++;
        return Queue[j+1].vely;
    }

    public static double ReadVelY(int j){
        if(Queue[j+1].flag==0 || Queue[j+1].flag==1)
            j++;
        return Queue[j+1].velz;
    }

    public static double ReadVelZ(int j){
        if(Queue[j+1].flag==0 || Queue[j+1].flag==1)
            j++;
        return Queue[j+1].accelz;
    }


    public static void updateVel(int j,double x,double y, double z){
        Queue[j].velx = x;
        Queue[j].vely = y;
        Queue[j].velz = z;
        Queue[j].flag = 2;
    }

    public static void updatePos(int j,double x,double y, double z){
        Queue[j].x = x;
        Queue[j].y = y;
        Queue[j].z = z;
        Queue[j].flag = 3;
    }
}
