package com.example.carwashsimulatingapp;

import android.os.Parcel;
import android.os.Parcelable;

public class CarPlate implements Parcelable {
    private String carPlate, ticketNumber;
    private boolean hasPickedUp;

    public CarPlate(String carPlate, String ticketNumber, boolean hasPickedUp) {
        this.carPlate = carPlate;
        this.ticketNumber = ticketNumber;
        this.hasPickedUp = hasPickedUp;
    }

    protected CarPlate(Parcel in) {
        carPlate = in.readString();
        ticketNumber = in.readString();
        hasPickedUp = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(carPlate);
        dest.writeString(ticketNumber);
        dest.writeByte((byte) (hasPickedUp ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CarPlate> CREATOR = new Creator<CarPlate>() {
        @Override
        public CarPlate createFromParcel(Parcel in) {
            return new CarPlate(in);
        }

        @Override
        public CarPlate[] newArray(int size) {
            return new CarPlate[size];
        }
    };

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public boolean getHasPickedUp() {
        return hasPickedUp;
    }

    public void setHasPickedUp(boolean hasPickedUp) {
        this.hasPickedUp = hasPickedUp;
    }
}
