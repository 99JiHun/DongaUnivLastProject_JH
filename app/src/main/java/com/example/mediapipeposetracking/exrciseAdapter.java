package com.example.mediapipeposetracking;

import android.util.Log;

import com.google.mediapipe.formats.proto.LandmarkProto;

public class exrciseAdapter {

    static private double sumAngle_deg = 0.0;
    static private final double judgeAng = 25.0;
    static private final double judgeAng3 = 40.0;
    static private final double judgeAng5 = 90.0;

    static private double sumAngle_deg2 = 0.0;

    final static double yVector[][] = {{0,1}};

    static double vSize(double[][] a){
        return Math.sqrt(Math.pow(a[0][0],2) + Math.pow(a[0][1],2));
    }
    static double dot(double[][] a, double[][] b) {
        return a[0][0] * b[0][0] + a[0][1] * b[0][1];
    }
    static double angle(double dot, double v1Size, double v2Size){
        return Math.toDegrees(Math.acos(dot / (v1Size * v2Size)));
    }
    static double predictSigdiff(double colAng, double curAng){
        double x = colAng - curAng;
        double k = 0.1; // 조절 가능한 상수
        double logisticFun = Math.pow(Math.E,-1 * k * x) / Math.pow((1 +Math.pow(Math.E, -1 * k * x)),2);
        double result = 4 * logisticFun;
        return Math.round(result * 100);
    }
    static double predictPose(double colAng,double curAng){
        double x = colAng - curAng > 0 ? colAng - curAng : (curAng - colAng);
        double k = 0.1; // 조절 가능한 상수
        double logisticFun = 1 / (1 +Math.pow(Math.E, -1 * k * x));
        double result = 1 - (2*(logisticFun - 0.5));
        return Math.round(result * 100);
    } //sigmoid 함수 이용해서 두 값의 차이가 0에 가까우면 0.5의 값이 나오니까 그 값을 빼고 그 값을 가지고 얼마나 가까운지 판단
    //방향성에 대한 것
    static int maxPre(double a,double b,double c){
        if(a >= b && a>=c)return 0;
        if(b >= a && b>=c)return 1;
        if(c >= a && c>=b)return 2;
        return -1;
    }
    static int predictDirection(double curY){
        double x = curY;
        double k = 0.1; // 조절 가능한 상수
        double logisticFun = (1 - Math.pow(Math.E, -1 * k * x)) / (1 +Math.pow(Math.E, -1 * k * x));
        // y is minus -1 , y is plus 1 , y is zero 0
        double predictDown = Math.abs(-1 - logisticFun);
        double predictStop = Math.abs(0 - logisticFun);
        double predictUp = Math.abs(1 - logisticFun);
        int maxIndex = maxPre(predictDown,predictStop,predictUp);
        return maxIndex;
    }
    static void setVector(LandmarkProto.NormalizedLandmarkList[] arr, double[][] v1, double[][] v2,int addSize){
        for(int i=0;i<addSize;i++){
            v1[0][0] += (arr[i].getLandmark(25).getX() - arr[i].getLandmark(23).getX());
            v1[0][1] += (arr[i].getLandmark(25).getY() - arr[i].getLandmark(23).getY());
            v2[0][0] += (arr[i].getLandmark(23).getX() - arr[i].getLandmark(11).getX());
            v2[0][1] += (arr[i].getLandmark(23).getY() - arr[i].getLandmark(11).getY());
            double ang1 = angle(dot(v1,yVector),vSize(v1),vSize(yVector));
            double ang2 = angle(dot(v2,yVector),vSize(v2),vSize(yVector));
            sumAngle_deg += ang1; //knee
            sumAngle_deg2 += ang2; //hip
        }//23 and 24 is hip, 25 and 26 is knee, 27 and 28 is ankle, 11 and 12 is shoulder
        //v1 에 (x,y) 벡터값
        sumAngle_deg /= addSize;
        sumAngle_deg2 /= addSize;
    }
    static void secPredictYvector(LandmarkProto.NormalizedLandmarkList[] arr, double[][] v1, int addSize){
        //일단 간단하게 코만, 다른 움직이는거 추가 해도 될듯
        v1[0][0] = 0;
        v1[0][1] = 0;
        double preData = arr[0].getLandmark(0).getY();
        for(int i=1;i<addSize;i++){
            v1[0][1] += (arr[i].getLandmark(0).getY() - preData);
            preData = arr[i].getLandmark(0).getY();
        }//23 and 24 is hip, 25 and 26 is knee, 27 and 28 is ankle, 11 and 12 is shoulder
    }
    public static void isStand(LandmarkProto.NormalizedLandmarkList[] arr, boolean sit){
        // Assuming landmark_coords is a 2D array of coordinates
        //스쿼트 갯수 판단
        int addSize = arr.length;
        double v1[][] = new double[1][2]; //knee and ankle
        double v2[][] = new double[1][2]; //hip and shoulder
        double directVector[][] = new double[1][2];
        setVector(arr,v1,v2,addSize);
        secPredictYvector(arr,directVector,addSize);
        int dirIndex = predictDirection(directVector[0][1]);
        if(dirIndex == 0){
            Log.v("dir","down");
        }
        else if(dirIndex == 1){
            Log.v("dir","stop");
        }
        else{
            if(dirIndex == -1){
                Log.v("dir","err");
            }
            Log.v("dir","up");
        }
        Log.v("exercise",String.valueOf(sumAngle_deg));
        Log.v("exercise",String.valueOf(sumAngle_deg2));
        double standPercent2 = predictPose(judgeAng,sumAngle_deg);
        double sitPercent5 = predictPose(judgeAng5,sumAngle_deg);
        double standPercent2_2 = predictSigdiff(judgeAng,sumAngle_deg);
        double sitPercent5_2 = predictSigdiff(judgeAng5,sumAngle_deg);


        //25 and 90
        String sen = "knee angle is " + String.valueOf(Math.round(sumAngle_deg)) + " " + "judge is 25 " + "sig is" + String.valueOf(standPercent2) + " "+ "sigdif is " + String.valueOf(standPercent2_2);
        String sen2 = "hip angle is " + String.valueOf(Math.round(sumAngle_deg)) + " " + "judge is 90 " +"sig is" + String.valueOf(sitPercent5) + " "+ "sigdif is " + String.valueOf(sitPercent5_2);

        Log.v("sig",sen);
        Log.v("sig",sen2);

        if ((sumAngle_deg2 >= 0 && sumAngle_deg >= 0 && (80 <= standPercent2_2 || judgeAng > sumAngle_deg)) || humanPose.stand) {
            //엉덩이 각도와 무릎 각도가 0보다 크고, 무릎 각도가 25도와 70수치 이상 가깝거나 무릎 각도가 25도 보다 작을때 혹은 이미 서있는 상태라고
            //정의 되어 있다면 stand 상태
            humanPose.stand = true;
            Log.v("exercise","stand");
            if (humanPose.sit) {
                humanPose.count++;
            }
            humanPose.sit = false;
            humanPose.bad = false;
        }
        if (((25 <= sumAngle_deg2 ) && (50 <= sumAngle_deg && (80 <= sitPercent5_2 || judgeAng5 < sumAngle_deg))) || humanPose.sit) {
            //엉덩이 각도가 25도 보다 크고 무릎 각도가 50보다 크고 무릎 각도가 90과 70수치 정도 가까이 있거나 90도 보다 큰 각일때 혹은
            //이미 앉아 있는 상태라면 sit 상태
            humanPose.sit = true;
            humanPose.stand = false;
            Log.v("exercise","sit");
            if (humanPose.sit && sumAngle_deg2 > 40) {
                Log.v("exercise","bad");
                humanPose.bad = true;
            }
        }
    }
}
