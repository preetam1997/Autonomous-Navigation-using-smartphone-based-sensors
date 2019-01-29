package com.example.preetam.tranny;

/**
 * Created by Preetam on 05-05-2018.
 */

public class Pos {
    double interval=0.5;//time interval is 500 milisecond
    int indx=0;

    public void init()
    {
        QueueAccl.Queue[0].velx=0.0;
        QueueAccl.Queue[0].vely=0.0;
        QueueAccl.Queue[0].velz=0.0;
        QueueAccl.Queue[0].x=0.0;
        QueueAccl.Queue[0].y=0.0;
        QueueAccl.Queue[0].z=0.0;
    }


    public void trap_vel()
    {
        double sumx=0.0,sumy=0.0,sumz=0.0;

        sumx=0.5*interval*(QueueAccl.ReadAcclX(indx)+2*QueueAccl.ReadAcclX(indx+1)+2*QueueAccl.ReadAcclX(indx+2)+QueueAccl.ReadAcclX(indx+3));
        sumy=0.5*interval*(QueueAccl.ReadAcclY(indx)+2*QueueAccl.ReadAcclY(indx+1)+2*QueueAccl.ReadAcclY(indx+2)+QueueAccl.ReadAcclY(indx+3));
        sumz=0.5*interval*(QueueAccl.ReadAcclZ(indx)+2*QueueAccl.ReadAcclZ(indx+1)+2*QueueAccl.ReadAcclZ(indx+2)+QueueAccl.ReadAcclZ(indx+3));

        if(QueueAccl.Queue[indx-3].flag==2)
        {
            sumx=sumx-QueueAccl.Queue[indx-3].velx;
            sumy=sumy-QueueAccl.Queue[indx-3].vely;
            sumz=sumz-QueueAccl.Queue[indx-3].velz;

        }
        indx=indx+3;
        QueueAccl.updateVel(indx,sumx,sumy,sumz);

    }

    public void trap_pos()
    {
        double sumx=0.0,sumy=0.0,sumz=0.0;

        sumx=0.5*interval*(QueueAccl.ReadVelX(indx)+2*QueueAccl.ReadVelX(indx-3)+2*QueueAccl.ReadVelX(indx-6)+QueueAccl.ReadVelX(indx-9));
        sumx=0.5*interval*(QueueAccl.ReadVelY(indx)+2*QueueAccl.ReadVelY(indx-3)+2*QueueAccl.ReadVelY(indx-6)+QueueAccl.ReadVelY(indx-9));
        sumx=0.5*interval*(QueueAccl.ReadVelZ(indx)+2*QueueAccl.ReadVelZ(indx-3)+2*QueueAccl.ReadVelZ(indx-6)+QueueAccl.ReadVelZ(indx-9));

        if(QueueAccl.Queue[indx-9].flag==3)
        {
            sumx=sumx-QueueAccl.Queue[indx-9].x;
            sumy=sumy-QueueAccl.Queue[indx-9].y;
            sumz=sumz-QueueAccl.Queue[indx-9].z;

        }

        QueueAccl.updatePos(indx+9,sumx,sumy,sumz);

    }
//for every four runs of trap_vel, trap_pos runs once
//the queue is assumed to be infinite
}
