package e.hp.redo;

import android.util.Log;

public class cars {
    public String getCarName() {
        return CarName;
    }

    public void setCarName(String carName) {
        CarName = carName;
    }

    public long getPerHead() {
        return PerHead;
    }

    public void setPerHead(long perHead) {
        PerHead = perHead;
    }

    public long getBasePrice() {
        return BasePrice;
    }

    public void setBasePrice(long basePrice) {
        BasePrice = basePrice;
    }

    public long getQuantity() {
        Log.e("Quantity:::",String.valueOf(Quantity));
        return Quantity;
    }

    public void setQuantity(long quantity) {
        Quantity = quantity;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getBookingStatus() {
        Log.e("BookingStatus:::",BookingStatus);
        return BookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        BookingStatus = bookingStatus;
    }

    private String CarName;
    private String Image;
    private String BookingStatus;
    private String ModelNumber;

    public void setKmsValue(String kmsValue) {
        this.kmsValue = kmsValue;
    }

    public void setChargesValue(String chargesValue) {
        this.chargesValue = chargesValue;
    }

    private String kmsValue;
    private String chargesValue;

    public String getModelNumber() {
        return ModelNumber;
    }

    public void setModelNumber(String modelNumber) {
        ModelNumber = modelNumber;
    }

    private long PerHead,BasePrice,Quantity;

    public cars(String carName, long perHead, long basePrice, long quantity, String image, String bookingStatus,String modelNumber) {
        CarName = carName;
        PerHead = perHead;
        BasePrice = basePrice;
        Quantity = quantity;
        Image = image;
        BookingStatus = bookingStatus;
        ModelNumber=modelNumber;
    }
    public cars(){}
}
