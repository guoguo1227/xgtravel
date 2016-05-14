package com.stars.travel.aop;

public aspect LogAspect {
    before() : loggableCalls(){

    }
}
interface TimestampedObject {
    long getTimestamp();

    void timestamp();
}
//and
aspect Timestamp {
    private long TimestampedObject.timestamp;
    public long TimestampedObject.getTimestamp(){
        return timestamp;
    }

    public void TimestampedObject.timestamp(){
        this.timestamp = System.currentTimeMillis();
    }
}