package com.example.edconcierge;

import android.graphics.Bitmap;
import android.widget.ImageView;

//we could transit more paremeters to asytask
public class TaskParameters {
    public int[][] MapMatrix;
    int[] current;
    int[] destination;
    ImageView map;
    Bitmap temp;
    public TaskParameters(int[][] a,int[]b,int[]c,ImageView d,Bitmap e){
        MapMatrix=a;
        current=b;
        destination=c;
        map=d;
        temp=e;
    }

    public int[][] getMapMatrix() {
        return MapMatrix;
    }
}
