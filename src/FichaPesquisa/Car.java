package FichaPesquisa;

public class Car implements Comparable<Car> {
    private String model;
    private String manufacturer;
    private int year;
    private double price;

    public Car(String model, String manufacturer, int year, double price) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.year = year;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", year=" + year +
                ", price=" + price +
                '}';
    }

    // Implementing the compareTo method so binary search works based on the year
    @Override
    public int compareTo(Car otherCar) {
        return Integer.compare(this.year, otherCar.getYear());
    }
}