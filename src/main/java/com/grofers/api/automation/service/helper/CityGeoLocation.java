package com.grofers.api.automation.service.helper;

/***
 * @author Sonal Singh Chauhan
 */
public class CityGeoLocation {
    public Double latMin, latMax;
    public Double lngMin, lngMax;

    public CityGeoLocation(Double latMin, Double latMax, Double lngMin, Double lngMax ){
        this.latMin = latMin;
        this.latMax = latMax;
        this.lngMin = lngMin;
        this.lngMax = lngMax;
    }

    public boolean IsBelongsToThisCity(Double lat, Double lng){
        boolean isBelongToGrofersCity = false;
        if(lat > this.latMin && lat < this.latMax)
            if(lng > this.lngMin && lng < this.lngMax)
                isBelongToGrofersCity = true;
        return isBelongToGrofersCity;
    }
}
