package com.example.travelplanner.model;

public class Hotel {
    private String propertyId;
    private String propertyName;
    private String city;
    private String area;
    private String address;
    private String roomType;
    private String hotelStarRating;
    private String hotelDescription;
    private String siteStayReviewRating;
    private String hotelFacilities;
    private double price;

    // Getters and Setters
    public String getPropertyId() { return propertyId; }
    public void setPropertyId(String propertyId) { this.propertyId = propertyId; }

    public String getPropertyName() { return propertyName; }
    public void setPropertyName(String propertyName) { this.propertyName = propertyName; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public String getHotelStarRating() { return hotelStarRating; }
    public void setHotelStarRating(String hotelStarRating) { this.hotelStarRating = hotelStarRating; }

    public String getHotelDescription() { return hotelDescription; }
    public void setHotelDescription(String hotelDescription) { this.hotelDescription = hotelDescription; }

    public String getSiteStayReviewRating() { return siteStayReviewRating; }
    public void setSiteStayReviewRating(String siteStayReviewRating) { this.siteStayReviewRating = siteStayReviewRating; }

    public String getHotelFacilities() { return hotelFacilities; }
    public void setHotelFacilities(String hotelFacilities) { this.hotelFacilities = hotelFacilities; }

    public double getPrice() { return price; } // 👈 Getter
    public void setPrice(double price) { this.price = price; } // 👈 Setter
}
