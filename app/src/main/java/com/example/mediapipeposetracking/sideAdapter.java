package com.example.mediapipeposetracking;

import android.util.Log;

import com.google.mediapipe.formats.proto.LandmarkProto;

public class sideAdapter {
    public static void isSide(LandmarkProto.NormalizedLandmarkList[] arr){
        //측면 판단
        int addSize = arr.length;
        double v1[][] = new double[2][2];
        double v2[][] = new double[2][2];
        int count = 0;
        for(int i=0;i<addSize;i++){
            if(arr[i].getLandmark(0).getVisibility() <= 0.7 || arr[i].getLandmark(0).getPresence() <= 0.7
            && arr[i].getLandmark(9).getVisibility() <= 0.7 || arr[i].getLandmark(9).getPresence() <= 0.7
            && arr[i].getLandmark(10).getVisibility() <= 0.7 || arr[i].getLandmark(10).getPresence() <= 0.7){
                count+=1;
                continue;
            }//코, 입 정확도에 따라 무시 혹은 ok
            if(i < addSize/2){
                v1[0][0] += (arr[i].getLandmark(0).getX() - arr[i].getLandmark(10).getX());
                v1[0][1] += (arr[i].getLandmark(0).getY() - arr[i].getLandmark(10).getY());
                v1[1][0] += (arr[i].getLandmark(0).getX() - arr[i].getLandmark(9).getX());
                v1[1][1] += (arr[i].getLandmark(0).getY() - arr[i].getLandmark(9).getY());
            }
            else{
                v2[0][0] += (arr[i].getLandmark(0).getX() - arr[i].getLandmark(10).getX());
                v2[0][1] += (arr[i].getLandmark(0).getY() - arr[i].getLandmark(10).getY());
                v2[1][0] += (arr[i].getLandmark(0).getX() - arr[i].getLandmark(9).getX());
                v2[1][1] += (arr[i].getLandmark(0).getY() - arr[i].getLandmark(9).getY());
            }
        }//0 is nose, 9 and 10 is mouth
        if(count > addSize/2) {
            Log.v("face","no");
            return;
        }
        v1[0][0] /= (addSize/2.0);v1[0][1] /= (addSize/2.0);v1[1][0] /= (addSize/2.0);v1[1][1] /= (addSize/2.0);
        v2[0][0] /= (addSize/2.0);v2[0][1] /= (addSize/2.0);v2[1][0] /= (addSize/2.0);v2[1][1] /= (addSize/2.0);
        //v1 에 (x,y) 벡터값
        double dotProduct = v1[0][0] * v2[1][0] + v1[0][1] * v2[1][1];
        double v1Size = Math.sqrt(Math.pow(v1[0][0],2) + Math.pow(v1[0][1],2));
        double v2Size = Math.sqrt(Math.pow(v2[0][0],2) + Math.pow(v2[0][1],2));
        double angle_rad = Math.acos(dotProduct / (v1Size * v2Size));
        double angle_deg = Math.toDegrees(angle_rad);

        if(angle_deg >= 52){ //이각도 이상 정면 작으면 측면
            humanPose.side = false;
            Log.v("face","front");
        }
        else{
            humanPose.side = true;
            Log.v("face","side");
        }

    }
}
