package nvd.hasan.profilemanager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service  implements SensorEventListener {

    private SensorManager sensorManager = null;
    private Sensor lightSensor = null;
    private Sensor accelerometerSensor = null;
    private Sensor proxyimitySensor = null;

    static float lightSensorValue = 0;
    static float accelerometerSensorValueX = 0;
    static float accelerometerSensorValueY = 0;
    static float accelerometerSensorValueZ = 0;
    static float proximitySensorvalue = 0;

    AudioManager audioManager;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proxyimitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorInitialize();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void sensorInitialize(){
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, proxyimitySensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

//        float lightSensorValue = 0;
//        float accelerometerSensorValueX = 0;
//        float accelerometerSensorValueY = 0;
//        float accelerometerSensorValueZ = 0;
//        float proximitySensorvalue = 0;


        if( event.sensor.getType() == Sensor.TYPE_LIGHT)
        {
            lightSensorValue = event.values[0];
        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accelerometerSensorValueX = event.values[0];
            accelerometerSensorValueY = event.values[1];
            accelerometerSensorValueZ = event.values[2];
        }
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            proximitySensorvalue = event.values[0];
        }
        if (accelerometerSensorValueX == 0 && accelerometerSensorValueY == 0 && accelerometerSensorValueZ == 0 && lightSensorValue == 0){
            return;
        }
        else {
            changeProfile(lightSensorValue, accelerometerSensorValueX, accelerometerSensorValueY, accelerometerSensorValueZ, proximitySensorvalue);
        }

    }

    public void changeProfile(float light, float x, float y, float z, float proximity){
        Log.d("light", String.valueOf(light));
        if (light <= 3){
            Log.d("light1", String.valueOf(light));
            if ( x >=3 && z > 4){
                maxVolume();
            }
            else if (x < -1 && z > 4){
                maxVolume();
            }
            else if (z < 0 && proximity <= 1 ){
                silentVolume();
            }
            else {
                normalVolume();
            }
        }
        else{
            normalVolume();
        }
//        if (z > 0 && light > 0 && proximity > 0){
//            normalVolume();
//        }
//        else if (z < 0 && proximity == 0 && light <= 3){
//            silentVolume();
//        }
//        else {
//            maxVolume();
//        }
    }

    public void silentVolume(){
        audioManager.setStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, 1);
    }
    public void normalVolume(){
        audioManager.setStreamVolume(AudioManager.STREAM_RING, (audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)/2),1);
    }
    public void maxVolume(){
        audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMaxVolume(AudioManager.STREAM_RING), 1);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
