package com.example.mediapipeposetracking;

import android.util.Log;

import com.google.mediapipe.formats.proto.LandmarkProto;

public class exrciseAdapter {

    public static void isStand(LandmarkProto.NormalizedLandmarkList[] arr, boolean sit){
        // Assuming landmark_coords is a 2D array of coordinates
        //스쿼트 갯수 판단
        int addSize = arr.length;
        double v1[][] = new double[2][2];
        double v2[][] = new double[2][2];
        for(int i=0;i<addSize;i++){

            if(i < addSize/2){
                v1[0][0] += (arr[i].getLandmark(25).getX() - arr[i].getLandmark(23).getX());
                v1[0][1] += (arr[i].getLandmark(25).getY() - arr[i].getLandmark(23).getY());
                v1[1][0] += (arr[i].getLandmark(25).getX() - arr[i].getLandmark(27).getX());
                v1[1][1] += (arr[i].getLandmark(25).getY() - arr[i].getLandmark(27).getY());
            }
            else{
                v2[0][0] += (arr[i].getLandmark(25).getX() - arr[i].getLandmark(23).getX());
                v2[0][1] += (arr[i].getLandmark(25).getY() - arr[i].getLandmark(23).getY());
                v2[1][0] += (arr[i].getLandmark(25).getX() - arr[i].getLandmark(27).getX());
                v2[1][1] += (arr[i].getLandmark(25).getY() - arr[i].getLandmark(27).getY());
            }
        }//23 and 24 is hip, 25 and 26 is knee, 27 and 28 is ankle
        v1[0][0] /= (addSize/2.0);v1[0][1] /= (addSize/2.0);v1[1][0] /= (addSize/2.0);v1[1][1] /= (addSize/2.0);
        v2[0][0] /= (addSize/2.0);v2[0][1] /= (addSize/2.0);v2[1][0] /= (addSize/2.0);v2[1][1] /= (addSize/2.0);
        //v1 에 (x,y) 벡터값
        double dotProduct = v1[0][0] * v2[1][0] + v1[0][1] * v2[1][1];
        double v1Size = Math.sqrt(Math.pow(v1[0][0],2) + Math.pow(v1[0][1],2));
        double v2Size = Math.sqrt(Math.pow(v2[0][0],2) + Math.pow(v2[0][1],2));
        double angle_rad = Math.acos(dotProduct / (v1Size * v2Size));
        double angle_deg = Math.toDegrees(angle_rad);

        if (angle_deg >= 120 && angle_deg <= 180) { //각 해보면서 고치기
            humanPose.stand = true;
            if (sit) {
                humanPose.count += 1;
                Log.v("exrcise","UP!");
            }
            humanPose.sit = false;
        }
        else if (angle_deg >= 50 && angle_deg <= 110) {
            humanPose.stand = false;
            humanPose.sit = true;
            Log.v("exrcise","SIT!");
        }
    }
}
