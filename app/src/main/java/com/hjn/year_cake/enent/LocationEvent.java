package com.hjn.year_cake.enent;

import com.amap.api.location.AMapLocation;

/**
 * Created by YearCake on 2018/9/21.
 * description:
 * version v1.0 content:
 */

public class LocationEvent {
    private AMapLocation aMapLocation;
    public LocationEvent(AMapLocation location) {
        this.aMapLocation = location;
    }

    public AMapLocation getaMapLocation() {
        return aMapLocation;
    }

    public void setaMapLocation(AMapLocation aMapLocation) {
        this.aMapLocation = aMapLocation;
    }
}
